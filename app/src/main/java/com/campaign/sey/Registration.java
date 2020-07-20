package com.campaign.sey;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;


public class Registration extends Fragment implements View.OnClickListener {
    private static final int GALLERY = 201;
    private ScrollView login_form,reg_form;
    private ConstraintLayout addImage;
    private ProgressBar progressbar;
    private CircleImageView productImage;
    private EditText phone,etphone,name;
    private Button _sign_up_button;
    private StorageReference ImageRef;
    private DatabaseReference mref;
    private SimpleDateFormat df;
    private Date date;
    private FirebaseDatabase mfirebaseDatabase;
    private ByteArrayOutputStream mByteArrayOutputStream;
    private Uri resultUri;
    private Bitmap pFixBitmap;
    private StorageTask<UploadTask.TaskSnapshot> storageTask;
    private PrefManager prefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        Toolbar toolbar =getActivity(). findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);

        progressbar= view.findViewById(R.id.progressbar);
        addImage= view.findViewById(R.id.addImage);
        productImage= view.findViewById(R.id.productImage);
        _sign_up_button= view.findViewById(R.id._sign_up_button);
        name= view.findViewById(R.id.name);
        phone= view.findViewById(R.id.phone);
        etphone= view.findViewById(R.id.phone2);
        reg_form = view.findViewById(R.id.reg_form);
        login_form = view.findViewById(R.id.login_form);

        addImage.setOnClickListener(this);
        _sign_up_button.setOnClickListener(this);
        view.findViewById(R.id.tvLogin).setOnClickListener(this);
        view.findViewById(R.id.btn_reg).setOnClickListener(this);
        view.findViewById(R.id.btnSignin).setOnClickListener(this);

        ImageRef = FirebaseStorage.getInstance().getReference();

        mref= FirebaseDatabase.getInstance().getReference();
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = new Date();
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mref = mfirebaseDatabase.getReference();

        mByteArrayOutputStream = new ByteArrayOutputStream();

    }

    public void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id._sign_up_button:
                if (validateForm()){
                    if (null!=resultUri)
                        register();
                    else
                        registerWithoutImage();
                }
                break;
            case R.id.btnSignin:
if (!notValidPhone(phone))
    prefManager = new PrefManager(getActivity());
 //

                    mref.child(Utils.PERSON).child("Users").child(phone.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try {
                            if (dataSnapshot.child("phone").getValue().toString().equals(phone.getText().toString().trim())){
                                prefManager.setUserPhone(dataSnapshot.child("phone").getValue().toString());
                                prefManager.setUserName(dataSnapshot.child("username").getValue().toString());
                                prefManager.setImageUrl(dataSnapshot.child("imageResource").getValue().toString());
                                prefManager.setRegistered(true);

                                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_signInFragment_to_usersFragment);


//                               findNavController(getActivity(),R.id.nav_host_fragment)
//                                        .navigate(R.id.commentsFragment, null,
//                                                new NavOptions.Builder().setPopUpTo(R.id.FirstFragment, true).build());

//
                               // NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                               // navController.navigate(R.id.commentsFragment);
                            }else{
                                Toast.makeText(getActivity(), "Phone number not registered", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                                Toast.makeText(getActivity(), "Phone number not registered", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });



                break;
            case R.id.addImage:
                choosePhotoFromGallery();
                break;
            case R.id.btn_reg:
                login_form.setVisibility(View.GONE);
                reg_form.setVisibility(View.VISIBLE);
                break;
            case R.id.tvLogin:
                login_form.setVisibility(View.VISIBLE);
                reg_form.setVisibility(View.GONE);
                break;
            default:
        }

    }
    private boolean validateForm() {
        String mylname = name.getText().toString();
        if (TextUtils.isEmpty(mylname)) {
            name.setError("Name can't be empty.");
            name.requestFocus();
            return false;
        } else {
            name.setError(null);
        }
        return !notValidPhone(etphone);
    }

    private boolean notValidPhone(EditText etphone) {
        String mphone = etphone.getText().toString();
        if (TextUtils.isEmpty(mphone)) {
            etphone.setError("Phone Number can't be empty..");
            etphone.requestFocus();
            return true;
        } else if(mphone.length()<10) {
            etphone.setError("Enter correct phone number.");
            etphone.requestFocus();
            return true;
        }else {
            etphone.setError(null);
        }
        return false;
    }

    private void register(){
        final StorageReference filePath = ImageRef.child(phone + ".jpg");
        storageTask=filePath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                         String phone =etphone.getText().toString().trim();
                         String mname =name.getText().toString().trim();
                        Model data = new Model(
                                phone,
                                mname,
                                uri.toString(),
                                "1",
                                createTransactionID(),
                                df.format(date)
                        );
                       // Toast.makeText(getContext(), resultUri.toString(), Toast.LENGTH_SHORT).show();
                        mref.child(Utils.PERSON).child("Users").child(phone).setValue(data);
                    }
                });
                login_form.setVisibility(View.VISIBLE);
                reg_form.setVisibility(View.GONE);
                progressbar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

            }

        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                        Toast.makeText(getActivity(), "Error Updating , Check Internet Connectivity", Toast.LENGTH_SHORT).show();
                        progressbar.setVisibility(View.GONE);

                    }
                })



                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        progressbar.setVisibility(View.VISIBLE);
                    }
                });


}
private void registerWithoutImage(){
    progressbar.setVisibility(View.VISIBLE);

    String phone =etphone.getText().toString().trim();
                 String mname =name.getText().toString().trim();
                        Model data = new Model(
                                phone,
                                mname,
                                "",
                                "1",
                                createTransactionID(),
                                df.format(date)
                        );
    mref.child(Utils.PERSON).child("Users").child(phone).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                progressbar.setVisibility(View.GONE);
                                login_form.setVisibility(View.VISIBLE);
                                reg_form.setVisibility(View.GONE);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Error, Check Internet Connectivity", Toast.LENGTH_SHORT).show();
                                progressbar.setVisibility(View.GONE);

                            }
                        });



}
    public String createTransactionID(){
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0,6);
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
                    pFixBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    // String path = saveImage(bitmap);
                   // Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();

                    productImage.setImageBitmap(pFixBitmap);
                    //      UploadImageOnServerButton.setVisibility(View.VISIBLE);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        }

    }


}
