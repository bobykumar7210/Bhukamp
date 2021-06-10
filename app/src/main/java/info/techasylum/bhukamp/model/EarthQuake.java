package info.techasylum.bhukamp.model;

public class EarthQuake {
    private double magnitude;
    private String place;
    private String date;
    private String time;
   private String detailUrl;

    public EarthQuake(double magnitude, String place, String date, String time, String detailUrl) {
        this.magnitude = magnitude;
        this.place = place;
        this.date = date;
        this.time = time;
        this.detailUrl = detailUrl;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
