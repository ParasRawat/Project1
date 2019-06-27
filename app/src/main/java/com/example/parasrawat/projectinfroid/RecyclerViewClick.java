package com.example.parasrawat.projectinfroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewClick extends AppCompatActivity {
    public static final String TAG="RECYCLER_VIEW_ONCLICK";
    ArrayList<Integer> photoarray=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerviewclicklayout);
        photoarray.add(R.drawable.poverty);
        photoarray.add(R.drawable.childlabour);
        photoarray.add(R.drawable.garbage);
        photoarray.add(R.drawable.women);
        getIncomingIntents();


    }
    private void getIncomingIntents(){
        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("image_issue") && getIntent().hasExtra("image_desc")){
            String imageurl=getIntent().getStringExtra("image_url");
            String imageissue=getIntent().getStringExtra("image_issue");
            String imagedesc=getIntent().getStringExtra("image_desc");
            String reportstatus=getIntent().getStringExtra("report_status");
            Log.d(TAG, "getIncomingIntents ======: "+imageurl);
            setview(imageurl,imageissue,imagedesc,reportstatus);


        }
    }

    private void setview(String imageur,String imageissue,String imagedesc,String status){
        TextView issue=findViewById(R.id.description_click);
        issue.setText(imageissue);
        TextView description=findViewById(R.id.typeddescription);
        description.setText(imagedesc);
        ImageView imageView=findViewById(R.id.imageview);
        imageView.setImageResource(photoarray.get(Integer.parseInt(imageur)));
//        imageView.setImageResource(Integer.parseInt(imageur));
        TextView t_status=findViewById(R.id.t_status);
        t_status.setText("Status : "+status);



    }

}
