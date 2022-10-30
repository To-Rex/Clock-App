package app.clock.alarmclock.models;

public class GetTimes {
    private String times;
    private String coments;
    private String switchs;

    public GetTimes(String times, String coments, String switchs) {
        this.times = times;
        this.coments = coments;
        this.switchs = switchs;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getComents() {
        return coments;
    }

    public void setComents(String coments) {
        this.coments = coments;
    }

    public String getSwitchs() {
        return switchs;
    }

    public void setSwitchs(String switchs) {
        this.switchs = switchs;
    }


}
