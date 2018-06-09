package zlei.bysj.cslk;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Switch;

import org.greenrobot.eventbus.EventBus;

public class SetActivity extends AppCompatActivity {

    private RadioGroup logoPositionRG;
    private Switch zooomSwitch;
    private Switch compassSwitch;
    private Switch languageSwitch;
    private Switch scaleSwitch;
    private Switch swipeSwitch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        SettingItemView contactMe = findViewById(R.id.contactMe);
        SettingItemView openSourceLicense = findViewById(R.id.openSourceLicense);
        SettingItemView about = findViewById(R.id.about);

        logoPositionRG = findViewById(R.id.rg);
        zooomSwitch = findViewById(R.id.sw_zoom);
        compassSwitch = findViewById(R.id.sw_compass);
        languageSwitch = findViewById(R.id.sw_language);
        scaleSwitch = findViewById(R.id.sw_scale);
        swipeSwitch = findViewById(R.id.sw_swipe);


        logoPositionRG.check(Constants.checkedRadioButtonId);
        zooomSwitch.setChecked(Constants.isZoomChecked);
        compassSwitch.setChecked(Constants.isCompassChecked);
        languageSwitch.setChecked(Constants.isLanguageChecked);
        scaleSwitch.setChecked(Constants.isScaleChecked);
        swipeSwitch.setChecked(Constants.isSwipeChecked);

        contactMe(contactMe);
        openSourceLicense(openSourceLicense);
        about(about);
    }


    private void about(SettingItemView about) {
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }

    private void openSourceLicense(SettingItemView openSourceLicense) {
        openSourceLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetActivity.this, OpenSLActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 联系作者点击事件
     * @param contactMe
     */
    private void contactMe(SettingItemView contactMe) {
        contactMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SetActivity.this);
                View contact_me = View.inflate(SetActivity.this, R.layout.contactme, null);
                Button contactButtonBack = (Button) contact_me.findViewById(R.id.contactme_button_back);
                builder.setView(contact_me);
                final AlertDialog dialog = builder.show();
                contactButtonBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (logoPositionRG.getCheckedRadioButtonId() != Constants.checkedRadioButtonId){
            if (logoPositionRG.getCheckedRadioButtonId() == R.id.rb_left){
                EventBus.getDefault().post(new MessageEvent(Constants.LOGO_POSITION_CHANGED_LEFT));
            }
            if (logoPositionRG.getCheckedRadioButtonId() == R.id.rb_middle){
                EventBus.getDefault().post(new MessageEvent(Constants.LOGO_POSITION_CHANGED_MIDDLE));
            }
            if (logoPositionRG.getCheckedRadioButtonId() == R.id.rb_right){
                EventBus.getDefault().post(new MessageEvent(Constants.LOGO_POSITION_CHANGED_RIGHT));
            }
        }
        if (zooomSwitch.isChecked()){
            EventBus.getDefault().post(new MessageEvent(Constants.ZOOMSWITCH_CHANGED_OPEN));
        }else {
            EventBus.getDefault().post(new MessageEvent(Constants.ZOOMSWITCH_CHANGED_CLOSE));
        }

        if (compassSwitch.isChecked()){
            EventBus.getDefault().post(new MessageEvent(Constants.COMPASS_SWITCH_CHANGED_OPEN));
        }else {
            EventBus.getDefault().post(new MessageEvent(Constants.COMPASS_SWITCH_CHANGED_CLOSE));
        }
        if (languageSwitch.isChecked()){
            EventBus.getDefault().post(new MessageEvent(Constants.LANGUAGE_SWITCH_CHANGED_OPEN));
        }else {
            EventBus.getDefault().post(new MessageEvent(Constants.LANGUAGE_SWITCH_CHANGED_CLOSE));
        }
        if (scaleSwitch.isChecked()){
            EventBus.getDefault().post(new MessageEvent(Constants.SCALE_SWITCH_CHANGED_OPEN));
        }else {
            EventBus.getDefault().post(new MessageEvent(Constants.SCALE_SWITCH_CHANGED_CLOSE));
        }
        if (swipeSwitch.isChecked()){
            EventBus.getDefault().post(new MessageEvent(Constants.SWIPE_SWITCH_CHANGED_OPEN));
        }else {
            EventBus.getDefault().post(new MessageEvent(Constants.SWIPE_SWITCH_CHANGED_CLOSE));
        }


        Constants.isScaleChecked = scaleSwitch.isChecked();
        Constants.isLanguageChecked = languageSwitch.isChecked();
        Constants.isCompassChecked = compassSwitch.isChecked();
        Constants.isZoomChecked = zooomSwitch.isChecked();
        Constants.checkedRadioButtonId = logoPositionRG.getCheckedRadioButtonId();
        Constants.isSwipeChecked = swipeSwitch.isChecked();

    }
}
