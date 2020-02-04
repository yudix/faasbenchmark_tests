package data;

import java.util.List;

public class Function {

    public String functionName;
    public String description;
    public String runtime;
    public String memorySize;
    public List<Result> results = null;

    @Override
    public String toString() {
        return "Function{" +
                "functionName='" + functionName + '\'' +
                ", description='" + description + '\'' +
                ", runtime='" + runtime + '\'' +
                ", memorySize='" + memorySize + '\'' +
                ", results=" + results +
                '}';
    }
}
