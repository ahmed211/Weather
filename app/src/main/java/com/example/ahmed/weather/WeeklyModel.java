package com.example.ahmed.weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ahmed on 7/23/2017.
 */

public class WeeklyModel {


    private Temp temp;
    private List<Weather> weather;

    class Temp{
        @SerializedName("min")
        private Double tempMin;
        @SerializedName("max")
        private Double tempMax;

        public Double getTempMax() {
            return tempMax;
        }
        public Double getTempMin() {
            return tempMin;
        }

    }

    class Weather{
        @SerializedName("main")
        private String main;

        @SerializedName("icon")
        private String icon;

        public String getIcon() {
            return icon;
        }

        public String getMain() {
            return main;
        }


    }

    public Double getTempMax() {
        return temp.tempMax;
    }

    public Double getTempMin() {
        return temp.tempMin;
    }

    public String getIcon() {
        return weather.get(0).icon;
    }

    public String getMain() {
        return weather.get(0).main;
    }


}
