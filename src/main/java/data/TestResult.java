
package data;

import java.util.DoubleSummaryStatistics;
import java.util.List;

public class TestResult {

    public String filePath;
    public String provider;
    public String testName;
    public String testDescription;
    public String stackDescription;
    public HttpConfig httpConfig;
    public List<Function> functions;
    public DoubleSummaryStatistics statistics;

    @Override
    public String toString() {
        return "TestResult{" +
                "filePath='" + filePath + '\'' +
                ", provider='" + provider + '\'' +
                ", testName='" + testName + '\'' +
                ", testDescription='" + testDescription + '\'' +
                ", stackDescription='" + stackDescription + '\'' +
                ", httpConfig=" + httpConfig +
                ", functions=" + functions +
                '}';
    }
}
