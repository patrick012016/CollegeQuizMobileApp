package pl.dominikpiskor.quizapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import pl.dominikpiskor.quizapp.Fragments.HomeFragment;
import pl.dominikpiskor.quizapp.Fragments.ProfileFragment;
import pl.dominikpiskor.quizapp.Fragments.SettingsFragment;
import pl.dominikpiskor.quizapp.LocalDataBase.UserLocalStore;
import pl.dominikpiskor.quizapp.databinding.ActivityMenuBinding;

/**
 * The class responsible for rendering the main menu view
 */
public class MenuActivity extends AppCompatActivity {

    /**
     * Initializing items from the view
     */
    UserLocalStore userLocalStore;
    ActivityMenuBinding binding;

    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userLocalStore = UserLocalStore.getInstance(this);
        replaceFragment(new HomeFragment());

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

    /**
     * The method responsible for dynamically replacing views
     * @param fragment dynamic view
     */
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    //==============================================================================================

    /**
     * The method responsible for creating a notification after the user logs out
     * @param view view
     */
    public void logoutUser(View view) {
        userLocalStore.clearUserData();
        userLocalStore.setUserLoggedIn(false);

        NotificationManager mNotificationManager;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext().getApplicationContext(), "channel");
        mBuilder.setSmallIcon(R.drawable.icon_logo_foreground);
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

    /**
     * The method responsible for opening the hyperlink of the application rule terms
     * @param view view
     */
    public void termOpen(View view) {
        Uri uri = Uri.parse("https://dominikpiskor.pl/Home/Privacy");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
