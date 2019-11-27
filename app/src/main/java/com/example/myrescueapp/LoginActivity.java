package com.example.myrescueapp;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myrescueapp.Model.User;
import com.example.myrescueapp.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginActivity extends AppCompatActivity {

    MaterialEditText email2, password1;
    Button btn_login;
    private ProgressDialog loadingBar;
    private TextView AdminLink, NotAdminLink;

    private CheckBox chkBoxRememberMe;
    private String parentDbName = "Users";

    FirebaseAuth auth;
    TextView forgot_password;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();

        email2 = findViewById(R.id.email);
        password1 = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        forgot_password = findViewById(R.id.forgot_password);
        loadingBar = new ProgressDialog(this);
        //AdminLink = (TextView) findViewById(R.id.admin_panel_link);
        //otAdminLink = (TextView) findViewById(R.id.not_admin_panel_link);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AllowAccessToAccount();
            }
        });



        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });


    }
        private void LoginUser()
        {
            String email1 = email2.getText().toString();
            String password2= password1.getText().toString();

            if (TextUtils.isEmpty(email1))
            {
                Toast.makeText(this, "Please write your email number...", Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(password2))
            {
                Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
            }
            else
            {
                loadingBar.setTitle("Login Account");
                loadingBar.setMessage("Please wait, while we are checking the credentials.");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();


               AllowAccessToAccount();
            }
        }



        private void AllowAccessToAccount()
        {

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = email2.getText().toString();
                String txt_password = password1.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(LoginActivity.this, "All fileds are required", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.setTitle("Login Account");
                    loadingBar.setMessage("Please wait, while we are checking the credentials.");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    auth.signInWithEmailAndPassword(txt_email, txt_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(LoginActivity.this, "logged in Successfully...", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                    }    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(LoginActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                }
            }

        });

    }

}
