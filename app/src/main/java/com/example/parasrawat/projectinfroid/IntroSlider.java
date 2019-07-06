    package com.example.parasrawat.projectinfroid;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

    public class IntroSlider extends AppCompatActivity {
    private ImageView imageView1;
    TextView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slider);
        imageView2=findViewById(R.id.heading);
        imageView1=findViewById(R.id.splashimage);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.anim);
        imageView1.startAnimation(animation);
        imageView2.startAnimation(animation);
        Thread timer=new Thread(){
            public void run(){
                try{
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                    if(user!=null) startActivity(new Intent(IntroSlider.this,ContentUploader.class));
                    else startActivity(new Intent(IntroSlider.this,OnBoardingActivity.class));
                }
            }
        };
        timer.start();
    }
}
