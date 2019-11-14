package ahmed.javcoder.egynews;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ahmed.javcoder.egynews.Models.Users;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    EditText usernameField , passwordfield , confirmpasswordfield , emailfield ;

    String euse , pass , confirmpass , egmail ;

    FirebaseAuth firebaseAuth ;
    FirebaseDatabase firebaseDatabase ;
    DatabaseReference databaseReference ;
    ProgressDialog progressDialog ;

    @Override
    protected void onStart() {
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this) ;
        FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser() ;
        if(user != null)
        {
            startActivity(new Intent(getApplicationContext() , Main2Activity.class));

        }else if (googleSignInAccount != null)
        {
            startActivity(new Intent(getApplicationContext() , Main2Activity.class));
        }
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameField = findViewById(R.id.user_ID) ;
        passwordfield =  findViewById(R.id.pass_ID) ;
        confirmpasswordfield =  findViewById(R.id.confirmpass_ID) ;
        emailfield = findViewById(R.id.gmail_ID) ;

        firebaseAuth = FirebaseAuth.getInstance() ;
        firebaseDatabase = FirebaseDatabase.getInstance() ;
        databaseReference = firebaseDatabase.getReference() ;


    }

    public void SignIn(View view) {
        startActivity(new Intent(getApplicationContext() , LoginActivity.class));
        finish();
    }

    public void registero(View view) {

        euse = usernameField.getText().toString() ;
        pass = passwordfield.getText().toString() ;
        confirmpass = confirmpasswordfield.getText().toString() ;
        egmail = emailfield.getText().toString();

        if(TextUtils.isEmpty(euse))
        {
            usernameField.setError("this field is required");
            usernameField.requestFocus() ;
            return ;
        }

        if (TextUtils.isEmpty(pass)){
            passwordfield.setError("this field is required");
            passwordfield.requestFocus() ;
            return;
        }

        if(pass.length() < 6)
        {
            passwordfield.setError("this field should be greater than 6 ");
            passwordfield.requestFocus() ;
            return;
        }

        if(!confirmpass.equals(pass))
        {
            confirmpasswordfield.setError("this field is required");
            confirmpasswordfield.requestFocus() ;
            return;
        }

        if(TextUtils.isEmpty(egmail))
        {
            emailfield.setError("this field is required");
            emailfield.requestFocus() ;
            return;
        }


        progressDialog =  new ProgressDialog(RegisterActivity.this) ;
        progressDialog.setMessage("Please wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        CreateAuthUser(euse , egmail ,  pass) ;

    }

    private void CreateAuthUser(final String euse, final String egmail,  String pass) {
        firebaseAuth.createUserWithEmailAndPassword(egmail , pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            String id  = task.getResult().getUser().getUid() ;
                            SaveUser(egmail , euse  , id); ;
                        }else
                        {
                            Toast.makeText(getApplicationContext(),task.getException().getMessage() , Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    private void SaveUser(String egmail, String euse, String id) {
        Users usermodel = new Users(egmail , euse) ;
        databaseReference .child("Users").child(id).setValue(usermodel) ;
        startActivity(new Intent(getApplicationContext() , Main2Activity.class));
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(), "Successfully processed" , Toast.LENGTH_LONG).show();
    }


    @Override
    public void onBackPressed() {
        finishAffinity();
    }



}
