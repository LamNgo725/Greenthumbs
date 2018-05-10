package com.example.j.crop.Plants;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
/**import android.graphics.Bitmap;*/

/**
 * http://www.androiddeft.com/2017/05/06/android-storing-data-using-sqlite-database/
 * Bases on the link above.
 * Created by lhn41 on 3/28/2018.
 */
@Entity(tableName ="plant")
public class Plant implements Serializable
{

    @ColumnInfo(name = "plantName")
    private String plant_name;

    private long note_id;

    @PrimaryKey(autoGenerate = true)
    private long plant_id;

    private int x, y;

    public void setPlant_name(String name)
    {
        plant_name = name;
    }


    public String getPlant_name()
    {
        return plant_name;
    }

    public long getNote_id() {
        return note_id;
    }

    public void setNote_id(long note_id) {
        this.note_id = note_id;
    }

    public long getPlant_id() {
        return plant_id;
    }

    public void setPlant_id(long plant_id) {
        this.plant_id = plant_id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
