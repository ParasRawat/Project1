package com.example.parasrawat.projectinfroid.FragmentClasses;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parasrawat.projectinfroid.MainActivity;
import com.example.parasrawat.projectinfroid.ModelClasses.Report;
import com.example.parasrawat.projectinfroid.ModelClasses.User;
import com.example.parasrawat.projectinfroid.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class reportclass extends Fragment {

    Spinner spinner;
    View view;
    String title,type,desc;
    ArrayList<String> imguris=new ArrayList<>();
    DatabaseReference dbrefc,dbrefu;
    User user=new User();
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.demoreport,container,false);
//        toggle.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CardView addimg=view.findViewById(R.id.addimg);
        imageView=view.findViewById(R.id.imgv1);
        spinner=view.findViewById(R.id.issuespinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(),R.array.issue,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        CardView submit=view.findViewById(R.id.b_submit);
        dbrefc=FirebaseDatabase.getInstance().getReference("Contributions");
        dbrefu=FirebaseDatabase.getInstance().getReference("Users");

        //set user email from shared preferences
        final String username=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        user.setUsername(username);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText th=view.findViewById(R.id.reprtheader);
                final EditText td=view.findViewById(R.id.reportmessage);
                TextView therr=view.findViewById(R.id.err_th);
                TextView tderr=view.findViewById(R.id.err_td);
                TextView tyerr=view.findViewById(R.id.err_type);

                title=th.getText().toString();
                type=spinner.getSelectedItem().toString();
                desc=td.getText().toString();
                therr.setVisibility(View.GONE);
                tderr.setVisibility(View.GONE);
                tyerr.setVisibility(View.GONE);

                Boolean valid=true;

                if(title.isEmpty()){
//                    th.setError("set title");
//                    th.requestFocus();
                    therr.setVisibility(View.VISIBLE);
                    valid=false;
                }
                if(desc.isEmpty()){
                    tderr.setVisibility(View.VISIBLE);
                    valid=false;
                }
                if(spinner.getSelectedItemPosition()==0){
                    tyerr.setVisibility(View.VISIBLE);
                    valid=false;
                }
                if(valid){
                    //push contribution data in contributions table
                    final Report report=new Report();
                    report.setByuser(user.getUsername());
                    report.setTitle(title);
                    report.setType(type);
                    report.setDesc(desc);
                    report.setImguris(imguris);
                    Date curr=Calendar.getInstance().getTime();
                    report.setTime(curr.toString());
                    final String key=dbrefc.child(type).push().getKey();
                    dbrefc.child(type).child(key).setValue(report);

                    //push contirbution id in user table
                    dbrefu.child(user.getUsername()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            user=dataSnapshot.getValue(User.class);
                            HashMap<String,String> contris=new HashMap<>();
                            if(user.getContributions()!=null) contris=user.getContributions();
                            contris.put(report.getType(),key);
                            user.setContributions(contris);
                            dbrefu.child(user.getUsername()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getContext(), "Thankyou for being a good citizen :)", Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        //add image
        addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        return view;
    }

    @Override
    public boolean getAllowEnterTransitionOverlap() {
        return super.getAllowEnterTransitionOverlap();
    }

    void  AlertDialog(){
        final AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
        alert.setMessage("Are you sure your want to exit?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getActivity().finish();
                ;
            }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

    }

    private void selectImage(){
        final CharSequence[] options={"Choose from Gallery","Cancel"};
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(options[i].equals("Take Photo")){
                    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //File f=new File(android.os.Environment.getExternalStorageDirectory(),"temp.jpg");
                    //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent,1);
                }
                else if(options[i].equals("Choose from Gallery")){
                    Intent intent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent,2);
                }
                else if(options[i].equals("Cancel")){
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==1){
//                File f = new File(Environment.getExternalStorageDirectory().toString());
//                for (File temp : f.listFiles()) {
//                    if (temp.getName().equals("temp.jpg")) {
//                        f = temp;
//                        break;
//                    }
//                }
//                try {
//                    Bitmap bitmap;
//                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
//                            bitmapOptions);
//                    imageView.setImageBitmap(bitmap);
//                    String path = android.os.Environment
//                            .getExternalStorageDirectory()
//                            + File.separator
//                            + "Phoenix" + File.separator + "default";
//                    f.delete();
//                    OutputStream outFile = null;
//                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
//                    try {
//                        outFile = new FileOutputStream(file);
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
//                        outFile.flush();
//                        outFile.close();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                Bundle extras = data.getExtras();
//                Bitmap imageBitmap = (Bitmap) extras.get("data");
//                imageView.setImageBitmap(imageBitmap);
            }
            else if(requestCode==2 && data!=null && data.getData()!=null){
                Uri selectedimg=data.getData();
//                String[] filePath = { MediaStore.Images.Media.DATA };
//                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
//                c.moveToFirst();
//                int columnIndex = c.getColumnIndex(filePath[0]);
//                String picturePath = c.getString(columnIndex);
//                c.close();
//                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                //imguris.add(selectedimg.toString());
                imageView.setVisibility(View.VISIBLE);
                Picasso.get().load(selectedimg).into(imageView);
            }
        }
    }
}
