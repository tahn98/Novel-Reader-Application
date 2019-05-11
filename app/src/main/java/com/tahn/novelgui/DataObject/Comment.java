package com.tahn.novelgui.DataObject;

public class Comment {
    private String content;
    private String cm_id;
    private String date_comment;
    private String name_user;

    public Comment(String content, String cm_id, String date_comment, String name_user) {
        this.content = content;
        this.cm_id = cm_id;
        this.date_comment = date_comment;
        this.name_user = name_user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComeument_id() {
        return cm_id;
    }

    public void setComeument_id(String comeument_id) {
        this.cm_id = comeument_id;
    }

    public String getDate_comment() {
        return date_comment;
    }

    public void setDate_comment(String date_comment) {
        this.date_comment = date_comment;
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }
}
