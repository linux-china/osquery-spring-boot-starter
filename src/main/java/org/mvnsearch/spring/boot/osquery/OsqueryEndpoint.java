package org.mvnsearch.spring.boot.osquery;

import org.mvnsearch.osquery.OsqueryProcess;
import org.mvnsearch.osquery.jdbc.ProcessResult;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * osquery endpoint
 *
 * @author linux_china
 */
@RestControllerEndpoint(id = "osquery", enableByDefault = true)
public class OsqueryEndpoint {
    private OsqueryProcess osquery;

    public OsqueryEndpoint() {
        osquery = new OsqueryProcess();
    }

    @GetMapping
    public Map<String, Object> invoke() {
        Map<String, Object> info = new HashMap<>();
        String version = osquery.getVersion();
        info.put("version", version);
        info.put("osName", System.getProperty("os.name"));
        info.put("tables", osquery.getTables());
        info.put("schema", "https://osquery.io/schema/" + version + "/");
        return info;
    }

    @GetMapping("/{tableName}")
    public String tableShow(@PathVariable(name = "tableName") String tableName) throws IOException {
        ProcessResult result = osquery.getTableOutput(tableName, "json");
        return result.getOutput();
    }

    @GetMapping("/{tableName}/{columnNames}")
    public String tableShow(@PathVariable(name = "tableName") String tableName,
                            @PathVariable(name = "columnNames") String columnNames) throws IOException {
        ProcessResult result = osquery.query("select " + columnNames + " from " + tableName, "json");
        return result.getOutput();
    }

}
