package com.example.parasrawat.projectinfroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parasrawat.projectinfroid.FragmentClasses.myactivity;
import com.example.parasrawat.projectinfroid.FragmentClasses.reportclass;
import com.example.parasrawat.projectinfroid.ModelClasses.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class ContentUploader extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    Spinner spinner;
    DatabaseReference dbrefu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_uploader);

        drawerLayout=findViewById(R.id.drawblelayout);
        Toolbar toolbar=findViewById(R.id.Toolbar);
        NavigationView navigationView=findViewById(R.id.navigationbar);

        //add New User in database
        final FirebaseUser curruser=FirebaseAuth.getInstance().getCurrentUser();
        final User user=new User();
        user.setUsername(curruser.getDisplayName());
        user.setEmail(curruser.getEmail());
        final DatabaseReference dbrefu=FirebaseDatabase.getInstance().getReference("Users");
        dbrefu.child(user.getUsername()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("contentuploader",dataSnapshot.toString());
                if (dataSnapshot.getValue()==null){
                    dbrefu.child(curruser.getDisplayName()).setValue(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        navigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new reportclass()).commit();
            navigationView.setCheckedItem(R.id.seekhelp);
        }

        //remove title from toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //retrieve and set useremail
        View view=navigationView.getHeaderView(0);
        TextView temail=view.findViewById(R.id.navuseremail);
        String email=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        temail.setText(email);

        //retrieve and set username
        TextView tname=view.findViewById(R.id.navusername);
        String username=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        tname.setText(username);

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            finishAffinity();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.seekhelp:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new reportclass()).commit();

                break;
            case R.id.contributions:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new myactivity()).commit();

                break;
            case R.id.logout:
                Toast.makeText(this,"User Signed Out",Toast.LENGTH_LONG).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ContentUploader.this,MainActivity.class));
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}
