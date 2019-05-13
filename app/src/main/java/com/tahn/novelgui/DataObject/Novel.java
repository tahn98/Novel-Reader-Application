package com.tahn.novelgui.DataObject;

import java.io.Serializable;

public class Novel implements Serializable {
    int id;
    String name;
    String description;
    String author_name;
    String cover; // link image

    public Novel(int id, String name, String cover) {
        this.id = id;
        this.name = name;
        this.cover = cover;
    }

    public Novel(int id, String name, String description, String author_name, String cover, String rating, String dateTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author_name = author_name;
        this.cover = cover;
        this.rating = rating;
        this.dateTime = dateTime;
    }

    String rating;
    String dateTime;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Novel(int id, String name, String description, String author_name, String cover, String rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author_name = author_name;
        this.cover = cover;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "Novel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", author_name='" + author_name + '\'' +
                ", cover='" + cover + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }
}
