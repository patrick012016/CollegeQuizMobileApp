package com.example.quizapp.Fragments;

import static android.text.Html.FROM_HTML_MODE_COMPACT;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.LocalDataBase.UserLocalStore;
import com.example.quizapp.R;
import com.example.quizapp.dto.UserInfoDto;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProfileFragment extends Fragment {

    TextView username;
    TextView firstName;
    TextView lastName;
    TextView email;
    TextView subscription;
    ProgressBar progressBar;
    ConstraintLayout constraintLayout;
    UserInfoDto userInfoDto;
    private final Gson gson = new Gson();

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        username = getView().findViewById(R.id.userNameText);
        firstName = getView().findViewById(R.id.firstNameText);
        lastName = getView().findViewById(R.id.lastNameText);
        email = getView().findViewById(R.id.emailText);
        subscription = getView().findViewById(R.id.accountStatusText);
        progressBar = getView().findViewById(R.id.progressBarProfile);
        constraintLayout = getView().findViewById(R.id.constraintLayoutProfile);
        constraintLayout.setAlpha(0.4f);
        progressBar.setVisibility(View.VISIBLE);
        downloadInfo();

//        username.setText(userInfoDto.getUsername());
//        username.setText(userInfoDto.getUsername());
//        username.setText(userInfoDto.getUsername());
//        username.setText(userInfoDto.getUsername());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    public void downloadInfo()
    {
        OkHttpClient client = new OkHttpClient();
        String url = "https://dominikpiskor.pl/api/v1/dotnet/UserAPI/UserDetails";
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + UserLocalStore.getInstance(getContext()).getUserLocalDatabase())
                .build();

        client.newCall(request).enqueue(new Callback() {

            /*
             * Jeśli połączenie nie zostanie nawiązane z serwerem
             */
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                        "Nie udało się pobrać danych użytkownika", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.code() == 200) {
                    userInfoDto = gson.fromJson(response.body().string(), UserInfoDto.class);
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            constraintLayout.setAlpha(1f);
                            progressBar.setVisibility(View.INVISIBLE);
                            username.setText(userInfoDto.getUsername());
                            firstName.setText((Html.fromHtml(firstName.getText()
                                    + "<strong>" + userInfoDto.getFirstName() + "</strong>", FROM_HTML_MODE_COMPACT)));
                            lastName.setText((Html.fromHtml(lastName.getText()
                                    + "<strong>" + userInfoDto.getLastName() + "</strong>", FROM_HTML_MODE_COMPACT)));
                            email.setText((Html.fromHtml(email.getText()
                                    + "<strong>" + userInfoDto.getEmail() + "</strong>", FROM_HTML_MODE_COMPACT)));
                            if (userInfoDto.getAccountStatus() == 0) {
                                subscription.setText((Html.fromHtml(subscription.getText()
                                        + "<strong>" + "Standard" + "</strong>", FROM_HTML_MODE_COMPACT)));
                            } else if (userInfoDto.getAccountStatus() == 1) {
                                subscription.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_local_fire_gold, 0);
                                subscription.setText((Html.fromHtml(subscription.getText()
                                        + "<strong>" + "Gold" + "</strong>", FROM_HTML_MODE_COMPACT)));
                            } else if (userInfoDto.getAccountStatus() == 2) {
                                subscription.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_local_fire_platinum, 0);
                                subscription.setText((Html.fromHtml(subscription.getText()
                                        + "<strong>" + "Platinum" + "</strong>", FROM_HTML_MODE_COMPACT)));
                            }
                        });
                    } else {
                        if(getActivity() != null) {
                            getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                                    "Nie udało się pobrać danych użytkownika", Toast.LENGTH_SHORT).show());
                        }
                    }
                }
            }
        });
    }

}