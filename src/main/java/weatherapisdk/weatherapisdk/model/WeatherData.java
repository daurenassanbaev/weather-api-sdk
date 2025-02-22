package weatherapisdk.weatherapisdk.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Represents weather data received from the API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {

    @JsonProperty("weather")
    private Weather weather;

    private Temperature temperature;

    @JsonProperty("visibility")
    private int visibility;

    @JsonProperty("wind")
    private Wind wind;

    private long datetime;

    @JsonProperty("sys")
    private Sys sys;

    @JsonProperty("timezone")
    private int timezone;

    @JsonProperty("name")
    private String name;

    @JsonSetter("weather")
    public void setWeatherFromArray(Weather[] weatherArray) {
        if (weatherArray != null && weatherArray.length > 0) {
            this.weather = weatherArray[0];
        }
    }

    @JsonSetter("main")
    public void setMain(Temperature temperature) {
        this.temperature = temperature;
    }

    @JsonGetter("temperature")
    public Temperature getTemperatureForOutput() {
        return temperature;
    }

    @JsonSetter("dt")
    public void setDt(long dt) {
        this.datetime = dt;
    }

    @JsonGetter("datetime")
    public long getDatetimeForOutput() {
        return datetime;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Represents weather conditions.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {
        @JsonProperty("main")
        private String main;
        @JsonProperty("description")
        private String description;

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    /**
     * Represents temperature details.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Temperature {
        @JsonProperty("temp")
        private double temp;
        @JsonProperty("feels_like")
        private double feels_like;

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public double getFeelsLike() {
            return feels_like;
        }

        public void setFeelsLike(double feels_like) {
            this.feels_like = feels_like;
        }
    }

    /**
     * Represents wind-related data.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Wind {
        @JsonProperty("speed")
        private double speed;

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }
    }

    /**
     * Represents sunrise and sunset timings.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Sys {
        @JsonProperty("sunrise")
        private long sunrise;
        @JsonProperty("sunset")
        private long sunset;

        public long getSunrise() {
            return sunrise;
        }

        public void setSunrise(long sunrise) {
            this.sunrise = sunrise;
        }

        public long getSunset() {
            return sunset;
        }

        public void setSunset(long sunset) {
            this.sunset = sunset;
        }
    }
}