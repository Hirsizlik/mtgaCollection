package hirsizlik.mtgacollection.database;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Access to Raw_CardDatabase in MTGA to load localized names of cards.
 *
 * @author Markus Schagerl
 */
public class RawCardDatabaseDAO implements AutoCloseable {

	private final Connection c;
	private PreparedStatement readLocalizationStatement;

	/**
	 * Constructs a new instance and opening a read-only connection to the database
	 * @param toDB the path to the database
	 * @throws IllegalStateException wrapped ClassNotFoundException or SQLException, neither should ever happen
	 */
	public RawCardDatabaseDAO(final Path toDB) {
		try {
			Class.forName("org.sqlite.JDBC");
			Properties p = new Properties();
			p.setProperty("open_mode", "1");// "1" == read only
			c = DriverManager.getConnection("jdbc:sqlite:" + toDB.toAbsolutePath().toString(), p);
			c.setReadOnly(true);
		} catch (ClassNotFoundException | SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Returns the English localization for the given id.
	 * @param localizationId the id
	 * @return the English, non-formatted value
	 * @throws IllegalStateException SQL error, for example if no localization with that id is found
	 */
	public String getEnglishNonFormattedLocalization(final int localizationId) {
		try {
			if (readLocalizationStatement == null) {
				readLocalizationStatement = c.prepareStatement(
						"SELECT l.Formatted, l.enUs from Localizations l where l.LocId = ?");
			}

			readLocalizationStatement.setLong(1, localizationId);
			readLocalizationStatement.execute();

			ResultSet rs = readLocalizationStatement.getResultSet();
			record LocResult(boolean formatted, String enUs) {}

			List<LocResult> results = new ArrayList<>();
			while (rs.next()) {
				boolean formatted = rs.getInt(1) == 1;
				String enUs = rs.getString(2);
				results.add(new LocResult(formatted, enUs));
			}

			// there should be either no, one or two results
			// zero -> no result found, throw a SQLException
			// one -> a result with formatted = 1 (but doesn't use any formatting)
			// two -> one result with HTML-Tags, one without, use the one without
			if (results.isEmpty()) {
				throw new SQLException("No Localization with LocId %d found".formatted(localizationId));
			} else if (results.size() == 1) {
				return results.get(0).enUs;
			} else {
				return results.stream()
						.filter(lr -> !lr.formatted)
						.map(lr -> lr.enUs)
						.findFirst()
						.orElseThrow(() -> new IllegalStateException("Multiple Localizations for "
								+ "%d found, but none of them are not formatted".formatted(localizationId)));
			}
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void close() {
		try {
			c.close();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}
}
