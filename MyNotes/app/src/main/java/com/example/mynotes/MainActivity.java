package com.example.mynotes;

import static android.app.PendingIntent.getActivity;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mynotes.adapters.NotesListViewAdapter;
import com.example.mynotes.adapters.NotesPagerAdapter;
//import com.example.mynotes.db.DatabaseCallback;
import com.example.mynotes.db.DatabaseHelper;
import com.example.mynotes.db.DatabaseManager;
import com.example.mynotes.fragments.FragmentShow;
import com.example.mynotes.model.Note;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseManager databaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //получаю синглтон объект бд, чтобы во время завершения приложения закрыть потоек
        databaseManager = DatabaseManager.getInstance(this);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        NotesPagerAdapter adapter = new NotesPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Связываем TabLayout и ViewPager2
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Show");
                            break;
                        case 1:
                            tab.setText("Add");
                            break;
                        case 2:
                            tab.setText("Update");
                            break;
                        case 3:
                            tab.setText("Delete");
                            break;
                    }
                }
        ).attach();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseManager != null) {
            databaseManager.close(); // Закрываем базу данных
        }
    }
}