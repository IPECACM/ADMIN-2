package com.example.dashbord;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.util.HashMap;

public class Notice extends AppCompatActivity {
    private ImageButton Notice;
    Uri pdfuri;
    private EditText PdfName;
    private TextView Name;
    private Button Upload;
    String title;
     private FirebaseStorage storage;
   private FirebaseDatabase firebaseDatabase;
   DatabaseReference reference;
     private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        storage=FirebaseStorage.getInstance();
        PdfName=findViewById(R.id.name1);
     reference=FirebaseDatabase.getInstance().getReference();
        Notice= findViewById(R.id.button2);
        Upload=findViewById(R.id.upload);

        progressDialog=new ProgressDialog(Notice.this);

       Upload.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {

         title=PdfName.getText().toString();
         if(title.isEmpty())
         {
             Toast.makeText(Notice.this,"Fill name",Toast.LENGTH_SHORT).show();
         }
         else
         if(pdfuri==null)
         {
             Toast.makeText(Notice.this,"Select a file",Toast.LENGTH_SHORT).show();

         }
         else
         {
             UploadFile();
         }

     }
 });
 Notice.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
        if(ContextCompat.checkSelfPermission(Notice.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            selectNotice();
        }
        else {
            ActivityCompat.requestPermissions(Notice.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9 );
        }
     }
 });

    }



    private void UploadFile() {
           progressDialog.setTitle("Please wait..");
           progressDialog.setMessage("Uploading pdf");
           progressDialog.show();

        String filename= System.currentTimeMillis()+"";
    StorageReference storageReference= storage.getReference();
    storageReference.child("Notices").child(filename).putFile(pdfuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            Task<Uri> uriTask= taskSnapshot.getStorage().getDownloadUrl();
            while ( !uriTask.isComplete());
            Uri uri= uriTask.getResult();
            uploadData(String.valueOf(uri));


        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            progressDialog.dismiss();
            Toast.makeText(Notice.this,"Error",Toast.LENGTH_SHORT).show();
        }
    });

    }

    private void uploadData(String downloadUrl) {
   String uniquekey = reference.child("pdf").push().getKey();




        HashMap data= new HashMap();
        data.put("pdfTitle",title);
        data.put("pdfUrl", downloadUrl);
        data.put("key",uniquekey);

        reference.child("pdf").child(uniquekey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                Toast.makeText(Notice.this,"Successful",Toast.LENGTH_SHORT).show();
                PdfName.setText(" ");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Notice.this,"Error",Toast.LENGTH_SHORT).show();



            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
     if (requestCode==9 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
     {
         selectNotice();
     }
     else
     {
         Toast.makeText(Notice.this, "Please give Permissions",Toast.LENGTH_SHORT).show();
     }
    }

    private void selectNotice() {
        Intent i= new Intent();
        i.setType("application/pdf");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,86);

    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            pdfuri = data.getData();
          //  Name.setText(data.getData().getLastPathSegment());
        } else {
                Toast.makeText(Notice.this,"Please select pdf",Toast.LENGTH_SHORT).show();
        }

    }

}
