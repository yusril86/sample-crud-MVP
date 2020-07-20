package com.pareandroid.note_mvppattern;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.RecyclerViewAdapter> {
    private Context context;
    private List<Notes> notes;
    private ItemClickListener itemClickListener;

    public MainAdapter(Context context, List<Notes> notes, ItemClickListener itemClickListener) {
        this.context = context;
        this.notes = notes;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_note,
                viewGroup, false);
        return new RecyclerViewAdapter(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter recyclerViewAdapter, int i) {
        Notes note = notes.get(i);
        recyclerViewAdapter.tv_tittle.setText(note.getTittle());
        recyclerViewAdapter.tv_note.setText(note.getNote());
        recyclerViewAdapter.tv_date.setText(note.getDate());
        recyclerViewAdapter.card_item.setCardBackgroundColor(note.getColor());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_tittle,tv_note,tv_date;
        CardView card_item;
        ItemClickListener itemClickListener;

         RecyclerViewAdapter(@NonNull View itemView,ItemClickListener itemClickListener) {
            super(itemView);

            tv_tittle = itemView.findViewById(R.id.tittle);
            tv_note = itemView.findViewById(R.id.note);
            tv_date = itemView.findViewById(R.id.date);
            card_item = itemView.findViewById(R.id.card_item);

            this.itemClickListener = itemClickListener;
            card_item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.OnitemClick(v,getAdapterPosition());
        }
    }

    public interface ItemClickListener{
        void OnitemClick(View view, int position);
    }
}
