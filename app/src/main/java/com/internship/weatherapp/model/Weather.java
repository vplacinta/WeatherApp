
package com.internship.weatherapp.model;

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

    private  int image;

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

    public int getImage() {
        switch (id)
        {
            case 500:
            case  501:
                return R.drawable.ic_light_rain;

            case 800:
            case  801:
                return R.drawable.ic_clear;

            case 803:
            case  802:
                return R.drawable.ic_cloudy;

            case 600:
            case 602:
            case  601:
            case  611:
                return R.drawable.ic_snow;

            case  741:
                return R.drawable.ic_fog;

            case  960:
            case  901:
            case  902:
                return R.drawable.ic_storm;

            default:
                return  R.drawable.sun;
        }
    }
}
