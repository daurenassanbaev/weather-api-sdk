package weatherapisdk.weatherapisdk.cronjob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import weatherapisdk.weatherapisdk.factory.WeatherSDKFactory;
import weatherapisdk.weatherapisdk.model.enums.Mode;

/**
 * Scheduled job for weather updates.
 */
@Service
@EnableScheduling
public class WeatherUpdateCronJob {

    private final WeatherSDKFactory weatherSDKFactory;
    private static final Logger log = LoggerFactory.getLogger(WeatherUpdateCronJob.class);

    public WeatherUpdateCronJob(WeatherSDKFactory weatherSDKFactory) {
        this.weatherSDKFactory = weatherSDKFactory;
    }

    /**
     * Periodically updates weather data.
     *
     * Runs at a fixed rate defined in the configuration.
     */
    @Scheduled(fixedRateString = "${cron-job.weather-updates-rate}")
    public void pollWeatherUpdates() {
        log.info("Polling: Starting weather updates for all SDK instances...");
        weatherSDKFactory.getAllInstances().forEach((apiKey, sdk) -> {
            if (sdk.getMode() == Mode.POLLING) {
                for (String city : sdk.getRequestedCities()) {
                    try {
                        sdk.updateWeather(city);
                        log.info("Polling: Updated weather for city: {} (API Key: {})", city, apiKey);
                    } catch (Exception e) {
                        log.error("Polling: Failed to update weather for city: {} (API Key: {}). Reason: {}",
                                city, apiKey, e.getMessage());
                    }
                }
            }
        });
    }
}
