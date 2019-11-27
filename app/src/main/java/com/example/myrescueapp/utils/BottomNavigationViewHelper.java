package com.example.myrescueapp.utils;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;

import com.example.myrescueapp.MotoristMapActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;

import com.example.myrescueapp.LikesActivity;
import com.example.myrescueapp.ProfileActivity;
import com.example.myrescueapp.R;
import com.example.myrescueapp.TestActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BottomNavigationViewHelper {

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
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

                    case R.id.ic_map:
                        Intent intent1 = new Intent(context, MotoristMapActivity.class); // ACTIVITY_NUM = 0
                        context.startActivity(intent1);
                        break;

                    case R.id.ic_view_list:
                        Intent intent2 = new Intent(context, TestActivity.class); // ACTIVITY_NUM = 1
                        context.startActivity(intent2);
                        break;

//                    case R.id.:
//                        Intent intent3 = new Intent(context, AddGarageActivity.class); // ACTIVITY_NUM = 2
//                        context.startActivity(intent3);
//                        break;

//                    case R.id.ic_alert:
//                        Intent intent4 = new Intent(context, LikesActivity.class); // ACTIVITY_NUM = 3
//                        context.startActivity(intent4);
//                        break;

//                    case R.id.ic_account:
//                        Intent intent5 = new Intent(context, ProfileActivity.class); // ACTIVITY_NUM = 4
//                        context.startActivity(intent5);
//                        break;
                }
                return false;
            }
        });
    }
}
