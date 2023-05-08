package pl.dominikpiskor.quizapp.GameFragments;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

/**
 * The class responsible for rendering the dynamic image view in the quiz with four answers
 */
public class BodyImageFragment extends Fragment {

    /**
     * Initializing items from the view
     */
    ImageView imageView;
    Dialog dialog;
    String imgUrl;
    Bitmap bitmap;
    ImageView imageDialog;

    public BodyImageFragment() { }

    //==============================================================================================

    public static BodyImageFragment newInstance() {
        BodyImageFragment fragment = new BodyImageFragment();
        return fragment;
    }

    //==============================================================================================

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = getView().findViewById(R.id.imageQuizBody);
        Bundle bundle = this.getArguments();
        imgUrl = bundle.getString("img");
        if(!imgUrl.isEmpty()) {
            imageDownload(imgUrl);
        }
        imageView.setOnClickListener(view1 -> popResizeImage());
    }

    //==============================================================================================

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //==============================================================================================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_body_image, container, false);
    }

    //==============================================================================================

    /**
     * The method responsible for showing the zoom image included in the quiz
     */
    public void popResizeImage() {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.fragment_body_image);
        if(!imgUrl.isEmpty()) {
            imageDialog = dialog.findViewById(R.id.imageQuizBody);
            imageDialog.setImageBitmap(bitmap);
        }
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    //==============================================================================================

    @Override
    public void onPause() {
        super.onPause();
        if (dialog != null)
            dialog.cancel();
    }

    //==============================================================================================

    /**
     * The method responsible for downloading data about the image for the quiz
     */
    public void imageDownload(String source) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(source)
                .header("Content-Type", "application/octet-stream")
                .header("Accept", "application/json")
                .header("Connection", "close")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    InputStream inputStream = response.body().byteStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    getActivity().runOnUiThread(() -> imageView.setImageBitmap(bitmap));

                }
            }
        });
    }
}
