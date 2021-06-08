package com.example.dashbord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class sig_selection extends AppCompatActivity {

    public EditText year;
    public Button Ok;
    public  Button Close;
    FirebaseAuth Fauth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sig_selection);
        year=findViewById(R.id.yr);
        Ok=findViewById(R.id.ok);
        Close=findViewById(R.id.close);
        Fauth=FirebaseAuth.getInstance();

             Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String YR= year.getText().toString();
                if(TextUtils.isEmpty(YR))
                {
                    Toast.makeText(sig_selection.this, "Fill", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Validate();
                }
            }
        });

        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseRegistration();
            }
        });
    }

    private void CloseRegistration() {
        String YR= year.getText().toString();

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Student Details");
        ref.orderByChild("RegistrationYear").equalTo(YR).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot childSnapshot: dataSnapshot.getChildren()) {

                    //Check whether child with key 'isAvailable' exist or not
                    if (childSnapshot.hasChild("hasChoosen")) {

                        //Now update the status with false
                        ref.child(Objects.requireNonNull(childSnapshot.getKey()))
                                .child("hasChoosen")
                                .setValue(false);
                        Toast.makeText(sig_selection.this, "Registrations Closed", Toast.LENGTH_SHORT).show();

                        year.setText("");
                    }
                }


//                String status = Objects.requireNonNull(dataSnapshot.child("hasChoosen").getValue()).toString();
//                if(status.equals("true"))
//                {
//                    Map<String, Object> newmap = new HashMap<>();
//                    newmap.put("hasChoosen","false");
//                    ref.updateChildren(newmap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            Toast.makeText(MainActivity.this, "DONEee", Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//
//                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void Validate() {
        String YR= year.getText().toString();


        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Student Details");

        ref.orderByChild("RegistrationYear").equalTo(YR).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot childSnapshot: snapshot.getChildren()) {

                    //Check whether child with key 'isAvailable' exist or not
                    if (childSnapshot.hasChild("hasChoosen")) {

                        //Now update the status with false
                        ref.child(Objects.requireNonNull(childSnapshot.getKey()))
                                .child("hasChoosen")
                                .setValue(true);
                        Toast.makeText(sig_selection.this, "Registrations Opened", Toast.LENGTH_SHORT).show();
                        year.setText("");

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        ref.orderByChild("RegistrationYear").equalTo(YR).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
//
//                    //Check whether child with key 'isAvailable' exist or not
//                    if (childSnapshot.hasChild("hasChoosen")) {
//
//                        //Now update the status with false
//                        ref.child(Objects.requireNonNull(childSnapshot.getKey()))
//                                .child("hasChoosen")
//                                .setValue(false);
//                    }
//                }
//
//        ref.orderByChild("RegistrationYear").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                String status = (dataSnapshot.child("RegistrationYear").getValue().toString());
//                if(status.equals(YR))
//                {
//                    Map<String, Object> newmap = new HashMap<>();
//                    newmap.put("hasChoosen","true");
//                     ref.updateChildren(newmap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                         @Override
//                         public void onComplete(@NonNull Task<Void> task) {
//                             Toast.makeText(sig_selection.this, "DONE", Toast.LENGTH_SHORT).show();
//
//                         }
//                     });
//
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }
}


