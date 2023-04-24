package com.example.quizapp.Fragments;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.R;

import io.github.g00fy2.quickie.QRResult;
import io.github.g00fy2.quickie.ScanQRCode;

public class HomeFragment extends Fragment {

    /*
     * Inicjowanie elementów z widoku
     */
    ImageButton qrButton;
    TextView test;
    ItemViewModel viewModel;
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
        test = getView().findViewById(R.id.qrLabel);
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
        String code = "";

        if (result instanceof QRResult.QRSuccess) {
            code = ((QRResult.QRSuccess) result).getContent().getRawValue();
        } else if (result == QRResult.QRUserCanceled.INSTANCE) {
            Toast.makeText(getActivity(), "Anulowano skanowanie", Toast.LENGTH_SHORT).show();
            viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
            viewModel.setData("dziala widzowie pogchmip");
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