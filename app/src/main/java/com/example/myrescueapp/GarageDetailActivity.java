package com.example.myrescueapp;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.net.Uri;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myrescueapp.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.myrescueapp.utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

public class GarageDetailActivity extends AppCompatActivity {
    public static final String EXTRA_GARAGE_ID = "garageId";
    public static final Garage EXTRA_GARAGE = null;
    private Context mContext = GarageDetailActivity.this;
    private static final int ACTIVITY_NUM = 1;
    private String garagePhoneNumber = "";
    private Garage currentGarage;
    StringBuilder builder;


    private RecyclerView mServiceRecycler;

    DatabaseReference mCurrentGarageRef;


    private List<GarageService> garageServices;
    private ServicesAdapter mServicesAdapter;
    private Garage garageCurrent;
    private String garageEmail;

    RelativeLayout relativeLayoutqwerty;
    RelativeLayout relativeLayoutMain;
    Button buttonYes;
    Button buttonNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage_detail);
        relativeLayoutMain = findViewById(R.id.relLayoutMain);
        relativeLayoutqwerty = findViewById(R.id.relLayoutqwerty);
        relativeLayoutqwerty.setVisibility(View.INVISIBLE);

        buttonYes = findViewById(R.id.btn_yes);
        buttonNo = findViewById(R.id.btn_no);

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayoutqwerty.setVisibility(View.INVISIBLE);
                Toast.makeText(mContext, "Service Booked Successfully", Toast.LENGTH_LONG).show();

               String msg = builder.toString();

                Intent i = new Intent(Intent.ACTION_SENDTO);

                i.setType("message/rfc822");
                i.setData(Uri.parse("mailto:"+garageEmail));
//                i.putExtra(Intent.EXTRA_EMAIL, new String[] {garageEmail} );
                i.putExtra(Intent.EXTRA_SUBJECT, "Booking service request");
                i.putExtra(Intent.EXTRA_TEXT, msg);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);


                Toast.makeText(mContext, "email sent ", Toast.LENGTH_SHORT).show();

            }
        });

        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayoutqwerty.setVisibility(View.INVISIBLE);

            }
        });


        //setUpCurrentGarage();
        setupBackArrow();

        setupViews();
        setUpServices2();

        setupCall();
        setupchat();
        //  killActivity()
    }


    private void setUpCurrentGarage() {
        mCurrentGarageRef = FirebaseDatabase.getInstance().getReference("Garages");

        mCurrentGarageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Garage garage = postSnapshot.getValue(Garage.class);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void setupBackArrow() {
        RelativeLayout backNav = (RelativeLayout) findViewById(R.id.relLayout_back_arrow);
        backNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void setupViews() {
        String garageId = getIntent().getStringExtra("gId");
//        String garageName = currentGarage.getName();
        String garageName = getIntent().getStringExtra("gName");
        String garageLocation = getIntent().getStringExtra("gLocation");
        String garageNumber = getIntent().getStringExtra("gPhone");
        String garageImage = getIntent().getStringExtra("gImage");
        garageEmail = getIntent().getStringExtra("gEmail");
        String garagemoredetails = getIntent().getStringExtra("gmoredetails");

        garagePhoneNumber = garageNumber;

        TextView tvName = (TextView) findViewById(R.id.garage_name);
        tvName.setText(garageName);

        TextView tvLocation = (TextView) findViewById(R.id.garage_location);
        tvLocation.setText(garageLocation);

        TextView phoneNumber = (TextView) findViewById(R.id.tvphonenum);
        phoneNumber.setText(garageNumber);


        TextView tvmoregaragedetails = (TextView) findViewById(R.id.et_garage_more_details);
        tvmoregaragedetails.setText(garagemoredetails);

        ImageView imageView = (ImageView) findViewById(R.id.garage_image);



        Glide.with(mContext)
                .load(garageImage)
                .centerCrop()
                .into(imageView);

        imageView.setContentDescription(garageName);

    }

    private void setupCall() {

        final StringBuilder strBuilder = new StringBuilder("tel:");
        strBuilder.append(garagePhoneNumber);

        final String phoneNumber = strBuilder.toString();

        final RelativeLayout btnCall = (RelativeLayout) findViewById(R.id.relLayoutCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse(phoneNumber));

                startActivity(callIntent);

            }
        });
    }

    private void setUpServices() {
        //List<GarageService> garageServices;
        //GarageService []garageServices2=new GarageService[20];

        mServiceRecycler = findViewById(R.id.services_recycler);
        mServiceRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        garageServices = new ArrayList<>();
        mCurrentGarageRef = FirebaseDatabase.getInstance().getReference("Garages").child("garageServices");

        mCurrentGarageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    GarageService garagesrvs = postSnapshot.getValue(GarageService.class);
                    garageServices.add(garagesrvs);
                }

                mServicesAdapter = new ServicesAdapter(mContext, garageServices);
                mServiceRecycler.setAdapter(mServicesAdapter);
                mServicesAdapter.setListener(new ServicesAdapter.Listener() {
                    @Override
                    public void onClick(View view, int position, GarageService garageService) {
                        Intent intent = new Intent(mContext, GarageDetailActivity.class);
                        intent.putExtra("sName", garageService.getName());
                        intent.putExtra("sRate", garageService.getChargeRate());
                        intent.putExtra("sImage", garageService.getServiceImage());
                        intent.putExtra("sRteString", garageService.getChargeRateString());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        ServicesAdapter adapter = new ServicesAdapter(garageServices);
//        servicesRecycler.setAdapter(adapter);
//        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(GarageDetailActivity.this,LinearLayoutManager.HORIZONTAL,false);
//        servicesRecycler.setLayoutManager(linearLayoutManager);

//        adapter.setListener(new ServicesAdapter.Listener() {
//            @Override
//            public void onClick(int position) {
//                Intent intent = new Intent(GarageDetailActivity.this,ServiceDetailActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    private void setUpServices2() {
        final List<GarageService>  garageServices = new ArrayList<>();
        //GarageService []garageServices2=new GarageService[20];

        mServiceRecycler = findViewById(R.id.services_recycler);
        mServiceRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        for (GarageService garageService:GarageService.garageServices){
            garageServices.add(garageService);
        }


        mServicesAdapter = new ServicesAdapter(mContext, garageServices);
        mServiceRecycler.setAdapter(mServicesAdapter);
        mServicesAdapter.setListener(new ServicesAdapter.Listener() {
            @Override
            public void onClick(View view, int position, GarageService garageService) {
                setUpDialog();
                builder=new StringBuilder("");
                builder.append(garageService.name).append(" @ Ksh ");
                builder.append(garageService.chargeRate).append(garageService.chargeRateString);

            }
        });


    }

    private void setUpDialog() {
        //relativeLayoutMain.setBackgroundColor(getColor(R.color.blacktrans));
        relativeLayoutqwerty.setVisibility(View.VISIBLE);

    }


    private void setupchat() {

        final RelativeLayout btnChat = (RelativeLayout) findViewById(R.id.relchat);
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String garageName = getIntent().getStringExtra("gName");
                String garageNumber = getIntent().getStringExtra("gPhone");
                String garageImage = getIntent().getStringExtra("gImage");
                String garageUserid = getIntent().getStringExtra("userid");
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("gName", garageName);
                intent.putExtra("gPhone", garageNumber);
                intent.putExtra("gImage", garageImage);
                intent.putExtra("userid", garageUserid);
//                Toast.makeText(mContext, "user id: "+ garageUserid, Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
    }

}


