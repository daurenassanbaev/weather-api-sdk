package weatherapisdk.weatherapisdk.service;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import weatherapisdk.weatherapisdk.client.WeatherApiClient;
import weatherapisdk.weatherapisdk.model.WeatherData;
import weatherapisdk.weatherapisdk.model.enums.Mode;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WeatherSDK {

    private final String apiKey;
    private final Mode mode;
    private final WeatherApiClient weatherApiClient;
    private final Set<String> requestedCities = ConcurrentHashMap.newKeySet();

    public WeatherSDK(String apiKey, Mode mode, WeatherApiClient weatherApiClient) {
        this.apiKey = apiKey;
        this.mode = mode;
        this.weatherApiClient = weatherApiClient;
    }

    @Cacheable(value = "weatherCache", key = "#city")
    public WeatherData getWeather(String city) {
        requestedCities.add(city.trim().toLowerCase());
        return weatherApiClient.fetchWeatherData(city, apiKey);
    }

    @CachePut(value = "weatherCache", key = "#city")
    public WeatherData updateWeather(String city) {
        return weatherApiClient.fetchWeatherData(city, apiKey);
    }

    public String getApiKey() {
        return apiKey;
    }

    public Mode getMode() {
        return mode;
    }

    public Set<String> getRequestedCities() {
        return requestedCities;
    }
}
