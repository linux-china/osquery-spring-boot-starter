package org.mvnsearch.spring.boot.osquery;

import org.mvnsearch.osquery.OsqueryProcess;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;

import java.util.HashMap;
import java.util.Map;

/**
 * osquery endpoint
 *
 * @author linux_china
 */
public class OsqueryEndpoint extends AbstractEndpoint<Map<String, Object>> {
    private OsqueryProcess osquery;

    public OsqueryEndpoint() {
        super("osquery");
        osquery = new OsqueryProcess();
    }

    @Override
    public Map<String, Object> invoke() {
        Map<String, Object> info = new HashMap<>();
        String version = osquery.getVersion();
        info.put("version", version);
        info.put("osName", System.getProperty("os.name"));
        info.put("tables", osquery.getTables());
        info.put("schema", "https://osquery.io/schema/" + version + "/");
        return info;
    }
}
