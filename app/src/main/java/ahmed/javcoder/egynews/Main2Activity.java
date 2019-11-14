package ahmed.javcoder.egynews;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.antonyt.infiniteviewpager.InfinitePagerAdapter;
import com.antonyt.infiniteviewpager.InfiniteViewPager;
import com.balysv.materialripple.MaterialRippleLayout;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

import ahmed.javcoder.egynews.Adapters.RecycloAdapter;
import ahmed.javcoder.egynews.Adapters.RecycloAdapter2;
import ahmed.javcoder.egynews.Models.Users;
import de.hdodenhof.circleimageview.CircleImageView;
import me.didik.component.StickyNestedScrollView;
import me.relex.circleindicator.CircleIndicator;

public class Main2Activity extends AppCompatActivity {

    ActionBarDrawerToggle mtoggle ;
    NavigationView navigationView ;
    Toolbar toolbar ;
    DrawerLayout drawerLayout ;
    RecycloAdapter recycloAdapter1 ;
    RecycloAdapter2 recycloAdapter2 ;
    RecyclerView recyclerView1 , recyclerView2 ;


    String[] textlist =
            {
                    "Sports" ,
                    "Science" ,
                    "Health"
            };
    String[] textlist2 =
            {
                    "Technology",
                    "Business" ,
                    "Entertainment"
            };

    int[] imagelist =
            {
                    R.drawable.football ,
                    R.drawable.chemistry ,
                    R.drawable.doctor
            };
    int[] imagelist2 =
            {
                    R.drawable.domain ,
                    R.drawable.creditcard ,
                    R.drawable.movie
            };


    FirebaseDatabase firebaseDatabase ;
    DatabaseReference refro ;
    FirebaseUser user;
    GoogleSignInAccount googleSignInAccount;
    GoogleSignInClient googleSignInClient ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        drawerLayout  = findViewById(R.id.drawer) ;
        navigationView =  findViewById(R.id.navigation_ID) ;
        toolbar =  findViewById(R.id.toolbarr_ID) ;
        recyclerView1 = findViewById(R.id.leftrecycler) ;
        recyclerView2 = findViewById(R.id.rightrecycler) ;

        mtoggle = new ActionBarDrawerToggle(this , drawerLayout  , toolbar,  R.string.open , R.string.close) ;
        drawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();

        recycloAdapter1 = new RecycloAdapter(this , imagelist , textlist) ;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext() , LinearLayoutManager.VERTICAL , false) ;
        recyclerView1.setLayoutManager(linearLayoutManager);
        recyclerView1.setAdapter(recycloAdapter1);

        recycloAdapter2 = new RecycloAdapter2(this , imagelist2 , textlist2) ;
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getApplicationContext() , LinearLayoutManager.VERTICAL , false) ;
        recyclerView2.setLayoutManager(linearLayoutManager2);
        recyclerView2.setAdapter(recycloAdapter2);

        firebaseDatabase = FirebaseDatabase.getInstance() ;
        refro = firebaseDatabase.getReference() ;

        //to check of user make sign in with google or firebase ;
        CheckSignIn();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build() ;
        googleSignInClient = GoogleSignIn.getClient(this , gso) ;


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                int itemId = menuItem.getItemId();
                if(itemId == R.id.logout)
                {
                    user = FirebaseAuth.getInstance().getCurrentUser() ;
                    googleSignInAccount = GoogleSignIn.getLastSignedInAccount(Main2Activity.this);
                    if (user != null){
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext() , RegisterActivity.class));
                        finish();
                    }else if (googleSignInAccount != null){
                        googleSignInClient.signOut()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(Main2Activity.this,"Successfully signed out",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Main2Activity.this, RegisterActivity.class));
                                        finish();
                                    }
                                });
                    }

                }else if (itemId == R.id.Setting){
                    startActivity(new Intent(Main2Activity.this , SettingsActivity.class));
                    finish();
                }else if (itemId == R.id.myprofile1){
                    startActivity(new Intent(Main2Activity.this , MyprofileActivity.class));
                    finish();
                }else if (itemId == R.id.shareApp){
                    startActivity(new Intent(Main2Activity.this , ShareActivity.class));
                    finish();
                }else if (itemId == R.id.rateUs){
                    startActivity(new Intent(Main2Activity.this , RateUsActivity.class));
                    finish();
                }else if (itemId == R.id.contact){
                    startActivity(new Intent(Main2Activity.this , ContactusActivity.class));
                    finish();
                }
                return false ;
            }
        });


    }

    private void CheckSignIn() {
        user = FirebaseAuth.getInstance().getCurrentUser() ;
        googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (user != null){
            String id = FirebaseAuth.getInstance().getCurrentUser().getUid() ;

            refro.child("Users").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Users usermodels = dataSnapshot.getValue(Users.class) ;
                    if(usermodels != null)
                    {
                        final TextView textView = navigationView.getHeaderView(0).findViewById(R.id.textHeader) ;
                        textView.setText(usermodels.getUsername());
                        final CircleImageView imageViewHeader = navigationView.getHeaderView(0).findViewById(R.id.headerImagee_ID);
                        Picasso.get()
                                .load(usermodels.getImage())
                                .placeholder(R.drawable.ic_user)
                                .error(R.drawable.ic_user)
                                .into(imageViewHeader);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext() , databaseError.getMessage() , Toast.LENGTH_LONG).show();
                }
        });

        }else if (googleSignInAccount != null){
            TextView textView = navigationView.getHeaderView(0).findViewById(R.id.textHeader) ;
            textView.setText(googleSignInAccount.getDisplayName());
            CircleImageView imageViewHeader = navigationView.getHeaderView(0).findViewById(R.id.headerImagee_ID);
            Picasso.get()
                    .load(googleSignInAccount.getPhotoUrl())
                    .placeholder(R.drawable.ic_user)
                    .error(R.drawable.ic_user)
                    .into(imageViewHeader);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mtoggle.onOptionsItemSelected(item))
        {
            return  true ;

        }
        return super.onOptionsItemSelected(item);
    }

    private void exit(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this) ;
        builder.setIcon(R.drawable.ic_question) ;
        builder.setTitle("Confirm exit!") ;
        builder.setMessage("Are you sure , you want to exit") ;
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        }) ;
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText( getApplicationContext(),"Very Good" , Toast.LENGTH_LONG).show();
            }
        }) ;

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.setCancelable(true) ;
            }
        }) ;
        AlertDialog alertDialog = builder.create() ;
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        exit() ;
    }
}
