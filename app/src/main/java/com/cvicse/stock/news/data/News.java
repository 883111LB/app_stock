package com.cvicse.stock.news.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

/**
 * Created by ruan_ytai on 16-12-29.
 */

public class News implements Parcelable
{
    private String ID;
    private UUID mId;
    private String mTitle;
    private String mCompany;
    private String mPublishTime;
    private String mReadNumber;
    private String mContent;

    public News()
    {
        this(UUID.randomUUID());
    }

    public News(UUID id)
    {
        mId = id;
    }

    public News(String id) {
        ID = id;
    }


    public News(String id,String title, String company, String publishTime, String readNumber)
    {
        this();
        ID = id;
        mTitle = title;
        mCompany = company;
        mPublishTime = publishTime;
        mReadNumber = readNumber;
        //mUrl = url;
    }

    protected News(Parcel in) {
        ID = in.readString();
        mId = (UUID)in.readSerializable();
        mTitle = in.readString();
        mCompany = in.readString();
        mPublishTime = in.readString();
        mReadNumber = in.readString();
        mContent = in.readString();
        //mUrl = in.readParcelable();
    }

    public String getTrueID() {
        return ID;
    }

    public void setTrueID(String id){
        ID = id;
    }


    public UUID getId() {
        return mId;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getReadNumber() {
        return mReadNumber;
    }

    public void setReadNumber(String readNumber) {
        mReadNumber = readNumber;
    }

    public String getPublishTime() {
        return mPublishTime;
    }

    public void setPublishTime(String publishTime) {
        mPublishTime = publishTime;
    }

    public String getCompany() {
        return mCompany;
    }

    public void setCompany(String company) {
        mCompany = company;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(ID);
        dest.writeSerializable(mId);
        dest.writeString(mTitle);
        dest.writeString(mCompany);
        dest.writeString(mPublishTime);
        dest.writeString(mReadNumber);
        dest.writeString(mContent);
        //dest.writeParcelable(mUrl);
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
