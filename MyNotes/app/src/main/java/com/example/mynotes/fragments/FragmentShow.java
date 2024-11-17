package com.example.mynotes.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.mynotes.R;
import com.example.mynotes.adapters.NotesListViewAdapter;
//import com.example.mynotes.db.DatabaseCallback;
import com.example.mynotes.db.DatabaseManager;
import com.example.mynotes.model.Note;

import java.sql.SQLDataException;
import java.util.List;

public class FragmentShow extends Fragment {
    private DatabaseManager databaseManager;
    private NotesListViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseManager = DatabaseManager.getInstance(getActivity());
        try {
            databaseManager.open();
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show, container, false);
        ListView listView = view.findViewById(R.id.contentListView);

        List<Note> notes = databaseManager.fetchAllNotes();
        adapter = new NotesListViewAdapter(getActivity(), notes);
        listView.setAdapter(adapter);

        return view;
    }

    public void refreshNotes() {
        List<Note> notes = databaseManager.fetchAllNotes();
        adapter.updateData(notes);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshNotes();
    }
}