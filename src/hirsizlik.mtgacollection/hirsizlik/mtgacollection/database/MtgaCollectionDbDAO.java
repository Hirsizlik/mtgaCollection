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
import hirsizlik.mtgacollection.bo.SetType;
import hirsizlik.mtgacollection.main.MtgaFiles;
import hirsizlik.mtgacollection.scryfall.ScryfallSetInfo;

/**
 * Manages access to the local database to store all the cards with names and the sets.
 *
 * @author Markus Schagerl
 */
public class MtgaCollectionDbDAO implements AutoCloseable {

	private static final String DATABASE_VERSION_STRING = "1";

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
	 * Creates the database tables. Also adds the database version and the given hashes.
	 * <p>
	 * Some notes:
	 * <ul>
	 * <li>releaseDate checks if the Text depicts a Date in ISO 8601 format
	 * <li>type is an enumeration with the following values: <b>P</b>remier, (Re-)<b>M</b>aster,
	 * Alchem<b>Y</b>, <b>O</b>ther
	 * <li>rarity is an enumeration with the following values: <b>L</b>and, <b>C</b>ommon, <b>U</b>ncommon,
	 * <b>R</b>are, <b>M</b>ythic
	 * <li>setCode is the set code used in arena, and is a foreign key to setInfo
	 * <li>digitalSetCode is the set code Arena uses for certain digital sets,
	 * e.g. Historic Anthologies and Alchemy sets. May be empty (true for most cards) and is a foreign key to setInfo
	 * <li>metadata holds misc. data as key-value pairs. Used for database version and the hashes from the MTGA files
	 * </ul>
	 * @throws IllegalStateException SQL error (should never happen)
	 */
	public void createTables() {
		String createSetInfo = """
				CREATE TABLE "setInfo" (
				"code"            TEXT PRIMARY KEY NOT NULL,
				"name"            TEXT NOT NULL,
				"releaseDate"     TEXT NOT NULL CHECK(length(releaseDate)=10 and SUBSTR(releaseDate, 5, 1) = '-'
				    and SUBSTR(releaseDate, 8, 1) = '-'),
				"type"  TEXT CHECK (type in ('P', 'M', 'Y', 'O')) NOT NULL
				)
				""";
		String createCardInfo = """
				CREATE TABLE "cardInfo" (
				"id"             INTEGER PRIMARY KEY NOT NULL,
				"name"           TEXT NOT NULL,
				"rarity"         TEXT CHECK( rarity IN ('L', 'C', 'U', 'R', 'M') ) NOT NULL,
				"setCode"        TEXT NOT NULL REFERENCES setInfo(code),
				"inBooster"      TEXT CHECK (inBooster IN ('Y', 'N') ) NOT NULL,
				"digitalSetCode" TEXT REFERENCES setInfo(code),
				"rebalanced"     TEXT CHECK (rebalanced IN ('Y', 'N') ) NOT NULL)
				""";
		String createMetadata = """
				CREATE TABLE "metadata" (
				"key"   TEXT PRIMARY KEY NOT NULL,
				"value" TEXT NOT NULL)
				""";
		String addVersion = """
				INSERT INTO metadata (key, value)
				VALUES
				("version", "%s")
				""".formatted(DATABASE_VERSION_STRING);

		try (Statement statement = c.createStatement()) {
			statement.execute(createSetInfo);
			statement.execute(createCardInfo);
			statement.execute(createMetadata);
			statement.execute(addVersion);
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
						"INSERT INTO cardInfo (id, name, rarity, setCode, inBooster, digitalSetCode, rebalanced) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?)");
			}
			psInsertNewInfo.setLong(1, cardInfo.id()); // index starts with 1, not 0
			psInsertNewInfo.setString(2, cardInfo.name());
			psInsertNewInfo.setString(3, cardInfo.rarity().getDbCode());
			psInsertNewInfo.setString(4, cardInfo.set().code());
			psInsertNewInfo.setString(5, cardInfo.inBooster() ? "Y" : "N");
			psInsertNewInfo.setString(6, cardInfo.digitalSet().map(SetInfo::code).orElse(null));
			psInsertNewInfo.setString(7, cardInfo.rebalanced() ? "Y" : "N");
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
				psGetCardInfo = c.prepareStatement("SELECT c.id, c.name, c.rarity, c.setCode, "
						+ "c.inBooster, c.digitalSetCode, c.rebalanced "
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
					rs.getString(5).equals("Y"), // should always be "Y" or "N"
					setInfoLoader.getByCodeNullFriendly(rs.getString(6)),
					rs.getString(7).equals("Y"));
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
		String rebalanced = "and c.rebalanced = 'N'";
		String sql = """
				SELECT s.code, s.name,
				(Select count(c.id) from CardInfo c where c.Rarity = 'C'
				    and s.code in (c.setCode, c.digitalSetCode) %1$s %2$s) as common,
				(Select count(c.id) from CardInfo c where c.Rarity = 'U'
				    and s.code in (c.setCode, c.digitalSetCode) %1$s %2$s) as uncommon,
				(Select count(c.id) from CardInfo c where c.Rarity = 'R'
				    and s.code in (c.setCode, c.digitalSetCode) %1$s %2$s) as rare,
				(Select count(c.id) from CardInfo c where c.Rarity = 'M'
				    and s.code in (c.setCode, c.digitalSetCode) %1$s %2$s) as mythic,
				s.releaseDate, s.type from SetInfo s
				""".formatted(excludeInBooster ? inBooster : "", excludeRebalanced ? rebalanced : "");
		try (Statement s = c.createStatement()) {
			s.execute(sql);
			ResultSet rs = Objects.requireNonNull(s.getResultSet());
			Map<String, SetInfo> setInfoMap = new HashMap<>();
			while (rs.next()) {
				String code = rs.getString(1);
				LocalDate release = LocalDate.parse(rs.getString(7));
				SetType setType = SetType.valueByCode(rs.getString(8));
				CardAmount cardAmount = new CardAmount(rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
				setInfoMap.put(code, new SetInfo(code, rs.getString(2), cardAmount, release, setType));
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
						INSERT INTO setInfo (code, name, releaseDate, type)
						VALUES (?, ?, ?, ?)
						""");
			}
			psInsertNewSet.setString(1, setInfo.code());
			psInsertNewSet.setString(2, setInfo.name());
			psInsertNewSet.setString(3, setInfo.releasedAt().toString());
			psInsertNewSet.setString(4, setInfo.type().getInternalSetType().getDbCode());

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
	 * Checks if the database and the MTGA files are the expected values.
	 * @param mtgaFiles the MTGA Files
	 * @return true if the values are as expected, otherwise false (including errors)
	 */
	public boolean checkVersionAndHashes(final MtgaFiles mtgaFiles) {
		String sql = "SELECT md.key, md.value from Metadata md";
		try (Statement s = c.createStatement()) {
			s.execute(sql);
			ResultSet rs = s.getResultSet();
			Map<String, String> resultMap = new HashMap<>();
			while (rs.next()) {
				resultMap.put(rs.getString(1), rs.getString(2));
			}
			return DATABASE_VERSION_STRING.equals(resultMap.get("version"))
					&& mtgaFiles.getCardDatabaseHash().equals(resultMap.get("cardDatabase"))
					&& mtgaFiles.getCardsHash().equals(resultMap.get("cards"));
		} catch (SQLException e) {
			// probably a old version without metadata, in which case just consider the check failed
			return false;
		}
	}

	/**
	 * Inserts the hashes of the read MTGA Files into the database. Those are checked at startup to see
	 * if the files got changed.
	 * @throws IllegalStateException if an SQL error occurred
	 */
	public void setHashes(final MtgaFiles mtgaFiles) {
		String sql = """
				INSERT INTO metadata (key, value) VALUES
				("cardDatabase", "%s"), ("cards", "%s")
				""".formatted(mtgaFiles.getCardDatabaseHash(), mtgaFiles.getCardsHash());
		try (Statement s = c.createStatement()) {
			s.execute(sql);
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
