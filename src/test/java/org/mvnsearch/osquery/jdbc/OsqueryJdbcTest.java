package org.mvnsearch.osquery.jdbc;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * osquery jdbc test
 *
 * @author linux_china
 */
public class OsqueryJdbcTest {
    private static OsqueryConnection connection;

    @BeforeAll
    public static void setUp() throws Exception {
        DriverManager.registerDriver(new OsqueryJdbcDriver());
        connection = (OsqueryConnection) DriverManager.getConnection("jdbc:osquery:local");
    }

    @AfterAll
    public static void tearDown() throws Exception {
        connection.close();
    }

    @Test
    public void testMeta() throws Exception {
        DatabaseMetaData metaData = connection.getMetaData();
        System.out.println(metaData.getDriverVersion());
    }

    public void testQuery() throws Exception {
        String SQL = "SELECT * FROM users";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL);
        while (resultSet.next()) {
            String username = resultSet.getString("username");
            System.out.println(username);
        }
        resultSet.close();
    }
}
