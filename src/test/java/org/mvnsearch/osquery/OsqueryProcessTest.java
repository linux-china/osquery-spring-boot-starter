package org.mvnsearch.osquery;

import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * osquery process test
 *
 * @author linux_china
 */
public class OsqueryProcessTest {
    private OsqueryProcess osquery = new OsqueryProcess();

    @Test
    public void testGetVersion() {
        System.out.println(osquery.getVersion());
    }

    @Test
    public void testGetTables() {
        List<String> tables = osquery.getTables();
        for (String table : tables) {
            System.out.println(table.trim());
        }
    }

    @Test
    public void testGetTableContent() {
        System.out.println(osquery.getTableOutput("docker_images", "json").getOutput());
    }
}
