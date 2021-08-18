package com.example.delta_appdev_task_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DogInfo extends AppCompatActivity {
    private String name,beh,weight,height,lifeSpan,url,Bfor,BGroup;
    private TextView Name,Beh,Weight,Height,LifeSpan,breedFor,breedGroup;
    private ImageView imageView;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_info);
        constraintLayout=findViewById(R.id.layout);
        constraintLayout.setBackgroundColor(Color.parseColor("#FF018786"));
        Name=findViewById(R.id.Name);
        Beh=findViewById(R.id.beh);
        Weight=findViewById(R.id.Weight);
        Height=findViewById(R.id.Height);
        LifeSpan=findViewById(R.id.LifeSpan);
        breedFor=findViewById(R.id.Bfor);
        breedGroup=findViewById(R.id.group);
        imageView=findViewById(R.id.imageView2);
        Intent intent=getIntent();
        name=intent.getStringExtra("name");
        beh=intent.getStringExtra("behaviour");
        weight=intent.getStringExtra("w");
        height=intent.getStringExtra("h");
        lifeSpan=intent.getStringExtra("life");
        url=intent.getStringExtra("link");
        Bfor=intent.getStringExtra("bfor");
        BGroup=intent.getStringExtra("bgroup");
        if(name!=null){
            Name.setText(name);
        }
        else {
            Name.setText("No name available");
        }
        if(beh!=null){
            Beh.setText(beh);
        }
        if(weight!=null){
            Weight.setText("Weight: "+weight+" kgs");
        }
        if(height!=null){
            Height.setText("Height: "+height+" cms");
        }
        if(lifeSpan!=null){
            LifeSpan.setText("Lifespan: "+lifeSpan+" years");
        }
        else{
            LifeSpan.setTextSize(30);
            LifeSpan.setText("No info available");
        }

        Picasso.get().load(url).resize(300,300).into(imageView);
        if(Bfor!=null){
            breedFor.setText("Breed for: "+Bfor);
        }
        if(BGroup!=null){
            breedGroup.setText("Breed Group:"+BGroup);
        }


    }
}