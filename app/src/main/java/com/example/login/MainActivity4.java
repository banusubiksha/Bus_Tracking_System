package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity4 extends AppCompatActivity {
    TextView t1,t2,t3;
    ImageView i1,i2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //will hide the title
        //enable full scree
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        // Set the title bar color
        getWindow().setStatusBarColor(getResources().getColor(R.color.yellow)); // Change your_color to the desired color
        getWindow().setNavigationBarColor(getResources().getColor(R.color.yellow)); // Change your_color to the desired color

        setContentView(R.layout.activity_main5);
        t1=(TextView) findViewById(R.id.loginas);
        t2=(TextView) findViewById(R.id.driver);
        t3=(TextView) findViewById(R.id.student);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity5.class);
                startActivity(i);
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });




    }
}