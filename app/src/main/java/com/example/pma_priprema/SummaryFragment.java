package com.example.pma_priprema;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SummaryFragment extends Fragment {

    public SummaryFragment() {
        super(R.layout.fragment_summary);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CreateNewRecordActivity activity =
                (CreateNewRecordActivity) requireActivity();

        Student student = activity.getStudent();

        ImageView summaryFoto = view.findViewById(R.id.summaryFoto);

        TextView summaryImePrezime =
                view.findViewById(R.id.summaryImePrezime);

        TextView summaryDatumRod =
                view.findViewById(R.id.summaryDatumRod);

        TextView summaryPredmet =
                view.findViewById(R.id.summaryPredmet);

        TextView summaryProfesor =
                view.findViewById(R.id.summaryProfesor);

        TextView summarySatiPR =
                view.findViewById(R.id.summarySatiPR);

        TextView summarySatiLV =
                view.findViewById(R.id.summarySatiLV);

        TextView summaryIzborni =
                view.findViewById(R.id.summaryIzborni);

        Button buttonSpremi =
                view.findViewById(R.id.buttonSpremi);

        summaryImePrezime.setText(
                student.getIme() + " " + student.getPrezime());

        summaryDatumRod.setText(
                "Datum rođenja: " + student.getDatumRod());

        summaryPredmet.setText(
                "Predmet: " + student.getPredmet());

        summaryProfesor.setText(
                "Profesor: " + student.getProfesor());

        summarySatiPR.setText(
                "Sati predavanja: " + student.getSatiPR());

        summarySatiLV.setText(
                "Sati laboratorijskih vježbi: " + student.getSatiLV());

        summaryIzborni.setText(
                "Izborni predmet: " +
                        (student.isIzborni() ? "Da" : "Ne"));

        if (student.getFotografija() != null) {
            summaryFoto.setImageURI(student.getFotografija());
        }

        buttonSpremi.setOnClickListener(v -> {
            activity.spremiStudenta();
        });
    }
}
