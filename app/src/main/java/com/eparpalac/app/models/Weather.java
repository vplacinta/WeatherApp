package com.eparpalac.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.internship.weatherapp.R;

public class Weather {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("main")
    @Expose
    private String main;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("icon")
    @Expose
    private String icon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {

        this.icon = icon;
    }

    public Object getIconPath() {

        Object iconPath = null;

        switch (this.id) {

            case 500:
            case 501:
                iconPath = R.mipmap.ic_rain;
                break;

            case 800:
                iconPath = R.mipmap.ic_clear;
                break;

            case 802:
            case 803:
                iconPath = R.mipmap.ic_cloudy;
                break;

            case 600:
            case 601:
            case 602:
                iconPath = R.mipmap.ic_snow;
                break;

            case 611:
                iconPath = R.mipmap.ic_snow;
                break;

            case 741:
                iconPath = R.mipmap.ic_fog;
                break;

            case 960:
            case 901:
                iconPath = R.mipmap.ic_storm;
                break;

            case 902:
                iconPath = R.mipmap.ic_storm;
                break;
            default:
                break;
        }
        return iconPath;
    }

}
