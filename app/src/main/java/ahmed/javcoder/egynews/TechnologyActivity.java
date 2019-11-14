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
import ahmed.javcoder.egynews.Adapters.TechnologyAdapter;
import ahmed.javcoder.egynews.Models.ItemModel;

public class TechnologyActivity extends AppCompatActivity {
    Toolbar toolbar ;
    RecyclerView technologyRecyclerview ;
    LinearLayoutManager technologyLinearLayoutManager ;
    TechnologyAdapter technologyAdapter ;
    ProgressBar technologyprogressBar ;
    List<ItemModel> technologyLList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technology);

        toolbar =  findViewById(R.id.technologytoolobaro_ID) ;
        technologyRecyclerview = findViewById(R.id.technologyRecyclerview_ID) ;
        technologyprogressBar = findViewById(R.id.technologyprogressbar_ID) ;

        technologyLinearLayoutManager = new LinearLayoutManager(getApplicationContext() , LinearLayoutManager.VERTICAL , false) ;
        technologyRecyclerview.setLayoutManager(technologyLinearLayoutManager);

        technologyLList =  new ArrayList<>() ;

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TechnologyActivity.this , Main2Activity.class));
                finish();
            }
        });

        asynkTask task =  new asynkTask() ;
        task.execute("https://newsapi.org/v2/top-headlines?country=eg&category=technology&apiKey=bfd24b9b4887496bac984611f22b7ba7\n");
    }

    public class asynkTask  extends android.os.AsyncTask<String , Void , List<ItemModel>>
    {
        @Override
        protected void onPreExecute()
        {
            technologyprogressBar.setVisibility(View.VISIBLE);
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
            technologyLList.clear();

            if(itemModels != null && !itemModels.isEmpty())
            {
                technologyprogressBar.setVisibility(View.GONE);
                technologyLList.addAll(itemModels) ;
                technologyAdapter =  new TechnologyAdapter(getApplicationContext() , technologyLList) ;
                technologyRecyclerview.setAdapter(technologyAdapter);
                final LayoutAnimationController controller =
                        AnimationUtils.loadLayoutAnimation(TechnologyActivity.this, R.anim.layout_animation_left);

                technologyRecyclerview.setLayoutAnimation(controller);
                technologyAdapter.notifyDataSetChanged();
                technologyRecyclerview.scheduleLayoutAnimation() ;
            }


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TechnologyActivity.this , Main2Activity.class));
        finish();
    }
}
