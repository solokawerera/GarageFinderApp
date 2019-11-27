package com.example.myrescueapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myrescueapp.utils.BottomNavigationViewHelper2;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.example.myrescueapp.utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

public class AddGarageActivity extends AppCompatActivity {

    String fuser;
    private static final int PICK_IMAGE_REQUEST=1;
    private Button mButtonChooseImage;
    private Button mButtonUpload;


    private EditText mEditTextGarageName;
    private EditText mEditTextGarageLocation;
    private EditText mEditTextPhonenumber;
    private EditText mEditTextEmail;
    private TextView moredetailsgarage;
    private ImageView mImageView;
    private EditText mEditTextLatitude;
    private EditText mEditTextLongitude;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;

    private Context mContext = AddGarageActivity.this;
    private static final int ACTIVITY_NUM = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_garage);
        setupBottomNavigationView();

        mButtonChooseImage = (Button) findViewById(R.id.btn_choose_image);
        mButtonUpload = (Button) findViewById(R.id.btn_upload);

        mImageView = (ImageView) findViewById(R.id.iv_garage_image);
        fuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mEditTextGarageName = (EditText) findViewById(R.id.et_garage_name);
        mEditTextGarageLocation = (EditText) findViewById(R.id.et_garage_location);
        mEditTextPhonenumber = (EditText) findViewById(R.id.et_garage_phone_number);
        mEditTextEmail = (EditText) findViewById(R.id.et_garage_email_address);
        moredetailsgarage=(TextView) findViewById(R.id.et_garage_more_details);

//        mEditTextLatitude = (EditText) findViewById(R.id.et_garage_latitude);
//        mEditTextLongitude = (EditText) findViewById(R.id.et_garage_longitute);


        mStorageRef = FirebaseStorage.getInstance().getReference("Garages");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Garages");
        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFileChooser();

            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()){
                    Toast.makeText(AddGarageActivity.this, "Upload in Progress", Toast.LENGTH_SHORT).show();
                }else {
                    uploadFile();
                }
            }
        });


    }

    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar2);
        BottomNavigationViewHelper2.setupBottomNavigationView2(bottomNavigationViewEx);
        BottomNavigationViewHelper2.enableNavigation(mContext,bottomNavigationViewEx );
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null){
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    String userid = fuser;
                                    String name = mEditTextGarageName.getText().toString().trim();
                                    String location = mEditTextGarageLocation.getText().toString().trim();
                                    String phoneNumber= mEditTextPhonenumber.getText().toString().trim();
                                    String email= mEditTextEmail.getText().toString().trim();
                                    String moredetails= moredetailsgarage.getText().toString().trim();


                                    List<GarageService> garageServices=new ArrayList<>();
                                    for (GarageService garageService:GarageService.garageServices){
                                        garageServices.add(garageService);
                                    }

                                   String uploadId = mDatabaseRef.push().getKey();
                                    Garage garage = new Garage(uploadId,userid,name,email,location,phoneNumber,moredetails,uri.toString(),garageServices);
                                    //Garage garage = new Garage(uploadId,userid,name,email,location,phoneNumber,uri.toString(),garageServices);
//                                    Garage garage = new Garage(uploadId,userid,name,location,phoneNumber,email,uri.toString(),garageServices);
                                    mDatabaseRef.child(uploadId).setValue(garage);
                                    Intent intent = new Intent(AddGarageActivity.this, GarageOwnerMapActivity.class);
                                    startActivity(intent);

                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });
        } else{
            Toast.makeText(this, "No Image Choosen", Toast.LENGTH_SHORT).show();
        }


    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            mImageUri = data.getData();

            Glide.with(mContext)
                    .load(mImageUri)
                    .centerCrop()
                    .into(mImageView);
            //mImageView.setImageURI(mImageUri);
        }
    }
}
