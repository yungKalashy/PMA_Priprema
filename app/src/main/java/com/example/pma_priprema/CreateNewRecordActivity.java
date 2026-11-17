package com.example.pma_priprema;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class CreateNewRecordActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private ViewPagerAdapter adapter;

    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_record);

        student = new Student("", "", "");

        viewPager = findViewById(R.id.viewPager);

        adapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(adapter);
    }

    public Student getStudent() {
        return student;
    }

    public ViewPager2 getViewPager(){
        return viewPager;
    }

    public void spremiStudenta(){
        android.content.Intent intent = new android.content.Intent();

        intent.putExtra("student", student);

        setResult(RESULT_OK, intent);
        finish();
    }
}
