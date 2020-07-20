package com.campaign.sey.recylcerchat;

/**
 * Created by Dytstudio.
 */

public class ChatData {
    String type, text, time;



    public ChatData() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ChatData(String type, String text, String time) {
        this.type = type;
        this.text = text;
        this.time = time;
    }


/*public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }*/
}
