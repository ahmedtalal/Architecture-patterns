package ahmed.javcoder.egynews.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;

import ahmed.javcoder.egynews.BusinessActivity;
import ahmed.javcoder.egynews.EntertainmentActivity;
import ahmed.javcoder.egynews.HealthActivity;
import ahmed.javcoder.egynews.R;
import ahmed.javcoder.egynews.ScienceActivity;
import ahmed.javcoder.egynews.SportsActivity;
import ahmed.javcoder.egynews.TechnologyActivity;

public class RecycloAdapter extends RecyclerView.Adapter<RecycloAdapter.MyViewHolder>
{
    private  View view ;
    private String[] textList ;
    private int[] imageList ;
    private LayoutInflater layoutInflater ;
    private Context context ;

    public RecycloAdapter(Context getcontext , int[] image , String[] text) {
        this.textList = text;
        this.imageList = image;
        this.context = getcontext;
        this.layoutInflater = LayoutInflater.from(getcontext) ;
    }

    @NonNull
    @Override
    public RecycloAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        view = layoutInflater.inflate(R.layout.recyleview_items , viewGroup , false) ;

        final  MyViewHolder myViewHolder =  new MyViewHolder(view);
        myViewHolder.rippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myViewHolder.textView.getText() == "Sports")
                {
                   // Toast.makeText(context , "sports" , Toast.LENGTH_LONG).show();
                    Intent intent =  new Intent(context , SportsActivity.class) ;
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
                    context.startActivity(intent);
                }else if(myViewHolder.textView.getText() == "Science")
                {
                    Intent intent =  new Intent(context , ScienceActivity.class) ;
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
                    context.startActivity(intent);
                }else if(myViewHolder.textView.getText() == "Health")
                {
                    Intent intent =  new Intent(context , HealthActivity.class) ;
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
                    context.startActivity(intent);
                }
            }
        });

        return new RecycloAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, int i)
    {
        viewHolder.imageView.setImageResource(imageList[i]);
        viewHolder.textView.setText(textList[i]);


    }

    @Override
    public int getItemCount() {
        return imageList.length ;
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder
    {
        ImageView imageView ;
        TextView textView ;
        MaterialRippleLayout rippleLayout ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recycleImage_ID);
            textView = itemView.findViewById(R.id.recycleText_ID) ;
            rippleLayout = itemView.findViewById(R.id.myprofile_mrl) ;
        }
    }
}
