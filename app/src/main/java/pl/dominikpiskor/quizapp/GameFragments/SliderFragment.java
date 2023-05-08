package pl.dominikpiskor.quizapp.GameFragments;

import static pl.dominikpiskor.quizapp.Utils.Constans.HUBURL;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import pl.dominikpiskor.quizapp.LocalDataBase.UserLocalStore;
import pl.dominikpiskor.quizapp.R;
import pl.dominikpiskor.quizapp.dto.QuizDto;
import pl.dominikpiskor.quizapp.hubs.HubConnectivity;
import com.google.android.material.slider.RangeSlider;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * The class responsible for rendering the dynamic quiz with slider type
 */
public class SliderFragment extends Fragment {

    private HubConnectivity hubConnectivity = HubConnectivity.getInstance(HUBURL);

    /**
     * Initializing items from the view
     */
    String qeusetionId, mydataAnswers;
    RangeSlider rangeSlider;
    QuizDto quizDto;
    Button buttonSend;
    Button butonLeft;
    Button butonRight;

    public SliderFragment() { }

    //==============================================================================================

    public static SliderFragment newInstance() {
        SliderFragment fragment = new SliderFragment();
        return fragment;
    }

    //==============================================================================================

    @Override
    public void onResume() {
        super.onResume();
        rangeSlider.setEnabled(true);
        rangeSlider.setClickable(true);
        rangeSlider.setFocusable(true);
    }

    //==============================================================================================

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //==============================================================================================

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        rangeSlider = getView().findViewById(R.id.rangeSlider);
        buttonSend = getView().findViewById(R.id.sendSlider);
        butonRight = getView().findViewById(R.id.buttonLeftToRight);
        butonLeft = getView().findViewById(R.id.buttonRightToLeft);
        mydataAnswers = bundle.getString("data");
        qeusetionId = bundle.getString("id");

        quizDto = new Gson().fromJson(mydataAnswers, QuizDto.class);
        rangeSlider.setValueFrom((float)quizDto.getMin());
        rangeSlider.setValueTo((float)quizDto.getMax());
        rangeSlider.setStepSize(quizDto.getStep());
        rangeSlider.setValues((float)quizDto.getMin(), (float)quizDto.getMax());

        butonLeft.setOnClickListener(v -> {
            List<Float> values = rangeSlider.getValues();
            rangeSlider.setValues((float)values.get(1), values.get(1));
        });

        butonRight.setOnClickListener(v -> {
            List<Float> values = rangeSlider.getValues();
            rangeSlider.setValues((float)values.get(0), values.get(0));
        });

        buttonSend.setOnClickListener(v -> {
            List<Float> answerValues = rangeSlider.getValues();
            float min = answerValues.get(0);
            float max = answerValues.get(1);
            view.performHapticFeedback(
                    HapticFeedbackConstants.VIRTUAL_KEY,
                    HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
            );
            answerSend((int)min, (int)max);
            rangeSlider.setEnabled(false);
            rangeSlider.setClickable(false);
            rangeSlider.setFocusable(false);
        });
    }

    //==============================================================================================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_slider, container, false);
    }

    //==============================================================================================

    /**
     * The method responsible for sending answers selected by the user
     * @param min min selected value
     * @param max max selected value
     */
    public void answerSend(int min, int max) {
        String ipConnection = hubConnectivity.getIpConenction();
        OkHttpClient client = new OkHttpClient();

        String url = "https://dominikpiskor.pl/api/v1/dotnet/QuizSessionAPI/SendAnswerJwt/" +
                ipConnection + "/" + qeusetionId + "/" + "r" + min + "," + max + "/false";
        Request request = new Request.Builder()
                .url(url)
                .method("post", null)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + UserLocalStore.getInstance(getContext()).getUserLocalDatabase())
                .header("Connection", "close")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) { e.printStackTrace();}
            @Override
            public void onResponse(Call call, Response response) throws IOException { }
        });
    }
}
