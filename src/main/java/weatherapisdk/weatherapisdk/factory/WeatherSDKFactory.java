package weatherapisdk.weatherapisdk.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import weatherapisdk.weatherapisdk.client.WeatherApiClient;
import weatherapisdk.weatherapisdk.model.enums.Mode;
import weatherapisdk.weatherapisdk.service.WeatherSDK;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WeatherSDKFactory {
    private final WeatherApiClient weatherApiClient = new WeatherApiClient();
    private final Map<String, WeatherSDK> sdkInstances = new ConcurrentHashMap<>();

    public synchronized WeatherSDK getInstance(String apiKey, Mode mode) {
        if (sdkInstances.containsKey(apiKey)) {
            return sdkInstances.get(apiKey);
        } else {
            WeatherSDK sdk = new WeatherSDK(apiKey, mode, weatherApiClient);
            sdkInstances.put(apiKey, sdk);
            return sdk;
        }
    }

    public synchronized void removeInstance(String apiKey) {
        sdkInstances.remove(apiKey);
    }

    public Map<String, WeatherSDK> getAllInstances() {
        return sdkInstances;
    }

}
