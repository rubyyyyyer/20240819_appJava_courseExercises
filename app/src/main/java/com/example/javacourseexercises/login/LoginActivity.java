package com.example.javacourseexercises.login;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.javacourseexercises.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText input_ids_et;
    private EditText input_pwd_et;
    private CheckBox cb_rem_ids;
    private CheckBox cb_rem_pwd;

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
        String userID = getSharedPreferences("Logon", MODE_PRIVATE)
                .getString("ids", "");

        input_ids_et.setText(userID);


        input_pwd_et = findViewById(R.id.input_pwd);
        String userPWD = getSharedPreferences("Logon", MODE_PRIVATE)
                .getString("pwd", "");
        input_pwd_et.setText(userPWD);
        cb_rem_ids = findViewById(R.id.cb_rem_ids);
        cb_rem_pwd = findViewById(R.id.cb_rem_pwd);
        cb_rem_ids.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cb_rem_ids.isChecked()) {
                    getSharedPreferences("Logon", MODE_PRIVATE)
                            .edit()
                            .putBoolean("REM_IDS", isChecked)
                            .commit();
                } else {
                    cb_rem_pwd.setChecked(false);
                }

            }
        });
        cb_rem_pwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cb_rem_ids.isChecked()) {
                    getSharedPreferences("Logon", MODE_PRIVATE).
                            edit().
                            putBoolean("REM_PWD", isChecked).
                            commit();
                } else {
                    cb_rem_pwd.setChecked(false);
                }

            }
        });
        cb_rem_ids.setChecked(
                getSharedPreferences("Logon", MODE_PRIVATE).getBoolean("REM_IDS", false));
        cb_rem_pwd.setChecked(
                getSharedPreferences("Logon", MODE_PRIVATE).getBoolean("REM_PWD", false));

    }


    public void login(View v) {

        String input_ids_tos = input_ids_et.getText().toString();
        final String input_pwd_tos = input_pwd_et.getText().toString();
        final Boolean remember_ids = getSharedPreferences("Logon", MODE_PRIVATE)
                .getBoolean("REM_IDS", false);
        final Boolean remember_pwd = getSharedPreferences("Logon", MODE_PRIVATE)
                .getBoolean("REM_PWD", false);

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
                        if (input_pwd_tos.equals(firebasePWD)) {
                            Toast.makeText(LoginActivity.this, getSharedPreferences("Logon", MODE_PRIVATE).getString("ids", "") + ", 登入成功", Toast.LENGTH_LONG)
                                    .show();
                            if (remember_ids && remember_pwd) {
                                getSharedPreferences("Logon", MODE_PRIVATE)
                                        .edit()
                                        .putString("ids", input_ids_tos)
                                        .putString("pwd", input_pwd_tos)
                                        .commit();
                            } else if (remember_ids && !remember_pwd) {
                                getSharedPreferences("Logon", MODE_PRIVATE)
                                        .edit()
                                        .putString("ids", input_ids_tos)
                                        .putString("pwd", "")
                                        .commit();
                            } else {
                                getSharedPreferences("Logon", MODE_PRIVATE)
                                        .edit()
                                        .putString("ids", "")
                                        .putString("pwd", "")
                                        .commit();
                            }

                            setResult(RESULT_OK);
                            finish();

                        } else {
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("帳密錯誤")
                                    .setMessage("請重新輸入")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (remember_ids && remember_pwd) {
                                                input_ids_et.setText(getSharedPreferences("Logon", MODE_PRIVATE)
                                                        .getString("ids", ""));
                                                input_pwd_et.setText(getSharedPreferences("Logon", MODE_PRIVATE)
                                                        .getString("pwd", ""));
                                            } else if (remember_ids && !remember_pwd) {
                                                input_pwd_et.setText("");
                                            } else {
                                                input_ids_et.setText("");
                                                input_pwd_et.setText("");
                                            }

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