package com.example.dashbord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ViewStudents extends AppCompatActivity {
    private RecyclerView recyclerView;
    DatabaseReference reference;
    ViewStudentsRecycler adapter;
    List<studentmodel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);

        Bundle bundle = getIntent().getExtras();
        String category = bundle != null ? bundle.getString("categorynew") : null;

        //String categorynew = bundle != null ? bundle.getString("categorynew") : null;
//
//
      //  Log.i("dnkjsndjksnjk", category);
//       // Log.i("dnkjsndjksnjk", categorynew);
//
//
//
//        Toast.makeText(ViewStudents.this, category, Toast.LENGTH_SHORT).show();
//
//

        recyclerView =findViewById(R.id.rv10);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewStudents.this));
        recyclerView.setHasFixedSize(true);
        reference= FirebaseDatabase.getInstance().getReference("Student Details");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot s: snapshot.getChildren())
                {


                    if(s.child("Task Submitted").hasChildren()) {
                        Log.i("asgsdadhsad", "hai bhai");

                        for (DataSnapshot data : s.child("Task Submitted").getChildren()) {

                            studentmodel m= new studentmodel();

                            if(data.child("Task Name").getValue().toString().equals(category)) {
                                m.setName(s.child("Name").getValue().toString());
                                m.setId(s.child("id").getValue().toString());
                                m.setIsDone(data.child("isDone").getValue().toString());
                                m.setNewid(data.child("newid").getValue().toString());
                                Log.i("oiedocfgvfrgjccvfjc", s.child("Name").getValue().toString());
                                Log.i("vfrgjccvfjc", s.child("id").getValue().toString());


                                list.add(m);


                           }
                           adapter = new ViewStudentsRecycler(getApplicationContext(), list);
                           adapter.notifyDataSetChanged();
                           recyclerView.setAdapter(adapter);
////
                            }
                        }
                    }
                }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

//
//        reference = FirebaseDatabase.getInstance().getReference().child("Student Details");
//        reference=FirebaseDatabase.getInstance().getReference().child("Student Tasks").child("SIG TECH").child(category);
//
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//           override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot s: snapshot.getChildren())
//                {
//                   // Log.i("hndjdhkjcc",s.child("Name").getValue().toString());
//
//                    newmodel imageUploadInfo = s.getValue(newmodel.class);
//
//                    list.add(imageUploadInfo);
//                }
//
//                adapter = new ViewStudentsRecycler(ViewStudents.this, list);
//                adapter.notifyDataSetChanged();
//                recyclerView.setAdapter(adapter);
//
//                }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        reference=FirebaseDatabase.getInstance().getReference().child("Student Tasks").child("SIG SOFT").child(category);
//
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot s: snapshot.getChildren())
//                {
//                    // Log.i("hndjdhkjcc",s.child("Name").getValue().toString());
//
//
//                    newmodel imageUploadInfo = s.getValue(newmodel.class);
//
//                    list.add(imageUploadInfo);
//                }
//
//                adapter = new ViewStudentsRecycler(ViewStudents.this, list);
//                adapter.notifyDataSetChanged();
//                recyclerView.setAdapter(adapter);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        reference=FirebaseDatabase.getInstance().getReference().child("Student Tasks").child("SIG FOUNDATION").child(category);
//
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot s: snapshot.getChildren())
//                {
//                    // Log.i("hndjdhkjcc",s.child("Name").getValue().toString());
//
//
//                    newmodel imageUploadInfo = s.getValue(newmodel.class);
//
//                    list.add(imageUploadInfo);
//                }
//
//                adapter = new ViewStudentsRecycler(ViewStudents.this, list);
//                adapter.notifyDataSetChanged();
//                recyclerView.setAdapter(adapter);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        reference=FirebaseDatabase.getInstance().getReference().child("Student Tasks").child("SIG PYTHON").child(category);
//
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot s: snapshot.getChildren())
//                {
//                    // Log.i("hndjdhkjcc",s.child("Name").getValue().toString());
//
//
//                    newmodel imageUploadInfo = s.getValue(newmodel.class);
//
//                    list.add(imageUploadInfo);
//                }
//
//                adapter = new ViewStudentsRecycler(ViewStudents.this, list);
//                adapter.notifyDataSetChanged();
//                recyclerView.setAdapter(adapter);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        reference=FirebaseDatabase.getInstance().getReference().child("Student Tasks").child("SIG GRAPH + SIG WEB").child(category);
//
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot s: snapshot.getChildren())
//                {
//
//                    newmodel imageUploadInfo = s.getValue(newmodel.class);
//
//                    list.add(imageUploadInfo);
//                }
//
//                adapter = new ViewStudentsRecycler(ViewStudents.this, list);
//                adapter.notifyDataSetChanged();
//                recyclerView.setAdapter(adapter);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
////
////       reference.addListenerForSingleValueEvent(new ValueEventListener() {
////           @Override
////           public void onDataChange(@NonNull DataSnapshot snapshot) {
////
////
////               for (DataSnapshot s : snapshot.getChildren()) {
////
////                   if (s.child("Tasks").child(category).hasChildren()) {
////
////                       for (DataSnapshot data : s.child("Tasks").child(category).getChildren()) {
////
////                           studentmodel model = new studentmodel();
////
////                           Log.i("asdgkhgs", data.child("isDone").getValue().toString());
////                           if (data.child("isDone").getValue().toString().equals("Under Review")) {
////
////                               Log.i("asgads", "true");
////                               model.setName(s.child("Name").getValue().toString());
////
//////                               model.setId(s.child("Email").getValue().toString());
//////                               model.setChoice0(s.child("Choice0").getValue().toString());
//////                               model.setChoice1(s.child("Choice1").getValue().toString());
//////                               model.setChoice2(s.child("Choice2").getValue().toString());
////
////
////                               list.add(model);
////
////
////                           }
////                           adapter = new ViewStudentsRecycler(getApplicationContext(), list);
////                           adapter.notifyDataSetChanged();
////                           recyclerView.setAdapter(adapter);
////
////                       }
////                   }
////               }
////           }
////
////
////
////           @Override
////           public void onCancelled(@NonNull DatabaseError error) {
////
////           }
////       });
////           }
////}
    }
}