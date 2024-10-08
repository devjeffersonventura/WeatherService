import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Classe principal que contém métodos para obter informações meteorológicas.
public class WeatherService {
    private static final String API_KEY = "8d436ea58b054c999aece3d5c6b375e6";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    // Método principal que inicia a execução do programa.
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome da cidade: ");
        String city = scanner.nextLine();
        scanner.close();

        // Chama os métodos para obter as informações meteorológicas.
        getCurrentWeather(city);
        getForecast(city);
        getWeatherAlerts(city);
    }

    // Método para obter o clima atual de uma determinada cidade.
    public static void getCurrentWeather(String city) throws Exception {
        try {
            String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8.toString());
            String url = BASE_URL + "weather?q=" + encodedCity + "&appid=" + API_KEY + "&units=metric" + "&lang=pt_br";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(WeatherService::parseCurrentWeather)
                    .join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para obter a previsão do tempo de uma determinada cidade.
    public static void getForecast(String city) throws Exception {
        try {
            String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8.toString());
            String url = BASE_URL + "forecast?q=" + encodedCity + "&appid=" + API_KEY + "&units=metric" + "&lang=pt_br";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(WeatherService::parseForecast)
                    .join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para obter os alertas meteorológicos de uma determinada cidade.
    public static void getWeatherAlerts(String city) throws Exception {
        try {
            String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8.toString());
            String url = BASE_URL + "alerts?q=" + encodedCity + "&appid=" + API_KEY + "&units=metric" + "&lang=pt_br";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(WeatherService::parseAlerts)
                    .join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para analisar a resposta da API de clima atual.
    private static String parseCurrentWeather(String responseBody) {
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        if (jsonObject.has("name") && jsonObject.has("main")) {
            String cityName = jsonObject.get("name").getAsString();
            String descriptionWeather = jsonObject.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString();
            double temperature = jsonObject.get("main").getAsJsonObject().get("temp").getAsDouble();
            double tempMax = jsonObject.get("main").getAsJsonObject().get("temp_max").getAsDouble();
            double tempMin = jsonObject.get("main").getAsJsonObject().get("temp_min").getAsDouble();
            System.out.println("Cidade: " + cityName + ", Temperatura Atual: " + temperature + "°C" + ", Temperatura Máxima: " + tempMax + "°C" + ", Temperatura Mínima: " + tempMin + "°C" + ", Clima: " + descriptionWeather);
        } else {
            System.out.println("Dados de tempo não disponíveis para a cidade especificada.");
        }
        return null;
    }

    // Método para analisar a resposta da API de previsão do tempo.
    private static String parseForecast(String responseBody) {
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        if (jsonObject.has("list")) {
            JsonArray list = jsonObject.getAsJsonArray("list");
            System.out.println("Previsão para os próximos dias:");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            for (int i = 0; i < list.size(); i += 8) { // Pega a previsão a cada 24 horas (8 * 3 horas)
                JsonObject dayForecast = list.get(i).getAsJsonObject();
                String date = dayForecast.get("dt_txt").getAsString();
                LocalDate dateTime = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                String formattedDate = dateTime.format(formatter);
                double tempMax = dayForecast.get("main").getAsJsonObject().get("temp_max").getAsDouble();
                double tempMin = dayForecast.get("main").getAsJsonObject().get("temp_min").getAsDouble();
                String descriptionWeather = dayForecast.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString();

                System.out.println("Data: " + formattedDate + ", Temperatura Máxima: " + tempMax + "°C, Temperatura Mínima: " + tempMin + "°C" + ", Clima: " + descriptionWeather);
            }
        } else {
            System.out.println("Previsão não disponível para a cidade especificada.");
        }
        return null;
    }

    // Método para analisar a resposta da API de alertas meteorológicos.
    private static String parseAlerts(String responseBody) {
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        if (jsonObject.has("alerts")) {
            JsonArray alerts = jsonObject.getAsJsonArray("alerts");
            System.out.println("Alertas Meteorológicos:");
            for (int i = 0; i < alerts.size(); i++) {
                JsonObject alert = alerts.get(i).getAsJsonObject();
                String event = alert.get("event").getAsString();
                String description = alert.get("description").getAsString();
                System.out.println("Evento: " + event + ", Descrição: " + description);
            }
        } else {
            System.out.println("Nenhum alerta meteorológico no momento.");
        }
        return null;
    }
}
