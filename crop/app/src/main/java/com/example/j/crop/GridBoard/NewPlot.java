package com.example.j.crop.GridBoard;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.j.crop.Notes.Note;
import com.example.j.crop.R;

public class NewPlot extends AppCompatActivity {

    int[] dimensions = {0, 0};
    private DrawerLayout mDrawerLayout;

    static private final int [] imageIds = {
            R.drawable.broccoli_icon,
            R.drawable.carrots_icon,
            R.drawable.cucumber_icon,
            R.drawable.eggplant_icon,
            R.drawable.marijuana_icon,
            R.drawable.onion_icon,
            R.drawable.peas_icon,
            R.drawable.potato_icon,
            R.drawable.red_flower_icon,
            R.drawable.yellow_flower_icon,
            R.drawable.dirt_icon
    };
    static private final int [] SelectedImageIds = {
            R.drawable.broccoli_icon_highlighted,
            R.drawable.carrots_icon_highlighted,
            R.drawable.cucumber_icon_highlighted,
            R.drawable.eggplant_icon_highlighted,
            R.drawable.marijuana_highlighted,
            R.drawable.onion_icon_highlighted,
            R.drawable.peas_icon_highlighted,
            R.drawable.potato_icon_highlighted,
            R.drawable.red_flower_icon_highlighted,
            R.drawable.yellow_flower_icon_highlighted,
            R.drawable.dirt_icon_highlighted
    };
    int selected = 0;

    void clearSelections()
    {
        for (int i = 0; i < 11; i++) {
            ImageView temp = findViewById(i);
            temp.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageIds[i]));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_plot);

        Button button_take_picture = findViewById(R.id.button_create);
        final EditText input_one = findViewById(R.id.input_1);
        //final EditText input_two = findViewById(R.id.input_2);

        button_take_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dimensions[0] = Integer.parseInt(input_one.getText().toString());
                dimensions[1] = selected;
                to_second_activity();
            }
        });



        LinearLayout layout = (LinearLayout) findViewById(R.id.linear);
        for (int i = 0; i < 11; i++) {
            final ImageView imageView;
            imageView = new ImageView(this);
            imageView.setId(i);
            imageView.setPadding(2, 2, 2, 2);
            imageView.setImageBitmap(BitmapFactory.decodeResource(
                    getResources(), imageIds[i]));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selected = imageView.getId();
                    clearSelections();
                    imageView.setImageBitmap(BitmapFactory.decodeResource(
                            getResources(), SelectedImageIds[selected]));
                }
            });
            layout.addView(imageView);
        }







        mDrawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);


        //end chris code add


        //handles button clicks inside of side drawer
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        switch (menuItem.getItemId()) {
                            case R.id.notes:
                                startActivity(new Intent(NewPlot.this, Note.class));
                                break;

                            // case R.id.
                        }
                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });


    }

    //side drawer buttons
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.side_drawer, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void to_second_activity() {

        Intent intent = new Intent(NewPlot.this, MainActivity.class);
        intent.putExtra("DIMENSIONS", dimensions);
        startActivity(intent);
    }

}
