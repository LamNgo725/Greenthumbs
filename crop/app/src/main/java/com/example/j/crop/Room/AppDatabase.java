package com.example.j.crop.Room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.j.crop.Notes.Note;
import com.example.j.crop.Photos.Photo;
import com.example.j.crop.Plants.Plant;

/**
 * Created by lhn41 on 4/2/2018.
 */
@Database(entities = {Plant.class, Note.class, Photo.class}, version = 1)
@TypeConverters({DateRoomConverter.class})
public abstract class AppDatabase extends RoomDatabase{
    public abstract DatabaseFunctions databaseFunc();

    private static AppDatabase plantDB;
    public static  AppDatabase getInstance(Context context) {
        if (null == plantDB) {
            plantDB = buildDatabaseInstance(context);
        }        return plantDB;
    }

    private static AppDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class,
                "plantdb.db").allowMainThreadQueries().build();
    }

    public  void cleanUp(){
        plantDB = null;
    }
}