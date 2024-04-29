import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        Button searchButton = new Button("Submit");
        Label resultLabel = new Label();
        
        // Styling
        routeInput.setPromptText("Enter Route Number");
        resultLabel.setWrapText(true);
        resultLabel.setPadding(new Insets(10));

        // Styling the submit button
        searchButton.setStyle("-fx-text-fill: white; -fx-background-color: black;");

        searchButton.setOnAction(e -> {
            String routeNumber = routeInput.getText();
            if (!routeNumber.isEmpty()) {
                String jsonData = fetchData(routeNumber);
                String routeInfo = parseJson(jsonData);
                resultLabel.setText(routeInfo);
            } else {
                resultLabel.setText("Please enter a route number.");
            }
        });

        VBox searchBox = new VBox(10);
        searchBox.getChildren().addAll(routeInput, searchButton);
        searchBox.setPadding(new Insets(10));
        searchBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-width: 1px;");

        VBox root = new VBox(10);
        Label titleLabel = new Label("Septa Bus Route Information");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #9435ca; -fx-font-size: 18pt");


        // Load and set the image
        ImageView imageView = new ImageView(new Image("https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEjCuZGWje3t_4b-5HuyFMS3BxK3gJzi4QSR9n_CA5fh3eWxMeod36CF5tcGs0NkLQqbRpoiz89WgKmOhepvUO9zL3EaxUIPDIqgxz5lcurYJTVaWUSdVHSCOvmaHhzmM_sLE80q9EFq9U1LqGhkb8LMc0oXuZnT1opPrZO4tYtTzAvg9qJihjHabPf82Q/s518/screenshot.20168.jpg"));
        imageView.setFitWidth(350);
        imageView.setFitHeight(350);

        root.getChildren().addAll(titleLabel, searchBox, resultLabel, imageView);
        root.setPadding(new Insets(20));
        root.setSpacing(5);

        primaryStage.setScene(new Scene(root, 400, 450));
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

    public static void main(String[] args) {
        launch(args);
    }
}
