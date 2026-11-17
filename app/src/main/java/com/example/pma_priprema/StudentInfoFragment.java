package com.example.pma_priprema;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class StudentInfoFragment extends Fragment {

    public StudentInfoFragment() {
        super(R.layout.fragment_student_info);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText inputPredmet = view.findViewById(R.id.inputPredmet);
        EditText inputProfesor = view.findViewById(R.id.inputProfesor);
        EditText inputSatiPR = view.findViewById(R.id.inputSatiPR);
        EditText inputSatiLV = view.findViewById(R.id.inputSatiLV);

        Switch switchIzborni = view.findViewById(R.id.switchIzborni);

        Button buttonDalje = view.findViewById(R.id.buttonDalje);

        buttonDalje.setOnClickListener(v -> {

            CreateNewRecordActivity activity =
                    (CreateNewRecordActivity) requireActivity();

            Student student = activity.getStudent();

            student.setPredmet(inputPredmet.getText().toString());
            student.setProfesor(inputProfesor.getText().toString());

            int satiPR = inputSatiPR.getText().toString().isEmpty()
                    ? 0 : Integer.parseInt(inputSatiPR.getText().toString());

            int satiLV = inputSatiLV.getText().toString().isEmpty()
                    ? 0 : Integer.parseInt(inputSatiLV.getText().toString());

            student.setSatiPR(satiPR);
            student.setSatiLV(satiLV);

            student.setIzborni(switchIzborni.isChecked());

            activity.getViewPager().setCurrentItem(2, true);
        });
    }
}
