public class BusRouteData {
    private List<Bus> bus;
    // all attributes for a Septa bus route(not all are used)

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

        // Getter and Setter for route_id
        public String getRouteId() {
            return route_id;
        }

        public void setRouteId(String route_id) {
            this.route_id = route_id;
        }

        // Getter and Setter for Direction
        public String getDirection() {
            return Direction;
        }

        public void setDirection(String Direction) {
            this.Direction = Direction;
        }

        // Getter and Setter for destination
        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        // Getter and Setter for estimated_seat_availability
        public String getEstimatedSeatAvailability() {
            return estimated_seat_availability;
        }

        public void setEstimatedSeatAvailability(String estimated_seat_availability) {
            this.estimated_seat_availability = estimated_seat_availability;
        }
        // Other getters and setters as needed
    }
}
