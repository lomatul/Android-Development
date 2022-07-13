package com.example.myno.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName = "Notes")
public class Notes implements Serializable {
    //we need to create some objects

    //we need primary key like id as int which is 1st object

   @PrimaryKey(autoGenerate = true)//it will create new item automatically
    int ID =0;

    //2nd object title as string
    @ColumnInfo(name = "title")
        String title= "";

    //3rd object title as string
    @ColumnInfo(name = "notes")
    String notes = "";

    //4th object to store the date as string
    @ColumnInfo(name = "date")
    String date = "";

    //5th object to pin and unpin as boolean
    @ColumnInfo(name = "pinned")
    Boolean pinned = false;
//now we need to generate our getter setter method


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getPinned() {
        return pinned;
    }

    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
    }
}
