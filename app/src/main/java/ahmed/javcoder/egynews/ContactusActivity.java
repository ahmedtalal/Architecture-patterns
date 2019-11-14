package ahmed.javcoder.egynews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ahmed.javcoder.egynews.Models.JavaMailApi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactusActivity extends AppCompatActivity {
    private Toolbar toolbar ;
    private EditText email , message ;
    private Button sendMessage ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);

        toolbar = findViewById(R.id.contactusToolbar) ;
        email = findViewById(R.id.emailField_ID) ;
        message = findViewById(R.id.messageField) ;
        sendMessage = findViewById(R.id.sendBtn_ID) ;

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContactusActivity.this , Main2Activity.class));
                finish();
            }
        });

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMail() ;
            }
        });


    }

    private void SendMail() {
        String emailfield = email.getText().toString().trim();
        String messagefield = message.getText().toString().trim() ;
        if (TextUtils.isEmpty(emailfield)){
            email.setError("the email field is required");
            email.requestFocus() ;
            return;
        }

        if (TextUtils.isEmpty(messagefield)){
            message.setError("the message field is required");
            message.requestFocus() ;
            return;
        }

        JavaMailApi javaMailApi = new JavaMailApi(this , emailfield , messagefield) ;
        javaMailApi.execute() ;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ContactusActivity.this , Main2Activity.class));
    }
}