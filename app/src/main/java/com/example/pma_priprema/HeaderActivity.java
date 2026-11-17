package com.example.pma_priprema;

import android.os.Bundle;
import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HeaderActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Student> studenti;
    StudentAdapter adapter;

    private ActivityResultLauncher<Intent> createStudentLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header);

        recyclerView = findViewById(R.id.recyclerViewStudenti);

        studenti = new ArrayList<>();
        adapter = new StudentAdapter(studenti);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        createStudentLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK
                            && result.getData() != null) {

                        Student student = result.getData()
                                .getParcelableExtra("student");

                        if (student != null) {
                            studenti.add(student);

                            adapter.notifyItemInserted(
                                    studenti.size() - 1
                            );
                        }
                    }
                }
        );

        FloatingActionButton floatingActionButton =
                findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(
                    HeaderActivity.this,
                    CreateNewRecordActivity.class
            );

            createStudentLauncher.launch(intent);
        });
    }
}
