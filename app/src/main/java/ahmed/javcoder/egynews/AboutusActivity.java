package ahmed.javcoder.egynews;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class AboutusActivity extends AppCompatActivity {

    Toolbar toolbar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        toolbar=  findViewById(R.id.toolobaron_ID) ;

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutusActivity.this , SettingsActivity.class));
                finish();
            }
        });

    }


    public void facebooklink(View view)
    {
        String link = "https://www.facebook.com/ahmd.talal.5?ref=bookmarks";
        goToUrl(link);
    }

    public void twitterlink(View view)
    {

        String link = "https://www.youtube.com/channel/UCxjsmxYugcfGrjRaP2gteiQ?view_as=subscriber";
        goToUrl(link);
    }

    public void Googlelink(View view)
    {
        String link = "https://mail.google.com/mail/u/0/#inbox";
        goToUrl(link);
    }

    public void youtubeLink(View view) {
        String link = "https://mail.google.com/mail/u/0/#inbox";
        goToUrl(link);
    }

    public void gethihLink(View view) {
        String link = "https://github.com/EngAhmedTalal?tab=repositories";
        goToUrl(link);
    }





    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        //launchBrowser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(launchBrowser);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AboutusActivity.this , SettingsActivity.class));
        finish();
    }



}
