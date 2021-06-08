package com.example.dashbord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskStatus extends AppCompatActivity {
    private RecyclerView recyclerView;
    DatabaseReference reference;
    TaskStatusRecycler adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_status);
        Bundle bundle = getIntent().getExtras();
        String category = bundle != null ? bundle.getString("category") : null;


        Log.i("dnkjsndjksnjk", category);



        Toast.makeText(TaskStatus.this, category, Toast.LENGTH_SHORT).show();


        recyclerView = findViewById(R.id.rv11);
        recyclerView.setLayoutManager(new LinearLayoutManager(TaskStatus.this));
        recyclerView.setHasFixedSize(true);

        reference = FirebaseDatabase.getInstance().getReference().child("Tasks");

        Query query = reference.orderByChild("SIG").equalTo(category);

        FirebaseRecyclerOptions<taskdisplaymodel> options =
                new FirebaseRecyclerOptions.Builder<taskdisplaymodel>()
                        .setQuery(query, taskdisplaymodel.class)
                        .build();

        adapter = new TaskStatusRecycler(options, TaskStatus.this);
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