package ahmed.javcoder.egynews;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import java.util.HashMap;

import ahmed.javcoder.egynews.Models.Users;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyprofileActivity extends AppCompatActivity {

    private Toolbar toolbar ;
    private CircleImageView photo ;
    private EditText name , firstName , lastName , email , address , phone ;
    private ImageButton camera  , actionBtn ;
    private MaterialRippleLayout materialRippleLayout ;
    private ProgressDialog progressDialog ;
    private static final int RN_PHOTO = 2 ;
    private Uri imageUri ;

    private FirebaseUser user;
    private FirebaseDatabase database ;
    private  DatabaseReference reference ;
    private FirebaseStorage fbs ;
    private StorageReference storageReference ;
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
        actionBtn = findViewById(R.id.editBtn) ;


        user = FirebaseAuth.getInstance().getCurrentUser() ;
        database = FirebaseDatabase.getInstance();
        reference = database.getReference() ;
        fbs = FirebaseStorage.getInstance() ;
        storageReference = fbs.getReference().child("UriImages") ;
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

        actionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            phone.setEnabled(true);
            email.setEnabled(false);
            String id = FirebaseAuth.getInstance().getUid() ;
            reference.child("Users").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Users userModel = dataSnapshot.getValue(Users.class) ;
                    if (!userModel.getUsername().equals(" ")){
                        name.setText(userModel.getUsername());
                    }else {
                        name.setText(" ");
                    }
                    if (!userModel.getPhone().equals(" ")){
                       phone.setText(userModel.getPhone());
                    }else {
                        phone.setText(" ");
                    }
                    if (!userModel.getAddress().equals(" ")){
                        address.setText(userModel.getAddress());
                    }else {
                        address.setText(" ");
                    }

                    if (!userModel.getEmail().equals("")){
                        email.setText(userModel.getEmail());
                    }else {
                        email.setText(" ");
                    }

                    Picasso.get()
                            .load(userModel.getImage())
                            .placeholder(R.drawable.ic_user)
                            .error(R.drawable.ic_user)
                            .into(photo);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

            firstName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MyprofileActivity.this , "this field not available now " , Toast.LENGTH_LONG).show();
                }
            });

            lastName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MyprofileActivity.this , "this field not available now " , Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void getInfoFromGoogleAccount() {
        if (account != null){
            firstName.setEnabled(true);
            lastName.setEnabled(true);
            camera.setVisibility(View.INVISIBLE);
            address.setEnabled(false);
            phone.setEnabled(false);
            materialRippleLayout.setVisibility(View.INVISIBLE);
            email.setEnabled(false);
            //get information from acount now
            name.setText(account.getDisplayName());
            firstName.setText(account.getGivenName());
            lastName.setText(account.getFamilyName());
            email.setText(account.getEmail());
            Picasso.get()
                    .load(account.getPhotoUrl())
                    .placeholder(R.drawable.ic_user)
                    .error(R.drawable.ic_user)
                    .into(photo);

            address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MyprofileActivity.this , "this field not available now " , Toast.LENGTH_LONG).show();
                }
            });

            phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MyprofileActivity.this , "this field not available now " , Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    private void editProfileInfo(EditText name, final EditText email, EditText firstName, EditText lastName, EditText phone, EditText address , CircleImageView photo) {
        final String username = name.getText().toString().trim() ;
        final String gmail = email.getText().toString().trim() ;
        final String location = address.getText().toString().trim() ;
        final String phoneNum = phone.getText().toString().trim() ;


        if (user != null){

            if (TextUtils.isEmpty(username)){
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

            // here we will write code
            progressDialog =  new ProgressDialog(this) ;
            progressDialog.setMessage("Please wait....");
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            uploadPhoto(username , gmail , location , phoneNum , imageUri) ;

        }else if (account != null){
            //here we will write code
        }
    }

    private void uploadPhoto(final String username, final String gmail, final String location, final String phoneNum, final Uri imageUri) {
        UploadTask uploadTask ;
        final StorageReference rfs = storageReference.child(imageUri.getLastPathSegment()) ;
        uploadTask = rfs.putFile(imageUri) ;
        Task<Uri> task = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()){
                    Toast.makeText(getApplicationContext() , task.getException().getMessage() , Toast.LENGTH_LONG).show();
                }
                return rfs.getDownloadUrl() ;
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
               Uri retrieveUriImage = task.getResult() ;
                String converImage = retrieveUriImage.toString() ;
                saveUser(username , gmail , location , phoneNum , converImage) ;
            }
        }) ;


    }

    private void saveUser(final String username, final String gmail, final String location, final String phoneNum, final String converImage) {
        reference.child("Users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String , Object> hashMap = new HashMap<>() ;
                hashMap.put("username" , username);
                hashMap.put("email" , gmail);
                hashMap.put("address" , location);
                hashMap.put("phone" ,phoneNum);
                hashMap.put("image" , converImage) ;
                reference.child("Users").child(user.getUid()).updateChildren(hashMap) ;
                progressDialog.dismiss();
                startActivity(new Intent(MyprofileActivity.this , Main2Activity.class));
                finish();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
            imageUri = data.getData() ;
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
