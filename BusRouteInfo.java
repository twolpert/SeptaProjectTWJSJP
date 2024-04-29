
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class BusRouteInfo extends Application {

    private static final String API_URL = "http://www3.septa.org/transitview/bus_route_data/";

    @Override
    public void start(Stage primaryStage) {
        TextField routeInput = new TextField();
        Button searchButton = new Button("Search");
        Label resultLabel = new Label();

        searchButton.setOnAction(e -> {
            String routeNumber = routeInput.getText();
            if (!routeNumber.isEmpty()) {
                String jsonData = fetchData(routeNumber);
                System.out.println("Received JSON data: " + jsonData); // Debug print
                String routeInfo = parseJson(jsonData);
                System.out.println("Parsed route info: " + routeInfo); // Debug print
                resultLabel.setText(routeInfo);
            } else {
                resultLabel.setText("Please enter a route number.");
            }
        });

        VBox root = new VBox(10);
        root.getChildren().addAll(new Label("Enter Route Number:"), routeInput, searchButton, resultLabel);
        root.setPrefSize(300, 200);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Bus Route Info");
        primaryStage.show();
    }
    private String fetchData(String routeNumber) {
    StringBuilder jsonData = new StringBuilder();
    try {
        URL url = new URL(API_URL + routeNumber);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            jsonData.append(line);
        }
        reader.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return jsonData.toString();
}



   private String parseJson(String jsonData) {
    Gson gson = new Gson();
    try {
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        JsonArray busesArray = jsonObject.getAsJsonArray("bus");
        StringBuilder routeInfo = new StringBuilder();

        for (JsonElement busElement : busesArray) {
            JsonObject busObject = busElement.getAsJsonObject();
            String routeId = busObject.get("route_id").getAsString();
            String direction = busObject.get("Direction").getAsString();
            String destination = busObject.get("destination").getAsString();
            String seatAvailability = busObject.get("estimated_seat_availability").getAsString();
            
            String nextStopName;
            try {
                nextStopName = busObject.get("next_stop_name").getAsString();
            } catch (Exception e) {
                nextStopName = "Unknown";
            }

            // Append next stop name to route info
            routeInfo.append("Route ID: ").append(routeId)
                    .append(", Direction: ").append(direction)
                    .append(", Destination: ").append(destination)
                    .append(", Seat Availability: ").append(seatAvailability)
                    .append(", Next Stop: ").append(nextStopName)
                    .append("\n");
        }

        return routeInfo.toString();
    } catch (Exception e) {
        e.printStackTrace();
        return "Failed to parse JSON data: " + e.getMessage();
    }
}
}
