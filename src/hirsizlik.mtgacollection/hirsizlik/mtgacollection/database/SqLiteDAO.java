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
import java.util.Optional;

import hirsizlik.mtgacollection.bo.CardAmount;
import hirsizlik.mtgacollection.bo.CardInfo;
import hirsizlik.mtgacollection.bo.Rarity;
import hirsizlik.mtgacollection.bo.SetInfo;
import hirsizlik.mtgacollection.scryfall.ScryfallSetInfo;

/**
 * Manages the access to the SQLite-Database, which is used to store all known
 * cards and sets.
 *
 * @author Markus Schagerl
 */
public class SqLiteDAO implements AutoCloseable{

	private final Connection c;

	private PreparedStatement psInsertNewInfo;
	private PreparedStatement psGetCardInfo;
	private PreparedStatement psInsertNewSet;

	/**
	 * Constructs a new instance, also opening a connection to the database and preparing some statements.
	 * @param toDB the path to the database
	 * @throws ClassNotFoundException thrown by Class.forName, should never be thrown
	 * @throws SQLException thrown by DriverManager.getConnection (probably database not found)
	 * and Connection#prepareStatement (some SQL error, should never happen).
	 */
	public SqLiteDAO(final Path toDB) throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:" + toDB.toAbsolutePath().toString());
		c.setAutoCommit(false);
	}

	/**
	 * Creates the setInfo and the cardInfo tables. Should only be used for new databases.
	 * The unknown set is added to the created setInfo table
	 * @throws SQLException SQL error (should never happen)
	 */
	public void createTables() throws SQLException {
		String setInfoSql = """
				CREATE TABLE "setInfo" (
				"code"            TEXT PRIMARY KEY NOT NULL,
				"name"            TEXT NOT NULL,
				"releaseDate"     TEXT NOT NULL CHECK(length(releaseDate)=10 and substr(releaseDate, 5, 1) = '-'
				    and substr(releaseDate, 8, 1) = '-'),
				"isSupplemental"  TEXT CHECK (isSupplemental in ('Y', 'N')) not null default 'N'
				)
				""";
		String cardInfoSql = """
				CREATE TABLE "cardInfo" (
				"id"         INTEGER PRIMARY KEY NOT NULL,
				"name"       TEXT NOT NULL DEFAULT 'Unknown',
				"rarity"     TEXT CHECK( rarity IN ('?', 'L', 'C', 'U', 'R', 'M') ) NOT NULL DEFAULT '?',
				"setCode"    TEXT NOT NULL DEFAULT '?',
				"inBooster"  TEXT CHECK (inBooster IN ('Y', 'N') ) NOT NULL DEFAULT 'Y')
				""";
		String addUnknownSet = """
				INSERT INTO setInfo (code, name, releaseDate, isSupplemental)
				VALUES ("?", "Unknown", "1970-01-01", "N")
				""";
		try(Statement statement = c.createStatement()){
			statement.execute(setInfoSql);
			statement.execute(cardInfoSql);
			statement.execute(addUnknownSet);
		}
		c.commit();
	}

	private void addCardToBatch(final CardInfo card) throws SQLException {
		if(psInsertNewInfo == null) {
			psInsertNewInfo = c.prepareStatement("INSERT INTO cardInfo (id, name, rarity, setCode, inBooster) VALUES " +
					"(?, ?, ?, ?, ?)");
		}
		psInsertNewInfo.setLong(1, card.id()); // index starts with 1, not 0
		psInsertNewInfo.setString(2, card.name());
		psInsertNewInfo.setString(3, card.rarity().getDbCode());
		psInsertNewInfo.setString(4, card.set().code());
		psInsertNewInfo.setString(5, card.inBooster() ? "Y" : "N");
		psInsertNewInfo.addBatch();
	}

	/**
	 * Adds a new card to the database if no card with the same id is found.
	 * Actually adding the card is delayed until {@link #executeBatchedCards()} is called.
	 * @param cardInfo the card to be added
	 * @param setInfoLoader needed for the set of the card
	 * @return true if a card was added, otherwise false
	 * @throws SQLException a SQL error
	 */
	public boolean addCardIfNewBatched(final CardInfo cardInfo, final SetInfoLoader setInfoLoader) throws SQLException {
		Optional<CardInfo> ocFromDb = getCard(cardInfo.id(), setInfoLoader);
		if(ocFromDb.isEmpty()) {
			addCardToBatch(cardInfo);
			return true;
		}

		return false;
	}

	/**
	 * Executes the inserts from {@link #addCardIfNewBatched(CardInfo)}
	 * @throws SQLException
	 */
	public void executeBatchedCards() throws SQLException {
		psInsertNewInfo.executeBatch();
		c.commit();
	}

	/**
	 * Loads a card from the database
	 * @param id the id of the card
	 * @param setInfoLoader to load the corresponding set of the card
	 * @return the card packed in a Optional, empty if no card was found
	 * @throws SQLException a SQL error
	 */
	public Optional<CardInfo> getCard(final int id, final SetInfoLoader setInfoLoader) throws SQLException {
		if(psGetCardInfo == null) {
			psGetCardInfo = c.prepareStatement("SELECT c.id, c.name, c.rarity, c.setCode, c.inBooster "
					+ "FROM CardInfo c WHERE c.id = ?");
		}
		psGetCardInfo.setLong(1, id);
		psGetCardInfo.execute();
		ResultSet rs = psGetCardInfo.getResultSet();
		if(rs == null || !rs.next())
			return Optional.empty();

		return Optional.of(new CardInfo(rs.getInt(1),
				rs.getString(2),
				Rarity.valueByCode(rs.getString(3)),
				setInfoLoader.getByCode(rs.getString(4)),
				rs.getString(5).equals("Y")));// should always be "Y" or "N"
	}

	/**
	 * Loads all sets and returns them as a map.
	 * @return all sets as a map, using the setCode as key.
	 * @throws SQLException a SQL error
	 */
	public Map<String, SetInfo> getSetMap() throws SQLException {
		String sql = """
				SELECT s.code, s.name,
				(Select count(c.id) from CardInfo c where c.Rarity = 'C' and c.setCode = s.code and c.inBooster = 'Y') as common,
				(Select count(c.id) from CardInfo c where c.Rarity = 'U' and c.setCode = s.code and c.inBooster = 'Y') as uncommon,
				(Select count(c.id) from CardInfo c where c.Rarity = 'R' and c.setCode = s.code and c.inBooster = 'Y') as rare,
				(Select count(c.id) from CardInfo c where c.Rarity = 'M' and c.setCode = s.code and c.inBooster = 'Y') as mythic,
				s.releaseDate, s.isSupplemental from SetInfo s
				""";
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
		}
	}

	/**
	 * Adds a new set to the database using the data provided by Scryfall to a batch.
	 * @param setInfo the Scryfall set data used
	 * @throws SQLException a SQL error
	 * @see #executeBatchedSets()
	 */
	public void addSetFromScryfallBatch(final ScryfallSetInfo setInfo) throws SQLException {
		if(psInsertNewSet == null) {
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
	}

	/**
	 * Executes the inserts from {@link #addSetFromScryfallBatch(ScryfallSetInfo)}, then commits.
	 * @throws SQLException
	 */
	public void executeBatchedSets() throws SQLException {
		psInsertNewSet.executeBatch();
		c.commit();
	}

	/**
	 * Closes the database connection.
	 * @see Connection#close()
	 */
	@Override
	public void close() throws SQLException {
		c.close();
	}
}
