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

public class SyllabusRecycler extends RecyclerView.Adapter<SyllabusRecycler.ViewHolder> {

    private Context context;
    private List<syllabusmodel> MainImageUploadInfoList;


    public SyllabusRecycler(Context context, List<syllabusmodel> TempList) {

        this.MainImageUploadInfoList = TempList;

        this.context = context;

    }


    @Override
    public SyllabusRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.syllabuslayout, parent, false);

        SyllabusRecycler.ViewHolder viewHolder = new SyllabusRecycler.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SyllabusRecycler.ViewHolder holder, int position) {
        final syllabusmodel UploadInfo = MainImageUploadInfoList. get(position);


        holder.Title.setText(UploadInfo.getName());
        holder.SIG.setText(UploadInfo.getSIG());

        holder.Delete.setOnClickListener(new View.OnClickListener() {

//
            @Override
              public void onClick(View view) {

            AlertDialog.Builder builder= new AlertDialog.Builder(holder.Delete.getContext());
                builder.setMessage("Are you Sure you want to Delete?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Syllabus");

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

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        public TextView  Title;
        //        public TextView Email;
  public TextView SIG;
        public ImageView Delete;
        //public ImageView Edit;
//        public Button Show;
//        public CardView cardView;
//        public TextView UserID;

        public ViewHolder(View itemView) {
            super(itemView);

            Title = (TextView) itemView.findViewById(R.id.f_name);
            SIG = (TextView) itemView.findViewById(R.id.f_post);

            Delete=(ImageView)itemView.findViewById(R.id.f_delete);
            //Edit=(ImageView)itemView.findViewById(R.id.editnotice);


        }
    }
}


