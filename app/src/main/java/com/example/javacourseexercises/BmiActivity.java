package com.example.javacourseexercises;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BmiActivity extends AppCompatActivity {

    private EditText input_weight;
    private EditText input_height;
    private TextView showBmi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        findViews();
    }

    private void findViews() {
        input_weight = findViewById(R.id.input_weight);
        input_height = findViewById(R.id.input_height);
        showBmi = findViewById(R.id.showBmi);
        Button bmiInfo = findViewById(R.id.btn_bmiInfo);
        bmiInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(BmiActivity.this )
                        .setTitle("What's BMI")
                        .setMessage("The BMI is a convenient rule of thumb used to broadly categorize a person as based on tissue mass (muscle, fat, and bone) and height. ")
                        .setPositiveButton("OK",null)
                        .show();
            }
        });
    }

    public void bmi(View view) {
        String input_weightString = input_weight.getText().toString();
        String input_heightString = input_height.getText().toString();
        Float input_weightFloat = Float.parseFloat(input_weightString);
        Float input_heightFloat = Float.parseFloat(input_heightString);

        Float bmi = input_weightFloat / (input_heightFloat * input_heightFloat);

        //在LogCat查看
        Log.d("BmiActivity","BMI："+bmi);
        //直接會跳一個Toast小框框
        Toast.makeText(this,"You're BMI is："+bmi,Toast.LENGTH_LONG).show();
        //直接顯示在畫面上
        showBmi.setText("" + bmi);
        //使用對話框顯示訊息
        new AlertDialog.Builder(this)
                .setTitle("BMI")
                .setMessage("You're BMI is" + bmi)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        input_weight.setText("");
                        input_height.setText("");
                        showBmi.setText("");
                    }
                })
                .show();
    }

}