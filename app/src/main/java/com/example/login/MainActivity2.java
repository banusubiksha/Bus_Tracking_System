package com.example.login;

import android.app.Application;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity2 extends AppCompatActivity {
    EditText us,pass,cpass;
    Button b;
    FirebaseAuth mAuth;
    ProgressBar pr;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent i = new Intent(getApplicationContext(), MainActivity3.class);
            startActivity(i);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main2);
        mAuth=FirebaseAuth.getInstance();
        us=(EditText)findViewById(R.id.user);
        pass=(EditText)findViewById(R.id.pass);
        cpass=(EditText)findViewById(R.id.passwd);
        b=(Button) findViewById(R.id.button1);
        pr=(ProgressBar)findViewById(R.id.progress);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pr.setVisibility(View.VISIBLE);
                String user = us.getText().toString();
                String pas = pass.getText().toString();
                String cpas = cpass.getText().toString();
                if (user.equals("") || pas.equals("") || cpas.equals("")) {
                    Toast.makeText(MainActivity2.this, "please enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (!(pas.equals(cpas))) {
                        Toast.makeText(MainActivity2.this, "Enter Correct Password", Toast.LENGTH_LONG).show();
                    }
                    mAuth.createUserWithEmailAndPassword(user, pas)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    pr.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {

                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(MainActivity2.this, "Account Created",
                                                Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(MainActivity2.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });
    }

}
