package ahmed.javcoder.egynews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText loginEmailField , loginPasswordField ;
    Toolbar toolbar ;
    TextView forgotPassword ;
    Button  googleBtn ;
    GoogleSignInClient googleSignInclient;
    CallbackManager callbackManager ;

    private FirebaseAuth auth ;
    private FirebaseUser user ;
    private static final int DEFAULT_SIGN_IN = 0 ;
    private static final String TAG = "FACEBOOK_LOG" ;

    private String resultEmail , resutlPassword ;

    private ProgressDialog progressDialog ;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmailField =  findViewById(R.id.emailno_ID) ;
        loginPasswordField = findViewById(R.id.pass_ID) ;
        toolbar = findViewById(R.id.toolbaro) ;
        forgotPassword = findViewById(R.id.forgetpassword) ;
        googleBtn = findViewById(R.id.google) ;


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(" ");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this , RegisterActivity.class));
                finish();
            }
        });

        auth =  FirebaseAuth.getInstance() ;
        user = auth.getCurrentUser() ;

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this , ForgotpasswordActivity.class);
                intent.putExtra("passwordReturned" , "returned1") ;
                startActivity(intent);
                finish();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInclient = GoogleSignIn.getClient(this , gso) ;

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });
    }

    private void SignIn() {
        Intent intent = googleSignInclient.getSignInIntent() ;
        startActivityForResult(intent , DEFAULT_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == DEFAULT_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data) ;
            handleSignInResult(task) ;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }



    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class) ;
            startActivity(new Intent(LoginActivity.this , Main2Activity.class));
        } catch (ApiException e) {
            Log.w("Google Sign In Error", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_LONG).show();
        }
    }

    public void LogIn(View view) {
        resultEmail =  loginEmailField.getText().toString() ;
        resutlPassword = loginPasswordField.getText().toString() ;

        if(TextUtils.isEmpty(resultEmail))
        {
            loginEmailField.setError("Please enter your email");
            loginEmailField.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(resutlPassword))
        {
            loginPasswordField.setError("Please enter your password");
            loginPasswordField.requestFocus();
            return;
        }

        if(resutlPassword.length() < 6)
        {
            loginPasswordField.setError("password less than 6");
            loginPasswordField.requestFocus();
            return;
        }

        progressDialog =  new ProgressDialog(LoginActivity.this) ;
        progressDialog.setMessage("Please wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        logoIn(resultEmail , resutlPassword) ;
    }

    public void SignUn(View view)
    {
        startActivity(new Intent(getApplicationContext() , RegisterActivity.class));
        finish();
    }


    private void logoIn(String resultEmail, String resutlPassword)
    {
        auth.signInWithEmailAndPassword(resultEmail , resutlPassword).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            startActivity(new Intent(getApplicationContext() , Main2Activity.class));
                            progressDialog.dismiss();
                            finish();
                        }else
                        {
                            Toast.makeText(getApplicationContext() , task.getException().getMessage() , Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LoginActivity.this , RegisterActivity.class));
    }


}
