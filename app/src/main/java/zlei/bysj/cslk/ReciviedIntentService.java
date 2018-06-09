package zlei.bysj.cslk;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class ReciviedIntentService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_RECEIVE = "RECEIVE DATA";
    private static final String TAG = "sout";

    private MyDatabaseHelper databaseHelper;
    private SQLiteDatabase writableDatabase;

    public ReciviedIntentService() {
        super("ReciviedIntentService");

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            databaseHelper = new MyDatabaseHelper(this,"data",null,1);

            
            if (action.equals(ACTION_RECEIVE)){ 
                Log.e(TAG, "onHandleIntent: 开始服务接收数据" );
                String ip = intent.getStringExtra("IP");
                int port = intent.getIntExtra("PORT",10000);
                socketConnect(ip,port);
            }
        }
    }


    private void socketConnect(String ip, int port) {

        Socket socket = null;
                InputStream inputStream = null;
                String data = null;
                try {
                    Log.e("sout", "1");
                    socket = new Socket();
                    Log.e("sout", "1.1");
                    InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, port);
                    Log.e("sout", "1.2");
                    socket.connect(inetSocketAddress, 10000); //端口号为10000
                    Log.e("sout", "2");
                    inputStream = socket.getInputStream();
                            Log.e("sout","3");
                    while (true) {
                        Log.e("sout","4");
                        byte[] buf = new byte[1024];
                        Log.e("sout","5");
                        int len = inputStream.read(buf);
                        Log.e("sout","6");
                        Log.e(TAG, "socketConnect: -------------"+len );
                            data = new String(buf, 0, len, "GBK");
                            putDataInDB(data);
                        Log.e("sout", "==========" + data);
                    }
//                            Log.e("sout","4");
                } catch (Exception e) {
                    Log.e("sout", "5" + e.getMessage());
                    e.printStackTrace();
                    // Toast.makeText(MainActivity.this, "出错了", Toast.LENGTH_SHORT).show();
                } finally {
                    try {
                        if (socket != null) {
                            socket.shutdownInput();
                            socket.close();
                            socket = null;
                        }
                        // TODO: 2018/5/16 关闭service，等待用户重新点击显示数据按钮，重新启动本service
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

    private void putDataInDB(String data) {
        writableDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put("value", data);
        // 返回,显示数据添加在第几行
        long rowid=writableDatabase.insert("roaddata",null,contentValues);
        if (rowid == -1){
            Log.e(TAG, "putDataInDB: 插入数据失败" );
        }else{
            Log.e(TAG, "putDataInDB: 插入数据成功" );
            EventBus.getDefault().post(new MessageEvent(Constants.DATABASE_CHANGE));
        }
        writableDatabase.close();


//        String sql = "insert into roaddata(id, value) values(?, ?);";
//        writableDatabase.execSQL(sql,new Object[]{null,data});

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (writableDatabase != null){
            writableDatabase.close();
        }
        if (databaseHelper != null){
            databaseHelper.close();
        }

    }
}
