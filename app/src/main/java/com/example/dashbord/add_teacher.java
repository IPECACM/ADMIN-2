package com.example.dashbord;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

public class add_teacher extends AppCompatActivity {
    private static final int Gallery_Pick = 1;
    Uri ImageUri;
    String downloadimageurl;
    private ImageView addteacherimage;
    private EditText addTeacherName;
    private EditText addTeacherEmail;
    private EditText addTeacherPost;
    private Button addTeacherButton;
    private  String Name,Email,Post;
    private FirebaseStorage storage;
    StorageReference Fstorage;
    private ProgressDialog progressDialog;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        addteacherimage=findViewById(R.id.addteachermage);
        addTeacherName=findViewById(R.id.addteachername);
        addTeacherEmail=findViewById(R.id.addteacheremail);
        addTeacherPost=findViewById(R.id.addteacherpost);
        addTeacherButton=findViewById(R.id.addteacherbutton);
        storage=FirebaseStorage.getInstance();
        Fstorage=FirebaseStorage.getInstance().getReference().child("Faculty Images");
        progressDialog=new ProgressDialog(add_teacher.this);


        reference=FirebaseDatabase.getInstance().getReference();

        addteacherimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });

        addTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validate();

            }
        });




    }

    private void OpenGallery() {

        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent, Gallery_Pick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Gallery_Pick && resultCode == RESULT_OK && data != null) {
            ImageUri = data.getData();
            addteacherimage.setImageURI(ImageUri);
        }
    }

    private void Validate() {
        Name=addTeacherName.getText().toString();
        Email=addTeacherEmail.getText().toString();
        Post=addTeacherPost.getText().toString();
        if(ImageUri==null)
        {
            Toast.makeText(add_teacher.this,"Add Image",Toast.LENGTH_SHORT).show();
        }

       else if(TextUtils.isEmpty(Name) || TextUtils.isEmpty(Email) || TextUtils.isEmpty(Post))
        {
            Toast.makeText(add_teacher.this,"Fill All Feilds",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(add_teacher.this, "Error:" + message, Toast.LENGTH_SHORT).show();

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

        HashMap<String, Object> facultymap = new HashMap<>();
        facultymap.put("F_Name",Name);
        facultymap.put("F_Email",Email);
        facultymap.put("F_Post",Post);
        facultymap.put("F_Image",downloadimageurl);
      reference.child("Faculty Details").push().setValue(facultymap).addOnCompleteListener(new OnCompleteListener<Void>() {
          @Override
          public void onComplete(@NonNull Task<Void> task) {
              if(task.isSuccessful())
              {
                  progressDialog.dismiss();
                  Toast.makeText(add_teacher.this,"Successfull!!",Toast.LENGTH_SHORT).show();
                  addTeacherEmail.setText("");
                  addTeacherName.setText("");
                  addTeacherPost.setText("");
              }
              else
              { progressDialog.dismiss();
                  String message = task.getException().toString();
                  Toast.makeText(add_teacher.this, "Error:" + message, Toast.LENGTH_SHORT).show();              }

          }
      });





    }


}