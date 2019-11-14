package ahmed.javcoder.egynews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity{

    private TextView splashText ;
    private MaterialRippleLayout materialRippleLayout ;
    private Animation animation2 ;
    private CircleImageView circleImageView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        splashText = findViewById(R.id.splashText_ID) ;
        materialRippleLayout = findViewById(R.id.splashripple) ;
        circleImageView = findViewById(R.id.image1) ;

        circleImageView.setScaleX(0) ;
        circleImageView.setScaleY(0);
        circleImageView.animate().scaleY(1).scaleX(1).setDuration(1000);

        animation2 = AnimationUtils.loadAnimation(this , R.anim.anim_text1) ;
        materialRippleLayout.setAnimation(animation2);

        TimerTask timerTask =  new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this , RegisterActivity.class));
                finish();
            }
        };

        new Timer().schedule(timerTask , 1300);

    }

}
