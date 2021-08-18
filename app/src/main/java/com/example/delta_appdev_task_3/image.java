package com.example.delta_appdev_task_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class image extends AppCompatActivity {
    private static final int SELECT_PICTURE =200 ;
    private Button forUpload,OpenGal;
    private InputStream inputStream;
    private Uri selectedImageUri;
    private ImageView imageView;
    private ProgressBar progressBar;
    private boolean isChosen=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        imageView=findViewById(R.id.imageView3);
        forUpload=findViewById(R.id.buttonUpload);
        progressBar=findViewById(R.id.progressBar2);
        OpenGal=findViewById(R.id.gal);
        imageChooser();

        forUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(isChosen){
                        upload();
                        imageView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    else {
                        Toast.makeText(image.this, "Please choose the image first", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        OpenGal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
    }

    private void upload() throws IOException {
        File file=new File(selectedImageUri.getPath());
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), getBytes(inputStream));
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        Call<img> call= interfaceForAPI.getter().uploadImage(body);
        call.enqueue(new Callback<img>() {
            @Override
            public void onResponse(Call<img> call, Response<img> response) {
                if(response!=null){
                    if(response.isSuccessful()){
                        img resp=response.body();
                        analysis(resp);
                    }
                    else {
                        Toast.makeText(image.this, "This Image doesn't pass our filters. Please Enter a valid Image", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    Toast.makeText(image.this, "null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<img> call, Throwable t) {
                Toast.makeText(image.this, t.toString(), Toast.LENGTH_SHORT).show();
            }


        });
    }

    public void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    Bitmap photo;
                    try {
                        photo = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImageUri);
                        inputStream=getContentResolver().openInputStream(selectedImageUri);
                        imageView.setImageBitmap(photo);
                        isChosen=true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }
    public void analysis(img Temp){
        Call<List<dogAnalysis>> call=interfaceForAPI.getter().getAnalysis(Temp.getId());
        call.enqueue(new Callback<List<dogAnalysis>>() {
            @Override
            public void onResponse(Call<List<dogAnalysis>> call, Response<List<dogAnalysis>> response) {
                if(response!=null){
                    if(response.isSuccessful()){
                        List<dogAnalysis> dogger=response.body();
                        Intent intent=new Intent(image.this,Analysis.class);
                        intent.putExtra("name",dogger.get(0).getLabels().get(0).getName());
                        intent.putExtra("id",dogger.get(0).getImageId());
                        intent.putExtra("time",dogger.get(0).getCreatedAt());
                        intent.putExtra("url",Temp.getUrl());
                        startActivity(intent);
                        finish();

                    }
                    else {

                        Toast.makeText(image.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(image.this, "null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<dogAnalysis>> call, Throwable t) {
                Toast.makeText(image.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });

    }


}

  