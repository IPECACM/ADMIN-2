package com.example.dashbord;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeletePrime extends AppCompatActivity {
    DatabaseReference OData;

    // Creating RecyclerView.
    RecyclerView recyclerView;

    // Creating RecyclerView.Adapter.
    deleteprimeRecycler adapter ;


    // Creating List of ImageUploadInfo class.
    List<teammodel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_prime);
        recyclerView = (RecyclerView) findViewById(R.id.rv3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(DeletePrime.this));

        OData = FirebaseDatabase.getInstance().getReference("Members Details").child("Prime Core");


        OData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    teammodel imageUploadInfo = postSnapshot.getValue(teammodel.class);

                    list.add(imageUploadInfo);
                }

                adapter = new deleteprimeRecycler(getApplicationContext(), list);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DeletePrime.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
