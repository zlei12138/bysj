package zlei.bysj.cslk;

import android.annotation.SuppressLint;

import android.app.ActivityManager;
import android.app.ApplicationErrorReport;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "sout";
    private String password = null;
    private String ip = "169.254.245.131";
    private int port = 10000;
    private WifiAdmin wifiAdmin;
    private ListView lv;
    private MyAdapter adapter = null;
    private List<ScanResult> wifiList;
    private String realIp;
    private static int hasNewData = 0;
    private RecyclerView show_DataByRecyc;
    ArrayList<MultiItemEntity> list = new ArrayList<>();
    ExpandableItemAdapter recyclerView_adapter;

    private TextView tv_tix;
    Socket socket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        wifiAdmin = WifiAdmin.getInstance(getApplicationContext());
        lv = (ListView) findViewById(R.id.lv);
        tv_tix = (TextView) findViewById(R.id.tv_tix);
        show_DataByRecyc = (RecyclerView) findViewById(R.id.show_DataByRecyc);

        recyclerView_adapter = new ExpandableItemAdapter(list);
        final GridLayoutManager manager = new GridLayoutManager(this, 3);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
//                return adapter.getItemViewType(position) == ExpandableItemAdapter.TYPE_ROADDATA ? 1 : 3;
                return 3;
            }
        });
        show_DataByRecyc.setAdapter(recyclerView_adapter);
        show_DataByRecyc.setLayoutManager(manager);
//        recyclerView_adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        recyclerView_adapter.openLoadAnimation(BaseMultiItemQuickAdapter.SLIDEIN_BOTTOM  );



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //连接WiFi
//                Log.e("sout","被点击了"+position);
                final ScanResult result = wifiList.get(position);

                ImageView lock = (ImageView) view.findViewById(R.id.lock);
                int visibility = lock.getVisibility();
                if (visibility == View.VISIBLE) {
                    // Toast.makeText(MainActivity.this, "--有密码--", Toast.LENGTH_SHORT).show();
                    LayoutInflater factory = LayoutInflater.from(HomeActivity.this);//提示框
                    final View viewEdit = factory.inflate(R.layout.editbox_layout, null);//这里必须是final的
                    final EditText edit=(EditText)viewEdit.findViewById(R.id.editText);//获得输入框对象
                    //edit.setText(password);
                    new AlertDialog.Builder(HomeActivity.this)
                            .setTitle("输入密码：")//提示框标题
                            .setView(viewEdit)
                            .setPositiveButton("确定",//提示框的两个按钮
                                    new android.content.DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            //事件
                                            password = edit.getText().toString().trim();
                                            if (password != null){
                                                connect(result, password);
                                            }
                                        }
                                    }).setNegativeButton("取消", null).create().show();

                } else if (visibility == View.INVISIBLE) {
                    //  Toast.makeText(MainActivity.this, "--无密码--", Toast.LENGTH_SHORT).show();
                    connect(result, "");
                }

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent){
        int message = messageEvent.getMessage();
        if(message == Constants.DATABASE_CHANGE){
            MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this,"data",null,1);
            SQLiteDatabase readableDatabase = myDatabaseHelper.getReadableDatabase();
            Cursor cursor = readableDatabase.rawQuery("select * from roaddata", null);
            if (cursor.moveToLast()) {
            // 该cursor是最后一条数据
                String data = cursor.getString(1);
                Log.e(TAG, "HomeActivity_onMoonEvent: "+data);
//                Toast.makeText(this,"HomeActivity_onMoonEvent: "+data,Toast.LENGTH_LONG).show();

                showDataOnList(data);
            }
        }
    }

    private void showDataOnList(String data) {
        ArrayList<String[]> list_ones = new ArrayList<>();
        String[] datas = data.split("&");
        Log.e(TAG, "datas长度: "+datas.length);
        for (int i = 0; i < datas.length; i++) {
            String[] data_one = datas[i].split("_");
            list_ones.add(data_one);
            Log.e(TAG, "数据拆分"+ list_ones.get(i)[0]+"======="+list_ones.get(i)[1]);
        }


        if (recyclerView_adapter != null && show_DataByRecyc != null){
            Level0Item lv0 = new Level0Item("Received a new data....", "subtitle of ");
            lv0.addSubItem(new RoadData(list_ones.get(0)[0],list_ones.get(0)[1],list_ones.get(1)[0],list_ones.get(1)[1],list_ones.get(2)[0],list_ones.get(2)[1],list_ones.get(3)[0],list_ones.get(3)[1],
                    list_ones.get(4)[0],list_ones.get(4)[1],list_ones.get(5)[0],list_ones.get(5)[1],list_ones.get(6)[0],list_ones.get(6)[1],list_ones.get(7)[0],list_ones.get(7)[1],
                    list_ones.get(8)[0],list_ones.get(8)[1],list_ones.get(9)[0],list_ones.get(9)[1],list_ones.get(10)[0],list_ones.get(10)[1],list_ones.get(11)[0],list_ones.get(11)[1],
                    list_ones.get(12)[0],list_ones.get(12)[1],list_ones.get(13)[0],list_ones.get(13)[1],list_ones.get(14)[0],list_ones.get(14)[1],list_ones.get(15)[0],list_ones.get(15)[1],
                    list_ones.get(16)[0],list_ones.get(16)[1],list_ones.get(17)[0],list_ones.get(17)[1],list_ones.get(18)[0],list_ones.get(18)[1],list_ones.get(19)[0],list_ones.get(19)[1]));
            recyclerView_adapter.addData(lv0);
            int itemCount = recyclerView_adapter.getItemCount();

            Log.e("sout", "handleMessage: "+itemCount );
            show_DataByRecyc.smoothScrollToPosition(itemCount);

        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        //注册
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //解注册
        EventBus.getDefault().unregister(this);
        if (socket != null){
            try {
                socket.shutdownInput();
                socket.close();
                socket = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        socketConnect();
    }

    private void connect(final ScanResult result, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (password.equals("")) {
                    wifiAdmin.addNetwork(wifiAdmin.createWifiInfo(result.SSID, "", 1, "wt"));

                } else {
                    WifiInfo wifiInfo = wifiAdmin.getWifiInfo();
                    wifiAdmin.disconnectWifi(wifiAdmin.getNetworkId());
                    wifiAdmin.addNetwork(wifiAdmin.createWifiInfo(result.SSID, password, 3, "wt"));
                    int ip = wifiAdmin.getIPAddress();
                    realIp = intToIp(ip);
                }

            }
        }).start();
    }

    private void receiveData() {
        LayoutInflater factory = LayoutInflater.from(HomeActivity.this);//提示框
        final View viewEdit = factory.inflate(R.layout.editbox_layout2, null);//这里必须是final的
        final EditText ip_Edit=(EditText)viewEdit.findViewById(R.id.ip);//获得输入框对象
        final EditText port_Edit=(EditText)viewEdit.findViewById(R.id.port);//获得输入框对象
        ip_Edit.setText(ip);
        port_Edit.setText(port+"");
        if (port != 0){
            port_Edit.setText(port+"");
        }
        new AlertDialog.Builder(HomeActivity.this)
                .setTitle("输入ip和端口号：")//提示框标题
                .setView(viewEdit)
                .setPositiveButton("确定",//提示框的两个按钮
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                ip = ip_Edit.getText().toString().trim();
                                port = Integer.parseInt(port_Edit.getText().toString().trim());

                                if (ip != null && port != 0 ){
                                    lv.setVisibility(View.INVISIBLE);
                                    tv_tix.setVisibility(View.INVISIBLE);
                                    Log.e("sout","kaishisocket"+ip+port);


                                    Constants.IP = ip;
                                    Constants.PORT = port;
//                                    socketConnect();

                                    if(!isServiceWork(HomeActivity.this,"zlei.bysj.cslk.ReciviedIntentService")){

//                                        receiveData();
                                        Intent intent = new Intent(HomeActivity.this,ReciviedIntentService.class);
                                        intent.setAction("RECEIVE DATA");
                                        intent.putExtra("IP",Constants.IP);
                                        intent.putExtra("PORT",Constants.PORT);
                                        startService(intent);
                                        Log.e(TAG, "onOptionsItemSelected: 服务正在开启" );
                                    }else {
                                        Log.e(TAG, "onOptionsItemSelected: 服务已经开启了" );
                                    }


                                }else {
                                    Log.e("sout","meiyou"+ip+port);
                                }
                            }
                        }).setNegativeButton("取消", null).create().show();
        //0.0.0.0:错误
