package com.example.j.crop.Notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.j.crop.Photos.FetchPhoto;
import com.example.j.crop.R;
import com.example.j.crop.Room.AppDatabase;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by J on 4/8/2018.
 * (NoteListActivity)
 */

public class AddNoteActivity extends AppCompatActivity implements NotesAdapter.OnNoteItemClick {


    private TextView textViewMsg;
    private RecyclerView recyclerView;
    private AppDatabase noteDatabase;
    private List<Note> notes;
    private NotesAdapter notesAdapter;
    private int pos;
    private Button photoIcon;
    private Button exit;
    private static int x;
    private static int y;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_recycler);


        Bundle intent = getIntent().getExtras();
        x = intent.getInt("x");
        y = intent.getInt("y");

        initializeViews();
        displayList();
    }

    private void displayList(){
        noteDatabase = AppDatabase.getInstance(AddNoteActivity.this);
        new RetrieveTask(this).execute();
    }

    private static class RetrieveTask extends AsyncTask<Void,Void,List<Note>>{

        private WeakReference<AddNoteActivity> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(AddNoteActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<Note> doInBackground(Void... voids) {
            if (activityReference.get()!=null) {
                long plant_id = activityReference.get().noteDatabase.databaseFunc().getPlantId(x, y);
                return activityReference.get().noteDatabase.databaseFunc().getNotesForPlant(plant_id);
            }
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<Note> notes) {
            if (notes!=null && notes.size()>0 ){
                activityReference.get().notes.clear();
                activityReference.get().notes.addAll(notes);
                // hides empty text view
                activityReference.get().textViewMsg.setVisibility(View.GONE);
                activityReference.get().notesAdapter.notifyDataSetChanged();
            }
        }
    }

    private void initializeViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textViewMsg =  (TextView) findViewById(R.id.tv__empty);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(listener);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(AddNoteActivity.this));
        photoIcon = (Button) findViewById(R.id.uploadPhoto);
        notes = new ArrayList<>();
        notesAdapter = new NotesAdapter(notes,AddNoteActivity.this);
        recyclerView.setAdapter(notesAdapter);
        exit = (Button) findViewById(R.id.exit);


        photoIcon.setOnClickListener(new View.OnClickListener() { //starting the photo page
            public void onClick(View v)
            {
                Intent intent = new Intent(AddNoteActivity.this, FetchPhoto.class);

                intent.putExtra("x", x);
                intent.putExtra("y", y);

                startActivity(intent);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() { //go back to plots
            public void onClick(View v)
            {
                finish();
            }
        });

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(AddNoteActivity.this, AddNote.class);

            intent.putExtra("x", x);
            intent.putExtra("y", y);

            startActivityForResult(intent, 100);

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode > 0 ){
            if( resultCode == 1){
                notes.add((Note) data.getSerializableExtra("note"));
            }else if( resultCode == 2){
                notes.set(pos,(Note) data.getSerializableExtra("note"));
            }
            listVisibility();
        }
    }

    @Override
    public void onNoteClick(final int pos) {
        new AlertDialog.Builder(AddNoteActivity.this)
                .setTitle("Select Options")
                .setItems(new String[]{"Delete", "Update"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                AddNoteActivity.this.pos = pos;
                                int holder = pos;
                                startActivityForResult(
                                        new Intent(AddNoteActivity.this,
                                                AddNote.class).putExtra("delete",notes.get(holder)),
                                        100);
                                notes.remove(pos);
                                listVisibility();
                                break;
                            case 1:
                                AddNoteActivity.this.pos = pos;
                                startActivityForResult(
                                        new Intent(AddNoteActivity.this,
                                                AddNote.class).putExtra("note",notes.get(pos)),
                                        100);
                                break;
                        }
                    }
                }).show();

    }
    private void listVisibility() {
        int emptyMsgVisibility = View.GONE;
        if (notes.size() == 0) { // no item to display
            if (textViewMsg.getVisibility() == View.GONE) emptyMsgVisibility = View.VISIBLE;
        }
        textViewMsg.setVisibility(emptyMsgVisibility);
        notesAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        noteDatabase.cleanUp();
        super.onDestroy();
    }
}

