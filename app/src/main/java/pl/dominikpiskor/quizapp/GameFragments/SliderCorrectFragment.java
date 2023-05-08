package pl.dominikpiskor.quizapp.GameFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.dominikpiskor.quizapp.R;
import pl.dominikpiskor.quizapp.dto.CorrectSliderDto;
import com.google.android.material.slider.RangeSlider;
import com.google.gson.Gson;

/**
 * The helper class responsible for rendering sliders with correct answer
 */
public class SliderCorrectFragment extends Fragment {

    private final Gson gson = new Gson();

    /**
     * Initializing items from the view
     */
    RangeSlider rangeSlider;
    RangeSlider rangeSliderMid;

    public SliderCorrectFragment() {}

    //==============================================================================================

    public static SliderCorrectFragment newInstance(String param1, String param2) {
        SliderCorrectFragment fragment = new SliderCorrectFragment();
        return fragment;
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
        rangeSlider = getView().findViewById(R.id.rangeSliderCorrect);
        rangeSliderMid = getView().findViewById(R.id.rangeSliderCorrectMiddle);

        String dataCorrect = bundle.getString("dataSlider");
        CorrectSliderDto[] correctSliderDto = gson.fromJson(dataCorrect, CorrectSliderDto[].class);

        rangeSliderMid.setValueFrom(correctSliderDto[0].getAnswerMin());
        rangeSliderMid.setValueTo(correctSliderDto[0].getAnswerMax());
        rangeSliderMid.setStepSize(correctSliderDto[0].getAnswerStep());
        rangeSliderMid.setValues((float) correctSliderDto[0].getAnswerCorrect());

        rangeSlider.setValueFrom(correctSliderDto[0].getAnswerMin());
        rangeSlider.setValueTo(correctSliderDto[0].getAnswerMax());
        rangeSlider.setStepSize(correctSliderDto[0].getAnswerStep());
        rangeSlider.setValues((float) correctSliderDto[0].getAnswerMinCounted(), (float) correctSliderDto[0].getAnswerMaxCounted());
    }

    //==============================================================================================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_slider_correct, container, false);
    }
}
