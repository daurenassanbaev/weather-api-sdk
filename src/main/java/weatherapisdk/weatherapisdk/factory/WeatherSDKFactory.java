package weatherapisdk.weatherapisdk.factory;

import org.springframework.stereotype.Component;
import weatherapisdk.weatherapisdk.client.WeatherApiClient;
import weatherapisdk.weatherapisdk.model.enums.Mode;
import weatherapisdk.weatherapisdk.service.WeatherSDK;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Factory class for managing WeatherSDK instances.
 */
@Component
public class WeatherSDKFactory {
    private final WeatherApiClient weatherApiClient = new WeatherApiClient();
    private final Map<String, WeatherSDK> sdkInstances = new ConcurrentHashMap<>();

    /**
     * Retrieves an existing WeatherSDK instance or creates a new one.
     *
     * @param apiKey the API key for authentication.
     * @param mode   the mode of operation.
     * @return an instance of {@link WeatherSDK}.
     */
    public synchronized WeatherSDK getInstance(String apiKey, Mode mode) {
        if (sdkInstances.containsKey(apiKey)) {
            return sdkInstances.get(apiKey);
        } else {
            WeatherSDK sdk = new WeatherSDK(apiKey, mode, weatherApiClient);
            sdkInstances.put(apiKey, sdk);
            return sdk;
        }
    }

    /**
     * Removes an instance of WeatherSDK associated with the given API key.
     *
     * @param apiKey the API key of the instance to be removed.
     */
    public synchronized void removeInstance(String apiKey) {
        sdkInstances.remove(apiKey);
    }

    /**
     * Retrieves all existing WeatherSDK instances.
     *
     * @return a map containing all WeatherSDK instances.
     */
    public Map<String, WeatherSDK> getAllInstances() {
        return sdkInstances;
    }
}