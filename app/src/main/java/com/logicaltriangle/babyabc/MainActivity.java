package com.logicaltriangle.babyabc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button number, letter, info_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        number = findViewById(R.id.learn_number_btn_id);
        letter = findViewById(R.id.learn_letter_btn_id);
        info_btn = findViewById(R.id.info_btn);

        /************************Click listener for number button***************************/
        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Learn_Number_Activity.class));
                finish();
            }
        });

        /************************Click listener for letter button***************************/
        letter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Learn_Letter_Activity.class));
                finish();
            }
        });


        /************************Click listener for info button***************************/
        info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InfoActivity.class));
                finish();
            }
        });
    }
}
