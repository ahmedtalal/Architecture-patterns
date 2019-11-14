package ahmed.javcoder.egynews;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotpasswordActivity extends AppCompatActivity {

    private Toolbar toolbar ;
    private Button button ;
    private EditText emailsend ;
    private ProgressBar progressBar ;
    private FirebaseAuth auth ;
    private String returned  = null ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        toolbar = findViewById(R.id.passwordToolbar) ;
        button = findViewById(R.id.resetpassword) ;
        emailsend = findViewById(R.id.sendEmail_ID) ;
        progressBar = findViewById(R.id.progress) ;

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reset password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent() ;
        returned = intent.getStringExtra("passwordReturned") ;

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (returned.equals("returned")){
                    startActivity(new Intent(ForgotpasswordActivity.this , SettingsActivity.class));
                    finish();
                }else if (returned.equals("returned1")){
                    startActivity(new Intent(ForgotpasswordActivity.this , LoginActivity.class));
                    finish();
                }
            }
        });

        auth = FirebaseAuth.getInstance() ;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recieveEmail = emailsend.getText().toString().trim() ;
                if (TextUtils.isEmpty(recieveEmail)){
                    emailsend.setError("this field is required");
                    emailsend.requestFocus() ;
                    return;
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    auth.sendPasswordResetEmail(recieveEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext() , "Please check your email" , Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ForgotpasswordActivity.this , LoginActivity.class));
                            }else {
                                Toast.makeText(getApplicationContext() , task.getException().getMessage() , Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (returned.equals("returned")){
            startActivity(new Intent(ForgotpasswordActivity.this , SettingsActivity.class));
            finish();
        }else if (returned.equals("returned1")){
            startActivity(new Intent(ForgotpasswordActivity.this , LoginActivity.class));
            finish();
        }
    }
}
