package com.example.dashbord;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
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

public class GalleryRecycler extends RecyclerView.Adapter<GalleryRecycler.ViewHolder> {

    private Context context;
    private List<gallerymodel> MainImageUploadInfoList;


    public GalleryRecycler(Context context, List<gallerymodel> TempList) {

        this.MainImageUploadInfoList = TempList;

        this.context = context;

    }


    @Override
    public GalleryRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallerylayout, parent, false);

        GalleryRecycler.ViewHolder viewHolder = new GalleryRecycler.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final GalleryRecycler.ViewHolder holder, int position) {
        final gallerymodel UploadInfo = MainImageUploadInfoList. get(position);


        Glide.with(context).load(UploadInfo.getImage()).into(holder.Image);


holder.Drop.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.dropdown,popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.delete:

                        AlertDialog.Builder builder= new AlertDialog.Builder(holder.Drop.getContext());
                        builder.setMessage("Are you Sure you want to Delete?");
                        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase.getInstance().getReference("Gallery").child(UploadInfo.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                            }
                        });
                        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.create();
                        builder.show();


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

        public ImageView Image;
        public ImageView Drop;

        public ViewHolder(View itemView) {
            super(itemView);

            Image= (ImageView) itemView.findViewById(R.id.imageView);
            Drop=(ImageView)itemView.findViewById(R.id.dropdown);


        }
    }
}


