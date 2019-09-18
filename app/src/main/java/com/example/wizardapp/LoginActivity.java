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

public class LoginActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    private ProgressDialog loadingbar;
    private EditText Inputemail, Inputpassword;
    private Button LoginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Inputemail = (EditText) findViewById(R.id.login_email);
        Inputpassword = (EditText) findViewById(R.id.login_password);
        LoginButton = (Button)findViewById(R.id.btn_login) ;

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        mAuth = FirebaseAuth.getInstance();
        loadingbar = new ProgressDialog(this);


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = Inputemail.getText().toString();
                final String password = Inputpassword.getText().toString();

                if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(LoginActivity.this, "please type your email",
                            Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(password))
                {
                    Toast.makeText(LoginActivity.this, "please input password",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loadingbar.setMessage("please wait while we are checking credentials.");
                            loadingbar.setCanceledOnTouchOutside(false);
                    loadingbar.show();
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new
                                OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult>
                                                                   task) {
                                        if (task.isSuccessful()) {
                                            startActivity(new Intent
                                                    (getApplicationContext(), HomeActivity.class));
                                            Toast.makeText(LoginActivity.this, "Logged in succesfully", Toast.LENGTH_SHORT).show();

                                        } else {
                                            loadingbar.dismiss();
                                            Toast.makeText(LoginActivity.this, "Account with" +email+ "Does not Exist, please sign up", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });

            }
        });


        


    }
}
