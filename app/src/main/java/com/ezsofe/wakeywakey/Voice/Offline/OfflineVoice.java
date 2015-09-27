package com.ezsofe.wakeywakey.Voice.Offline;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Jia Rong on 9/27/2015.
 */
public class OfflineVoice {

    public String _id;

    @SerializedName("User_ID")
    public String user_id;

    @SerializedName("URL")
    public String url;

    @SerializedName("CreatedOn")
    public Date createdOn;
}
