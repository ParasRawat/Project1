package com.example.parasrawat.projectinfroid.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.parasrawat.projectinfroid.ContentUploader;
import com.example.parasrawat.projectinfroid.ModelClasses.Report;
import com.example.parasrawat.projectinfroid.R;
import com.example.parasrawat.projectinfroid.RecyclerViewClick;

import java.util.ArrayList;
import java.util.List;

import static com.example.parasrawat.projectinfroid.MainActivity.TAG;

public class HorizontalAdaptor extends RecyclerView.Adapter<HorizontalAdaptor.ViewHolder> {
    ArrayList<Integer> photoarray;
    ArrayList<Report > reports;
    Context context;

    public HorizontalAdaptor(ArrayList<Integer> photoarray, ArrayList<Report> reports, Context context) {
        this.photoarray = photoarray;
        this.reports=reports;
        this.context = context;
    }

    @NonNull

    @Override
    public HorizontalAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerviewlayout,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final HorizontalAdaptor.ViewHolder viewHolder, final int i) {
        viewHolder.imageView.setImageResource(photoarray.get(i));
        final Report report=reports.get(i);
        viewHolder.issue.setText(report.getTitle());
        viewHolder.description.setText(report.getDesc());
        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, RecyclerViewClick.class);
                intent.putExtra("report",report);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return photoarray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        TextView issue;
        TextView description;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            issue=itemView.findViewById(R.id.issue);
            description=itemView.findViewById(R.id.description);
            imageView=itemView.findViewById(R.id.imageview);
            relativeLayout=itemView.findViewById(R.id.recyclerview);
        }
    }
}
