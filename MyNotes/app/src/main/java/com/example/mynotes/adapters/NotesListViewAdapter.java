package com.example.mynotes.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.mynotes.R;
import com.example.mynotes.model.Note;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class NotesListViewAdapter extends BaseAdapter {
    protected final Context context;
    protected final List<Note> notes;

    public NotesListViewAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int i) {
        return notes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_component, parent, false);

            holder = new ViewHolder();
            holder.noteIdOrder = convertView.findViewById(R.id.idOfNote);
            holder.noteName = convertView.findViewById(R.id.nameOfNote);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Note note = notes.get(position);
        holder.noteIdOrder.setText(String.valueOf(note.getId_order()));
        holder.noteName.setText(note.getName());

        return convertView;
    }

    static class ViewHolder {
        TextView noteIdOrder;
        TextView noteName;
    }

    public void updateData(List<Note> newNotes){
        this.notes.clear();
        this.notes.addAll(newNotes);
        notifyDataSetChanged();
    }
}
