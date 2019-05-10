package com.tahn.novelgui.Retrofit_Config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookRetrofit {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("NAME")
    @Expose
    private String nAME;
    @SerializedName("DESCRIPTION")
    @Expose
    private String dESCRIPTION;
    @SerializedName("AUTHOR_NAME")
    @Expose
    private String aUTHORNAME;
    @SerializedName("COVER")
    @Expose
    private String cOVER;

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getNAME() {
        return nAME;
    }

    public void setNAME(String nAME) {
        this.nAME = nAME;
    }

    public String getDESCRIPTION() {
        return dESCRIPTION;
    }

    public void setDESCRIPTION(String dESCRIPTION) {
        this.dESCRIPTION = dESCRIPTION;
    }

    public String getAUTHORNAME() {
        return aUTHORNAME;
    }

    public void setAUTHORNAME(String aUTHORNAME) {
        this.aUTHORNAME = aUTHORNAME;
    }

    public String getCOVER() {
        return cOVER;
    }

    public void setCOVER(String cOVER) {
        this.cOVER = cOVER;
    }

}
