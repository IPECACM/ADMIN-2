package com.example.dashbord;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class GalleryFrag extends Fragment {
    private ImageButton gallery;
    Uri ImageUri;
    private ImageView image;
    String downloadimageurl;
    private static final int Gallery_Pick = 1;
    private Button Upload;
    private FirebaseStorage storage;
    private StorageReference FStorage;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    public GalleryFrag() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_gallery, container, false);
        gallery=v.findViewById(R.id.imageButton);
        image=v.findViewById(R.id.image);
        reference=FirebaseDatabase.getInstance().getReference();
        FStorage=FirebaseStorage.getInstance().getReference().child("Images");
        storage=FirebaseStorage.getInstance();
        // reference=FirebaseDatabase.getInstance().getReference();
        Upload=v.findViewById(R.id.button);

        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ImageUri == null) {
                    Toast.makeText(getContext(), "Image is mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    StoreImage();
                }
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UploadPic();
            }
        });
        return  v;
    }

    private void StoreImage() {
        final StorageReference filepath = FStorage.child(ImageUri.getLastPathSegment());
        final UploadTask uploadtask = filepath.putFile(ImageUri);
        uploadtask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(getContext(), "Error:" + message, Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getContext(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                Task<Uri> urltask = uploadtask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        downloadimageurl = filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }

                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadimageurl = task.getResult().toString();
                            Toast.makeText(getContext(), "URL successfull", Toast.LENGTH_SHORT).show();
                            SaveInDatabase();
                        }
                    }
                });


            }
        });



    }

    private void SaveInDatabase() {
        HashMap<String, Object> productMap = new HashMap<>();
        String uniquekey = reference.child("Gallery").push().getKey();

        productMap.put("image", downloadimageurl);
        productMap.put("id",uniquekey);

        reference.child("Gallery").child(uniquekey).setValue(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override //wait krna ek sec
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "image added successfully", Toast.LENGTH_SHORT).show();
//                                S = S_Id;
//                                SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
//                                SharedPreferences.Editor editor = sharedPref.edit();
//                                editor.putString("newid", S);
//                                //  editor.putString("value", mail);
//                                editor.apply();
                } else {
                    String message = task.getException().toString();
                    Toast.makeText(getContext(), "Error:" + message, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void UploadPic() {

        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent, Gallery_Pick);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Gallery_Pick && resultCode == RESULT_OK && data != null) {
            ImageUri = data.getData();
            image.setImageURI(ImageUri);
        }
    }
    }
