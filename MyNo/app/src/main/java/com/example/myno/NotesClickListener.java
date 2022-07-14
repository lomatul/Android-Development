package com.example.myno;

import androidx.cardview.widget.CardView;

import com.example.myno.Models.Notes;

public interface NotesClickListener {
    //we have to create 2 methods
    //1st to pass note items
    void onClick(Notes notes);
    //another when user will long click the item
    void onLongClick(Notes notes, CardView cardView);

}
