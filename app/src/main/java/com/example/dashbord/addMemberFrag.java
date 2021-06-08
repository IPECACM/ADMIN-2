package com.example.dashbord;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dashbord.R;
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


public class addMemberFrag extends Fragment implements AdapterView.OnItemSelectedListener {
    private static final int Gallery_Pick = 1;
    Uri ImageUri;
    String downloadimageurl;
    private ImageView addmemberimage;
    private EditText addmemberName;
    private EditText addmemberEmail;
    private EditText addmemberPost;
    private Button addmemberButton;
    private  String Name,Email,Post,Select;
    private FirebaseStorage storage;
    StorageReference Fstorage;
    private ProgressDialog progressDialog;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    private Spinner spinner1;


    public addMemberFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_student, container, false);
        addmemberimage=v.findViewById(R.id.addmemberimage);
        addmemberName=v.findViewById(R.id.addmembername);
        addmemberEmail=v.findViewById(R.id.addmemberemail);
        addmemberPost=v.findViewById(R.id.addmemberpost);
        addmemberButton=v.findViewById(R.id.addmemberbutton);
        storage=FirebaseStorage.getInstance();
        Fstorage=FirebaseStorage.getInstance().getReference().child("Member Images");
        progressDialog=new ProgressDialog(getContext());


        reference=FirebaseDatabase.getInstance().getReference();

        spinner1=v.findViewById(R.id.spinner);
        if(getActivity()!=null) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.posts, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter);
            spinner1.setOnItemSelectedListener(this);
        }
        addmemberimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });

        addmemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validate();

            }
        });



return v;
    }



    private void OpenGallery() {

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
            addmemberimage.setImageURI(ImageUri);
        }
    }

    private void Validate() {
        Name=addmemberName.getText().toString();
        Email=addmemberEmail.getText().toString();
        Post=addmemberPost.getText().toString();
//        Select=spinner.getSelectedItem().toString();
        if(ImageUri==null)
        {
            Toast.makeText(getContext(),"Add Image",Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(Name) || TextUtils.isEmpty(Email) || TextUtils.isEmpty(Post))
        {
            Toast.makeText(getContext(),"Fill All Fields",Toast.LENGTH_SHORT).show();
        }
        else
        {
            SaveImage();

        }


    }

    private void SaveImage() {
        progressDialog.setTitle("Please wait..");
        progressDialog.setMessage("Uploading Data");
        progressDialog.show();
        final StorageReference filepath = Fstorage.child(ImageUri.getLastPathSegment());
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
                // Toast.makeText(add_teacher.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
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
                            // Toast.makeText(add_teacher.this, "URL successfull", Toast.LENGTH_SHORT).show();
                            SaveInfo();
                        }
                    }
                });


            }
        });
    }

    private void SaveInfo() {
     // Select=spinner1.getSelectedItem().toString();
    //  Log.i("qkjdklsdlxas",Select);

        String uniquekey = reference.child("Members Details").push().getKey();

        HashMap<String, Object> facultymap = new HashMap<>();
        facultymap.put("key",uniquekey);
        facultymap.put("F_Name",Name);
        facultymap.put("F_Email",Email);
        facultymap.put("F_Post",Post);
        facultymap.put("F_Image",downloadimageurl);
        facultymap.put("Designation",spinner1.getSelectedItem().toString());
        reference.child("Members Details").child(spinner1.getSelectedItem().toString()).child(uniquekey).setValue(facultymap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(),"Successfull!!",Toast.LENGTH_SHORT).show();
                    addmemberEmail.setText("");
                    addmemberName.setText("");
                    addmemberPost.setText("");
                }
                else
                { progressDialog.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(getContext(), "Error:" + message, Toast.LENGTH_SHORT).show();

                }

            }
        });





    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String ss = spinner1.getSelectedItem().toString();
        Log.i("wjdkwjckdjc",ss);
        Toast.makeText(getContext(), ss, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
