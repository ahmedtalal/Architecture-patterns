package ahmed.javcoder.egynews;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

public class RateUsActivity extends AppCompatActivity {

    private RatingBar ratingBar ;
    private Button btn ;
    private Toolbar toolbar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);

        ratingBar =findViewById(R.id.rate_id) ;
        btn = findViewById(R.id.rateBtn_ID) ;
        toolbar = findViewById(R.id.ratepage) ;

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RateUsActivity.this , Main2Activity.class));
                finish();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW) ;
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=ahmed.javcoder.egynews")) ;
                Log.v("Uri" , "https://play.google.com/store/apps/details?id=ahmed.javcoder.egynews") ;
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RateUsActivity.this , Main2Activity.class));
        finish();
    }
}
