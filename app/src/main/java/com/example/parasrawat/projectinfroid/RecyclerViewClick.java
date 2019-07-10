package com.example.parasrawat.projectinfroid;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.parasrawat.projectinfroid.ModelClasses.Report;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class RecyclerViewClick extends AppCompatActivity {
    public static final String TAG="RECYCLER_VIEW_ONCLICK";
    StorageReference strref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerviewclicklayout);

        strref=FirebaseStorage.getInstance().getReference();

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
        //retrieve image
        ArrayList<String> imgarray=report.getImguris();
        if(imgarray!=null){
            strref.child("HelpImages").child(report.getType()).child(report.getKey()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    ImageView imageView=findViewById(R.id.imageview);
                    Picasso.get().load(uri).into(imageView);
                }
            });
        }
    }

}
