package com.example.mynotes.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mynotes.R;
import com.example.mynotes.adapters.NotesListViewAdapter;
//import com.example.mynotes.db.DatabaseCallback;
import com.example.mynotes.db.DatabaseManager;
import com.example.mynotes.model.Note;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FragmentAdd extends Fragment{
    private DatabaseManager databaseManager;

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
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        Button addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            addNote(view);
        });

        return view;
    }

    public void addNote(View view) {

        TextInputEditText textInputEditText = view.findViewById(R.id.noteEditText);
        String noteText = Objects.requireNonNull(textInputEditText.getText()).toString().trim();

        if (!noteText.isEmpty()) {
            databaseManager.insert(noteText);
            textInputEditText.setText(""); // Очистка поля ввода
            Toast.makeText(getActivity(), "Заметка успешно добавлена!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Вы ничего не ввели:(", Toast.LENGTH_LONG).show();
        }
    }
}
