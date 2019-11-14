package ahmed.javcoder.egynews.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import ahmed.javcoder.egynews.Models.ItemModel;
import ahmed.javcoder.egynews.R;
import ahmed.javcoder.egynews.ShowinfoActivity;

public class EntertainmentAdapter extends RecyclerView.Adapter<EntertainmentAdapter.MyViewHolder>
{
    private View view;
    private LayoutInflater layoutInflater ;
    private Context context ;
    private List<ItemModel> listItem ;

    public EntertainmentAdapter(Context getcontext, List<ItemModel> list)
    {
        this.context = getcontext;
        this.listItem = list;
        this.layoutInflater = LayoutInflater.from(getcontext) ;
    }

    @NonNull
    @Override
    public EntertainmentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        view = layoutInflater.inflate(R.layout.sports_view , viewGroup , false) ;

        return new EntertainmentAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EntertainmentAdapter.MyViewHolder viewHolder, int i)
    {
        final String title =listItem.get(i).getTextmdodel() ;
        final String image = listItem.get(i).getImagemodel() ;
        final String source = listItem.get(i).getUrlsource() ;
        final String name = listItem.get(i).getName() ;
        final String description = listItem.get(i).getDescritption() ;
        final String publshedAt = listItem.get(i).getPublishedAt() ;



//        URL url = null ;
//        try {
//            url = new URL(source) ;
//        } catch (MalformedURLException e)
//        {
//            e.printStackTrace();
//        }
//        //Uri uriUrl = Uri.parse(source);
//
//
//        //viewHolder.sportsSourceTextview.setText(String.valueOf(url));
//
//        viewHolder.sportsSourceTextview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //String s = (String) viewHolder.sportsSourceTextview.getText();
//                Uri uriUrl = Uri.parse(source);
//                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
//                launchBrowser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(launchBrowser);
//            }
//        });
        viewHolder.sportsAticalTextview.setText(title);
        Glide.with(context)
                .load(image)
                .placeholder(R.drawable.entertainment)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        viewHolder.progressBar.setVisibility(View.VISIBLE);

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        viewHolder.progressBar.setVisibility(View.GONE);
                        viewHolder.sportsImageview.setAlpha(Float.parseFloat("1"));

                        return false;
                    }
                })
                .into(viewHolder.sportsImageview);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , ShowinfoActivity.class) ;
                intent.putExtra("name" , name) ;
                intent.putExtra("title" , title) ;
                intent.putExtra("description" , description) ;
                intent.putExtra("publishedAt" , publshedAt) ;
                intent.putExtra("source" , source) ;
                intent.putExtra("image" , image) ;
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
                context.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public   class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView sportsImageview ;
        TextView sportsAticalTextview ;
        MaterialRippleLayout materialRippleLayout ;
        ProgressBar progressBar ;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            sportsImageview = itemView.findViewById(R.id.sportsImage_ID) ;
            sportsAticalTextview = itemView.findViewById(R.id.sportsArtical_ID) ;
           // materialRippleLayout = itemView.findViewById(R.id.myprofile) ;
            progressBar = itemView.findViewById(R.id.progressbar) ;
        }
    }


}
