package ahmed.javcoder.egynews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Custom extends PagerAdapter
{
    private Context context;
    private LayoutInflater layoutInflater;
    private int[] images ;
    private String[] texts ;

    public Custom(Context context, int[] images , String[] text)
    {
        this.context = context;
        this.images = images;
        this.texts = text ;
    }

    @Override
    public int getCount() {
        return images.length;

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.testing  , null) ;
        ImageView imageView = view.findViewById(R.id.images) ;
        TextView textView = view.findViewById(R.id.texts) ;
        imageView.setImageResource(images[position]);
        textView.setText(texts[position]);

        ViewPager vp = (ViewPager) container;
        vp.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
