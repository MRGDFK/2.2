package com.example.mrgdfk_feed;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static int cnt;
    private TextView count;

    TextView profile;
    ImageButton emptybtn;
    ImageButton filledbtn;

    Button view;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emptybtn = (ImageButton) findViewById(R.id.empty_heart);
        filledbtn = (ImageButton) findViewById(R.id.filled_heart);
        count = (TextView) findViewById(R.id.count_like);
        profile = (TextView) findViewById(R.id.gotoprofile);
        view = (Button) findViewById(R.id.view_image);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,View_Image.class);
                startActivity(i);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(MainActivity.this, user_profile.class);
                startActivity(x);
            }
        });
        emptybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cnt =1;
                filledbtn.setVisibility(View.VISIBLE);
                emptybtn.setVisibility(View.INVISIBLE);
                count.setText(String.valueOf(cnt));
            }
        });

        filledbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cnt++;
                count.setText(String.valueOf(cnt));
            }
        });

    }

}