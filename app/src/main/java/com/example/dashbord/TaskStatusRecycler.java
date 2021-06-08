package com.example.dashbord;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskStatusRecycler extends FirebaseRecyclerAdapter<taskdisplaymodel,TaskStatusRecycler.ViewHolder> {

    private Context context;


//    public TaskStatusRecycler(Context context, ArrayList<Map<String,String>> TempList) {
//
//        this.tasklist = TempList;
//
//        this.context = context;
//
//    }

    public TaskStatusRecycler(FirebaseRecyclerOptions<taskdisplaymodel> options, Context context) {
        super(options);
        this.context = context;
//
    }

    @Override
    public TaskStatusRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.taskstatuslayout, parent, false);

        TaskStatusRecycler.ViewHolder viewHolder = new TaskStatusRecycler.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TaskStatusRecycler.ViewHolder holder, int position,@NonNull final taskdisplaymodel UploadInfo) {


            holder.Title.setText(UploadInfo.getName());
           holder.Card.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Bundle bundle = new Bundle();
                   bundle.putString("categorynew", UploadInfo.getName());
                   Intent intent = new Intent(context, ViewStudents.class);
                   intent.putExtras(bundle);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent);
               }
           });





    }

//    @Override
//    public int getItemCount() {
//
//        return tasklist.size();
//    }

    class ViewHolder extends RecyclerView.ViewHolder {


       public  TextView Title;
      //  public  TextView Title1;
        public  CardView Card;


        public ViewHolder(View itemView) {
            super(itemView);

            Title = (TextView) itemView.findViewById(R.id.f_name);
           //w Title1 = (TextView) itemView.findViewById(R.id.f_post);
            Card=(CardView)itemView.findViewById(R.id.card);


        }
    }
}