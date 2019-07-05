package com.example.parasrawat.projectinfroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.parasrawat.projectinfroid.ModelClasses.Report;

import java.util.ArrayList;

public class RecyclerViewClick extends AppCompatActivity {
    public static final String TAG="RECYCLER_VIEW_ONCLICK";
    ArrayList<Integer> photoarray=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerviewclicklayout);

        //photoarray.add(R.drawable.poverty);

        getIncomingIntents();


    }
    private void getIncomingIntents(){
        if(getIntent().hasExtra("report")){
            Report report=(Report) getIntent().getSerializableExtra("report");
            setView(report);
        }
    }

    private void setView(Report report){
        TextView issue=findViewById(R.id.t_title);
        issue.setText(report.getTitle());
        TextView t_status=findViewById(R.id.t_status);
        t_status.setText(report.getStatus());
        TextView t_category=findViewById(R.id.t_category);
        t_category.setText(report.getType());
        TextView description=findViewById(R.id.t_desc);
        description.setText(report.getDesc());
        //TODO retrieve image
//        if(photoarray.get(0)!=null){
//            ImageView imageView=findViewById(R.id.imageview);
//            imageView.setImageResource(photoarray.get(0));
//        }
    }

}
