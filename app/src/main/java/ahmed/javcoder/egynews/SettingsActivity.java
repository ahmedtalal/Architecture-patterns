package ahmed.javcoder.egynews;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import ahmed.javcoder.egynews.Models.Users;
import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CircleImageView circleImageView ;
    private MaterialRippleLayout  aboutripple , privacyripple , faqsripple , passwordripple ;

    private FirebaseUser user ;
    private FirebaseDatabase database ;
    private DatabaseReference reference ;
    private GoogleSignInAccount gsa ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // declare components
        toolbar = findViewById(R.id.settingToolbar) ;
        circleImageView = findViewById(R.id.profilePhotoSettingPage) ;
        aboutripple = findViewById(R.id.aboutRipple) ;
        privacyripple = findViewById(R.id.privacyRipple) ;
        faqsripple = findViewById(R.id.faqsRipple) ;
        passwordripple = findViewById(R.id.passwordRippple) ;

        // make instance
        user = FirebaseAuth.getInstance().getCurrentUser() ;
        database = FirebaseDatabase.getInstance() ;
        reference = database.getReference() ;
        gsa = GoogleSignIn.getLastSignedInAccount(this) ;

        // this methos is used to upload user'photo ;
        UploadImage(circleImageView) ;


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this , Main2Activity.class));
                finish();
            }
        });

        aboutripple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this ,  AboutusActivity.class));
                finish();
            }
        });

        privacyripple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this , PrivacyActivity.class));
                finish();
            }
        });

        faqsripple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this , FaqsActivity.class));
                finish();
            }
        });

        passwordripple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(SettingsActivity.this , ForgotpasswordActivity.class);
              intent.putExtra("passwordReturned" , "returned") ;
              startActivity(intent);
                finish();
            }
        });
    }

    private void UploadImage(final CircleImageView circleImageView) {
        if (user != null){
            reference.child("Users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Users userModel = dataSnapshot.getValue(Users.class) ;
                    Picasso.get()
                            .load(userModel.getImage())
                            .into(circleImageView);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else if (gsa != null){
            Picasso.get()
                    .load(gsa.getPhotoUrl())
                    .into(circleImageView);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SettingsActivity.this , Main2Activity.class));
        finish();
    }
}
