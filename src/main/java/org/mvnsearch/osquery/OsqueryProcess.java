package org.mvnsearch.osquery;

import org.mvnsearch.osquery.jdbc.ProcessResult;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * osquery process
 *
 * @author linux_china
 */
public class OsqueryProcess {
  private String osqueryiPath = "osqueryi";

  public void setOsqueryiPath(String osqueryiPath) {
    this.osqueryiPath = osqueryiPath;
  }

  public String getVersion() {
    ProcessResult result = execute("--version");
    return result.getOutput().substring(result.getOutput().lastIndexOf(" ") + 1).trim();
  }

  public List<String> getTables() {
    ProcessResult result = execute("--L");
    List<String> tables = new ArrayList<>();
    String[] lines = result.getOutput().split(System.lineSeparator());
    for (String line : lines) {
      tables.add(line.replace("=>", "").trim());
    }
    return tables;
  }

  public ProcessResult getTableOutput(String table, String format) {
    if (StringUtils.isEmpty(format)) {
      return execute("--A", table);
    } else if (format.equals("csv")) {
      return execute("--csv", "--separator", ",", "--A", table);
    } else {
      return execute("--A", table, "--" + format);
    }
  }

  public ProcessResult query(String sql, String format) {
    if (StringUtils.isEmpty(format)) {
      return execute(sql);
    } else if (format.equals("csv")) {
      return execute("--csv", "--separator", ",", sql);
    } else {
      return execute("--" + format, sql);
    }
  }

  private ProcessResult execute(String... args) {
    try {
      List<String> command = new ArrayList<>();
      command.add(osqueryiPath);
      command.addAll(Arrays.asList(args));
      ProcessBuilder pb = new ProcessBuilder(command);
      Process p = pb.start();
      String output = StreamUtils.copyToString(p.getInputStream(), Charset.defaultCharset());
      String error = StreamUtils.copyToString(p.getErrorStream(), Charset.defaultCharset());
      //Wait to get exit value
      int exitValue = p.waitFor();
      return new ProcessResult(exitValue, output, error);
    } catch (Exception e) {
      return new ProcessResult(-1, null, e.getMessage());
    }
  }


}
