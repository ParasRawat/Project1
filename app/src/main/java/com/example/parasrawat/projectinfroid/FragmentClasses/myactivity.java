package com.example.parasrawat.projectinfroid.FragmentClasses;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.parasrawat.projectinfroid.Adapters.HorizontalAdaptor;
import com.example.parasrawat.projectinfroid.ModelClasses.Report;
import com.example.parasrawat.projectinfroid.ModelClasses.User;
import com.example.parasrawat.projectinfroid.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class myactivity extends Fragment {
    View view;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    ArrayList<Integer> photoarray=new ArrayList<>();
    ArrayList<String> issue=new ArrayList<>();
    ArrayList<String> description=new ArrayList<>(),status=new ArrayList<>();
    public static final String TAG="myactivity";
    TextView t_null;
    ArrayList<Report> reports=new ArrayList<>();
    //TextView ratingmeter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_my,container,false);

        final TextView tcount=view.findViewById(R.id.Contribution);
        t_null=view.findViewById(R.id.t_null);
        t_null.setVisibility(View.VISIBLE);
        t_null.setText("Start Contributing \n By using Seek Help option");
        //ratingmeter=view.findViewById(R.id.ratingcount);
//        issue.add("Poverty");
//        description.add("Poverty Description \n One of the major cause for Indias high reputation");
//        photoarray.add(R.drawable.childlabour);
//        issue.add("ChildLabour");
//        description.add("Child labour description \n Key factor to hamper tha hampers the youth of our country");
//        photoarray.add(R.drawable.garbage);
//        issue.add("Garbage");
//        description.add("Garbage description \n The current issue targetted by our government under SWATCH BHARAT ABHIYAN");
//        photoarray.add(R.drawable.women);
//        issue.add("Feminism");
//        description.add("Feminism description \n Need of equal rights for men and women");

        recyclerView=view.findViewById(R.id.horizontalrecyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //fetch data
        String username=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        DatabaseReference dbrefu=FirebaseDatabase.getInstance().getReference("Users");
        final DatabaseReference dbrefc=FirebaseDatabase.getInstance().getReference("Contributions");
        dbrefu.child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                Log.d(TAG+"user",user.toString());
                Log.d(TAG+"datas",dataSnapshot.getValue().toString());
                HashMap<String,String> contris=new HashMap<>();
                if(user.getContributions()!=null) {
                    contris = user.getContributions();
                    for (HashMap.Entry<String,String> entry:contris.entrySet()) {
                        Log.d(TAG+"contriid",entry.getKey());
                        dbrefc.child(entry.getValue()).child(entry.getKey()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Report report=dataSnapshot.getValue(Report.class);
                                Log.d(TAG+"datasn",dataSnapshot.getValue().toString());
                                photoarray.add(R.drawable.poverty);
//                                issue.add(report.getTitle());
//                                description.add(report.getDesc());
//                                status.add(report.getStatus());
                                reports.add(report);

                                if(reports.size()!=0){
                                    t_null.setVisibility(View.GONE);
                                }
                                adapter=new HorizontalAdaptor(photoarray,reports,getContext());
                                recyclerView.setAdapter(adapter);
                                tcount.setText("Total Contributions = "+reports.size());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
                //TODO else "start contributing"
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}
