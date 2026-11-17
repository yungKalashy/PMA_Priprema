package com.example.pma_priprema;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import java.util.Calendar;

public class PersonalInfoFragment extends Fragment {

    private ImageView imageFoto;
    private ActivityResultLauncher<Intent> cameraLauncher;

    private EditText inputIme;
    private EditText inputPrezime;
    private EditText inputDatumRod;

    public PersonalInfoFragment() {
        super(R.layout.fragment_personal_info);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputIme = view.findViewById(R.id.inputIme);
        inputPrezime = view.findViewById(R.id.inputPrezime);
        inputDatumRod = view.findViewById(R.id.inputDatumRod);

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
        Button buttonDalje = view.findViewById(R.id.buttonDalje);

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == requireActivity().RESULT_OK
                            && result.getData() != null) {

                        String imageUriString =
                                result.getData().getStringExtra("imageUri");

                        if (imageUriString != null) {
                            Uri imageUri = Uri.parse(imageUriString);
                            imageFoto.setImageURI(imageUri);

                            CreateNewRecordActivity activity =
                                    (CreateNewRecordActivity) requireActivity();

                            activity.getStudent().setFotografija(imageUri);
                        }
                    }
                }
        );

        buttonSlika.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), CameraActivity.class);
            cameraLauncher.launch(intent);
        });

        buttonDalje.setOnClickListener(v -> {

            CreateNewRecordActivity activity =
                    (CreateNewRecordActivity) requireActivity();

            Student student = activity.getStudent();

            student.setIme(inputIme.getText().toString());
            student.setPrezime(inputPrezime.getText().toString());
            student.setDatumRod(inputDatumRod.getText().toString());

            activity.getViewPager().setCurrentItem(1, true);
        });
    }
}
