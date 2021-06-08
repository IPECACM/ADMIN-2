package com.example.dashbord;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class RegisterSigFragment extends Fragment {
    EditText name;
    private StorageReference FStorage;
    private ProgressDialog progressDialog;
    String downloadimageurl;
    private static final int Gallery_Pick = 1;
    Button Register;
    Uri ImageUri;
    String Name;
    CircleImageView Image;
    DatabaseReference databaseReference;
    FirebaseDatabase database;

    public RegisterSigFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register_sig, container, false);
        name = v.findViewById(R.id.signame);
        Register = v.findViewById(R.id.register);
        Image = v.findViewById(R.id.image);
        FStorage = FirebaseStorage.getInstance().getReference().child("Images");
        progressDialog = new ProgressDialog(getActivity());


        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadpic();

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = name.getText().toString();
                if (TextUtils.isEmpty(Name)) {
                    Toast.makeText(getContext(), "Fill the fields", Toast.LENGTH_SHORT).show();
                } else if (ImageUri == null) {
                    Toast.makeText(getContext(), "Upload SIG LOGO", Toast.LENGTH_SHORT).show();

                } else {
                    storepic();

                }
            }
        });
        return v;
    }

    private void uploadpic() {
//
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
            Image.setImageURI(ImageUri);
        }
    }

    private void storepic() {
        progressDialog.setTitle("Please wait..");
        progressDialog.setMessage("Uploading Data");
        progressDialog.show();
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
               // Toast.makeText(getContext(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();
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
                           // Toast.makeText(getContext(), "URL successfull", Toast.LENGTH_SHORT).show();
                            register();
                        }
                    }
                });
            }


            private void register() {
                String uniquekey = databaseReference.child("SIGs").push().getKey();

                HashMap<String, Object> sigmap = new HashMap<>();

                sigmap.put("Name", Name);
                sigmap.put("id", uniquekey);
                sigmap.put("logo", downloadimageurl);

                databaseReference.child("SIGs").child(uniquekey).setValue(sigmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
progressDialog.dismiss();
                        Toast.makeText(getContext(), "Sig Registered Successfully", Toast.LENGTH_SHORT).show();
                        name.setText(" ");

                    }
                });

            }
        });
    }
}