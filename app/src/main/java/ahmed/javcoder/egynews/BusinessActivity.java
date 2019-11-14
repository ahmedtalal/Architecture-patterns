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

import ahmed.javcoder.egynews.Adapters.BusinessAdapter;
import ahmed.javcoder.egynews.Adapters.ConnectionsAdapter;
import ahmed.javcoder.egynews.Adapters.ScienceAdapter;
import ahmed.javcoder.egynews.Models.ItemModel;

public class BusinessActivity extends AppCompatActivity {

    Toolbar toolbar ;
    RecyclerView businessRecyclerview ;
    LinearLayoutManager businessLinearLayoutManager ;
    BusinessAdapter businessAdapter ;
    ProgressBar businessprogressBar ;
    List<ItemModel> businessList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        toolbar =  findViewById(R.id.businesstoolobaro_ID) ;
        businessRecyclerview = findViewById(R.id.businessRecyclerview_ID) ;
        businessprogressBar = findViewById(R.id.businessprogressbar_ID) ;

        businessLinearLayoutManager = new LinearLayoutManager(getApplicationContext() , LinearLayoutManager.VERTICAL , false) ;
        businessRecyclerview.setLayoutManager(businessLinearLayoutManager);

        businessList =  new ArrayList<>() ;

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BusinessActivity.this , Main2Activity.class));
                finish();
            }
        });

        asynkTask task =  new asynkTask() ;
        task.execute("https://newsapi.org/v2/top-headlines?country=eg&category=business&apiKey=bfd24b9b4887496bac984611f22b7ba7\n");
    }


    public class asynkTask  extends android.os.AsyncTask<String , Void , List<ItemModel>>
    {
        @Override
        protected void onPreExecute()
        {
            businessprogressBar.setVisibility(View.VISIBLE);
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
            businessList.clear();

            if(itemModels != null && !itemModels.isEmpty())
            {
                businessprogressBar.setVisibility(View.GONE);
                businessList.addAll(itemModels) ;
                businessAdapter =  new BusinessAdapter(getApplicationContext() , businessList) ;
                businessRecyclerview.setAdapter(businessAdapter);
                final LayoutAnimationController controller =
                        AnimationUtils.loadLayoutAnimation(BusinessActivity.this, R.anim.layout_animation_left);

                businessRecyclerview.setLayoutAnimation(controller);
                businessAdapter.notifyDataSetChanged();
                businessRecyclerview.scheduleLayoutAnimation() ;
            }


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(BusinessActivity.this , Main2Activity.class));
        finish();
    }
}
