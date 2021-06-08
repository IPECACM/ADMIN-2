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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
public class taskDisplayRecycler extends FirebaseRecyclerAdapter<taskdisplaymodel,taskDisplayRecycler.ViewHolder> {

    private Context context;
    private List<taskdisplaymodel> MainImageUploadInfoList;


//    public taskDisplayRecycler(Context context, List<taskdisplaymodel> TempList) {
//
//        this.MainImageUploadInfoList = TempList;
//
//        this.context = context;
//
//    }

    public taskDisplayRecycler(FirebaseRecyclerOptions<taskdisplaymodel> options, Context context) {
        super(options);
        // this.MainImageUploadInfoList = TempList;

         this.context = context;
//
    }


    @Override
    public taskDisplayRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.uploadtasklayout, parent, false);

        taskDisplayRecycler.ViewHolder viewHolder = new taskDisplayRecycler.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final taskDisplayRecycler.ViewHolder holder, int position,@NonNull final taskdisplaymodel UploadInfo) {
//        final taskdisplaymodel UploadInfo = MainImageUploadInfoList.get(position);


        holder.Title.setText(UploadInfo.getName());
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder= new AlertDialog.Builder(holder.Delete.getContext());
                builder.setMessage("Are you Sure you want to Delete?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Tasks");

                        reference.child(UploadInfo.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
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
//                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Tasks");
//                reference.child(UploadInfo.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        //Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
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
//    }

//    @Override
//    public int getItemCount() {
//
//        return MainImageUploadInfoList.size();
//    }

    class ViewHolder extends RecyclerView.ViewHolder {


        public TextView Title;
        public ImageView Delete;

        public ViewHolder(View itemView) {
            super(itemView);

            Title = (TextView) itemView.findViewById(R.id.task);
            Delete=itemView.findViewById(R.id.delete);


        }
    }
}
