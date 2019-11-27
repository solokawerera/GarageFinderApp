package com.example.myrescueapp;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.myrescueapp.utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    DataSnapshot postSnapshot2;
    private Context mContext = TestActivity.this;
    private static final int ACTIVITY_NUM = 1;

    private RecyclerView mGarageRecycler;
    private CaptionedImagesAdapter mAdapter;


    private DatabaseReference mDatabaseRef;
    private List<Garage> mGarages;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        setupBottomNavigationView();
       // killActivity();
        mGarageRecycler = findViewById(R.id.garage_recycler);
        mGarageRecycler.setLayoutManager(new LinearLayoutManager(this));

        mGarages = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Garages");




        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Garage garage = postSnapshot.getValue(Garage.class);
                    mGarages.add(garage);
                }


                mAdapter = new CaptionedImagesAdapter(mContext, mGarages);
                mGarageRecycler.setAdapter(mAdapter);

                mAdapter.setListener(new CaptionedImagesAdapter.Listener() {
                    public void onClick(View view, int position, Garage cGarage) {



                        Intent intent = new Intent(mContext, GarageDetailActivity.class);
                        intent.putExtra("gName", cGarage.getName());
                        intent.putExtra("gLocation", cGarage.getLocation());
                        intent.putExtra("gPhone", cGarage.getPhoneNumber());
                        intent.putExtra("gId", cGarage.getGarageId());
                        intent.putExtra("gImage", cGarage.getImageResourceId());
                        intent.putExtra("gEmail", cGarage.getEmail());
                        intent.putExtra("userid", cGarage.getUserid());
                        intent.putExtra("gmoredetails", cGarage.getMoregaragedetails());
                        startActivity(intent);



                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(TestActivity.this, "Error retrieving data", Toast.LENGTH_SHORT).show();

            }
        });



    }

    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext,bottomNavigationViewEx );
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }
}

