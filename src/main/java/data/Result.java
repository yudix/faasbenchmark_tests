package data;

public class Result {
    public Integer id;
    public Double invocationOverhead;
    public Double duration;
    public Double responseTime;
    public Boolean reused;
    public Boolean failed;

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", invocationOverhead=" + invocationOverhead +
                ", duration=" + duration +
                ", responseTime=" + responseTime +
                ", reused=" + reused +
                ", failed=" + failed +
                '}';
    }
}
