package weatherapisdk.weatherapisdk.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {

    @JsonProperty("weather")
    @Setter(AccessLevel.NONE)
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

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {
        @JsonProperty("main")
        private String main;
        @JsonProperty("description")
        private String description;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Temperature {
        @JsonProperty("temp")
        private double temp;
        @JsonProperty("feels_like")
        private double feels_like;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Wind {
        @JsonProperty("speed")
        private double speed;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Sys {
        @JsonProperty("sunrise")
        private long sunrise;
        @JsonProperty("sunset")
        private long sunset;
    }
}
