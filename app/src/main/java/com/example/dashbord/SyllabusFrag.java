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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;


public class SyllabusFrag extends Fragment implements AdapterView.OnItemSelectedListener {
    Button Select, Upload;
    Spinner spinner;
    EditText Name;
    String title, sig;
    Uri pdfuri;
    private ArrayList<String> arrayList = new ArrayList<>();

    private FirebaseStorage storage;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    private ProgressDialog progressDialog;
    String item;
    spinnermodel model;

    public SyllabusFrag() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_syllabus, container, false);
        Select = v.findViewById(R.id.select);
        Name = v.findViewById(R.id.name);
        Upload = v.findViewById(R.id.upload);
        spinner = v.findViewById(R.id.spinner);
        storage = FirebaseStorage.getInstance();

        reference = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(getContext());

        showdataspinner();


        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                title = Name.getText().toString();
                sig = spinner.getSelectedItem().toString();
                if (title.isEmpty() || sig.isEmpty()) {
                    Toast.makeText(getContext(), "Fill All", Toast.LENGTH_SHORT).show();
                } else if (pdfuri == null) {
                    Toast.makeText(getContext(), "Select a file", Toast.LENGTH_SHORT).show();

                } else {
                    UploadFile();
                }

            }
        });
        Select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectNotice();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });
        return v;

    }

    private void showdataspinner() {
        reference.child("SIGs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot item : snapshot.getChildren()) {
                    arrayList.add(item.child("Name").getValue(String.class));
                }
                if (SyllabusFrag.this != null) {
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.style_spinner, arrayList);
                    spinner.setAdapter(arrayAdapter);
                    Log.i("Spinner", arrayList.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void UploadFile() {
        progressDialog.setTitle("Please wait..");
        progressDialog.setMessage("Uploading pdf");
        progressDialog.show();

        String filename = System.currentTimeMillis() + "";
        StorageReference storageReference = storage.getReference();
        storageReference.child("Syllabus").child(filename).putFile(pdfuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri uri = uriTask.getResult();
                uploadData(String.valueOf(uri));


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void uploadData(String downloadUrl) {
        String uniquekey = reference.child("Tasks").push().getKey();
        String abc = spinner.getSelectedItem().toString();


        HashMap data = new HashMap();
        data.put("Name", title);
        data.put("pdfUrl", downloadUrl);
        data.put("SIG", abc);
        data.put("id", uniquekey);


        reference.child("Syllabus").child(uniquekey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
                Name.setText(" ");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();


            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectNotice();
        } else {
            Toast.makeText(getContext(), "Please give Permissions", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectNotice() {
        Intent i = new Intent();
        i.setType("application/pdf");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, 86);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            pdfuri = data.getData();
            //  Name.setText(data.getData().getLastPathSegment());
        } else {
            Toast.makeText(getContext(), "Please select pdf", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        item=spinner.getSelectedItem().toString();
        String ss = spinner.getSelectedItem().toString();
        Log.i("wjdkwjckdjc",ss);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}