package com.example.j.crop.Photos;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.j.crop.Room.AppDatabase;

import java.lang.ref.WeakReference;

/**
 * Created by J on 4/27/2018.
 */

public class AddPhoto extends AppCompatActivity {

    private AppDatabase plantDatabase;
    private Photo photo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        plantDatabase = AppDatabase.getInstance(AddPhoto.this);

        photo = new Photo();

        //int x,y;

       // Bundle intent = getIntent().getExtras();
       // x = intent.getInt("x");
      //  y = intent.getInt("y");

       // plant.setX(x);
        //plant.setY(y);

        new AddPhoto.InsertTask(AddPhoto.this, photo).execute();
    }

    private void setResult(Photo photo, int flag){
        setResult(flag,new Intent().putExtra("photo", photo));
        finish();
    }

    private static class InsertTask extends AsyncTask<Void, Void, Boolean> {

        private WeakReference<AddPhoto> activityReference;
        private Photo photo;

        // only retain a weak reference to the activity
        InsertTask(AddPhoto context, Photo photo) {
            activityReference = new WeakReference<>(context);
            this.photo = photo;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            long j = activityReference.get().plantDatabase.databaseFunc().insertPhoto(photo);
            photo.setPhoto_id(j);
          //  Log.e("Photo ID ", "doInBackground: "+j );
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool) {
                activityReference.get().setResult(photo, 1);
                activityReference.get().finish();
            }
        }
    }
}
