package com.example.dashbord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class registration extends AppCompatActivity {
    private EditText name, phone, year, branch, email, password,RegYear;
    private String Name, Phone, Year, Branch, Email, Password,regYear;
    private Button Register;
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    String currentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        name = findViewById(R.id.Name);
        phone = findViewById(R.id.phone);
        year = findViewById(R.id.year_sec);
        branch = findViewById(R.id.Branch);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.password);
        Register = findViewById(R.id.register_button);
        RegYear=findViewById(R.id.regyear);

        firebaseAuth=FirebaseAuth.getInstance();
//        currentuser=firebaseAuth.getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference();



        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Name = name.getText().toString();
                Phone = phone.getText().toString();
                Year = year.getText().toString();
                Branch = branch.getText().toString();
                Email = email.getText().toString();
                Password = password.getText().toString();
                regYear=RegYear.getText().toString();

                if (TextUtils.isEmpty(Name) || TextUtils.isEmpty(Phone) || TextUtils.isEmpty(Year) || TextUtils.isEmpty(Branch) || TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password)||TextUtils.isEmpty(regYear)) {
                    Toast.makeText(registration.this, "Fill All Fields", Toast.LENGTH_SHORT).show();

                } else {
                    registerUser();
                }
            }
        });


    }

    private void registerUser() {
//        String key= databaseReference.getKey().trim();


        firebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {


                    HashMap<String, Object> studentMap = new HashMap<>();
                    studentMap.put("Name", Name);
                    studentMap.put("Phone", Phone);
                    studentMap.put("Email", Email);
                    studentMap.put("Year", Year);
                    studentMap.put("Branch", Branch);
                    studentMap.put("password",Password);
                    studentMap.put("hasChoosen","false");
                    studentMap.put("RegistrationYear",regYear);
                    studentMap.put("Choice0", "SIG TECH");
                    studentMap.put("Choice1", "");
                    studentMap.put("Choice2", "");
                    studentMap.put("id",firebaseAuth.getCurrentUser().getUid());




                    databaseReference.child("Student Details").child(firebaseAuth.getCurrentUser().getUid()).setValue(studentMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                         //   progressDialog.dismiss();
                            Toast.makeText(registration.this, "Student registered Successfully!!", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                else
                {
                    Toast.makeText((registration.this),"Error !"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}