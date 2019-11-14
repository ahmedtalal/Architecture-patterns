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
import ahmed.javcoder.egynews.Adapters.ScienceAdapter;
import ahmed.javcoder.egynews.Adapters.SportsAdapter;
import ahmed.javcoder.egynews.Models.ItemModel;

public class ScienceActivity extends AppCompatActivity {

    Toolbar toolbar ;
    RecyclerView scienceRecyclerview ;
    LinearLayoutManager scienceLinearLayoutManager ;
    ScienceAdapter scienceAdapter ;
    ProgressBar scienceprogressBar ;
    List<ItemModel> scienceLList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_science);

        toolbar =  findViewById(R.id.sciencetoolobaro_ID) ;
        scienceRecyclerview = findViewById(R.id.scienceRecyclerview_ID) ;
        scienceprogressBar = findViewById(R.id.scienceprogressbar_ID) ;

        scienceLinearLayoutManager = new LinearLayoutManager(getApplicationContext() , LinearLayoutManager.VERTICAL , false) ;
        scienceRecyclerview.setLayoutManager(scienceLinearLayoutManager);

        scienceLList =  new ArrayList<>() ;

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScienceActivity.this , Main2Activity.class));
                finish();
            }
        });

        asynkTask task =  new asynkTask() ;
        task.execute("https://newsapi.org/v2/top-headlines?country=eg&category=science&apiKey=bfd24b9b4887496bac984611f22b7ba7\n");
    }

    public class asynkTask  extends android.os.AsyncTask<String , Void , List<ItemModel>>
    {
        @Override
        protected void onPreExecute()
        {
            scienceprogressBar.setVisibility(View.VISIBLE);
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
            scienceLList.clear();

            if(itemModels != null && !itemModels.isEmpty())
            {
                scienceprogressBar.setVisibility(View.GONE);
                scienceLList.addAll(itemModels) ;
                scienceAdapter =  new ScienceAdapter(getApplicationContext() , scienceLList) ;
                scienceRecyclerview.setAdapter(scienceAdapter);
                final LayoutAnimationController controller =
                        AnimationUtils.loadLayoutAnimation(ScienceActivity.this, R.anim.layout_animation_left);

                scienceRecyclerview.setLayoutAnimation(controller);
                scienceAdapter.notifyDataSetChanged();
                scienceRecyclerview.scheduleLayoutAnimation() ;
            }


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ScienceActivity.this , Main2Activity.class));
        finish();
    }
}
