package org.mvnsearch.spring.boot.osquery;

import org.mvnsearch.osquery.OsqueryProcess;
import org.mvnsearch.osquery.jdbc.ProcessResult;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * osquery endpoint
 *
 * @author linux_china
 */
@Endpoint(id = "osquery", enableByDefault = true)
public class OsqueryEndpoint {
    private OsqueryProcess osquery;

    public OsqueryEndpoint() {
        osquery = new OsqueryProcess();
    }

    @ReadOperation
    public Map<String, Object> invoke() {
        Map<String, Object> info = new HashMap<>();
        String version = osquery.getVersion();
        info.put("version", version);
        info.put("osName", System.getProperty("os.name"));
        info.put("tables", osquery.getTables());
        info.put("schema", "https://osquery.io/schema/" + version + "/");
        return info;
    }

    @ReadOperation
    public String tableShow(@Selector String tableName) throws IOException {
        ProcessResult result = osquery.getTableOutput(tableName, "json");
        return result.getOutput();
    }

}
