package com.example.parasrawat.projectinfroid;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
