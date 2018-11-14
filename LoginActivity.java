package com.example.rohitranjan.vehiclepayment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth mAuth;
    EditText editTextEmail, editTextPassword;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.emailLogin);
        editTextPassword = findViewById(R.id.passLogin);
        progressBar = findViewById(R.id.progressbar);

        findViewById(R.id.signUp_activity).setOnClickListener(this);
        findViewById(R.id.loginButton).setOnClickListener(this);

    }

    private void userLogin(){
        final String email = editTextEmail.getText().toString().trim();
        String password= editTextPassword.getText().toString().trim();

        if (email.isEmpty()||(!Patterns.EMAIL_ADDRESS.matcher(email).matches())||password.isEmpty()||(password.length()<6)){
            if(email.isEmpty()){
                editTextEmail.setError("Email is Required");
                editTextEmail.requestFocus();
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                editTextEmail.setError("Please enter a valid email");
                editTextEmail.requestFocus();

            }

            if (password.isEmpty()){
                editTextPassword.setError("Password is Required");
                editTextPassword.requestFocus();

            }

            if (password.length()<6){
                editTextPassword.setError("Minimum length of password should be 6");
                editTextPassword.requestFocus();

            }
            return;
        }



        progressBar.setVisibility(View.VISIBLE);
        if(editTextEmail.getText().toString().equals("admin@gmail.com")){
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()){
                        finish();
                        Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                        //Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()){
                        finish();
                        //Intent intent = new Intent(LoginActivity.this, ImageListActivity.class);
                        Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            if(user.getEmail().equals("admin@gmail.com")){
                finish();
                startActivity(new Intent(this, Dashboard.class));
            }
            else{
                finish();
                startActivity(new Intent(this, Dashboard.class));
            }
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signUp_activity:
                finish();
                startActivity(new Intent(this, SignupActivity.class));
                break;

            case R.id.loginButton:
                userLogin();
                break;
        }
    }


}
