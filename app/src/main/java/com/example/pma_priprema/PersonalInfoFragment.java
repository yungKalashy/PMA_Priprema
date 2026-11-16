package com.example.pma_priprema;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

public class PersonalInfoFragment extends Fragment {

    private ImageView imageFoto;
    private ActivityResultLauncher<Intent> cameraLauncher;

    public PersonalInfoFragment() {
        super(R.layout.fragment_personal_info);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText inputDatumRod = view.findViewById(R.id.inputDatumRod);

        inputDatumRod.setOnClickListener(v -> {
            Calendar kalendar = Calendar.getInstance();

            int godina = kalendar.get(Calendar.YEAR);
            int mjesec = kalendar.get(Calendar.MONTH);
            int dan = kalendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePicker = new DatePickerDialog(
                    requireContext(),
                    (datePickerView, odabranaGodina, odabraniMjesec, odabraniDan) -> {

                        String datum = odabraniDan + "." +
                                (odabraniMjesec + 1) + "." +
                                odabranaGodina + ".";

                        inputDatumRod.setText(datum);
                    },
                    godina, mjesec, dan
            );

            datePicker.show();
        });

        imageFoto = view.findViewById(R.id.imageFoto);

        Button buttonSlika = view.findViewById(R.id.buttonSlika);

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK
                            && result.getData() != null) {

                        String imageUriString =
                                result.getData().getStringExtra("imageUri");

                        if (imageUriString != null) {
                            Uri imageUri = Uri.parse(imageUriString);
                            imageFoto.setImageURI(imageUri);
                        }
                    }
                }
        );

        buttonSlika.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), CameraActivity.class);
            cameraLauncher.launch(intent);
        });
    }
}
