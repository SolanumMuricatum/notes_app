package com.example.mynotes.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mynotes.db.DatabaseManager;
import com.example.mynotes.fragments.FragmentAdd;
import com.example.mynotes.fragments.FragmentDel;
import com.example.mynotes.fragments.FragmentShow;
import com.example.mynotes.fragments.FragmentUpdate;

public class NotesPagerAdapter extends FragmentStateAdapter {

    public NotesPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentShow();
            case 1:
                return new FragmentAdd();
            case 2:
                return new FragmentUpdate();
            case 3:
                return new FragmentDel();
            default:
                return new FragmentShow();
        }
    }

    @Override
    public int getItemCount() {
        return 4; // Количество страниц
    }
}

