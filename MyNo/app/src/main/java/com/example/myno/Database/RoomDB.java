package com.example.myno.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myno.Models.Notes;

@Database(entities = Notes.class, version = 1,exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    //we have to create an instance to our room database
    private static RoomDB database;
    //we have to create another string variable for our room database name
    private static String DATABASE_NAME ="NoteApp";

    //we need to create e method to get the instance of the database
    public synchronized static RoomDB getInstance(Context context){
        //we will check if our database instance is null or not
        if (database==null){
            database= Room.databaseBuilder(context.getApplicationContext(),
                  RoomDB.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return database;
    }

//we have to create an instance of our  data access object or main DAO

    public abstract MainDAO mainDAO();




//our database is now ready



}
