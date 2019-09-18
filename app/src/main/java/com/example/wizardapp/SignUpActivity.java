package com.example.wizardapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {


    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    private ProgressDialog loadingbar;
    private EditText Inputname, Inputemail, Inputphone, Inputpassword;
    private Button SignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Inputname = (EditText) findViewById(R.id.sign_up_name);
        Inputemail = (EditText) findViewById(R.id.sign_up_email);
        Inputphone = (EditText) findViewById(R.id.sign_up_phone_number);
        Inputpassword = (EditText) findViewById(R.id.sign_up_password);
        SignUp = (Button) findViewById(R.id.btn_sign_up);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        mAuth = FirebaseAuth.getInstance();
        loadingbar = new ProgressDialog(this);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String name = Inputname.getText().toString();
                final String email = Inputemail.getText().toString();
                final String phone = Inputphone.getText().toString();
                final String password = Inputpassword.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(SignUpActivity.this, "please write your name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(email)) {
                    Toast.makeText(SignUpActivity.this, "please type your phone email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(SignUpActivity.this, "please type your phone number", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(SignUpActivity.this, "please insert your password", Toast.LENGTH_SHORT).show();
                } else {
                    loadingbar.setMessage("please wait while we are checking credentials.");
                            loadingbar.setCanceledOnTouchOutside(false);
                    loadingbar.show();
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new
                                OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult>
                                                                   task) {
                                        if (task.isSuccessful()) {
                                            users information = new users(
                                                    name,
                                                    email,
                                                    phone,
                                                    password
                                            );
                                            FirebaseDatabase.getInstance().getReference
                                                    ("users").child(FirebaseAuth
                                                    .getInstance().getCurrentUser().getUid()).setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()){

                                                                loadingbar.dismiss();
                                                                Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);startActivity(intent);
                                                                startActivity(intent);

                                                            }
                                                            else {
                                                                Toast.makeText(SignUpActivity.this, "Failed: please try again", Toast.LENGTH_SHORT).show();

                                                            }
                                                        }

                                                    });
                                        }
                                        else {
                                            Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

            }


        });

    }
}
