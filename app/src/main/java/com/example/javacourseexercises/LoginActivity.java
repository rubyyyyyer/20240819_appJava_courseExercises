package com.example.javacourseexercises;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText input_ids_et;
    private EditText input_pwd_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViews();
    }

    private void findViews() {
        input_ids_et = findViewById(R.id.input_ids);
        input_pwd_et = findViewById(R.id.input_pwd);
    }


    public void login(View v) {

        String input_ids_tos = input_ids_et.getText().toString();
        final String input_pwd_tos = input_pwd_et.getText().toString();

/*

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");*/

       FirebaseDatabase.getInstance().getReference("users")
                .child(input_ids_tos)
                .child("passwd")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String firebasePWD = (String) snapshot.getValue();
//                        Log.d(TAG, "firebasePWD: "+firebasePWD);
                        if (input_pwd_tos.equals(firebasePWD)){
                            Toast.makeText(LoginActivity.this,"登入成功",Toast.LENGTH_LONG)
                                    .show();
                            setResult(RESULT_OK);
                            finish();

                        }else {
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("帳密錯誤")
                                    .setMessage("請重新輸入")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            input_ids_et.setText("");
                                            input_pwd_et.setText("");
                                        }
                                    })
                                    .show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


 /*       if (input_ids_tos.equals("ruby") && input_pwd_tos.equals("1234")){
            Toast.makeText(this, "登入成功", Toast.LENGTH_SHORT)
                    .show();
        }*/


    }

}