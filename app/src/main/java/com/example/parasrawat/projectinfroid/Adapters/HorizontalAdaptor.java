package com.example.parasrawat.projectinfroid.Adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.parasrawat.projectinfroid.ModelClasses.Report;
import com.example.parasrawat.projectinfroid.R;
import com.example.parasrawat.projectinfroid.RecyclerViewClick;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HorizontalAdaptor extends RecyclerView.Adapter<HorizontalAdaptor.ViewHolder> {
    ArrayList<Report > reports;
    Context context;

    public HorizontalAdaptor(ArrayList<Report> reports, Context context) {
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
        //viewHolder.imageView.setImageResource(photoarray.get(i));
        final Report report=reports.get(i);
        viewHolder.issue.setText(report.getTitle());
        viewHolder.description.setText(report.getDesc());
        ArrayList<String> imgarray=report.getImguris();
        if(imgarray!=null){
            StorageReference strref=FirebaseStorage.getInstance().getReference();
            strref.child("HelpImages").child(report.getType()).child(report.getKey()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(viewHolder.imageView);
                }
            });
        }
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
        return reports.size();
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
