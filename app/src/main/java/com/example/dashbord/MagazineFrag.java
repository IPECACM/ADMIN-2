package com.example.dashbord;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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


public class MagazineFrag extends Fragment {
    private ImageButton Magazine;
    Uri pdfuri;
    private EditText Magname;
    private TextView Name;
    private Button Upload;
    String title;
    private FirebaseStorage storage;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    private ProgressDialog progressDialog;


    public MagazineFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_magazine, container, false);
        storage=FirebaseStorage.getInstance();
        Magname=v.findViewById(R.id.mag);
        reference=FirebaseDatabase.getInstance().getReference();
        Magazine= v.findViewById(R.id.buttonmag);
        Upload=v.findViewById(R.id.button);

        progressDialog=new ProgressDialog(getContext());

        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                title=Magname.getText().toString();
                if(title.isEmpty())
                {
                    Toast.makeText(getContext(),"Fill name",Toast.LENGTH_SHORT).show();
                }
                else
                if(pdfuri==null)
                {
                    Toast.makeText(getContext(),"Select a file",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    UploadFile();
                }

            }
        });
        Magazine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    selectNotice();
                }
                else {
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9 );
                }
            }
        });
        return  v;

    }



    private void UploadFile() {
        progressDialog.setTitle("Please wait..");
        progressDialog.setMessage("Uploading pdf");
        progressDialog.show();

        String filename= System.currentTimeMillis()+"";
        StorageReference storageReference= storage.getReference();
        storageReference.child("Magazine").child(filename).putFile(pdfuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
                Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadData(String downloadUrl) {
        String uniquekey = reference.child("Magazine").push().getKey();
        HashMap data= new HashMap();
        data.put("MagazineTitle",title);
        data.put("magazineUrl", downloadUrl);
        data.put("id",uniquekey);

        reference.child("Magazine").child(uniquekey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                Toast.makeText(getContext(),"Successful",Toast.LENGTH_SHORT).show();
                Magname.setText(" ");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();



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
            Toast.makeText(getContext(), "Please give Permissions",Toast.LENGTH_SHORT).show();
        }
    }

    private void selectNotice() {
        Intent i= new Intent();
        i.setType("application/pdf");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,86);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            pdfuri = data.getData();
            //  Name.setText(data.getData().getLastPathSegment());
        } else {
            Toast.makeText(getContext(),"Please select pdf",Toast.LENGTH_SHORT).show(); }
    }
}


