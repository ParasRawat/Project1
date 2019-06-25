    package com.example.parasrawat.projectinfroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

    public class IntroSlider extends AppCompatActivity {
    private ImageView imageView1,imageView2;

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
                        startActivity(new Intent(IntroSlider.this,MainActivity.class));
                }
            }
        };
        timer.start();

    }
}
