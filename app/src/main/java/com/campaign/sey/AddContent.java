package com.campaign.sey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddContent extends AppCompatActivity implements View.OnClickListener {

    private static final int GALLERY = 1001;
    private DatabaseReference mref;
    private SimpleDateFormat df;
    private Date date;
    private FirebaseDatabase mfirebaseDatabase;
    private Button addImage,addPro;
    private ImageView proImage;
    private ProgressBar progressBar;
    private EditText service;
    private EditText name,title,desc,youtube;
    private Bitmap pFixBitmap;
    private ByteArrayOutputStream mByteArrayOutputStream;
    private String ConvertImage;
    private byte[] ByteArray;
    private StorageReference ImageRef;
    private StorageTask storageTask;
    private Uri resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_content);

        ImageRef = FirebaseStorage.getInstance().getReference();

        mref= FirebaseDatabase.getInstance().getReference();
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = new Date();
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mref = mfirebaseDatabase.getReference();

        mByteArrayOutputStream = new ByteArrayOutputStream();


        addImage=findViewById(R.id.productImageBtn);
        service=findViewById(R.id.service);
        name=findViewById(R.id.name);
        title=findViewById(R.id.title);
        desc=findViewById(R.id.desc);
        addPro=findViewById(R.id.addPro);
        youtube=findViewById(R.id.youtube);

        proImage=findViewById(R.id.productImage);
        progressBar=findViewById(R.id.pbbar);

        addPro.setOnClickListener(this);
        addImage.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.productImageBtn:
                choosePhotoFromGallery();
                break;
            case R.id.addPro:
                progressBar.setVisibility(View.VISIBLE);
                if (null != pFixBitmap) {
                    final String key = mref.push().getKey();
                    final StorageReference filePath = ImageRef.child(key + ".jpg");
                    storageTask=filePath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Model data = new Model(
                                            key,
                                            name.getText().toString(),
                                            title.getText().toString(),
                                            uri.toString(),
                                            desc.getText().toString(),
                                            df.format(date),
                                            youtube.getText().toString());
                                    Toast.makeText(AddContent.this, resultUri.toString(), Toast.LENGTH_SHORT).show();
                                    mref.child(service.getText().toString()).child(key).setValue(data);
                                }
                            });
                            progressBar.setVisibility(View.GONE);
//                            try{
//                                new DownloadImage().execute(resultUri.toString());
//
//                            }catch (Exception ignored){
//
//                            }finally {
//                                GlideApp.with(AddServices.this)
//                                        .load(resultUri)
//                                        .placeholder(R.drawable.no_image)
//                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                        .priority(Priority.HIGH)
//                                        .into(proImage);
//                            }




                            Toast.makeText(AddContent.this, "Success", Toast.LENGTH_SHORT).show();

                        }

                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(AddContent.this, "Error Updating , Check Internet Connectivity", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);

                                }
                            })



                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    progressBar.setVisibility(View.VISIBLE);
                                }
                            });


                }
                break;
        }
    }


    public void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AddContent.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                resultUri = contentURI;

                try {
                    pFixBitmap = MediaStore.Images.Media.getBitmap(AddContent.this.getContentResolver(), contentURI);
                    // String path = saveImage(bitmap);
                    Toast.makeText(AddContent.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    proImage.setImageBitmap(pFixBitmap);
                    //      UploadImageOnServerButton.setVisibility(View.VISIBLE);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(AddContent.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        }

    }
}
