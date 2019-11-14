package ahmed.javcoder.egynews;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import ahmed.javcoder.egynews.Adapters.ConnectionsAdapter;
import ahmed.javcoder.egynews.Adapters.SportsAdapter;
import ahmed.javcoder.egynews.Models.ItemModel;

public class SportsActivity extends AppCompatActivity {

    Toolbar toolbar ;
    RecyclerView sportsRecyclerview ;
    LinearLayoutManager sportsLinearLayoutManager ;
    SportsAdapter sportsAdapter ;
    ProgressBar sportsprogressBar ;
    List<ItemModel> sportsLList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);

        toolbar =  findViewById(R.id.toolobaro_ID) ;
        sportsRecyclerview = findViewById(R.id.sportsRecyclerview_ID) ;
        sportsprogressBar = findViewById(R.id.progressbar_ID) ;

        sportsLinearLayoutManager = new LinearLayoutManager(getApplicationContext() , LinearLayoutManager.VERTICAL , false) ;
        sportsRecyclerview.setLayoutManager(sportsLinearLayoutManager);

        sportsLList =  new ArrayList<>() ;

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SportsActivity.this , Main2Activity.class));
                finish();
            }
        });

        asynkTask task =  new asynkTask() ;
        task.execute("https://newsapi.org/v2/top-headlines?country=eg&category=sports&apiKey=bfd24b9b4887496bac984611f22b7ba7\n");
    }


//    public void Urlresourco(View view)
//    {
//        SportsAdapter.MyViewHolder myViewHolder =  new SportsAdapter().MyViewHolder() ;
//        goToUrl (myViewHolder.);
//    }


    public class asynkTask  extends android.os.AsyncTask<String , Void , List<ItemModel>>
    {
        @Override
        protected void onPreExecute()
        {
            sportsprogressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }


        @Override
        protected List<ItemModel> doInBackground(String... strings)
        {
            if(strings[0] == null)
            {
                return null ;
            }

            List<ItemModel> list = ConnectionsAdapter.FetchDataFromUrl(strings[0]) ;

            return list;
        }

        @Override
        protected void onPostExecute(List<ItemModel> itemModels)
        {
            sportsLList.clear();

            if(itemModels != null && !itemModels.isEmpty())
            {
                sportsprogressBar.setVisibility(View.GONE);
                sportsLList.addAll(itemModels) ;
                sportsAdapter =  new SportsAdapter(getApplicationContext() , sportsLList) ;
                sportsRecyclerview.setAdapter(sportsAdapter);
                final LayoutAnimationController controller =
                        AnimationUtils.loadLayoutAnimation(SportsActivity.this, R.anim.layout_animation_left);

                sportsRecyclerview.setLayoutAnimation(controller);
                sportsAdapter.notifyDataSetChanged();
                sportsRecyclerview.scheduleLayoutAnimation() ;
            }


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SportsActivity.this , Main2Activity.class));
        finish();
    }

    //    private void goToUrl (String url) {
//        Uri uriUrl = Uri.parse(url);
//        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
//        //launchBrowser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(launchBrowser);
//    }
}
