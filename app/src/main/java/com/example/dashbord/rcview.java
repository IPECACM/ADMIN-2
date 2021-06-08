package com.example.dashbord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


public class rcview extends AppCompatActivity {

    RecyclerView rcv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rcview);

        rcv=(RecyclerView)findViewById(R.id.rcview);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        String arr[]={"SIG WEB","SIG GRAPH","SIG PLAN","SIG SOFT","SIG PYTHON","SIG TECH","SIG FOUNDATION"};
        rcv.setAdapter(new myadpater(arr));





    }
}