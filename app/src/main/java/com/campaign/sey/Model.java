/*
 * Copyright (C) 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.campaign.sey;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

/**
 * Data model for each row of the RecyclerView.
 */
public class Model {
    static final String YOUTUBE = "Youtube" ;
    static final String TITLE_KEY = "Title";
    static final String NAME_KEY = "Name";
    static final String DETAIL_KEY = "Detail";
    static final String IMAGE_KEY = "Image Resource";
    //Member variables representing the title, image and information about the sport
    private String id;
    private String name;
    private String title;
    private String imageResource;
    private String desc;
    private String date;
    private String youtube;
    private String type;
    private String text;
    private String time;



    public Model() {
    }

    public Model(String id, String name, String title, String youtube) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.youtube = youtube;
    }

    public Model(String id, String type, String text, String time, String imageResource) {
        this.id = id;
        this.type = type;
        this.text = text;
        this.time = time;
        this.imageResource = imageResource;
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

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public Model(String id, String name, String title, String imageResource, String desc, String date, String youtube) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.imageResource = imageResource;
        this.desc = desc;
        this.date = date;
        this.youtube = youtube;
    }

    static Intent starter(Context context, String title, String detail, String name, String imageResId, String youtube) {
        Intent detailIntent = new Intent(context, Details.class);
        detailIntent.putExtra(TITLE_KEY, title);
        detailIntent.putExtra(DETAIL_KEY,detail);
        detailIntent.putExtra(NAME_KEY,name );
        detailIntent.putExtra(IMAGE_KEY, imageResId);
        detailIntent.putExtra(YOUTUBE, youtube);
        return detailIntent;
    }

    @Override
    public String toString() {
        return "Model{" +
                "type='" + type + '\'' +
                ", text='" + text + '\'' +
                ", time='" + time + '\'' +
                '}';
    }



    private String phone;
    private String username;
    private String verify;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public Model(String phone,  String username,String imageResource, String verify, String id,  String date) {
        this.id = id;
        this.imageResource = imageResource;
        this.date = date;
        this.phone = phone;
        this.username = username;
        this.verify = verify;
    }
}
