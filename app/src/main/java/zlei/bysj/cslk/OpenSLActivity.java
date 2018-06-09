package zlei.bysj.cslk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OpenSLActivity extends AppCompatActivity {

    Toolbar oslToolbar;
    ImageView oslToolBackicon;
    TextView oslText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_sl);
        oslToolbar = findViewById(R.id.osl_toolbar);
        oslToolBackicon = findViewById(R.id.osl_tool_backicon);
        oslText = findViewById(R.id.osl_text);
        setOSLText();

        oslToolBackicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setOSLText() {
        Observable<String> observable = readOSL();
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()) .subscribe(new Subscriber<String>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                oslText.setText(s);
            }
        });
    }


    private Observable<String> readOSL(){

       return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                InputStreamReader inputStreamReader = null;
                try {
                    inputStreamReader = new InputStreamReader(getResources().getAssets().open("liscense.txt"));
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line="";
                    StringBuffer Result = new StringBuffer();
                    try {
                        while((line = bufferedReader.readLine()) != null){
                            Result.append(line);
                            Result.append("\n");
                        }
                        String test_osl = Result.toString();
                        subscriber.onNext(test_osl);
                        subscriber.onCompleted();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
