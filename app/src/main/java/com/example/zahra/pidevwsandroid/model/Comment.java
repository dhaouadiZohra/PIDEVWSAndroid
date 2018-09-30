package com.example.zahra.pidevwsandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;



public class Comment {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("description")
    @Expose
    private String description;

    //@SerializedName("postdate")
    //@Expose
    //private Date postdate;


    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }

    public Comment() {
    }

    public Comment(Integer id, String description) {
        this.id = id;
        this.description = description;
    }


    public Comment(Integer id) {
        this.id = id;
    }

    public Comment(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }



    public void setId(Integer id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
