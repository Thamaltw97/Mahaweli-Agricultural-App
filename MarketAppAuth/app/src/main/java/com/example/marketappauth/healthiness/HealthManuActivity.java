package com.example.marketappauth.healthiness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.marketappauth.MahaweliHomeActivity;
import com.example.marketappauth.R;

public class HealthManuActivity extends AppCompatActivity {

    private Button checkBtn;
    private Button viewbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_manu);

        checkBtn = findViewById(R.id.checkhealth);
        viewbtn = findViewById(R.id.viewhealth);

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthManuActivity.this, ClassifierActivity.class);
                startActivity(intent);
            }
        });

        viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthManuActivity.this, CropListActivity.class);
                startActivity(intent);
            }
        });
    }
}