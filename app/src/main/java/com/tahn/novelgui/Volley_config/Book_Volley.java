package com.tahn.novelgui.Volley_config;

public class Book_Volley {
    int id;
    String name;
    String description;
    String author_name;
    String cover; // link image

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

    public Book_Volley(int id, String name, String description, String author_name, String cover) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author_name = author_name;
        this.cover = cover;
    }

    @Override

    public String toString() {
        return "Book_Volley{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", author_name='" + author_name + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }
}
