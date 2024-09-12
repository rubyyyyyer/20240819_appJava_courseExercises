package com.example.javacourseexercises;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class BmiActivity extends AppCompatActivity {


    private static final String TAG = BmiActivity.class.getSimpleName();
    private EditText input_weight_et;
    private EditText input_height_et;
    private TextView result_bmi_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        findViews();

    }

    private void findViews() {
        input_weight_et = findViewById(R.id.input_weight);
        input_height_et = findViewById(R.id.input_height);

        result_bmi_tv = findViewById(R.id.result_bmi);

//      information
        Button info_bmi = findViewById(R.id.info_bmi);
        info_bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(BmiActivity.this)
                        .setTitle("Infomation")
                        .setMessage("Body mass index (BMI) is a value derived from the mass (weight) and height of a person.")
                        .setPositiveButton("OK",null)
                        .show();
            }
        });
    }

    public void bmi(View v){


        String input_weight_st = input_weight_et.getText().toString();
        String input_height_st = input_height_et.getText().toString();

        Float input_weight = Float.parseFloat(input_weight_st);
        Float input_height = Float.parseFloat(input_height_st);

        Float bmi = input_weight/(input_height*input_height);



//      #1 LogCat中顯示
        Log.d(TAG, "bmi: "+bmi);
//      #2 顯示在文字方塊
        result_bmi_tv.setText(""+bmi);
//      #3 Toast
        Toast.makeText(this,"You're BMI is "+bmi,Toast.LENGTH_LONG)
                .show();
//      #4 Alert
        /*new AlertDialog.Builder(this)
                .setTitle("You're BMI is")
                .setMessage(""+bmi)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        input_weight_et.setText("");
                        input_height_et.setText("");
                        result_bmi_tv.setText("");
                    }
                })
                .show();*/

//      #5 Intent
        Intent intent = new Intent(this,BmiResultActivity.class);
        intent.putExtra("BMI",bmi);
        startActivity(intent);
    }
}