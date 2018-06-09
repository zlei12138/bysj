package zlei.bysj.cslk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ImageView aboutImg = findViewById(R.id.about_img);
        final ProgressBar aboutProbar = findViewById(R.id.about_probar);
        ImageView aboutBack = findViewById(R.id.about_back);

        aboutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //picasso下载图片
        Picasso picasso = Picasso.with(this);//通过单例获取对象
        RequestCreator requestCreator = picasso.load("http://ovoz7yxxg.bkt.clouddn.com/%E5%9F%8E%E5%B8%82%E8%B7%AF%E5%86%B5_about.jpg");
        requestCreator.memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE) .fit().error(R.mipmap.ic_launcher).into(aboutImg, new Callback() {
            @Override
            public void onSuccess() {
                aboutProbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError() {

            }
        });//fit()自动填充
    }
}
