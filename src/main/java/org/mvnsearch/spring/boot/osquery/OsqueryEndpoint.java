package org.mvnsearch.spring.boot.osquery;

import org.mvnsearch.osquery.OsqueryProcess;
import org.mvnsearch.osquery.jdbc.ProcessResult;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * osquery endpoint
 *
 * @author linux_china
 */
@WebEndpoint(id = "osquery")
public class OsqueryEndpoint {
  private final OsqueryProcess osquery;

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
  public ResponseEntity<String> table(@Selector String tableName, @Nullable String format) {
    if (format == null) {
      format = "csv";
    }
    String output;
    if (tableName.contains("(")) {  // query with columns
      String newTableName = tableName.substring(0, tableName.indexOf("("));
      String columnNames = tableName.substring(tableName.indexOf("(") + 1, tableName.indexOf(")"));
      ProcessResult result = osquery.query("select " + columnNames + " from " + newTableName, format);
      output = result.getOutput();
    } else {
      ProcessResult result = osquery.getTableOutput(tableName, format);
      output = result.getOutput();
    }
    return ResponseEntity.ok()
      .header(HttpHeaders.CONTENT_TYPE, "text/plain; charset=utf-8")
      .header(HttpHeaders.ACCEPT_RANGES, "none") // disable Accept-Ranges for DuckDB
      .body(output);
  }

}
