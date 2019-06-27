package com.example.parasrawat.projectinfroid.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.parasrawat.projectinfroid.IntroSlider;
import com.example.parasrawat.projectinfroid.R;

public class ViewPagerAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    public int[] images={R.drawable.google,R.drawable.form,R.drawable.profile};
    public String[] headings={"Login","Report","Feed"};
    public int[] addimages={R.drawable.facebook};
    public String[] desc={"Login using facebook or google","File the Report with a short description","Keep track of your activities through feed"};
    public int[] backgroung={Color.rgb(0,0,128),
    Color.rgb(0,128,0),
    Color.rgb(220,60,60)};

    public ViewPagerAdapter(Context context) {
        this.context = context;

    }

    @Override
    public int getCount() {
        return this.headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view==o);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        if(position==0){
            View view=inflater.inflate(R.layout.slideother,container,false);
            RelativeLayout relativeLayout=view.findViewById(R.id.relativelayout2);
            ImageView imageView=view.findViewById(R.id.sliderimage);
            TextView head=view.findViewById(R.id.heading);
            TextView description=view.findViewById(R.id.desc);
            ImageView facebook=view.findViewById(R.id.facebook);
            relativeLayout.setBackgroundColor(backgroung[position]);
            imageView.setImageResource(images[position]);
            description.setText(desc[position]);
            head.setText(headings[position]);
            facebook.setImageResource(addimages[position]);
            container.addView(view);
            return view;
        }
        View view=inflater.inflate(R.layout.slides_layout,container,false);
        RelativeLayout relativeLayout=view.findViewById(R.id.relativelayout);
        ImageView imageView=view.findViewById(R.id.sliderimage);
        TextView head=view.findViewById(R.id.heading);
        TextView description=view.findViewById(R.id.desc);
        TextView lets=view.findViewById(R.id.start);
        lets.setVisibility(View.GONE);
        if(position==2){
            lets.setVisibility(View.VISIBLE);
            lets.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, IntroSlider.class));
                }
            });
        }
        relativeLayout.setBackgroundColor(backgroung[position]);
        imageView.setImageResource(images[position]);
        description.setText(desc[position]);
        head.setText(headings[position]);
        container.addView(view);
        return view;
    }
}
