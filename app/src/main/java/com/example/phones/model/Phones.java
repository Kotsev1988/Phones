package com.example.phones.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Phones implements Parcelable {
    String name;
    String descriptions;
    int image;
    Date date;
    public Phones(String name, String descriptions, int image, Date date){
        this.name = name;
        this.descriptions = descriptions;
        this.image = image;
        this.date = date;
    }

    public Phones(){

    }


    public String getName() {
        return name;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public int getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }



    protected Phones(Parcel in) {
        name = in.readString();
        descriptions = in.readString();
        image = in.readInt();
        date = new Date(in.readLong());
    }

    public static final Creator<Phones> CREATOR = new Creator<Phones>() {
        @Override
        public Phones createFromParcel(Parcel in) {

            return new Phones(in);
        }

        @Override
        public Phones[] newArray(int size) {
            return new Phones[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(descriptions);
        parcel.writeInt(image);
        parcel.writeLong(date.getTime());
    }


}
