package weatherapisdk.weatherapisdk.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import weatherapisdk.weatherapisdk.exception.CityNotFoundException;
import weatherapisdk.weatherapisdk.exception.InvalidAPIKeyException;
import weatherapisdk.weatherapisdk.exception.NetworkException;
import weatherapisdk.weatherapisdk.model.WeatherData;

@Component
public class WeatherApiClient {

    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public WeatherApiClient() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public WeatherData fetchWeatherData(String city, String apiKey) {
        String url = String.format("%s?q=%s&appid=%s", API_URL, city.trim().toLowerCase(), apiKey);
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return objectMapper.readValue(response.getBody(), WeatherData.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new CityNotFoundException("City not found: " + city);
        } catch (HttpClientErrorException.Unauthorized e) {
            throw new InvalidAPIKeyException("Invalid API key");
        } catch (ResourceAccessException e) {
            throw new NetworkException("Network error: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error calling OpenWeather API", e);
        }
    }
}
