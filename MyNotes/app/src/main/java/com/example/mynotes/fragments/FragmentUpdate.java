package com.example.mynotes.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mynotes.R;
import com.example.mynotes.db.DatabaseManager;
import com.example.mynotes.model.Note;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.SQLDataException;
import java.util.List;
import java.util.Objects;

public class FragmentUpdate extends Fragment {
    private DatabaseManager databaseManager;

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
        View view = inflater.inflate(R.layout.fragment_update, container, false);

        Button updateButton = view.findViewById(R.id.update_button);
        updateButton.setOnClickListener(v -> {
            updateNote(view);
        });

        return view;

    }

    public void updateNote(View view) {
        TextInputEditText textInputEditText = view.findViewById(R.id.updateIdNote);
        String noteIdOrderStr = Objects.requireNonNull(textInputEditText.getText()).toString().trim();

        TextInputEditText textInputEditText2 = view.findViewById(R.id.updateTextNote);
        String name = Objects.requireNonNull(textInputEditText2.getText()).toString().trim();

        if (!noteIdOrderStr.isEmpty() && !name.isEmpty()) {
            try {
                long noteIdOrder = Long.parseLong(noteIdOrderStr);

                // Проверяем, существует ли заметка перед апдейтом
                List<Note> notes = databaseManager.fetchAllNotes();
                boolean exists = false;
                long id = 0;
                for (Note note : notes) {
                    if (note.getId_order() == noteIdOrder) {
                        exists = true;
                        id = note.getId();
                        break;
                    }
                }

                if (exists) {
                    databaseManager.update(id, noteIdOrder, name);
                    textInputEditText.setText("");
                    textInputEditText2.setText("");
                    Toast.makeText(getActivity(), "Заметка успешно изменена!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Заметка с таким ID не найдена!", Toast.LENGTH_LONG).show();
                }

            } catch (NumberFormatException e){
                Toast.makeText(getActivity(), "Пожалуйста, введите корректный ID заметки", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getActivity(), "Какое-то поле осталось пустым:(", Toast.LENGTH_LONG).show();
        }
    }
}
