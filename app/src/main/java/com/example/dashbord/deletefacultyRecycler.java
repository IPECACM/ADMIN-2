package com.example.dashbord;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class deletefacultyRecycler extends RecyclerView.Adapter<deletefacultyRecycler.ViewHolder> {

    private Context context;
    private List<teammodel> MainImageUploadInfoList;


    public deletefacultyRecycler(Context context, List<teammodel> TempList) {

        this.MainImageUploadInfoList = TempList;

        this.context = context;

    }


    @Override
    public deletefacultyRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deletefacultylayout, parent, false);

        deletefacultyRecycler.ViewHolder viewHolder = new deletefacultyRecycler.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final deletefacultyRecycler.ViewHolder holder, int position) {
        final teammodel UploadInfo = MainImageUploadInfoList. get(position);


        holder.Name.setText(UploadInfo.getF_Name());
        holder.Post.setText(UploadInfo.getF_Post());
        Glide.with(context).load(UploadInfo.getF_Image()).into(holder.Icon);


        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder= new AlertDialog.Builder(holder.Delete.getContext());
                builder.setMessage("Are you Sure you want to Delete?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Members Details").child("Faculty");

                        reference.child(UploadInfo.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.create();
                builder.show();


            }
        });


    }
//                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Members Details").child("Faculty");
//                reference.child(UploadInfo.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                    }
//                });
//
//
//            }
//        });
//
//


    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        public TextView Name;
        public TextView Post;
        public ImageView Delete;
        public ImageView Edit;
        public ImageView Icon;


        public ViewHolder(View itemView) {
            super(itemView);

            Name = (TextView) itemView.findViewById(R.id.f_name);
            Post = (TextView) itemView.findViewById(R.id.f_post);
            Delete=(ImageView)itemView.findViewById(R.id.f_delete);
          //  Edit=(ImageView)itemView.findViewById(R.id.f_edit);
            Icon=(ImageView)itemView.findViewById(R.id.f_icon);


        }
    }
}


