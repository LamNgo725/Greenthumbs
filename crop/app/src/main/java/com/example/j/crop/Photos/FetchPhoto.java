package com.example.j.crop.Photos;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.j.crop.R;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Random;

import pub.devrel.easypermissions.EasyPermissions;


/**
 * Created by J on 4/28/2018.
 */


    public class FetchPhoto extends AppCompatActivity implements PhotoRecycler.OnItemClickListener{

        private RecyclerView myRecyclerView;
        private PhotoRecycler myRecyclerViewAdapter;
        private LinearLayoutManager linearLayoutManager;
        ImageView IMG;
        String root;
        String fname;


        /*image orientation*/
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.photo_recycler);


           // textInfo = (TextView)findViewById(R.id.info);
           // textInfo.setMovementMethod(new ScrollingMovementMethod());

            myRecyclerView = (RecyclerView)findViewById(R.id.myrecyclerview);
            linearLayoutManager =
                    new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            myRecyclerViewAdapter = new PhotoRecycler(this, this);
            myRecyclerViewAdapter.setOnItemClickListener(this);
            myRecyclerView.setAdapter(myRecyclerViewAdapter);
            myRecyclerView.setLayoutManager(linearLayoutManager);
            IMG = (ImageView) findViewById(R.id.img);


            if(Build.VERSION.SDK_INT>=24){
                try{
                    Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                    m.invoke(null);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            //check for permission
            if(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                //ask for permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1313);
                }
            }

        Button startCamera = (Button) findViewById(R.id.upload);
        Button exit = (Button) findViewById(R.id.exit);

        IMG.setOnClickListener(new View.OnClickListener() { //make the image disappear on click
            @Override
            public void onClick(View v) {
                IMG.setVisibility(View.GONE);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() { //go back to plots
            public void onClick(View v)
            {
                finish();
            }
        });

        startCamera.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v)
            {
                root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
                File myDir = new File(root + "/Greenthumb");
                myDir.mkdirs();
                Random generator = new Random();
                int n = 1000000;
                n = generator.nextInt(n);
                fname = "PlantPic" + n + ".png";
                File file = new File(myDir, fname);
                Uri outputFileUri = Uri.fromFile(file);
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

                startActivityForResult(cameraIntent, 1313);
            }
        });

            prepareGallery();
        }

        private void prepareGallery() {

            String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/Greenthumb";

            //Toast.makeText(getApplicationContext(), targetPath, Toast.LENGTH_LONG).show();
            File targetDirectory = new File(root);

            try {
                File[] files = targetDirectory.listFiles();
                for (File file : files) {
                    Uri uri = Uri.fromFile(file);
                    myRecyclerViewAdapter.add(myRecyclerViewAdapter.getItemCount(), uri);
                    //todo add photo to database
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        public void onItemClick(PhotoRecycler.ItemHolder item, int position) {

            String stringitemUri = item.getItemUri();

            /*display the selected image in a bigger view*/
            File imageFile = new File(stringitemUri);

            /*required for android 6.0+ for gallery permission*/
            String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (EasyPermissions.hasPermissions(this, galleryPermissions)) {
                //pickImageFromGallery();
            } else {
                EasyPermissions.requestPermissions(this, "Access for storage",
                        101, galleryPermissions);
            }
            final Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath()); //retrieving file
            Bitmap workingBitmap = Bitmap.createBitmap(myBitmap);
            final Bitmap trueCopy = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);
            IMG.setImageBitmap(trueCopy);
            IMG.setVisibility(View.VISIBLE);
            Toast.makeText(FetchPhoto.this, stringitemUri, Toast.LENGTH_SHORT).show();
        }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) //operation was successful
        {
            if (requestCode == 1313) //then we're taking a new photo
            {
                finish();
                Intent intent = new Intent(FetchPhoto.this, FetchPhoto.class);
                startActivity(intent); //restart the activity with the new photo in the list
            }
        }
    }
}

