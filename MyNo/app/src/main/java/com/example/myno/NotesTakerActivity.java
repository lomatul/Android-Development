package com.example.myno;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myno.Models.Notes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesTakerActivity extends AppCompatActivity {
    //declare
    EditText editText_title,editText_notes;
    ImageView imageView_save;
    Notes notes;
    boolean isOldNote = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_taker);

        imageView_save = findViewById(R.id.imageView_save);
        editText_title =findViewById(R.id.editText_title);
        editText_notes = findViewById(R.id.editText_notes);

        notes = new Notes();
        notes = (Notes) getIntent().getSerializableExtra("old_note");

        try {
            notes = (Notes) getIntent().getSerializableExtra("old_note");
            editText_title.setText(notes.getTitle());
            editText_notes.setText(notes.getNotes());
            isOldNote = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }



        imageView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we will get title and description and send to mainActivity

                String title = editText_title.getText().toString();
                String description = editText_notes.getText().toString();

                if (description.isEmpty()){
                    Toast.makeText(NotesTakerActivity.this,"please add some notes",Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleDateFormat formatter = new SimpleDateFormat("EEE,d MMM yyy HH:mm a");

                Date date= new Date();

                if (!isOldNote)
                {
                    notes = new Notes();
                }

                notes.setTitle(title);
                notes.setNotes(description);
                notes.setDate(formatter.format(date));

                Intent intent = new Intent();
                intent.putExtra("note",notes);

                setResult(Activity.RESULT_OK,intent);
                finish();

            }
        });
    }
}