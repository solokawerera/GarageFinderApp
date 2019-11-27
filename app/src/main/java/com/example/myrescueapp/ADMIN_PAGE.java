package com.example.myrescueapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.myrescueapp.utils.BottomNavigationViewHelper;
import com.example.myrescueapp.utils.BottomNavigationViewHelper2;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class ADMIN_PAGE extends AppCompatActivity {


    private Context mContext = ADMIN_PAGE.this;
    private static final int ACTIVITY_NUM = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__page);

        setupBottomNavigationView();
    }

    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar2);
        BottomNavigationViewHelper2.setupBottomNavigationView2(bottomNavigationViewEx);
        BottomNavigationViewHelper2.enableNavigation(mContext,bottomNavigationViewEx );
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }
}
