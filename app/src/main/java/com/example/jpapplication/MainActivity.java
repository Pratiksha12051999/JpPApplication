package com.example.jpapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText email;
    EditText pass1;
    Button loginButton;
    TextView notRegistered;
    FirebaseAuth fAuth;
    CheckBox showPassword;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        pass1 = findViewById(R.id.pass1);
        loginButton = findViewById(R.id.registerButton);
        notRegistered = findViewById(R.id.alreadyRegistered);
        showPassword = findViewById(R.id.showPassword);

        notRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RegisterIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(RegisterIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString().trim();
                String pwd = pass1.getText().toString().trim();
                if(mail.isEmpty()){
                    email.setError("Please enter an Email");
                    email.requestFocus();
                }
                else if(pwd.isEmpty())
                {
                    pass1.setError("Please enter a password");
                    pass1.requestFocus();
                }
                else if(!(mail.isEmpty()) && !(pwd.isEmpty())){
                    fAuth.signInWithEmailAndPassword(mail,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "User logged in", Toast.LENGTH_SHORT).show();
                                Intent goToAllTabs = new Intent(MainActivity.this, TabsActivity.class);
                                startActivity(goToAllTabs);
                            }
                            else{
                                Toast.makeText(MainActivity.this, "User Log In failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    pass1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    pass1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}