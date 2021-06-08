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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class magazineRecycler extends RecyclerView.Adapter<magazineRecycler.ViewHolder> {

    private Context context;
    private List<magazinemodel> MainImageUploadInfoList;


    public magazineRecycler(Context context, List<magazinemodel> TempList) {

        this.MainImageUploadInfoList = TempList;

        this.context = context;

    }


    @Override
    public magazineRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.magazinelayout, parent, false);

        magazineRecycler.ViewHolder viewHolder = new magazineRecycler.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final magazineRecycler.ViewHolder holder, int position) {
        final magazinemodel UploadInfo = MainImageUploadInfoList. get(position);


        holder.Title.setText(UploadInfo.getMagazineTitle());


        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder= new AlertDialog.Builder(holder.Delete.getContext());
                builder.setMessage("Are you Sure you want to Delete?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Magazine");

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
//                DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Magazine");
//                reference.child(UploadInfo.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
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





    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        public TextView  Title;
        //        public TextView Email;

        public ImageView Delete;
        //public ImageView Edit;
//        public Button Show;
//        public CardView cardView;
//        public TextView UserID;

        public ViewHolder(View itemView) {
            super(itemView);

            Title = (TextView) itemView.findViewById(R.id.f_name);

            Delete=(ImageView)itemView.findViewById(R.id.f_delete);
            //Edit=(ImageView)itemView.findViewById(R.id.editnotice);


        }
    }
}


