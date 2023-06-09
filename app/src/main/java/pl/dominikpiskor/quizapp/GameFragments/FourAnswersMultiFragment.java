package pl.dominikpiskor.quizapp.GameFragments;

import static pl.dominikpiskor.quizapp.Utils.Constans.HUBURL;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.dominikpiskor.quizapp.LocalDataBase.UserLocalStore;
import pl.dominikpiskor.quizapp.R;
import pl.dominikpiskor.quizapp.hubs.HubConnectivity;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * he class responsible for rendering the dynamic quiz with four answers (multiple correct)
 */
public class FourAnswersMultiFragment extends Fragment {

    private HubConnectivity hubConnectivity = HubConnectivity.getInstance(HUBURL);

    /**
     * Initializing items from the view
     */
    CardView cardMultiA, cardMultiB, cardMultiC, cardMultiD;
    TextView answerMultiA, answerMultiB, answerMultiC, answerMultiD;
    String qeusetionId, mydataAnswers;

    public FourAnswersMultiFragment() { }

    //==============================================================================================

    public static FourAnswersMultiFragment newInstance(String param1, String param2) {
        FourAnswersMultiFragment fragment = new FourAnswersMultiFragment();
        return fragment;
    }

    //==============================================================================================

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //==============================================================================================

    @Override
    public void onResume() {
        super.onResume();
        unlockCardResult();
    }

    //==============================================================================================

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        cardMultiA = getView().findViewById(R.id.cardMultiA);
        cardMultiB = getView().findViewById(R.id.cardMultiB);
        cardMultiC = getView().findViewById(R.id.cardMultiC);
        cardMultiD = getView().findViewById(R.id.cardMultiD);
        answerMultiA = getView().findViewById(R.id.answerMultiA);
        answerMultiB = getView().findViewById(R.id.answerMultiB);
        answerMultiC = getView().findViewById(R.id.answerMultiC);
        answerMultiD = getView().findViewById(R.id.answerMultiD);

        mydataAnswers = bundle.getString("data");
        qeusetionId = bundle.getString("id");

        String[] array = new Gson().fromJson(mydataAnswers, String[].class);
        answerMultiA.setText(array[0]);
        answerMultiB.setText(array[1]);
        answerMultiC.setText(array[2]);
        answerMultiD.setText(array[3]);

        cardMultiA.setOnClickListener(v -> {
            answerSend(0);
            view.performHapticFeedback(
                    HapticFeedbackConstants.VIRTUAL_KEY,
                    HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
            );
            blockCardResult(cardMultiA);
            setScale(cardMultiA);
        });
        cardMultiB.setOnClickListener(v -> {
            answerSend(1);
            view.performHapticFeedback(
                    HapticFeedbackConstants.VIRTUAL_KEY,
                    HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
            );
            blockCardResult(cardMultiB);
            setScale(cardMultiB);
        });
        cardMultiC.setOnClickListener(v -> {
            answerSend(2);
            view.performHapticFeedback(
                    HapticFeedbackConstants.VIRTUAL_KEY,
                    HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
            );
            blockCardResult(cardMultiC);
            setScale(cardMultiC);
        });
        cardMultiD.setOnClickListener(v -> {
            answerSend(3);
            view.performHapticFeedback(
                    HapticFeedbackConstants.VIRTUAL_KEY,
                    HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
            );
            blockCardResult(cardMultiD);
            setScale(cardMultiD);
        });
    }

    //==============================================================================================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_four_answers_multi, container, false);
    }

    //==============================================================================================

    /**
     * A helper method responsible for setting the size of the clicked card
     * @param one checked card
     */
    public void setScale(CardView one) {
        one.setScaleX(0.85f);
        one.setScaleY(0.85f);
    }

    //==============================================================================================

    /**
     * A helper method responsible for blocking clicked card
     * @param one unchecked card
     */
    public void blockCardResult(CardView one) {
        one.setFocusable(false);
        one.setClickable(false);
    }

    //==============================================================================================

    /**
     * A helper method responsible for resetting default view parameters
     */
    public void unlockCardResult() {
        cardMultiA.setFocusable(true);
        cardMultiA.setClickable(true);
        cardMultiB.setFocusable(true);
        cardMultiB.setClickable(true);
        cardMultiC.setFocusable(true);
        cardMultiC.setClickable(true);
        cardMultiD.setFocusable(true);
        cardMultiD.setClickable(true);
        cardMultiA.setScaleY(1);
        cardMultiB.setScaleY(1);
        cardMultiC.setScaleY(1);
        cardMultiD.setScaleY(1);
        cardMultiA.setScaleX(1);
        cardMultiB.setScaleX(1);
        cardMultiC.setScaleX(1);
        cardMultiD.setScaleX(1);
    }

    //==============================================================================================

    /**
     * The method responsible for sending answers selected by the user
     * @param idAnswer id of the selected answer
     */
    public void answerSend(int idAnswer) {
        String ipConnection = hubConnectivity.getIpConenction();
        OkHttpClient client = new OkHttpClient();

        String url = "https://dominikpiskor.pl/api/v1/dotnet/QuizSessionAPI/SendAnswerJwt/" + ipConnection + "/" + qeusetionId + "/" + idAnswer + "/true";
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
