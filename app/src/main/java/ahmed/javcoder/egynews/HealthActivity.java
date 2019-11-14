package ahmed.javcoder.egynews;

import android.content.Intent;
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
import ahmed.javcoder.egynews.Adapters.HealthAdapter;
import ahmed.javcoder.egynews.Adapters.ScienceAdapter;
import ahmed.javcoder.egynews.Models.ItemModel;

public class HealthActivity extends AppCompatActivity {
    Toolbar toolbar ;
    RecyclerView healthRecyclerview ;
    LinearLayoutManager healthLinearLayoutManager ;
    HealthAdapter healthAdapter ;
    ProgressBar healthprogressBar ;
    List<ItemModel> healthLList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        toolbar =  findViewById(R.id.Healthtoolobaro_ID) ;
        healthRecyclerview = findViewById(R.id.HealthRecyclerview_ID) ;
        healthprogressBar = findViewById(R.id.Healthprogressbar_ID) ;

        healthLinearLayoutManager = new LinearLayoutManager(getApplicationContext() , LinearLayoutManager.VERTICAL , false) ;
        healthRecyclerview.setLayoutManager(healthLinearLayoutManager);

        healthLList =  new ArrayList<>() ;

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HealthActivity.this , Main2Activity.class));
                finish();
            }
        });

        asynkTask task =  new asynkTask() ;
        task.execute("https://newsapi.org/v2/top-headlines?country=eg&category=health&apiKey=bfd24b9b4887496bac984611f22b7ba7\n");
    }

    public class asynkTask  extends android.os.AsyncTask<String , Void , List<ItemModel>>
    {
        @Override
        protected void onPreExecute()
        {
            healthprogressBar.setVisibility(View.VISIBLE);
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
            healthLList.clear();

            if(itemModels != null && !itemModels.isEmpty())
            {
                healthprogressBar.setVisibility(View.GONE);
                healthLList.addAll(itemModels) ;
                healthAdapter =  new HealthAdapter(getApplicationContext() , healthLList) ;
                healthRecyclerview.setAdapter(healthAdapter);
                final LayoutAnimationController controller =
                        AnimationUtils.loadLayoutAnimation(HealthActivity.this, R.anim.layout_animation_left);

                healthRecyclerview.setLayoutAnimation(controller);
                healthAdapter.notifyDataSetChanged();
                healthRecyclerview.scheduleLayoutAnimation() ;
            }


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(HealthActivity.this , Main2Activity.class));
        finish();
    }
}
