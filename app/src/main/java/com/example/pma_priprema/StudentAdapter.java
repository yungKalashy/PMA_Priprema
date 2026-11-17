
package com.example.pma_priprema;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private ArrayList<Student> studenti;

    public StudentAdapter(ArrayList<Student> studenti) {
        this.studenti = studenti;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student, parent, false);

        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studenti.get(position);

        holder.textImePrezime.setText(
                student.getIme() + " " + student.getPrezime()
        );

        holder.textPredmet.setText(student.getPredmet());

        if (student.getFotografija() != null) {
            holder.imageStudent.setImageURI(student.getFotografija());
        } else {
            holder.imageStudent.setImageResource(
                    android.R.drawable.ic_menu_camera
            );
        }
    }

    @Override
    public int getItemCount() {
        return studenti.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView textImePrezime;
        TextView textPredmet;
        ImageView imageStudent;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            textImePrezime = itemView.findViewById(R.id.textImePrezime);
            textPredmet = itemView.findViewById(R.id.textPredmet);
            imageStudent = itemView.findViewById(R.id.imageStudent);
        }
    }
}
