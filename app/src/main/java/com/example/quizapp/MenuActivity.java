package com.example.quizapp;

import static com.example.quizapp.Utils.Constans.HUBURL;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.quizapp.Fragments.HomeFragment;
import com.example.quizapp.Fragments.ItemViewModel;
import com.example.quizapp.Fragments.ProfileFragment;
import com.example.quizapp.Fragments.SettingsFragment;
import com.example.quizapp.LocalDataBase.UserLocalStore;
import com.example.quizapp.databinding.ActivityMenuBinding;
import com.example.quizapp.hubs.HubConnectivity;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

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
        userLocalStore = UserLocalStore.getInstance(this);
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

    public void logoutUser(View view) {
        userLocalStore.clearUserData();
        userLocalStore.setUserLoggedIn(false);

        NotificationManager mNotificationManager;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext().getApplicationContext(), "channel");
        mBuilder.setSmallIcon(R.drawable.watermarkdarkdefualt);
        mBuilder.setContentTitle("Quizazu");
        mBuilder.setContentText("Poprawnie wylogowano z aplikacji Quizazu");
        mBuilder.setAutoCancel(false);
        mBuilder.setContentIntent(null);
        mBuilder.setPriority(Notification.PRIORITY_DEFAULT);
        mNotificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "channel";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "channelNotify",
                    NotificationManager.IMPORTANCE_LOW);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }
        mNotificationManager.notify(0, mBuilder.build());
        finish();
        System.exit(0);
    }

    public void termOpen(View view) {
        Uri uri = Uri.parse("https://dominikpiskor.pl/Home/Privacy");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}