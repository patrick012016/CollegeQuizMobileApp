package pl.dominikpiskor.quizapp.Fragments;

import static pl.dominikpiskor.quizapp.Utils.Constans.CODE_REGEX;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import pl.dominikpiskor.quizapp.LobbyActivity;

import java.util.regex.Pattern;

import io.github.g00fy2.quickie.QRResult;
import io.github.g00fy2.quickie.ScanQRCode;
import pl.dominikpiskor.quizapp.R;

/**
 * The class responsible for rendering the dynamic user home view
 */
public class HomeFragment extends Fragment {

    /**
     * Initializing items from the view
     */
    ImageButton qrButton;
    EditText codeInput;
    Button startGame;
    String token;

    /**
     * Elements responsible for the QR library
     * https://github.com/G00fY2/quickie
     */
    ActivityResultLauncher<?> scanQrCode = registerForActivityResult(new ScanQRCode(), this::showQR);

    //==============================================================================================

    public HomeFragment() {
    }

    //==============================================================================================

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //==============================================================================================

    /**
     * The function responsible for opening the QR scanner window after pressing the button by the user
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        qrButton = getView().findViewById(R.id.qrButton);
        codeInput = getView().findViewById(R.id.qrInput);
        startGame = getView().findViewById(R.id.startButton);
        startGame.setOnClickListener(v -> {
            token = codeInput.getText().toString();
            if (codeInput.getText().toString() != null && Pattern.matches(CODE_REGEX, token)) {

                Intent intent = new Intent(getActivity(), LobbyActivity.class);
                intent.putExtra("token", token);
                startActivity(intent);
            }
            else {
                Toast.makeText(getActivity(), "Podaj odpowiedni kod!", Toast.LENGTH_SHORT).show();
            }
        });
        qrButton.setOnClickListener(view1 -> scanQrCode.launch(null));
    }

    //==============================================================================================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    //==============================================================================================

    /**
     * A function that downloads data from a QR code
     * https://github.com/G00fY2/quickie
     */
    private void showQR(QRResult result) {
        if (result instanceof QRResult.QRSuccess) {
            token = ((QRResult.QRSuccess) result).getContent().getRawValue();
            if (token != null && Pattern.matches(CODE_REGEX, token)) {
                Intent intent = new Intent(getActivity(), LobbyActivity.class);
                intent.putExtra("token", token);
                startActivity(intent);
            }
            else {
                Toast.makeText(getActivity(), "Zeskanuj odpowiedni kod!", Toast.LENGTH_SHORT).show();
            }
        } else if (result == QRResult.QRUserCanceled.INSTANCE) {
            Toast.makeText(getActivity(), "Anulowano skanowanie", Toast.LENGTH_SHORT).show();
        } else if (result == QRResult.QRMissingPermission.INSTANCE) {
            Toast.makeText(getActivity(), "Brak zezwolenia!", Toast.LENGTH_SHORT).show();
        } else if (result instanceof QRResult.QRError) {
            String error;
            error = ((QRResult.QRError) result).getException().getClass().getSimpleName() + ": " +
                    ((QRResult.QRError) result).getException().getLocalizedMessage();
            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        }
    }
}
