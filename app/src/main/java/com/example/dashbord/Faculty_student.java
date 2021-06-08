package com.example.dashbord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Faculty_student extends AppCompatActivity implements View.OnClickListener{

    public CardView cardF,cardS,cardnew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculty_and_student);

        cardF=(CardView) findViewById(R.id.addFaculty);
        cardS=(CardView) findViewById(R.id.addStudent);
        cardnew=(CardView)findViewById(R.id.addsig);
        cardF.setOnClickListener(this);
        cardS.setOnClickListener(this);
        cardnew.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
    Intent i;

    switch(view.getId())
    {
        case R.id.addFaculty :
            i=new Intent(this, registration.class);
            startActivity(i);
            break;
       case R.id.addStudent :
            i=new Intent(this, addMembers.class);
            startActivity(i);
            break;
        case R.id.addsig:
            i=new Intent(this,registerSig.class);
            startActivity(i);
            break;

    }
    }
}