package com.example.login;

import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText e1,e2;
    Button b;
    TextView t1,t2,t3;
    FirebaseAuth mAuth;
    ProgressBar pr;
    TextView t4,t5;
    EditText e;
    Button b1,b2;
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
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        //enable full screen

        t1=(TextView)findViewById(R.id.signin);
        e1=(EditText) findViewById(R.id.username);
        e2=(EditText) findViewById(R.id.password);
        b=(Button) findViewById(R.id.button);
        t2=(TextView) findViewById(R.id.textview);
        t3=(TextView)findViewById(R.id.signup);
        pr=(ProgressBar)findViewById(R.id.progress);
        mAuth=FirebaseAuth.getInstance();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pr.setVisibility(View.VISIBLE);
                String email=e1.getText().toString();
                String pass=e2.getText().toString();
                if(email.equals("")||pass.equals("")){
                    Toast.makeText(MainActivity.this,"please enter all fields",Toast.LENGTH_SHORT).show();
                }
                mAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pr.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(getApplicationContext(), "login Successful", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(), MainActivity3.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,MainActivity2.class);
                startActivity(i);
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                View dialogView=getLayoutInflater().inflate(R.layout.activity_main4,null);
                EditText emailbox=dialogView.findViewById(R.id.emailBox);
                builder.setView(dialogView);
                AlertDialog dialog=builder.create();
                dialogView.findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String useremail=emailbox.getText().toString();
                        if(TextUtils.isEmpty(useremail)&& !Patterns.EMAIL_ADDRESS.matcher(useremail).matches())
                        {
                            Toast.makeText(MainActivity.this,"Enter Registerd Email Id",Toast.LENGTH_LONG).show();
                            return;
                        }
                        mAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(MainActivity.this,"Check your Email",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(MainActivity.this,"Unable to send...Failed",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
                dialogView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                if(dialog.getWindow()!=null)
                {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
            }
        });

    }
}

