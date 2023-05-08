package pl.dominikpiskor.quizapp.GameFragments;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pl.dominikpiskor.quizapp.R;

public class BodyPopupImageFragment extends Fragment {

    AppCompatButton appCompatButton;
    Dialog dialog;
    String imgUrl;
    Bitmap bitmap;
    ImageView imageDialog;

    public BodyPopupImageFragment() {
        // Required empty public constructor
    }

    public static BodyPopupImageFragment newInstance() {
        BodyPopupImageFragment fragment = new BodyPopupImageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        imgUrl = bundle.getString("img");
        appCompatButton = getActivity().findViewById(R.id.buttonPopImage);

        if(!imgUrl.isEmpty()) {
            imageDownload(imgUrl);
        }
        appCompatButton.setOnClickListener(view1 -> popImage());
    }

    @Override
    public void onPause() {
        super.onPause();
        if (dialog != null)
            dialog.cancel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_body_popup_image, container, false);
    }

    public void popImage() {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.fragment_body_image);
        if(!imgUrl.isEmpty()) {
            imageDialog = dialog.findViewById(R.id.imageQuizBody);
            imageDialog.setImageBitmap(bitmap);
        }
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void imageDownload(String source) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(source)
                .header("Content-Type", "application/octet-stream")
                .header("Accept", "application/json")
                .header("Connection", "close")
                .build();

        client.newCall(request).enqueue(new Callback() {
            /*
             * Jeśli połączenie nie zostanie nawiązane z serwerem
             */
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    InputStream inputStream = response.body().byteStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                }
            }
        });
    }
}