//        if(!realIp.equals("0.0.0.0")){
    }

    //将获取的int转为真正的ip地址,参考的网上的，修改了下
    private String intToIp(int paramInt) {
        return (paramInt & 0xFF) + "." + (0xFF & paramInt >> 8) + "." + (0xFF & paramInt >> 16) + "."
                + (0xFF & paramInt >> 24);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            if (wifiAdmin.mWifiManager.isWifiEnabled()) {
                Toast.makeText(this, "wifi已经打开了", Toast.LENGTH_SHORT).show();
            } else {
                wifiAdmin.OpenWifi();
            }
            return true;
        }
        if (id == R.id.search_AP) {

            wifiAdmin.startScan();
            wifiAdmin.setWifiList();
            // StringBuilder stringBuilder = wifiAdmin.lookUpScan();
            // Log.e("sout",stringBuilder.toString()+"---");
            wifiList = wifiAdmin.getWifiList();
            Log.e("sout", wifiList.size()+"---");
//            for (ScanResult result : wifiList) {
//                Log.e("sout",result.SSID+"---");
//            }
            adapter = new MyAdapter(wifiList, this);
            lv.setVisibility(View.VISIBLE);
            lv.setAdapter(adapter);
            tv_tix.setVisibility(View.INVISIBLE);
//            Log.e("sout","sousuoredian---");
            return true;
        }
        if (id == R.id.show_data){
            show_DataByRecyc.setVisibility(View.VISIBLE);

            receiveData();
            return true;
        }

        if (id == R.id.goto_mapactivity){
            Intent intent = new Intent(this,Main2Activity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     *
     *
     * @param mContext
     * @param serviceName
     *      是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

}
