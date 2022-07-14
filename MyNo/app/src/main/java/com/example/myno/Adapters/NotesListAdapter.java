package com.example.myno.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myno.Models.Notes;
import com.example.myno.NotesClickListener;
import com.example.myno.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesListAdapter extends  RecyclerView.Adapter<NotesViewHolder>{

    //we have to create an object for the context
    Context context;
    //then we have to pass a list item that we want to show
    List<Notes>list;
    //we have to create an object for a listener mainly for the onclick listener for the items

    NotesClickListener listener;


    public NotesListAdapter(Context context, List<Notes> list, NotesClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
    //we have to bind all these data with view
        //at first we will send the title to title textview
        holder.textView_title.setText(list.get(position).getTitle());
        //we are giving a horizontal scrolling effect
        holder.textView_title.setSelected(true);
        //for text view notes
        holder.textView_notes.setText(list.get(position).getNotes());
        //we are setting the date to textview date
        holder.textView_date.setText(list.get(position).getDate());
        //we are giving a horizontal scrolling effect
        holder.textView_date.setSelected(true);


        if (list.get(position).getPinned()){
            holder.imageView_pin.setImageResource(R.drawable.pin);
        }
        else {
            holder.imageView_pin.setImageResource(0);
        }
        int color_code =getRandomColor();
        holder.notes_container.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code));

        holder.notes_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });

        holder.notes_container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(list.get(holder.getAdapterPosition()),holder.notes_container);
                return true;
            }
        });

    }

//for custom color change

    private int getRandomColor(){
        List<Integer> colorCode = new ArrayList<>();
        //we are making a list of color codes then we will pick a random color code every time
        colorCode.add(R.color.color1);
        colorCode.add(R.color.color2);
        colorCode.add(R.color.color3);
        colorCode.add(R.color.color4);
        colorCode.add(R.color.color5);
        colorCode.add(R.color.color6);

        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());

        return colorCode.get(random_color);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Notes> filteredList){
        list= filteredList;
        notifyDataSetChanged();
    }
}
//we need to make another class for the view holder of notes adapter

class  NotesViewHolder extends RecyclerView.ViewHolder{
    //private static final android.R.attr R = ;

    //we need to create some objects some objects of the view item in our custom list

    CardView notes_container;
    TextView textView_notes,textView_title,textView_date;
    ImageView imageView_pin;

    //we have to initialize all this in our constructor

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        //initializing
        notes_container = itemView.findViewById(R.id.notes_container);
        textView_title = itemView.findViewById(R.id.textView_title);
        textView_notes = itemView.findViewById(R.id.textView_notes);
        textView_date = itemView.findViewById(R.id.textView_date);
        imageView_pin = itemView.findViewById(R.id.imageView_pin);
    }
}