package com.example.catmaa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    EditText edtRegEmail, edtRegPassword, edtRegNama, edtRegConfirm;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtRegNama = findViewById(R.id.edtRegNama);
        edtRegEmail = findViewById(R.id.edtRegEmail);
        edtRegPassword = findViewById(R.id.edtRegPassword);
        edtRegConfirm = findViewById(R.id.edtRegConfirm);

        mAuth = FirebaseAuth.getInstance();
    }

    public void register(View v) {
        String email, nama, password, confirm;
        nama = edtRegNama.getText().toString();
        email = edtRegEmail.getText().toString();
        password = edtRegPassword.getText().toString();
        confirm = edtRegConfirm.getText().toString();

        if (password.equals(confirm)) {
            Log.d("RegisterActivity", "Attempting to register user with email: " + email);
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d("RegisterActivity", "Registration successful");
                        FirebaseUser user = mAuth.getCurrentUser();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    } else {
                        Log.w("RegisterActivity", "Registration failed", task.getException());
                        Toast.makeText(RegisterActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Log.d("RegisterActivity", "Password and Confirm Password do not match");
            Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_LONG).show();
        }
    }

    public void toLogin(View v){
        startActivity(new Intent(this, MainActivity.class));
    }
}