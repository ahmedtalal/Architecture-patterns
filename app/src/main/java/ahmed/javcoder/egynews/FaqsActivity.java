package ahmed.javcoder.egynews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ahmed.javcoder.egynews.Adapters.ExpandableAdapter;
import ahmed.javcoder.egynews.Adapters.ExpandableAdapter2;
import ahmed.javcoder.egynews.Adapters.ExpandableAdapter3;

public class FaqsActivity extends AppCompatActivity {
    private ExpandableListView expand ,expand2 , expand3 ;
    private Toolbar toolbar ;
    private List<String> listGroup ;
    private HashMap<String , List<String>> hashListChild ;
    private List<String> listGroup2 ;
    private HashMap<String , List<String>> hashListChild2 ;
    private List<String> listGroup3 ;
    private HashMap<String , List<String>> hashListChild3 ;
    private ExpandableAdapter adapter ;
    private ExpandableAdapter2 adapter2 ;
    private ExpandableAdapter3 adapter3 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);

        toolbar = findViewById(R.id.faqsToolbar) ;
        expand = findViewById(R.id.expandable_ID) ;
        expand2 = findViewById(R.id.expandable2_ID) ;
        expand3 = findViewById(R.id.expandable3_ID) ;

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FaqsActivity.this , SettingsActivity.class));
                finish();
            }
        });

        listGroup = new ArrayList<>() ;
        hashListChild =  new HashMap<>() ;
        listGroup2 = new ArrayList<>() ;
        hashListChild2 =  new HashMap<>() ;
        listGroup3 = new ArrayList<>() ;
        hashListChild3 =  new HashMap<>() ;

         adapter = new ExpandableAdapter(getApplicationContext() , listGroup , hashListChild) ;
        expand.setAdapter(adapter);

        adapter2 = new ExpandableAdapter2(getApplicationContext() , listGroup2 , hashListChild2) ;
        expand2.setAdapter(adapter2);

        adapter3 = new ExpandableAdapter3(getApplicationContext() , listGroup3 , hashListChild3) ;
        expand3.setAdapter(adapter3);

        initListData() ;
        initListData2() ;
        initListData3() ;
    }

    private void initListData3() {
        listGroup3.add(getString(R.string.field3)) ;

        String[] childbox ;
        List<String> list3 = new ArrayList<>() ;
        childbox =  getResources().getStringArray(R.array.field3) ;
        for (String items : childbox){
            list3.add(items) ;
        }

        hashListChild3.put(listGroup3.get(0) , list3) ;
        adapter3.notifyDataSetChanged();
    }

    private void initListData2() {
        listGroup2.add(getString(R.string.field2)) ;

        String[] childbox ;
        List<String> list2 =  new ArrayList<>() ;
        childbox = getResources().getStringArray(R.array.field2) ;
        for (String items :childbox){
            list2.add(items) ;
        }

        hashListChild2.put(listGroup2.get(0) , list2) ;
        adapter2.notifyDataSetChanged();
    }

    private void initListData() {
        listGroup.add(getString(R.string.field1)) ;

        String[] childbox ;
        List<String> list1 = new ArrayList<>() ;
        childbox = getResources().getStringArray(R.array.field1) ;
        for (String items : childbox){
            list1.add(items) ;
        }


        hashListChild.put(listGroup.get(0) , list1) ;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(FaqsActivity.this , SettingsActivity.class));
        finish();
    }
}
