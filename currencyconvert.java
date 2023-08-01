import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class CurrencyConverterApp extends Application {

    private static final String API_KEY = "a8bba08af1f10b38714233ef";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/a8bba08af1f10b38714233ef/latest/USD";

    private Label resultLabel;
    private TextField amountTextField;
    private TextField sourceCurrencyTextField;
    private TextField targetCurrencyTextField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Currency Converter");

        // Set unique icon for the stage
        Image iconImage = new Image("https://th.bing.com/th/id/R.1f365abf9c7b7083073460ea3ea169e7?rik=sqDLmy8Ng7AzMg&pid=ImgRaw&r=0");
        primaryStage.getIcons().add(iconImage);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(8);
        grid.setHgap(10);

        amountTextField = new TextField();
        amountTextField.setPromptText("Amount");
        GridPane.setConstraints(amountTextField, 0, 0);

        sourceCurrencyTextField = new TextField();
        sourceCurrencyTextField.setPromptText("Source Currency");
        GridPane.setConstraints(sourceCurrencyTextField, 1, 0);

        targetCurrencyTextField = new TextField();
        targetCurrencyTextField.setPromptText("Target Currency");
        GridPane.setConstraints(targetCurrencyTextField, 2, 0);

        Button convertButton = new Button("Convert");
        GridPane.setConstraints(convertButton, 0, 1);

        resultLabel = new Label();
        GridPane.setConstraints(resultLabel, 0, 2);

        grid.getChildren().addAll(amountTextField, sourceCurrencyTextField, targetCurrencyTextField, convertButton, resultLabel);

        convertButton.setOnAction(e -> convertCurrency());

        Scene scene = new Scene(grid, 300, 200);
        // Apply CSS styling to the scene
        scene.getStylesheets().add("https://github.com/BRAHMA001/Assignment2java/blob/9e18ddcabc865443d29e79220ca9b1ed4ae92521/styles.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // ... (existing code for convertCurrency and getExchangeRate methods)
}


public class CurrencyConverterApp extends Application {

    private static final String API_KEY = "a8bba08af1f10b38714233ef";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/a8bba08af1f10b38714233ef/latest/USD";

    private Label resultLabel;
    private TextField amountTextField;
    private TextField sourceCurrencyTextField;
    private TextField targetCurrencyTextField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Currency Converter");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(8);
        grid.setHgap(10);

        amountTextField = new TextField();
        amountTextField.setPromptText("Amount");
        GridPane.setConstraints(amountTextField, 0, 0);

        sourceCurrencyTextField = new TextField();
        sourceCurrencyTextField.setPromptText("Source Currency");
        GridPane.setConstraints(sourceCurrencyTextField, 1, 0);

        targetCurrencyTextField = new TextField();
        targetCurrencyTextField.setPromptText("Target Currency");
        GridPane.setConstraints(targetCurrencyTextField, 2, 0);

        Button convertButton = new Button("Convert");
        GridPane.setConstraints(convertButton, 0, 1);

        resultLabel = new Label();
        GridPane.setConstraints(resultLabel, 0, 2);

        grid.getChildren().addAll(amountTextField, sourceCurrencyTextField, targetCurrencyTextField, convertButton, resultLabel);

        convertButton.setOnAction(e -> convertCurrency());

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void convertCurrency() {
        String amountStr = amountTextField.getText();
        String sourceCurrency = sourceCurrencyTextField.getText().toUpperCase();
        String targetCurrency = targetCurrencyTextField.getText().toUpperCase();

        try {
            double amount = Double.parseDouble(amountStr);
            double exchangeRate = getExchangeRate(sourceCurrency, targetCurrency);
            double convertedAmount = amount * exchangeRate;
            resultLabel.setText(String.format("%.2f %s = %.2f %s", amount, sourceCurrency, convertedAmount, targetCurrency));
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid input");
        } catch (Exception ex) {
            resultLabel.setText("Error occurred");
        }
    }

    private double getExchangeRate(String sourceCurrency, String targetCurrency) throws Exception {
        String urlStr = String.format("%s?access_key=%s&base=%s&symbols=%s", API_URL, API_KEY, sourceCurrency, targetCurrency);
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();

        JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
        JsonObject rates = jsonObject.getAsJsonObject("rates");
        double exchangeRate = rates.get(targetCurrency).getAsDouble();
        return exchangeRate;
    }
}
