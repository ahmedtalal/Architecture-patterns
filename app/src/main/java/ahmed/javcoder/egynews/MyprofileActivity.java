package ahmed.javcoder.egynews;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.HashMap;

import ahmed.javcoder.egynews.Models.Users;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyprofileActivity extends AppCompatActivity {

    private Toolbar toolbar ;
    private CircleImageView photo ;
    private EditText name , firstName , lastName , email , address , phone ;
    private ImageButton camera ;
    private MaterialRippleLayout materialRippleLayout ;
    private static final int RN_PHOTO = 2 ;

    private FirebaseUser user;
    private FirebaseDatabase database ;
    private  DatabaseReference reference ;
    private GoogleSignInAccount account ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        toolbar = findViewById(R.id.myprofileToolbar) ;
        photo = findViewById(R.id.profilePhoto) ;
        name = findViewById(R.id.profileName_ID) ;
        firstName = findViewById(R.id.firstName) ;
        lastName = findViewById(R.id.lastName) ;
        email = findViewById(R.id.profileEmail) ;
        address = findViewById(R.id.profileAAddress) ;
        phone = findViewById(R.id.profilePhone) ;
        materialRippleLayout = findViewById(R.id.profileripple) ;
        camera = findViewById(R.id.camer_ID) ;


        user = FirebaseAuth.getInstance().getCurrentUser() ;
        database = FirebaseDatabase.getInstance();
        reference = database.getReference() ;
        account = GoogleSignIn.getLastSignedInAccount(this) ;

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyprofileActivity.this , Main2Activity.class));
                finish();
            }
        });

        getInfoFromGoogleAccount() ; // this method is used to get user information from google account
        getInfoFromFirebaseAccount() ; //  this method is used to get user information from firebase account


        materialRippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // this method is used to edit profile
                editProfileInfo(name , email , firstName , lastName , phone ,address ,  photo) ;
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opencamera() ;
            }
        });
    }



    private void getInfoFromFirebaseAccount() {
        if (user != null){
            photo.setEnabled(true);
            address.setEnabled(true);
            firstName.setEnabled(false);
            lastName.setEnabled(false);
            String id = FirebaseAuth.getInstance().getUid() ;
            reference.child("Users").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Users userModel = dataSnapshot.getValue(Users.class) ;
                    name.setText(userModel.getUsername());
                    email.setText(userModel.getEmail());
                    if (!userModel.getPhone().equals("")){
                       phone.setText(userModel.getPhone());
                    }
                    if (!userModel.getAddress().equals("")){
                        address.setText(userModel.getAddress());
                    }
                    Picasso.get()
                            .load(userModel.getImage())
                            .into(photo);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void getInfoFromGoogleAccount() {
        if (account != null){
            firstName.setEnabled(true);
            lastName.setEnabled(true);
            photo.setEnabled(false);
            address.setEnabled(false);

            name.setText(account.getDisplayName());
            firstName.setText(account.getGivenName());
            lastName.setText(account.getFamilyName());
            email.setText(account.getEmail());
            Picasso.get()
                    .load(account.getPhotoUrl())
                    .into(photo);

        }
    }

    private void editProfileInfo(EditText name, final EditText email, EditText firstName, EditText lastName, EditText phone, EditText address , CircleImageView photo) {
        final String username = name.getText().toString().trim() ;
        String first = firstName.getText().toString().trim() ;
        String last = lastName.getText().toString().trim() ;
        final String gmail = email.getText().toString().trim() ;
        final String location = address.getText().toString().trim() ;
        final String phoneNum = phone.getText().toString().trim() ;
        if (TextUtils.isEmpty(username)){
            name.setError("this field is required");
            name.requestFocus() ;
            return;
        }

        if (TextUtils.isEmpty(first)){
            name.setError("this field is required");
            name.requestFocus() ;
            return;
        }

        if (TextUtils.isEmpty(last)){
            name.setError("this field is required");
            name.requestFocus() ;
            return;
        }

        if (TextUtils.isEmpty(gmail)){
            name.setError("this field is required");
            name.requestFocus() ;
            return;
        }

        if (TextUtils.isEmpty(location)){
            name.setError("this field is required");
            name.requestFocus() ;
            return;
        }

        if (TextUtils.isEmpty(phoneNum)){
            name.setError("this field is required");
            name.requestFocus() ;
            return;
        }

        if (user != null){
            // here we will write code
            reference.child("Users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    HashMap<String , Object> hashMap = new HashMap<>() ;
                    hashMap.put("username" , username);
//                    userModel.setEmail(gmail);
//                    userModel.setAddress(location);
//                    userModel.setPhone(phoneNum);
                    reference.updateChildren(hashMap) ;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else if (account != null){
            //here we will write code
        }
    }

    private void opencamera() {
        openGallary() ;
    }

    private void openGallary() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT) ;
        intent.setType("image/*") ;
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY , true) ;
        startActivityForResult(Intent.createChooser(intent , "completed action"), RN_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RN_PHOTO &&  resultCode == RESULT_OK){
            Uri imageUri = data.getData() ;
            Picasso.get()
                    .load(imageUri)
                    .into(photo);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MyprofileActivity.this , Main2Activity.class));
        finish();
    }
}
