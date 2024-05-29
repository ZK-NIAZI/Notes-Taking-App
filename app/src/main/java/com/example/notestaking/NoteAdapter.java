package com.example.notestaking;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<Note> notes;

    public NoteAdapter(List<Note> notes) {
        this.notes = notes;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView noteTitleTextView;
        public TextView noteTextView;
        public TextView dateTextView; // New TextView for date
        public TextView dayTextView; // New TextView for day

        public ViewHolder(View itemView) {
            super(itemView);
            noteTitleTextView = itemView.findViewById(R.id.noteTitleTextView);
            noteTextView = itemView.findViewById(R.id.noteTextView);
            dateTextView = itemView.findViewById(R.id.datetext);
            dayTextView = itemView.findViewById(R.id.daytext);

        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.noteTitleTextView.setText(note.getTitle());
        holder.noteTextView.setText(note.getText());
        // Display the date and day
        holder.dateTextView.setText(note.getDate());
        holder.dayTextView.setText(note.getDay());

        // Set the long click listener and tag the position
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.inflate(R.menu.context_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.menu_delete) {
                            // Delete the selected note
                            int position = (int) view.getTag();
                            //deleteNote(position);
                            notes.remove(position);
                            notifyItemRemoved(position);
                            Toast.makeText(view.getContext(), position+"    Deleted", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return true;
            }
        });

        // Tag the position to the view
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}

