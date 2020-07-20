package com.campaign.sey.recylcerchat;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.campaign.sey.R;


/**
 * Created by Dytstudio.
 */

public class HolderYou extends RecyclerView.ViewHolder {

    private TextView time, chatText,user_phone;
    private ImageView image;

    public HolderYou(View v) {
        super(v);
        image = (ImageView) v.findViewById(R.id.image);
        time = (TextView) v.findViewById(R.id.tv_time);
        user_phone = (TextView) v.findViewById(R.id.user_phone);
        chatText = (TextView) v.findViewById(R.id.tv_chat_text);
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public TextView getTime() {
        return time;
    }

    public void setTime(TextView time) {
        this.time = time;
    }

    public TextView getChatText() {
        return chatText;
    }

    public void setChatText(TextView chatText) {
        this.chatText = chatText;
    }

    public TextView getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(TextView user_phone) {
        this.user_phone = user_phone;
    }
}
