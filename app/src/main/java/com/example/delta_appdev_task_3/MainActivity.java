package com.example.delta_appdev_task_3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private adapterForRecycler adapterForRecycler;
    private String url = "https://api.thedogapi.com/v1/";
    private boolean isScrolled=false,loading=true;
    private int scrolledOut,current,total,pageNo=1,controler=0;
    private LinearLayoutManager layoutManager;
    private ArrayList<DogImage> doggi;
    private ProgressBar progressBar;
    private FloatingActionButton upload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerForDog);
        progressBar=findViewById(R.id.progressBar);
        upload=findViewById(R.id.floatingActionButton);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,image.class);
                startActivity(intent);
                finish();
            }
        });
        doggi = new ArrayList<>();
        adapterForRecycler = new adapterForRecycler(this);
        layoutManager=new LinearLayoutManager(this);

        retrive();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
//                    isScrolled=true;
//                }
//            }

            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {

                if (dy > 0) { //check for scroll down
                    current = layoutManager.getChildCount();
                    total = layoutManager.getItemCount();
                    scrolledOut = layoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((current + scrolledOut) >= total) {
                            loading = false;
                            getData();
                        }
                    }

                }
            }
        });


    }

    private void retrive() {
        doggi.clear();
        Call<List<DogImage>> call = interfaceForAPI.getter().getInfo(pageNo);
        pageNo++;
        call.enqueue(new Callback<List<DogImage>>() {
            @Override
            public void onResponse(Call<List<DogImage>> call, Response<List<DogImage>> response) {
                if (response.isSuccessful()) {
                    List<DogImage> dogList = response.body();
                    for (DogImage dogImage : dogList) {
                        doggi.add(dogImage);
                    }


                    adapterForRecycler.setDogs(doggi);
                    recyclerView.setAdapter(adapterForRecycler);

                } else {
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<List<DogImage>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData() {


        progressBar.setVisibility(View.VISIBLE);

           Call<List<DogImage>> call = interfaceForAPI.getter().getInfo(pageNo);
           pageNo++;
           call.enqueue(new Callback<List<DogImage>>() {
               @Override
               public void onResponse(Call<List<DogImage>> call, Response<List<DogImage>> response) {
                   if (response.isSuccessful()) {
                       List<DogImage> dogList = response.body();
                       for (DogImage dogImage : dogList) {
                           doggi.add(dogImage);
                       }

                       adapterForRecycler.notifyDataSetChanged();

                       progressBar.setVisibility(View.GONE);

                   } else {
                       Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                   }
                   loading=true;
               }


               @Override
               public void onFailure(Call<List<DogImage>> call, Throwable t) {
                   Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
               }
           });

    }

}