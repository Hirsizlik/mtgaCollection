import org.apache.logging.log4j.spi.Provider;

/**
 * The module-info of this program.
 * This module is not intended to be used by other modules.
 * The one exports here is only because a warning says so.
 *
 * @author Markus Schagerl
 * @provides Provider, providing a log4j implementation
 */
module hirsizlik.mtgacollection {
	requires java.sql;
	requires java.net.http;
	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.databind;
    requires org.xerial.sqlitejdbc;
	requires transitive org.apache.logging.log4j;
	requires jdk.crypto.ec; // needed to access scryfall over HTTPS, if built with jlink / jpackage

    opens hirsizlik.mtgacollection.jackson.playercards to com.fasterxml.jackson.databind;
	opens hirsizlik.mtgacollection.jackson.inventory to com.fasterxml.jackson.databind;
	opens hirsizlik.mtgacollection.jackson.mtga.card to com.fasterxml.jackson.databind;
	opens hirsizlik.mtgacollection.jackson.mtga.localisation to com.fasterxml.jackson.databind;
	opens hirsizlik.mtgacollection.jackson.scryfall.set to com.fasterxml.jackson.databind;

	exports hirsizlik.mtgacollection.log4j.logger to org.apache.logging.log4j;
	provides Provider with hirsizlik.mtgacollection.log4j.logger.MtgaCollectionLoggerProvider;
}