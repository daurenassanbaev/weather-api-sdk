package weatherapisdk.weatherapisdk.service;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import weatherapisdk.weatherapisdk.client.WeatherApiClient;
import weatherapisdk.weatherapisdk.model.WeatherData;
import weatherapisdk.weatherapisdk.model.enums.Mode;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Provides weather data management functionalities.
 */
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

    /**
     * Retrieves weather data for a specified city.
     *
     * @param city the city for which to fetch weather data.
     * @return the weather data for the city.
     */
    @Cacheable(value = "weatherCache", key = "#city")
    public WeatherData getWeather(String city) {
        requestedCities.add(city.trim().toLowerCase());
        return weatherApiClient.fetchWeatherData(city, apiKey);
    }

    /**
     * Updates weather data for a specified city.
     *
     * @param city the city for which to update weather data.
     * @return the updated weather data.
     */
    @CachePut(value = "weatherCache", key = "#city")
    public WeatherData updateWeather(String city) {
        return weatherApiClient.fetchWeatherData(city, apiKey);
    }

    /**
     * Gets the API key used for authentication.
     *
     * @return the API key.
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Gets the operational mode of the SDK.
     *
     * @return the operational mode.
     */
    public Mode getMode() {
        return mode;
    }

    /**
     * Gets the set of requested cities.
     *
     * @return the set of requested cities.
     */
    public Set<String> getRequestedCities() {
        return requestedCities;
    }
}
