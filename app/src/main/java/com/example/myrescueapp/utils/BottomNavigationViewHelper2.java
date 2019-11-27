package com.example.myrescueapp.utils;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.myrescueapp.AddGarageActivity;
import com.example.myrescueapp.GarageOwnerMapActivity;
import com.example.myrescueapp.LikesActivity;
import com.example.myrescueapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BottomNavigationViewHelper2{

    public static void setupBottomNavigationView2(BottomNavigationViewEx bottomNavigationViewEx){
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }
    public static void enableNavigation(final Context context, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){


                    case R.id.ic_add:
                        Intent intent0 = new Intent(context, AddGarageActivity.class); // ACTIVITY_NUM = 2
                        context.startActivity(intent0);
                        break;

                    case R.id.ic_map:
                        Intent intent1 = new Intent(context, GarageOwnerMapActivity.class); // ACTIVITY_NUM = 3
                        context.startActivity(intent1);
                        break;

                    case R.id.ic_alert:
                        Intent intent2 = new Intent(context, LikesActivity.class); // ACTIVITY_NUM = 3
                        context.startActivity(intent2);
                        break;

                }
                return false;
            }
        });
    }
}
