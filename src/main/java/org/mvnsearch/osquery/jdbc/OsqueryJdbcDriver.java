package org.mvnsearch.osquery.jdbc;

import org.mvnsearch.osquery.OsqueryProcess;

import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * osquery jdbc driver
 *
 * @author linux_china
 */
public class OsqueryJdbcDriver implements java.sql.Driver {
    private OsqueryProcess osquery = new OsqueryProcess();
    public static String[] VERSION_PARTS;
    public static String VERSION;

    public OsqueryJdbcDriver() {
        VERSION = osquery.getVersion();
        VERSION_PARTS = VERSION.split("\\.");
    }

    public Connection connect(String url, Properties info) throws SQLException {
        return new OsqueryConnection(url);
    }

    public boolean acceptsURL(String url) throws SQLException {
        return url.startsWith("jdbc:osquery:");
    }

    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    public int getMajorVersion() {
        return Integer.valueOf(VERSION_PARTS[1]);
    }

    public int getMinorVersion() {
        return Integer.valueOf(VERSION_PARTS[2]);
    }

    public boolean jdbcCompliant() {
        return true;
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }
}
