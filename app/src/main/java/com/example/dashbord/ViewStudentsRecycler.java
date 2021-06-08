package com.example.dashbord;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLEngineResult;

public class ViewStudentsRecycler extends RecyclerView.Adapter<ViewStudentsRecycler.ViewHolder> {

    private Context context;
    private List<studentmodel> MainImageUploadInfoList;


    public ViewStudentsRecycler(Context context, List<studentmodel> TempList) {

        this.MainImageUploadInfoList = TempList;

        this.context = context;

    }

//    public ViewStudentsRecycler(FirebaseRecyclerOptions<studentmodel> options, ViewStudents taskDisplay) {
//        super(options);
//        // this.MainImageUploadInfoList = TempList;
//
//        this.context = context;
////
//    }


    @Override
    public ViewStudentsRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewstudentlayout, parent, false);

        ViewStudentsRecycler.ViewHolder viewHolder = new ViewStudentsRecycler.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewStudentsRecycler.ViewHolder holder, int position) {
        final studentmodel UploadInfo = MainImageUploadInfoList.get(position);

        holder.Title.setText(UploadInfo.getName());
        holder.Status.setText(UploadInfo.getIsDone());
        holder.Drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Log.i("ijnbjnjd", UploadInfo.getId());
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.getMenuInflater().inflate(R.menu.popupmenu, popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.reject:

                            case R.id.accept:
                                Log.i("value", (String) menuItem.getTitle());
                                DatabaseReference data = (DatabaseReference) FirebaseDatabase.getInstance()
                                        .getReference("Student Details").child(UploadInfo.getId()).child("Task Submitted");
                                data.child(UploadInfo.getNewid()).child("isDone").setValue(menuItem.getTitle());
                                holder.Status.setText(UploadInfo.getIsDone());

                                Log.i("dkfcdffk",UploadInfo.getId());
                                Log.i("dkfcdffdvfdvdfk",UploadInfo.getNewid());




                               return true;


                        }
                        return false;
                    }
                });
            }
        });
    }






    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        public TextView Title;
        public  CardView layout;
       public  TextView Status;
       public  ImageView Drop;

        public ViewHolder(View itemView) {
            super(itemView);

            Title = (TextView) itemView.findViewById(R.id.s_name);
            layout=(CardView)itemView.findViewById(R.id.card);
            Status=itemView.findViewById(R.id.status);
            Drop=itemView.findViewById(R.id.drop);


        }
    }



}

