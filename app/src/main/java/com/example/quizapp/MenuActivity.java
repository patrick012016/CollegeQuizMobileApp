package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.quizapp.Fragments.HomeFragment;
import com.example.quizapp.Fragments.ItemViewModel;
import com.example.quizapp.Fragments.ProfileFragment;
import com.example.quizapp.Fragments.SettingsFragment;
import com.example.quizapp.LocalDataBase.UserLocalStore;
import com.example.quizapp.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity {

    UserLocalStore userLocalStore;
    ActivityMenuBinding binding;
    ItemViewModel viewModel;

    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /*
         * Parametry startowe elementów
         */
        userLocalStore = new UserLocalStore(this);
        replaceFragment(new HomeFragment());
        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        viewModel.getSelectedItem().observe(this, item -> {
            xd(item);
        });

        /*
         * Podmienianie fragmentów widoku
         */
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case (R.id.homeIcon):
                    replaceFragment(new HomeFragment());
                    break;

                case (R.id.settingsIcon):
                    replaceFragment(new SettingsFragment());
                    break;

                case (R.id.profilIcon):
                    replaceFragment(new ProfileFragment());
                    break;
            }
            return true;
        });
    }

    //==============================================================================================

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    //==============================================================================================

    public void test(View view) {
        userLocalStore.clearUserData();
        userLocalStore.setUserLoggedIn(false);
    }

    //==============================================================================================

    public void xd(String item) {
        Toast.makeText(MenuActivity.this,
                item, Toast.LENGTH_LONG).show();
    }
    public void testwidok(View view) {
        Intent intent = new Intent(MenuActivity.this, LobbyActivity.class);
        startActivity(intent);
    }
}