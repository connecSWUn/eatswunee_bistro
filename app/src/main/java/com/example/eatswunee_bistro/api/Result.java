package com.example.eatswunee_bistro.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("detail")
    @Expose
    private String detail;

    @SerializedName("data")
    @Expose
    private Data data;

    @SerializedName("links")
    @Expose
    private List<Object> links;

    public String getStatus() {
        return status;
    }

    /*public void setStatus(String status) {
        this.status = status;
    }*/

    public String getTitle() {
        return title;
    }

    /*public void setTitle(String title) {
        this.title = title;
    }*/

    public String getDetail() {
        return detail;
    }

    /*public void setDetail(String detail) {
        this.detail = detail;
    }*/

    public Data getData() {
        return data;
    }

    /*public void setData(Data data) {
        this.data = data;
    }*/

    public List<Object> getLinks() {
        return links;
    }

    /*public void setLinks(List<Object> links) {
        this.links = links;
    }*/
}
