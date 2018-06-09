package zlei.bysj.cslk;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by zl on 2017/5/21.
 */

class MyAdapter extends BaseAdapter {

    private List<ScanResult> wifiList = null;
    private Context context;
    public static final String WIFI_AUTH_OPEN = "";
    public static final String WIFI_AUTH_ROAM = "[ESS]";

    public MyAdapter(List<ScanResult> wifiList, HomeActivity homeActivity) {
        this.wifiList = wifiList;
        context = homeActivity;
    }

    @Override
    public int getCount() {
        return wifiList.size();
    }

    @Override
    public Object getItem(int position) {
        return wifiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = View.inflate(context, R.layout.item, null);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        ImageView lock = (ImageView) view.findViewById(R.id.lock);
        ScanResult result = wifiList.get(position);



        String wifiName = result.SSID;
        if (wifiName.equals("")){
            wifiName = "null";
        }

        if (result.capabilities != null) {
            String capabilities = result.capabilities.trim();
            if (capabilities != null && (capabilities.equals(WIFI_AUTH_OPEN) || capabilities.equals(WIFI_AUTH_ROAM))) {
             //   Log.e("sout","无密码"+wifiName);
                lock.setVisibility(View.INVISIBLE);
            }
            else {
              //  Log.e("sout","有密码"+wifiName);
            }
        }

        tv.setText(wifiName);
        return view;
    }
}
