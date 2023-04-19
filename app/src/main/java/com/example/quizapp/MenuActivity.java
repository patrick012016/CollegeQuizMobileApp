package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quizapp.Fragments.HomeFragment;
import com.example.quizapp.Fragments.ProfileFragment;
import com.example.quizapp.Fragments.SettingsFragment;
import com.example.quizapp.LocalDataBase.UserLocalStore;
import com.example.quizapp.databinding.ActivityMenuBinding;

import io.github.g00fy2.quickie.QRResult;
import io.github.g00fy2.quickie.ScanQRCode;
import io.github.g00fy2.quickie.config.BarcodeFormat;

public class MenuActivity extends AppCompatActivity {

    UserLocalStore userLocalStore;
    ActivityMenuBinding binding;

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
}