package org.mvnsearch.spring.boot.osquery;

import org.mvnsearch.osquery.OsqueryProcess;
import org.mvnsearch.osquery.jdbc.ProcessResult;
import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * osquery operation endpoint
 *
 * @author linux_china
 */
public class OsqueryOperationEndpoint extends EndpointMvcAdapter {
    private OsqueryProcess osquery = new OsqueryProcess();

    public OsqueryOperationEndpoint(OsqueryEndpoint delegate) {
        super(delegate);
    }

    @RequestMapping(value = {"/{tableName}"})
    public void tableShow(@PathVariable String tableName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String format = request.getParameter("format");
        setContentType(format, response);
        ProcessResult result = osquery.getTableOutput(tableName, format);
        outputResult(result, response);
    }

    @RequestMapping(value = {"/{tableName}/{columns}"})
    @ResponseBody
    public void tableColumns(@PathVariable String tableName, @PathVariable String columns,
                             HttpServletRequest request, HttpServletResponse response) throws IOException {
        String format = request.getParameter("format");
        setContentType(format, response);
        ProcessResult result = osquery.query("select " + columns + " from " + tableName, format);
        outputResult(result, response);
    }

    private void setContentType(String format, HttpServletResponse response) {
        if ("json".equals(format)) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        } else {
            response.setContentType(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8");
        }
    }

    private void outputResult(ProcessResult result, HttpServletResponse response) throws IOException {
        ServletOutputStream output = response.getOutputStream();
        if (StringUtils.isEmpty(result.getError())) {
            output.write(result.getOutput().getBytes());
        } else {
            response.setStatus(400);
            output.write(result.getError().getBytes());
        }
        output.flush();
        output.close();
    }

}
