package com.tahn.novelgui.DataObject;

public class ChapterSimple {
    private String nameChap;
    private String bookId;
    private String uploadDate;
    private String numView;

    public String getNameChap() {
        return nameChap;
    }

    public void setNameChap(String nameChap) {
        this.nameChap = nameChap;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getNumView() {
        return numView;
    }

    public void setNumView(String numView) {
        this.numView = numView;
    }

    public ChapterSimple(String nameChap, String bookId, String uploadDate, String numView) {
        this.nameChap = nameChap;
        this.bookId = bookId;
        this.uploadDate = uploadDate;
        this.numView = numView;
    }
}
