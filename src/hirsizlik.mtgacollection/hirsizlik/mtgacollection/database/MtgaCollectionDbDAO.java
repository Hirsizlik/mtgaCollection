package hirsizlik.mtgacollection.database;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.sqlite.SQLiteConfig;

import hirsizlik.mtgacollection.bo.CardAmount;
import hirsizlik.mtgacollection.bo.CardInfo;
import hirsizlik.mtgacollection.bo.Rarity;
import hirsizlik.mtgacollection.bo.SetInfo;
import hirsizlik.mtgacollection.scryfall.ScryfallSetInfo;

/**
 * Manages access to the local database to store all the cards with names and the sets.
 *
 * @author Markus Schagerl
 */
public class MtgaCollectionDbDAO implements AutoCloseable{

	private final Connection c;

	private PreparedStatement psInsertNewInfo;
	private PreparedStatement psGetCardInfo;
	private PreparedStatement psInsertNewSet;

	/**
	 * Constructs a new instance and opening a connection to the database.
	 * @param toDB the path to the database
	 * @param readOnly if the database should be opened read only
	 * @throws IllegalStateException wrapped ClassNotFoundException or SQLException, neither should ever happen
	 */
	public MtgaCollectionDbDAO(final Path toDB, final boolean readOnly) {
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			config.setReadOnly(readOnly);
			config.enforceForeignKeys(true);
			c = DriverManager.getConnection("jdbc:sqlite:" + toDB.toAbsolutePath().toString(), config.toProperties());
			c.setAutoCommit(false);
		} catch (ClassNotFoundException | SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Creates the setInfo and the cardInfo tables. Should only be used for new databases.
	 * @throws IllegalStateException SQL error (should never happen)
	 */
	public void createTables() {
		String setInfoSql = """
				CREATE TABLE "setInfo" (
				"code"            TEXT PRIMARY KEY NOT NULL,
				"name"            TEXT NOT NULL,
				"releaseDate"     TEXT NOT NULL CHECK(length(releaseDate)=10 and substr(releaseDate, 5, 1) = '-'
				    and substr(releaseDate, 8, 1) = '-'),
				"isSupplemental"  TEXT CHECK (isSupplemental in ('Y', 'N')) not null
				)
				""";
		String cardInfoSql = """
				CREATE TABLE "cardInfo" (
				"id"         INTEGER PRIMARY KEY NOT NULL,
				"name"       TEXT NOT NULL,
				"rarity"     TEXT CHECK( rarity IN ('L', 'C', 'U', 'R', 'M') ) NOT NULL,
				"setCode"    TEXT NOT NULL,
				"inBooster"  TEXT CHECK (inBooster IN ('Y', 'N') ) NOT NULL)
				""";
		try (Statement statement = c.createStatement()) {
			statement.execute(setInfoSql);
			statement.execute(cardInfoSql);
			c.commit();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Adds a new card to the database.
	 * Actually adding the card is delayed until {@link #executeBatchedCards()} is called.
	 * @param cardInfo the card to be added
	 * @return true if a card was added, otherwise false
	 * @throws IllegalStateException a SQL error
	 */
	public void addCardBatched(final CardInfo cardInfo) {
		try {
			if (psInsertNewInfo == null) {
				psInsertNewInfo = c.prepareStatement(
						"INSERT INTO cardInfo (id, name, rarity, setCode, inBooster) VALUES " +
						"(?, ?, ?, ?, ?)");
			}
			psInsertNewInfo.setLong(1, cardInfo.id()); // index starts with 1, not 0
			psInsertNewInfo.setString(2, cardInfo.name());
			psInsertNewInfo.setString(3, cardInfo.rarity().getDbCode());
			psInsertNewInfo.setString(4, cardInfo.set().code());
			psInsertNewInfo.setString(5, cardInfo.inBooster() ? "Y" : "N");
			psInsertNewInfo.addBatch();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Executes the inserts from {@link #addCardBatched(CardInfo)}
	 * @throws IllegalStateException SQL error
	 */
	public void executeBatchedCards() {
		try {
			psInsertNewInfo.executeBatch();
			c.commit();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Loads a card from the database
	 * @param id the id of the card
	 * @param setInfoLoader to load the corresponding set of the card
	 * @return the card
	 * @throws IllegalStateException a SQL error
	 * @throws NullPointerException if no card was found
	 */
	public CardInfo getCard(final int id, final SetInfoLoader setInfoLoader) {
		try {
			if (psGetCardInfo == null) {
				psGetCardInfo = c.prepareStatement("SELECT c.id, c.name, c.rarity, c.setCode, c.inBooster "
						+ "FROM CardInfo c WHERE c.id = ?");
			}
			psGetCardInfo.setLong(1, id);
			psGetCardInfo.execute();
			ResultSet rs = psGetCardInfo.getResultSet();
			if (rs == null || !rs.next())
				throw new NullPointerException("No card with id %d found".formatted(id));

			return new CardInfo(rs.getInt(1),
					rs.getString(2),
					Rarity.valueByCode(rs.getString(3)),
					setInfoLoader.getByCode(rs.getString(4)),
					rs.getString(5).equals("Y"));// should always be "Y" or "N"
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}

	}

	/**
	 * Loads all sets and returns them as a map. No cards are excluded for card totals.
	 * @return all sets as a map, using the setCode as key.
	 * @throws SQLException a SQL error
	 */
	public Map<String, SetInfo> getSetMap() {
		return getSetMap(false, false);
	}

	/**
	 * Loads all sets and returns them as a map.
	 * @param excludeInBooster exclude cards which aren't in boosters for card totals
	 * @param excludeRebalanced exclude rebalanced cards for card totals
	 * @return all sets as a map, using the setCode as key.
	 * @throws IllegalStateException a SQL error
	 */
	public Map<String, SetInfo> getSetMap(final boolean excludeInBooster, final boolean excludeRebalanced) {
		String inBooster = "and c.inBooster = 'Y'";
		// TODO extends database for rebalanced cards instead of using the name
		String rebalanced = "and SUBSTRING(c.name, 1, 2) != 'A-'";
		String sql = """
				SELECT s.code, s.name,
				(Select count(c.id) from CardInfo c where c.Rarity = 'C' and c.setCode = s.code %1$s %2$s) as common,
				(Select count(c.id) from CardInfo c where c.Rarity = 'U' and c.setCode = s.code %1$s %2$s) as uncommon,
				(Select count(c.id) from CardInfo c where c.Rarity = 'R' and c.setCode = s.code %1$s %2$s) as rare,
				(Select count(c.id) from CardInfo c where c.Rarity = 'M' and c.setCode = s.code %1$s %2$s) as mythic,
				s.releaseDate, s.isSupplemental from SetInfo s
				""".formatted(excludeInBooster ? inBooster : "", excludeRebalanced ? rebalanced : "");
		try (Statement s = c.createStatement()) {
			s.execute(sql);
			ResultSet rs = Objects.requireNonNull(s.getResultSet());
			Map<String, SetInfo> setInfoMap = new HashMap<>();
			while (rs.next()) {
				String code = rs.getString(1);
				LocalDate release = LocalDate.parse(rs.getString(7));
				boolean isSupplemental = "Y".equals(rs.getString(8));
				CardAmount cardAmount = new CardAmount(rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
				setInfoMap.put(code, new SetInfo(code, rs.getString(2), cardAmount, release, isSupplemental));
			}

			return setInfoMap;
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Adds a new set to the database using the data provided by Scryfall to a batch.
	 * @param setInfo the Scryfall set data used
	 * @throws IllegalStateException a SQL error
	 * @see #executeBatchedSets()
	 */
	public void addSetFromScryfallBatch(final ScryfallSetInfo setInfo) {
		try {
			if (psInsertNewSet == null) {
				psInsertNewSet = c.prepareStatement("""
						INSERT INTO setInfo (code, name, releaseDate, isSupplemental)
						VALUES (?, ?, ?, ?)
						""");
			}
			psInsertNewSet.setString(1, setInfo.code());
			psInsertNewSet.setString(2, setInfo.name());
			psInsertNewSet.setString(3, setInfo.releasedAt().toString());
			psInsertNewSet.setString(4, setInfo.type().isSupplemental() ? "Y" : "N");

			psInsertNewSet.addBatch();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Executes the inserts from {@link #addSetFromScryfallBatch(ScryfallSetInfo)}, then commits.
	 * @throws IllegalStateException a SQL error
	 */
	public void executeBatchedSets() {
		try {
			psInsertNewSet.executeBatch();
			c.commit();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Closes the database connection.
	 * @see Connection#close()
	 * @throws IllegalStateException SQL error on closing
	 */
	@Override
	public void close() {
		try {
			c.close();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}
}
