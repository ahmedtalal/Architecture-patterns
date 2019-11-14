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
import ahmed.javcoder.egynews.Adapters.EntertainmentAdapter;
import ahmed.javcoder.egynews.Adapters.ScienceAdapter;
import ahmed.javcoder.egynews.Models.ItemModel;

public class EntertainmentActivity extends AppCompatActivity {

    Toolbar toolbar ;
    RecyclerView entertainmentRecyclerview ;
    LinearLayoutManager entertainmentLinearLayoutManager ;
    EntertainmentAdapter entertainmentAdapter ;
    ProgressBar entertainmentprogressBar ;
    List<ItemModel> entertainmentLList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);

        toolbar =  findViewById(R.id.entertainmenttoolobar_ID) ;
        entertainmentRecyclerview = findViewById(R.id.entertainmentRecyclerview_ID) ;
        entertainmentprogressBar = findViewById(R.id.entertainmentprogressbar_ID) ;

        entertainmentLinearLayoutManager = new LinearLayoutManager(getApplicationContext() , LinearLayoutManager.VERTICAL , false) ;
        entertainmentRecyclerview.setLayoutManager(entertainmentLinearLayoutManager);

        entertainmentLList =  new ArrayList<>() ;

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EntertainmentActivity.this , Main2Activity.class));
                finish();
            }
        });

        asynkTask task =  new asynkTask() ;
        task.execute("https://newsapi.org/v2/top-headlines?country=eg&category=entertainment&apiKey=bfd24b9b4887496bac984611f22b7ba7\n");
    }

    public class asynkTask  extends android.os.AsyncTask<String , Void , List<ItemModel>>
    {
        @Override
        protected void onPreExecute()
        {
            entertainmentprogressBar.setVisibility(View.VISIBLE);
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
            entertainmentLList.clear();

            if(itemModels != null && !itemModels.isEmpty())
            {
                entertainmentprogressBar.setVisibility(View.GONE);
                entertainmentLList.addAll(itemModels) ;
                entertainmentAdapter =  new EntertainmentAdapter(getApplicationContext() , entertainmentLList) ;
                entertainmentRecyclerview.setAdapter(entertainmentAdapter);
                final LayoutAnimationController controller =
                        AnimationUtils.loadLayoutAnimation(EntertainmentActivity.this, R.anim.layout_animation_left);

                entertainmentRecyclerview.setLayoutAnimation(controller);
                entertainmentAdapter.notifyDataSetChanged();
                entertainmentRecyclerview.scheduleLayoutAnimation() ;
            }


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EntertainmentActivity.this , Main2Activity.class));
        finish();
    }
}
