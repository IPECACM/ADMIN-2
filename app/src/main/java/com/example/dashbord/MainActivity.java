package com.example.dashbord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Year;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public CardView cardR,cardN,cardM,cardG,cardsig,cardsyllabus,viewtask,sig_selection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //year=findViewById(R.id.yr);
      //Ok=findViewById(R.id.ok);
      //Close=findViewById(R.id.close);
      sig_selection=(CardView)findViewById(R.id.sig_selection_form);
        cardR=(CardView) findViewById(R.id.registration);
        cardN=(CardView) findViewById(R.id.addNotice);
        cardM=(CardView) findViewById(R.id.magazine);
        cardG=(CardView) findViewById(R.id.gallery);
        cardsig=(CardView) findViewById(R.id.sig);
        cardsyllabus=(CardView)findViewById(R.id.sigsyllabus);
        viewtask=findViewById(R.id.viewtask);
        sig_selection.setOnClickListener(this);
        cardG.setOnClickListener(this);
        cardM.setOnClickListener(this);
        cardR.setOnClickListener(this);
        cardN.setOnClickListener(this);
        cardsig.setOnClickListener(this);
        cardsyllabus.setOnClickListener(this);
        viewtask.setOnClickListener(this);
//Fauth=FirebaseAuth.getInstance();
/*
        This is commitnew222222222.

        */


/*       Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String YR= year.getText().toString();
                if(TextUtils.isEmpty(YR))
                {
                    Toast.makeText(MainActivity.this, "Fill", Toast.LENGTH_SHORT).show();
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
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        ref.orderByChild("RegistrationYear").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                String status = Objects.requireNonNull(dataSnapshot.child("RegistrationYear").getValue()).toString();
//                if(status.equals(YR))
//                {
//                    Map<String, Object> newmap = new HashMap<>();
//                    newmap.put("hasChoosen","true");
//                     ref.updateChildren(newmap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                         @Override
//                         public void onComplete(@NonNull Task<Void> task) {
//                             Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
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
//        });*/
    }




    @Override
    public void onClick(View view) {
        Intent i;

        switch(view.getId()){
            case R.id.registration :
                i=new Intent(this,Faculty_student.class);
                startActivity(i);
                break;
            case R.id.addNotice :
                i=new Intent(this,Notice_Function.class);
                startActivity(i);
                break;
            case R.id.magazine :
                i=new Intent(this,Magazine.class);
                startActivity(i);
                break;
            case R.id.gallery :
                i=new Intent(this,MainActivity2.class);
                startActivity(i);
                break;
            case R.id.sig :
                i=new Intent(this,taskUpload.class);
                startActivity(i);
                break;
            case R.id.sigsyllabus:
                i=new Intent(this,Syllabus.class);
                startActivity(i);
                break;
            case R.id.viewtask:
                i=new Intent(this,ViewTasks.class);
                startActivity(i);
                break;
            case R.id.sig_selection_form:
                i=new Intent(this,sig_selection.class);
                startActivity(i);
                break;

        }
    }
}