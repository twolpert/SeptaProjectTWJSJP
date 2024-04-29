import java.util.List;

public class BusRouteData {
    private List<Bus> bus;

    public static class Bus {
    private String lat;
    private String lng;
    private String label;
    private String route_id;
    private String trip;
    private String VehicleID;
    private String BlockID;
    private String Direction;
    private String destination;
    private double heading;
    private int late;
    private String next_stop_id;
    private String next_stop_name; 
    private int next_stop_sequence;
    private String estimated_seat_availability;
    private int Offset;
    private String Offset_sec;
    private long timestamp;

    // Constructor, getters, and setters (if needed)

    // Getter and Setter for next_stop_name
    public String getNextStopName() {
        return next_stop_name;
    }

    public void setNextStopName(String next_stop_name) {
        this.next_stop_name = next_stop_name;
    }

    // Other getters as needed
    public String getRouteId() {
        return route_id;
    }

    public String getDirection() {
        return Direction;
    }
}  
}