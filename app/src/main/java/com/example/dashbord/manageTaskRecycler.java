package com.example.dashbord;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class    manageTaskRecycler extends RecyclerView.Adapter<manageTaskRecycler.ViewHolder> {

    private Context context;
    private List<taskmodel> MainImageUploadInfoList;


    public manageTaskRecycler(Context context, List<taskmodel> TempList) {

        this.MainImageUploadInfoList = TempList;
        this.context = context;

    }


    @Override
    public manageTaskRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.managetaskslayout, parent, false);

        manageTaskRecycler.ViewHolder viewHolder = new manageTaskRecycler.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final manageTaskRecycler.ViewHolder holder, int position) {
        final taskmodel UploadInfo = MainImageUploadInfoList. get(position);


        holder.Name.setText( UploadInfo.getName());

        holder.Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("category", UploadInfo.getName());
             Intent intent = new Intent(context, TaskDisplay.class);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        public TextView Name;
        public CardView Card;
//        public TextView Post;
        public ImageView Delete;
//        public ImageView Edit;
//        public ImageView Icon;


        public ViewHolder(View itemView) {
            super(itemView);

            Name = (TextView) itemView.findViewById(R.id.t_name);
            Card=(CardView)itemView.findViewById(R.id.card);
            Delete=(ImageView)itemView.findViewById(R.id.delete);


        }
    }
}



