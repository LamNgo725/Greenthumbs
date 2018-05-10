package com.example.j.crop.Room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.j.crop.Notes.Note;
import com.example.j.crop.Photos.Photo;
import com.example.j.crop.Plants.Plant;

import java.util.List;

/**
 * Created by lhn41 on 4/2/2018.
 */
@Dao
public interface DatabaseFunctions {
    @Query("SELECT * FROM plant")
    List<Plant> getAll();

    @Query("SELECT * FROM notes")
    List<Note> getNotes();

    @Insert
    long insertPlant(Plant plant);

    @Insert
    long insertNote(Note notes);

    @Insert
    long insertPhoto(Photo photo);

    @Update
    void updateNote(Note repos);

    @Delete
    void deleteNote(Note note);

    @Delete
    void deletePlant(Plant plant);

    @Query("SELECT * FROM notes " +
            "JOIN plant ON " +
            "(notes.plant_id=plant.plant_id)" +
            "WHERE notes.plant_id=:plant_id")
    List<Note> getNotesForPlant(final long plant_id);

    @Query("SELECT plant_id from plant WHERE x=:x AND y=:y")
    long getPlantId(int x, int y);
}
