package ahmed.javcoder.egynews;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class ShowinfoActivity extends AppCompatActivity {

    private TextView title , description , publishedAt , urlSource ;
    private ImageView photo ;
    private Toolbar toolbar ;
    private ProgressBar progressBar ;
    private CollapsingToolbarLayout collapsingToolbarLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showinfo);

        title = findViewById(R.id.titleID) ;
        description = findViewById(R.id.descriptionID) ;
        publishedAt = findViewById(R.id.timeID) ;
        urlSource = findViewById(R.id.sourceID) ;
        photo = findViewById(R.id.imageUri_id) ;
        toolbar = findViewById(R.id.toolbar) ;
        progressBar = findViewById(R.id.progoBar) ;
        collapsingToolbarLayout = findViewById(R.id.collapsing_ID);

        Intent intent = getIntent() ;
        String name_returned = intent.getStringExtra("name") ;
        String title_returned = intent.getStringExtra("title") ;
        String description_returned = intent.getStringExtra("description") ;
        final String source_retirned = intent.getStringExtra("source") ;
        String published_returned = intent.getStringExtra("publishedAt") ;
        String image_returned = intent.getStringExtra("image") ;

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        title.setText(title_returned);
        description.setText(description_returned);
        publishedAt.setText(published_returned);
        collapsingToolbarLayout.setTitle(name_returned);

        Glide.with(ShowinfoActivity.this)
                .load(image_returned)
                .placeholder(R.drawable.newsletter)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource)
                    {
                        progressBar.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        photo.setAlpha(Float.parseFloat("1"));
                        return false;
                    }
                })
                .into(photo) ;

        urlSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(source_retirned) ;
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW , uri) ;
                launchBrowser.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) ;
                startActivity(launchBrowser);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
