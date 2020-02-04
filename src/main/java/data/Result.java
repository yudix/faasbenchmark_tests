package data;

public class Result {
    public Integer id;
    public Float invocationOverhead;
    public Float duration;
    public Float responseTime;
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
