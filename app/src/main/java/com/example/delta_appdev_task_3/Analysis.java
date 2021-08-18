package com.example.delta_appdev_task_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Analysis extends AppCompatActivity {
    private TextView name,createdOn,id;
    private String Name,CreatedOn,ID,URL;
    private ImageView image;
    private Button backHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        name=findViewById(R.id.ApproxName);
        createdOn=findViewById(R.id.created);
        id=findViewById(R.id.id);
        image=findViewById(R.id.imageView4);
        backHome=findViewById(R.id.button2);
        Intent intent=getIntent();
        Name=intent.getStringExtra("name");
        CreatedOn=intent.getStringExtra("time");
        CreatedOn = CreatedOn.substring(0, Math.min(CreatedOn.length(), 10));
        ID=intent.getStringExtra("id");
        URL=intent.getStringExtra("url");
        name.setText("Name: "+Name);
        createdOn.setText("Created on: "+CreatedOn);
        id.setText("ID: "+ID);
        Picasso.get().load(URL).resize(300,300).into(image);
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Analysis.this,MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });
    }
}