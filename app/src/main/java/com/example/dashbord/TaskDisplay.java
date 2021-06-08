package com.example.dashbord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TaskDisplay extends AppCompatActivity {

    private RecyclerView recyclerView;
    DatabaseReference reference;
    taskDisplayRecycler adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_display);

        Bundle bundle = getIntent().getExtras();
        String category = bundle != null ? bundle.getString("category") : null;

         Log.i("dnkjsndjksnjk", category);


          Toast.makeText(TaskDisplay.this, category, Toast.LENGTH_SHORT).show();


        recyclerView =findViewById(R.id.rv6);
        recyclerView.setLayoutManager(new LinearLayoutManager(TaskDisplay.this));
        recyclerView.setHasFixedSize(true);

        reference = FirebaseDatabase.getInstance().getReference().child("Tasks");

        Query query = reference.orderByChild("SIG").equalTo(category);

        FirebaseRecyclerOptions<taskdisplaymodel> options =
                new FirebaseRecyclerOptions.Builder<taskdisplaymodel>()
                        .setQuery(query, taskdisplaymodel.class)
                        .build();

        adapter = new taskDisplayRecycler(options, TaskDisplay.this);
        recyclerView.setAdapter(adapter);



    }


    @Override
    public void onStart () {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop () {
        super.onStop();
        adapter.stopListening();
    }
}

