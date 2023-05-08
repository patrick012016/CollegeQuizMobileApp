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

public class HomeFragment extends Fragment {

    /*
     * Inicjowanie elementów z widoku
     */
    ImageButton qrButton;
    EditText codeInput;
    Button startGame;
    String token;
    /*
     * Elementy odpowiedzialne za biblioteke QR
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

    /*
     * Funkcja odpowiedzialna za otwieranie okna skanera QR po wciśnięciu przycisku przez użytkownika
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        qrButton = getView().findViewById(R.id.qrButton);
        codeInput = getView().findViewById(R.id.qrInput);
        startGame = getView().findViewById(R.id.startButton);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                token = codeInput.getText().toString();
                if (codeInput.getText().toString() != null && Pattern.matches(CODE_REGEX, token)) {

                    Intent intent = new Intent(getActivity(), LobbyActivity.class);
                    intent.putExtra("token", token);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getActivity(), "Podaj odpowiedni kod!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanQrCode.launch(null);
            }
        });
    }

    //==============================================================================================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    //==============================================================================================

    /*
     * Funkcja pobierająca dane z kodu QR
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