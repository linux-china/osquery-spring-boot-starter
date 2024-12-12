package org.mvnsearch.osquery;

/**
 * process result
 *
 * @author linux_china
 */
public class ProcessResult {
    private int exitValue;
    private String output;
    private String error;

    public ProcessResult(int exitValue, String output, String error) {
        this.exitValue = exitValue;
        this.output = output;
        this.error = error;
    }

    public int getExitValue() {
        return exitValue;
    }

    public void setExitValue(int exitValue) {
        this.exitValue = exitValue;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
