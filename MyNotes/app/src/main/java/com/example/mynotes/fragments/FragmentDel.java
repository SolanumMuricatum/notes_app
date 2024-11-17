package com.example.mynotes.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mynotes.R;
import com.example.mynotes.adapters.NotesListViewAdapter;
//import com.example.mynotes.db.DatabaseCallback;
import com.example.mynotes.db.DatabaseManager;
import com.example.mynotes.model.Note;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FragmentDel extends Fragment /*implements DatabaseCallback*/ {
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
        View view = inflater.inflate(R.layout.fragment_delete, container, false);

        Button deleteButton = view.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(v -> {
            deleteNote(view);
        });

        return view;
    }

    public void deleteNote(View view) {
        TextInputEditText textInputEditText = view.findViewById(R.id.deleteIdNote);
        String noteIdOrderStr = Objects.requireNonNull(textInputEditText.getText()).toString().trim();

        if (!noteIdOrderStr.isEmpty()) {
            try {
                long noteIdOrder = Long.parseLong(noteIdOrderStr);

                // Проверяем, существует ли заметка перед удалением
                List<Note> notes = databaseManager.fetchAllNotes();
                boolean exists = false;
                for (Note note : notes) {
                    if (note.getId_order() == noteIdOrder) {
                        exists = true;
                        break;
                    }
                }

                if (exists) {
                    databaseManager.delete(noteIdOrder);
                    textInputEditText.setText(""); // Очистка поля ввода
                    Toast.makeText(getActivity(), "Заметка успешно удалена!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Заметка с таким ID не найдена!", Toast.LENGTH_LONG).show();
                }

            } catch (NumberFormatException e){
                Toast.makeText(getActivity(), "Пожалуйста, введите корректный ID заметки", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getActivity(), "Вы ничего не ввели:(", Toast.LENGTH_LONG).show();
        }
    }

}
