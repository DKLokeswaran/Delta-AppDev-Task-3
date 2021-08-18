package com.example.delta_appdev_task_3;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Label {

    @SerializedName("Confidence")
    @Expose
    private Double confidence;
    @SerializedName("Name")
    @Expose
    private String name;

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}