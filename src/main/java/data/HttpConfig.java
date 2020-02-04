package data;

import java.util.List;

public class HttpConfig {
    public Integer sleepTime;
    public String hook;
    public QueryParams queryParams;
    public Headers headers;
    public Integer duration;
    public Integer requestDelay;
    public Integer concurrencyLimit;
    public String body;
    public String testType;
    public Object concurrentGraph;
    public List<HitsGraph> hitsGraph;

    @Override
    public String toString() {
        return "HttpConfig{" +
                "sleepTime=" + sleepTime +
                ", hook='" + hook + '\'' +
                ", queryParams=" + queryParams +
                ", headers=" + headers +
                ", duration=" + duration +
                ", requestDelay=" + requestDelay +
                ", concurrencyLimit=" + concurrencyLimit +
                ", body='" + body + '\'' +
                ", testType='" + testType + '\'' +
                ", concurrentGraph=" + concurrentGraph +
                ", hitsGraph=" + hitsGraph +
                '}';
    }
}
