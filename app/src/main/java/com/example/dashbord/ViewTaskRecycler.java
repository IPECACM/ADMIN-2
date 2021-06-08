package com.example.dashbord;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewTaskRecycler extends RecyclerView.Adapter<ViewTaskRecycler.ViewHolder> {

    private Context context;
    private List<sigmodel> MainImageUploadInfoList;


    public ViewTaskRecycler(Context context, List<sigmodel> TempList) {

        this.MainImageUploadInfoList = TempList;
        this.context = context;

    }


    @Override
    public ViewTaskRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewtasklayout, parent, false);

        ViewTaskRecycler.ViewHolder viewHolder = new ViewTaskRecycler.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewTaskRecycler.ViewHolder holder, int position) {
        final sigmodel UploadInfo = MainImageUploadInfoList.get(position);


        holder.Name.setText(UploadInfo.getName());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("category", UploadInfo.getName());
                Intent intent = new Intent(context, TaskStatus.class);
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
        public CardView layout;


        public ViewHolder(View itemView) {
            super(itemView);

            Name = (TextView) itemView.findViewById(R.id.t_name);
            layout=(CardView)itemView.findViewById(R.id.card);


        }
    }
}
