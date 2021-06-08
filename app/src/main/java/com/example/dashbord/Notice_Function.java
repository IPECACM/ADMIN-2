package com.example.dashbord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Notice_Function extends AppCompatActivity implements View.OnClickListener{

    public CardView cardA,cardD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice__function);

        cardA=(CardView) findViewById(R.id.addNotice);
        cardD=(CardView)findViewById(R.id.delete);
        cardD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Notice_Function.this,NoticeAdapter.class);
                startActivity(i);
            }
        });
        cardA.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;

        switch(view.getId()) {
            case R.id.addNotice:
                i = new Intent(this, Notice.class);
                startActivity(i);
                break;

        }



    }
}