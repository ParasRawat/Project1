package com.example.parasrawat.projectinfroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;

import java.util.ArrayList;

public class OnBoardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        PaperOnboardingPage scr1=new PaperOnboardingPage("Seek Help",
                "Start contributing by using this option",Color.rgb(220,215,153),R.drawable.ss1,R.drawable.socialcare);
        PaperOnboardingPage scr2=new PaperOnboardingPage("My Contributions",
                "Review all your contributions and keep track of help status",Color.rgb(238,177,173),R.drawable.ss2,R.drawable.charity);

        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(scr1);
        elements.add(scr2);

        PaperOnboardingFragment fragment=PaperOnboardingFragment.newInstance(elements);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment,fragment).commit();

        fragment.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
            @Override
            public void onRightOut() {
                Intent i=new Intent(OnBoardingActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}
