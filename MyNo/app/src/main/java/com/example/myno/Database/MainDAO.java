package com.example.myno.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myno.Models.Notes;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDAO {

    //we need to create e method to insert the data to our database //creating
    @Insert(onConflict = REPLACE)
    void insert(Notes notes);



    //we need to create e method to get all the data to our database //reading
    @Query("SELECT * FROM notes ORDER BY id DESC ")//star * means selecting all the items.here we are selecting from order by id
    List<Notes> getAll();



    //we need to create e method to update the data to our database//updating
    @Query("UPDATE notes SET title=:title,notes=:notes WHERE ID=:id")
    void update(int id, String title, String notes);

    //we need to create e method to deleting the data from our database//deleting
    @Delete
    void  delete(Notes notes);

//we need to create another abstract class for our room database















}

