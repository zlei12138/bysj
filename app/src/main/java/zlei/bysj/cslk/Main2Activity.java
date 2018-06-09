package zlei.bysj.cslk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Polyline;
import com.amap.api.maps2d.model.PolylineOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private static final String TAG = "sout";
    // 抽屉菜单对象
    public DrawerLayout drawerLayout;
    private LinearLayout main_right_drawer_layout;
    private ArrayList<Polyline> polylines;

    MapView mMapView = null;
    AMap aMap;
    UiSettings muiSettings;

    private ImageView rode_TypeA;
    private ImageView rode_TypeB;
    private View rode_TypeA_Bg;
    private View rode_TypeB_Bg;

    private Button button_road1;
    private Button button_road2;
    private Button button_road3;
    private Button button_road4;
    private Button button_road5;
    private Button button_road6;
    private Button button_road7;
    private Button button_road8;
    private Button button_road9;
    private Button button_road10;
    private Button button_road11;
    private Button button_road12;
    private Button button_road13;
    private Button button_road14;
    private Button button_road15;
    private Button button_road16;
    private Button button_road17;
    private Button button_road18;
    private Button button_road19;
    private Button button_road20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        mMapView = (MapView) findViewById(R.id.map);
//        mMapView.onCreate(savedInstanceState);

        // 定义北京市经纬度坐标（此处以北京坐标为例）
        LatLng centerBJPoint= new LatLng(29.5346134739,106.6064894199);
        // 定义了一个配置 AMap 对象的参数类
        AMapOptions mapOptions = new AMapOptions();
        // 设置了一个可视范围的初始化位置
        // CameraPosition 第一个参数： 目标位置的屏幕中心点经纬度坐标。
        // CameraPosition 第二个参数： 目标可视区域的缩放级别
        // CameraPosition 第三个参数： 目标可视区域的倾斜度，以角度为单位。
        // CameraPosition 第四个参数： 可视区域指向的方向，以角度为单位，从正北向顺时针方向计算，从0度到360度
        mapOptions.camera(new CameraPosition(centerBJPoint, 10f, 0, 0));
        // 定义一个 MapView 对象，构造方法中传入 mapOptions 参数类
        mMapView = new MapView(this, mapOptions);
        // 调用 onCreate方法 对 MapView LayoutParams 设置
        mMapView.onCreate(savedInstanceState);

        RelativeLayout main_content_frame_parent = findViewById(R.id.ralativelayout_mapview);
        main_content_frame_parent.addView(mMapView);

        rode_TypeA = findViewById(R.id.rode_TypeA);
        rode_TypeB = findViewById(R.id.rode_TypeB);
        rode_TypeA_Bg = findViewById(R.id.rode_TypeA_Bg);
        rode_TypeB_Bg = findViewById(R.id.rode_TypeB_Bg);

        button_road1 = (Button) findViewById(R.id.button_road1);
        button_road2 = (Button) findViewById(R.id.button_road2);
        button_road3 = (Button) findViewById(R.id.button_road3);
        button_road4 = (Button) findViewById(R.id.button_road4);
        button_road5 = (Button) findViewById(R.id.button_road5);
        button_road6 = (Button) findViewById(R.id.button_road6);
        button_road7 = (Button) findViewById(R.id.button_road7);
        button_road8 = (Button) findViewById(R.id.button_road8);
        button_road9 = (Button) findViewById(R.id.button_road9);
        button_road10 = (Button) findViewById(R.id.button_road10);
        button_road11 = (Button) findViewById(R.id.button_road11);
        button_road12 = (Button) findViewById(R.id.button_road12);
        button_road13 = (Button) findViewById(R.id.button_road13);
        button_road14 = (Button) findViewById(R.id.button_road14);
        button_road15 = (Button) findViewById(R.id.button_road15);
        button_road16 = (Button) findViewById(R.id.button_road16);
        button_road17 = (Button) findViewById(R.id.button_road17);
        button_road18 = (Button) findViewById(R.id.button_road18);
        button_road19 = (Button) findViewById(R.id.button_road19);
        button_road20 = (Button) findViewById(R.id.button_road20);
        Button positionButton = findViewById(R.id.main2_myposition);

        Button setting = findViewById(R.id.main2_setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this,SetActivity.class);
                startActivity(intent);
            }
        });
        init();
        positionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(main_right_drawer_layout);
            }
        });
        setAllBack();


        initLayout();

        mapTypeChooseLintenser();

        roadButtonLintenser();


        polylines = drawDefaultLines();

        changeLinesWid();


    }

    private ArrayList<Polyline> drawDefaultLines() {

        ArrayList<Polyline> Polylines = new ArrayList<Polyline>();
        ArrayList<LatLng> latLngs = new ArrayList<LatLng>();

        //崇文路
        latLngs.add(new LatLng(29.5372458280,106.6044723988));
        latLngs.add(new LatLng(29.5371431488,106.6044241190));
        latLngs.add(new LatLng(29.5369891298,106.6043865681));
        latLngs.add(new LatLng(29.5367931053,106.6043597460));
        latLngs.add(new LatLng(29.5366017477,106.6043168306));
        latLngs.add(new LatLng(29.5363543824,106.6043114662));
        latLngs.add(new LatLng(29.5361303530,106.6043061018));
        latLngs.add(new LatLng(29.5359389941,106.6043490171));
        latLngs.add(new LatLng(29.5357336330,106.6043972969));
        latLngs.add(new LatLng(29.5355936138,106.6044563055));
        latLngs.add(new LatLng(29.5354769310,106.6044884920));
        latLngs.add(new LatLng(29.5352809035,106.6045206785));
        latLngs.add(new LatLng(29.5350195330,106.6045582294));
        latLngs.add(new LatLng(29.5347955006,106.6045957804));
        latLngs.add(new LatLng(29.5345808025,106.6046011448));
        latLngs.add(new LatLng(29.5344081101,106.6045743227));
        latLngs.add(new LatLng(29.5341887437,106.6044831276));
        latLngs.add(new LatLng(29.5339507074,106.6043597460));
        latLngs.add(new LatLng(29.5337920161,106.6042953730));
        latLngs.add(new LatLng(29.5336006528,106.6041934490));
        latLngs.add(new LatLng(29.5334139566,106.6040968895));
        latLngs.add(new LatLng(29.5332272600,106.6040056944));
        latLngs.add(new LatLng(29.5330732350,106.6039091349));
        latLngs.add(new LatLng(29.5328865378,106.6038501263));
        latLngs.add(new LatLng(29.5326858379,106.6037589312));
        latLngs.add(new LatLng(29.5324898051,106.6036677361));
        latLngs.add(new LatLng(29.5323497814,106.6035926342));
        latLngs.add(new LatLng(29.5321490804,106.6035121679));
        latLngs.add(new LatLng(29.5318363595,106.6033834219));
        latLngs.add(new LatLng(29.5315376402,106.6032439470));
        latLngs.add(new LatLng(29.5313136000,106.6031420231));
        latLngs.add(new LatLng(29.5310755569,106.6030293703));
        latLngs.add(new LatLng(29.5309355312,106.6029918194));
        latLngs.add(new LatLng(29.5307815028,106.6029381752));
        latLngs.add(new LatLng(29.5306508118,106.6029059887));
        latLngs.add(new LatLng(29.5305247882,106.6028952599));
        latLngs.add(new LatLng(29.5303707592,106.6029006243));
        latLngs.add(new LatLng(29.5302120623,106.6029542685));
        latLngs.add(new LatLng(29.5300580328,106.6029971838));

        Polylines.add(aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(6).color(Color.argb(Constants.DEFAULTCOLOR_A, Constants.DEFAULTCOLOR_R, Constants.DEFAULTCOLOR_G, Constants.DEFAULTCOLOR_B))));

        //东水门大桥
        latLngs.clear();
        double DSMDQ_POINTS[][] = new double[][]{{29.5585450000,106.5879550000},{29.5583400000,106.5887550000},{29.5581250000,106.5895750000},
                {29.5580080000,106.5900150000},{29.5579520000,106.5902510000}
        ,{29.5578500000,106.5906210000},{29.5577700000,106.5908900000},{29.5577000000,106.5911690000},{29.5576400000,106.5914910000},{29.5575510000,106.5917960000}
                ,{29.5574620000,106.5921130000},{29.5573880000,106.5924450000},{29.5572940000,106.5927510000},{29.5572200000,106.5930620000},
                {29.5571400000,106.5933090000}
                ,{29.5570560000,106.5936200000},{29.5570000000,106.5938510000},{29.5569400000,106.5940820000},{29.5568980000,106.5942850000}};

        for (int i = 0; i < DSMDQ_POINTS.length; i++) {
            latLngs.add(new LatLng(DSMDQ_POINTS[i][0],DSMDQ_POINTS[i][1]));
        }
        Polylines.add(aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(6).color(Color.argb(Constants.DEFAULTCOLOR_A, Constants.DEFAULTCOLOR_R, Constants.DEFAULTCOLOR_G, Constants.DEFAULTCOLOR_B))));


        //南滨路
        latLngs.clear();
        double NBL_POINTS[][] = new double[][]{{29.5380770000,106.5517460000},{29.5380720000,106.5519920000},{29.5380630000,106.5522390000},{29.5380810000,106.5524970000}
                ,{29.5380950000,106.5527760000},{29.5381140000,106.5530510000},{29.5381560000,106.5532770000},{29.5381840000,106.5535070000}
                ,{29.5382540000,106.5537860000},{29.5383150000,106.5539960000},{29.5383900000,106.5542690000},{29.5384600000,106.5545110000}
                ,{29.5385480000,106.5547300000},{29.5386320000,106.5549400000},{29.5387400000,106.5551760000},{29.5388380000,106.5553630000}
                ,{29.5389260000,106.5555570000},{29.5390620000,106.5558140000},{29.5391600000,106.5559910000},{29.5392620000,106.5561950000}
                ,{29.5393700000,106.5564420000},{29.5394540000,106.5566460000},{29.5395420000,106.5568660000},{29.5396360000,106.5571070000}
                ,{29.5397430000,106.5574230000},{29.5398040000,106.5576330000},{29.5398830000,106.5579060000},{29.5399390000,106.5581210000}
                ,{29.5400140000,106.5583460000},{29.5400560000,106.5585610000},{29.5401030000,106.5588020000},{29.5401450000,106.5589950000}
                ,{29.5401820000,106.5592530000},{29.5402240000,106.5595320000},{29.5402660000,106.5597730000},{29.5402990000,106.5600520000}
                ,{29.5403410000,106.5602930000},{29.5403690000,106.5605400000},{29.5404060000,106.5608140000},{29.5404340000,106.5610500000}
                ,{29.5404760000,106.5613500000},{29.5405180000,106.5616400000},{29.5405510000,106.5618760000},{29.5405880000,106.5621710000}};

        for (int i = 0; i < NBL_POINTS.length; i++) {
            latLngs.add(new LatLng(NBL_POINTS[i][0],NBL_POINTS[i][1]));
        }
        Polylines.add(aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(6).color(Color.argb(Constants.DEFAULTCOLOR_A, Constants.DEFAULTCOLOR_R, Constants.DEFAULTCOLOR_G, Constants.DEFAULTCOLOR_B))));


        //重庆长江大桥
        latLngs.clear();
        double CQCJDQ_POINTS[][] = new double[][]{{29.5499120000,106.5625840000},{29.5496550000,106.5626270000},{29.5494360000,106.5626590000},{29.5492730000,106.5626810000}
                ,{29.5491230000,106.5627130000},{29.5489320000,106.5627400000},{29.5487310000,106.5627610000},{29.5485310000,106.5627830000}
                ,{29.5483070000,106.5628260000},{29.5480920000,106.5628530000},{29.5479190000,106.5628690000},{29.5477330000,106.5628950000}
                ,{29.5475690000,106.5629170000},{29.5473410000,106.5629440000},{29.5471030000,106.5629600000},{29.5468840000,106.5629920000}
                ,{29.5466880000,106.5630130000},{29.5464730000,106.5630350000},{29.5462910000,106.5630670000},{29.5460250000,106.5631100000}
                ,{29.5458100000,106.5631320000},{29.5454140000,106.5631740000},{29.5451380000,106.5632230000},{29.5448160000,106.5632390000}
                ,{29.5445600000,106.5632820000},{29.5443080000,106.5633190000},{29.5441020000,106.5633570000},{29.5439110000,106.5633840000}
                ,{29.5436870000,106.5633940000},{29.5435000000,106.5634210000},{29.5433180000,106.5634530000},{29.5431080000,106.5634750000}
                ,{29.5428140000,106.5635120000},{29.5425570000,106.5635550000},{29.5423750000,106.5635640000},{29.5421610000,106.5635910000}
                ,{29.5419600000,106.5636170000},{29.5416890000,106.5636550000},{29.5414140000,106.5636930000},{29.5410550000,106.5637250000}
                ,{29.5409010000,106.5637620000}
    };

        for (int i = 0; i < CQCJDQ_POINTS.length; i++) {
            latLngs.add(new LatLng(CQCJDQ_POINTS[i][0],CQCJDQ_POINTS[i][1]));
        }
        Polylines.add(aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(6).color(Color.argb(Constants.DEFAULTCOLOR_A, Constants.DEFAULTCOLOR_R, Constants.DEFAULTCOLOR_G, Constants.DEFAULTCOLOR_B))));

        //菜园坝大桥
        latLngs.clear();
        double CYBDQ_POINTS[][] = new double[][]{{29.5481990000,106.5510420000},{29.5481040000,106.5510400000},{29.5480380000,106.5510410000},{29.5479850000,106.5510400000}
                ,{29.5479360000,106.5510380000},{29.5478930000,106.5510380000},{29.5478520000,106.5510360000},{29.5478040000,106.5510360000}
                ,{29.5477610000,106.5510360000},{29.5477110000,106.5510320000},{29.5476560000,106.5510330000},{29.5476030000,106.5510330000}
                ,{29.5475710000,106.5510300000},{29.5475220000,106.5510360000},{29.5474730000,106.5510380000},{29.5474240000,106.5510400000}
                ,{29.5473710000,106.5510420000},{29.5473170000,106.5510450000},{29.5472640000,106.5510490000},{29.5472080000,106.5510530000}
                ,{29.5471550000,106.5510520000},{29.5470860000,106.5510520000},{29.5470350000,106.5510570000},{29.5469770000,106.5510570000}
                ,{29.5469160000,106.5510600000},{29.5468540000,106.5510600000},{29.5467960000,106.5510640000},{29.5467270000,106.5510650000}
                ,{29.5466570000,106.5510690000},{29.5465990000,106.5510710000},{29.5465310000,106.5510710000},{29.5464680000,106.5510720000}
                ,{29.5463940000,106.5510770000},{29.5463340000,106.5510770000},{29.5462650000,106.5510810000},{29.5461970000,106.5510830000}
                ,{29.5461130000,106.5510850000},{29.5460060000,106.5510880000},{29.5459360000,106.5510910000},{29.5458670000,106.5510960000}
                ,{29.5458040000,106.5510970000},{29.5457330000,106.5510990000},{29.5456760000,106.5511030000},{29.5456130000,106.5511070000}
                ,{29.5455490000,106.5511080000},{29.5454790000,106.5511120000},{29.5454200000,106.5511160000},{29.5453590000,106.5511180000}
                ,{29.5452900000,106.5511200000},{29.5452260000,106.5511220000},{29.5451470000,106.5511240000},{29.5450770000,106.5511270000}
                ,{29.5449990000,106.5511300000},{29.5449280000,106.5511320000},{29.5448600000,106.5511350000},{29.5447830000,106.5511380000}
                ,{29.5446800000,106.5511400000},{29.5444200000,106.5511500000},{29.5441120000,106.5511610000},{29.5439110000,106.5511710000}
                ,{29.5437430000,106.5511820000},{29.5435890000,106.5511980000},{29.5434820000,106.5512250000},{29.5433790000,106.5512570000}
                ,{29.5432950000,106.5512730000},{29.5431180000,106.5512840000},{29.5428380000,106.5512890000},{29.5424550000,106.5513000000}
                ,{29.5421190000,106.5513060000},{29.5418340000,106.5513110000},{29.5415310000,106.5513220000},{29.5411950000,106.5513380000}
                ,{29.5408080000,106.5513540000},{29.5404620000,106.5513650000},{29.5401540000,106.5513650000},{29.5398650000,106.5513810000}
                ,{29.5395570000,106.5513860000},{29.5392160000,106.5514020000},{29.5389030000,106.5514130000},{29.5385950000,106.5514240000}
                ,{29.5382500000,106.5514340000}

        };

        for (int i = 0; i < CYBDQ_POINTS.length; i++) {
            latLngs.add(new LatLng(CYBDQ_POINTS[i][0],CYBDQ_POINTS[i][1]));
        }
        Polylines.add(aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(6).color(Color.argb(Constants.DEFAULTCOLOR_A, Constants.DEFAULTCOLOR_R, Constants.DEFAULTCOLOR_G, Constants.DEFAULTCOLOR_B))));


        //真武山隧道
        latLngs.clear();
        double ZWSSD_POINTS[][] = new double[][]{{29.5123010000,106.6002590000},{29.5119980000,106.6007740000},{29.5117780000,106.6012080000},{29.5115350000,106.6016430000}
                ,{29.5112830000,106.6020990000},{29.5110640000,106.6024960000},{29.5108630000,106.6028390000},{29.5106060000,106.6033160000}
                ,{29.5102240000,106.6040080000},{29.5098730000,106.6046410000},{29.5095090000,106.6053170000},{29.5091960000,106.6058640000}
                ,{29.5088790000,106.6064220000},{29.5085850000,106.6069270000},{29.5082580000,106.6075110000},{29.5079410000,106.6080690000}
                ,{29.5075810000,106.6087130000},{29.5072450000,106.6092870000},{29.5068150000,106.6100430000},{29.5064700000,106.6106500000}
                ,{29.5060970000,106.6112930000},{29.5057510000,106.6119160000},{29.5053500000,106.6125970000},{29.5049710000,106.6132570000}
                ,{29.5047050000,106.6137230000},{29.5043840000,106.6143000000},{29.5041180000,106.6147400000},{29.5038190000,106.6152710000}
                ,{29.5034690000,106.6158770000},{29.5031330000,106.6164940000},{29.5028780000,106.6169350000}
        };

        for (int i = 0; i < ZWSSD_POINTS.length; i++) {
            latLngs.add(new LatLng(ZWSSD_POINTS[i][0],ZWSSD_POINTS[i][1]));
        }
        Polylines.add(aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(6).color(Color.argb(Constants.DEFAULTCOLOR_A, Constants.DEFAULTCOLOR_R, Constants.DEFAULTCOLOR_G, Constants.DEFAULTCOLOR_B))));


        //腾龙大道
        latLngs.clear();
        double TLDD_POINTS[][] = new double[][]{{29.6019969414,106.6015863419},{29.6016471227,106.6010284424},{29.6012739813,106.6004651785},{29.6008495314,106.5998536348},
                {29.6005183659,106.5994030237},{29.6000612625,106.5988183022},{29.5998047239,106.5985232592},{29.5994315757,106.5982067585},
                {29.5987552412,106.5976756811},{29.5981861838,106.5972840786},{29.5978410163,106.5971016884},{29.5971973223,106.5968710184},
                {29.5967122169,106.5967315435},{29.5960638512,106.5965652466},
                {29.5950800000,106.5962840000},{29.5949400000,106.5962460000},{29.5948180000,106.5962250000},{29.5946230000,106.5961760000}
                ,{29.5942960000,106.5960960000},{29.5939370000,106.5960210000},{29.5936200000,106.5959460000},{29.5932700000,106.5958710000}
                ,{29.5927800000,106.5957530000},{29.5924020000,106.5956610000},{29.5920990000,106.5955970000},{29.5917400000,106.5954950000}
                ,{29.5914460000,106.5954200000},{29.5911570000,106.5953390000},{29.5908860000,106.5952750000}
        };

        for (int i = 0; i < TLDD_POINTS.length; i++) {
            latLngs.add(new LatLng(TLDD_POINTS[i][0],TLDD_POINTS[i][1]));
        }
        Polylines.add(aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(6).color(Color.argb(Constants.DEFAULTCOLOR_A, Constants.DEFAULTCOLOR_R, Constants.DEFAULTCOLOR_G, Constants.DEFAULTCOLOR_B))));

        //通江大道
        latLngs.clear();
        double TJDD_POINTS[][] = new double[][]{{29.5446990000,106.6786810000},{29.5442460000,106.6784240000},{29.5437100000,106.6781340000},{29.5431030000,106.6777910000}
                ,{29.5426220000,106.6775280000},{29.5421320000,106.6772650000},{29.5415350000,106.6769320000},{29.5409890000,106.6766270000}
                ,{29.5407040000,106.6764600000},{29.5401950000,106.6762130000},{29.5400640000,106.6761280000},{29.5392290000,106.6756820000}
                ,{29.5386880000,106.6753770000},{29.5381460000,106.6750390000},{29.5377450000,106.6748030000},{29.5372780000,106.6744810000}
                ,{29.5368630000,106.6741700000},{29.5364710000,106.6738850000},{29.5359990000,106.6735210000},{29.5354110000,106.6730750000}
                ,{29.5351640000,106.6728770000},{29.5348650000,106.6726140000},{29.5345520000,106.6723620000},{29.5342630000,106.6721200000}
                ,{29.5339460000,106.6718740000}
        };

        for (int i = 0; i < TJDD_POINTS.length; i++) {
            latLngs.add(new LatLng(TJDD_POINTS[i][0],TJDD_POINTS[i][1]));
        }
        Polylines.add(aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(6).color(Color.argb(Constants.DEFAULTCOLOR_A, Constants.DEFAULTCOLOR_R, Constants.DEFAULTCOLOR_G, Constants.DEFAULTCOLOR_B))));


        //丁香路
        latLngs.clear();
        double JXL_POINTS[][] = new double[][]{{29.6024890000,106.5509770000},{29.6024840000,106.5512000000},{29.6024840000,106.5513470000},{29.6024820000,106.5515270000}
                ,{29.6024780000,106.5517160000},{29.6024760000,106.5518710000},{29.6024750000,106.5520830000},{29.6024710000,106.5522830000}
                ,{29.6024740000,106.5524490000},{29.6024700000,106.5525660000},{29.6024680000,106.5526410000},{29.6024620000,106.5528920000}
                ,{29.6024600000,106.5531360000},{29.6024530000,106.5533320000},{29.6024490000,106.5536450000},{29.6024530000,106.5536660000}
                ,{29.6024460000,106.5539590000},{29.6024410000,106.5542060000},{29.6024350000,106.5544500000},{29.6024320000,106.5547020000}
                ,{29.6024260000,106.5550150000},{29.6024220000,106.5550320000},{29.6024190000,106.5552820000},{29.6024160000,106.5554840000}
                ,{29.6024150000,106.5558820000},{29.6024110000,106.5563920000},{29.6024160000,106.5564130000},{29.6024110000,106.5566390000}
                ,{29.6024080000,106.5570910000},{29.6024050000,106.5576580000},{29.6024060000,106.5579680000},{29.6024060000,106.5583450000}
                ,{29.6024110000,106.5590850000},{29.6024060000,106.5598520000},{29.6024060000,106.5602570000},{29.6024070000,106.5605390000}
        };

        for (int i = 0; i < JXL_POINTS.length; i++) {
            latLngs.add(new LatLng(JXL_POINTS[i][0],JXL_POINTS[i][1]));
        }
        Polylines.add(aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(6).color(Color.argb(Constants.DEFAULTCOLOR_A, Constants.DEFAULTCOLOR_R, Constants.DEFAULTCOLOR_G, Constants.DEFAULTCOLOR_B))));

        //北滨二路
        latLngs.clear();
        double BBEL_POINTS[][] = new double[][]{{29.5865690000,106.5748890000},{29.5855800000,106.5749700000},{29.5851840000,106.5750180000},{29.5850180000,106.5750300000},{29.5843700000,106.5750830000},{29.5838420000,106.5751320000},{29.5833060000,106.5751750000}
                ,{29.5825360000,106.5752440000},{29.5814590000,106.5753410000},{29.5806940000,106.5754160000},{29.5798210000,106.5754910000}
                ,{29.5795400000,106.5755180000},{29.5793680000,106.5755450000},{29.5792180000,106.5755770000},{29.5789900000,106.5756360000}
                ,{29.5787290000,106.5757110000},{29.5785960000,106.5757530000},{29.5784190000,106.5758340000},{29.5781860000,106.5759730000}
                ,{29.5773270000,106.5764830000},{29.5770940000,106.5766060000},{29.5768280000,106.5767190000},{29.5764410000,106.5768370000}
                ,{29.5762400000,106.5768640000}

    };

        for (int i = 0; i < BBEL_POINTS.length; i++) {
            latLngs.add(new LatLng(BBEL_POINTS[i][0],BBEL_POINTS[i][1]));
        }
        Polylines.add(aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(6).color(Color.argb(Constants.DEFAULTCOLOR_A, Constants.DEFAULTCOLOR_R, Constants.DEFAULTCOLOR_G, Constants.DEFAULTCOLOR_B))));

        //北滨一路
        latLngs.clear();
        double BBYL_POINTS[][] = new double[][]{{29.5767740000,106.4696080000},{29.5758780000,106.4706490000},{29.5752250000,106.4713570000},{29.5746470000,106.4719100000}
                ,{29.5743810000,106.4721460000},{29.5737090000,106.4727390000},{29.5729990000,106.4733400000},{29.5723700000,106.4738600000}
                ,{29.5715480000,106.4745200000},{29.5708110000,106.4751100000},{29.5698500000,106.4759040000},{29.5691130000,106.4765580000}
                ,{29.5682640000,106.4773150000},{29.5677370000,106.4778140000},{29.5670510000,106.4784520000},{29.5666500000,106.4788110000}
                ,{29.5661930000,106.4792240000}
        };

        for (int i = 0; i < BBYL_POINTS.length; i++) {
            latLngs.add(new LatLng(BBYL_POINTS[i][0],BBYL_POINTS[i][1]));
        }
        Polylines.add(aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(6).color(Color.argb(Constants.DEFAULTCOLOR_A, Constants.DEFAULTCOLOR_R, Constants.DEFAULTCOLOR_G, Constants.DEFAULTCOLOR_B))));

        //渝鲁大道
        latLngs.clear();
        double YLDD_POINTS[][] = new double[][]{{29.6095410000,106.5619150000},{29.6092990000,106.5618770000},{29.6090140000,106.5618400000},{29.6083430000,106.5617170000}
                ,{29.6075500000,106.5615720000},{29.6062860000,106.5613570000},{29.6049330000,106.5611160000},{29.6035810000,106.5608900000}
                ,{29.6023630000,106.5606810000},{29.6013280000,106.5604990000},{29.6009080000,106.5604240000},{29.5996770000,106.5602090000}
                ,{29.5985150000,106.5600110000},{29.5973400000,106.5598070000},{29.5962160000,106.5596080000},{29.5959550000,106.5595760000}
                ,{29.5949280000,106.5593880000},{29.5941820000,106.5592650000},{29.5937020000,106.5591850000},{29.5933380000,106.5591200000}
                ,{29.5929550000,106.5590770000},{29.5925910000,106.5590560000},{29.5921570000,106.5590590000},{29.5917700000,106.5590910000}

        };

        for (int i = 0; i < YLDD_POINTS.length; i++) {
            latLngs.add(new LatLng(YLDD_POINTS[i][0],YLDD_POINTS[i][1]));
        }
        Polylines.add(aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(6).color(Color.argb(Constants.DEFAULTCOLOR_A, Constants.DEFAULTCOLOR_R, Constants.DEFAULTCOLOR_G, Constants.DEFAULTCOLOR_B))));
        //海尔路
        latLngs.clear();
        double HEL_POINTS[][] = new double[][]{{29.6006540000,106.6515710000},{29.6006920000,106.6518500000},{29.6007190000,106.6520270000},{29.6007660000,106.6522310000}
                ,{29.6008130000,106.6524880000},{29.6009200000,106.6528000000},{29.6010650000,106.6531640000}
                ,{29.6013630000,106.6537540000},{29.6018950000,106.6544730000},{29.6024730000,106.6551920000},{29.6027390000,106.6555520000}
                ,{29.6032240000,106.6561360000},{29.6037650000,106.6568180000},{29.6045490000,106.6577990000},{29.6050150000,106.6583840000}
                ,{29.6056820000,106.6592150000},{29.6064330000,106.6601540000},{29.6069970000,106.6608570000},{29.6075200000,106.6615170000}
                ,{29.6077290000,106.6617530000},{29.6080610000,106.6621930000},{29.6084010000,106.6626330000},{29.6087040000,106.6630940000 }
                ,{29.6089890000,106.6636040000},{29.6092030000,106.6640380000},{29.6094550000,106.6645530000},{29.6096460000,106.6649120000}
                ,{29.6098800000,106.6653850000},{29.6101410000,106.6659210000},{29.6103460000,106.6663130000},{29.6104630000,106.6665160000}
        };

        for (int i = 0; i < HEL_POINTS.length; i++) {
            latLngs.add(new LatLng(HEL_POINTS[i][0],HEL_POINTS[i][1]));
        }
        Polylines.add(aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(6).color(Color.argb(Constants.DEFAULTCOLOR_A, Constants.DEFAULTCOLOR_R, Constants.DEFAULTCOLOR_G, Constants.DEFAULTCOLOR_B))));

        //沙滨路
        latLngs.clear();
        double SBL_POINTS[][] = new double[][]{{29.5739520000,106.4668620000},{29.5734380000,106.4674520000},{29.5728230000,106.4681490000},{29.5722350000,106.4688250000}
                ,{29.5717400000,106.4693880000},{29.5712830000,106.4698820000},{29.5710540000,106.4701130000},{29.5706530000,106.4704830000}
                ,{29.5701540000,106.4708690000},{29.5695890000,106.4713090000},{29.5690340000,106.4717380000},{29.5684230000,106.4721890000}
                ,{29.5678020000,106.4726710000},{29.5672190000,106.4731170000},{29.5665280000,106.4736370000},{29.5658010000,106.4742060000}
                ,{29.5650870000,106.4748120000},{29.5643630000,106.4754720000},{29.5637430000,106.4760560000},{29.5632340000,106.4765440000}
                ,{29.5627540000,106.4769900000},{29.5621240000,106.4775960000},{29.5614890000,106.4782180000},{29.5610600000,106.4786200000}
                ,{29.5608360000,106.4788400000}
        };

        for (int i = 0; i < SBL_POINTS.length; i++) {
            latLngs.add(new LatLng(SBL_POINTS[i][0],SBL_POINTS[i][1]));
        }
        Polylines.add(aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(6).color(Color.argb(Constants.DEFAULTCOLOR_A, Constants.DEFAULTCOLOR_R, Constants.DEFAULTCOLOR_G, Constants.DEFAULTCOLOR_B))));

        //经纬大道
        latLngs.clear();
        double JWDD_POINTS[][] = new double[][]{{29.5440180000,106.4812280000},{29.5441620000,106.4817750000},{29.5443120000,106.4823590000},{29.5445170000,106.4831320000}
                ,{29.5446850000,106.4838160000},{29.5447690000,106.4842670000},{29.5448810000,106.4850660000},{29.5449930000,106.4857470000}
                ,{29.5451890000,106.4870670000},{29.5452870000,106.4876950000},{29.5454310000,106.4885530000},{29.5455670000,106.4893580000}
                ,{29.5456970000,106.4901520000},{29.5458370000,106.4909830000},{29.5460570000,106.4922280000},{29.5461970000,106.4930750000}
                ,{29.5462850000,106.4936550000}
        };

        for (int i = 0; i < JWDD_POINTS.length; i++) {
            latLngs.add(new LatLng(JWDD_POINTS[i][0],JWDD_POINTS[i][1]));
        }
        Polylines.add(aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(6).color(Color.argb(Constants.DEFAULTCOLOR_A, Constants.DEFAULTCOLOR_R, Constants.DEFAULTCOLOR_G, Constants.DEFAULTCOLOR_B))));

        //紫荆路
        latLngs.clear();
        double ZJL_POINTS[][] = new double[][]{{29.5986810000,106.5324660000},{29.5980700000,106.5324660000},{29.5975800000,106.5324600000},{29.5968430000,106.5324600000}
                ,{29.5963110000,106.5324660000},{29.5954250000,106.5324550000},{29.5947620000,106.5324440000},{29.5939790000,106.5324340000}
                ,{29.5932040000,106.5324280000},{29.5924530000,106.5324340000},{29.5917720000,106.5324230000},{29.5912130000,106.5324280000}
                ,{29.5906760000,106.5324280000},{29.5901210000,106.5324180000},{29.5898740000,106.5324280000},{29.5896270000,106.5324660000}
                ,{29.5893420000,106.5325190000},{29.5891410000,106.5325730000},{29.5889780000,106.5326430000},{29.5887960000,106.5327610000}
                ,{29.5884140000,106.5330400000},{29.5881340000,106.5332810000},{29.5879430000,106.5334740000},{29.5877650000,106.5336890000}
                ,{29.5875830000,106.5338980000},{29.5872800000,106.5342310000},{29.5870700000,106.5344770000},{29.5868180000,106.5347560000}
                ,{29.5866130000,106.5349980000},{29.5864770000,106.5352230000},{29.5863700000,106.5354220000},{29.5862680000,106.5355930000}
                ,{29.5861930000,106.5357760000}
        };

        for (int i = 0; i < ZJL_POINTS.length; i++) {
            latLngs.add(new LatLng(ZJL_POINTS[i][0],ZJL_POINTS[i][1]));
        }
        Polylines.add(aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(6).color(Color.argb(Constants.DEFAULTCOLOR_A, Constants.DEFAULTCOLOR_R, Constants.DEFAULTCOLOR_G, Constants.DEFAULTCOLOR_B))));
        //金龙路
        latLngs.clear();
        double JLL_POINTS[][] = new double[][]{{29.5876580000,106.5137470000},{29.5878070000,106.5139670000},{29.5880830000,106.5143690000},{29.5883670000,106.5148090000}
                ,{29.5885440000,106.5150820000},{29.5887870000,106.5154530000},{29.5890580000,106.5158760000},{29.5893190000,106.5162840000}
                ,{29.5893370000,106.5163480000},{29.5894820000,106.5165950000},{29.5896870000,106.5169220000},{29.5898230000,106.5171260000}
                ,{29.5899020000,106.5173620000},{29.5900830000,106.5176650000},{29.5902750000,106.5179410000},{29.5905170000,106.5183330000}
                ,{29.5909230000,106.5189660000},{29.5911800000,106.5193840000},{29.5913200000,106.5196150000},{29.5915900000,106.5201650000}
                ,{29.5917490000,106.5205030000},{29.5920429580,106.5211147070},{29.5923414985,106.5217262506},{29.5924301276,106.5217959881}
                ,{29.5926540322,106.5223377943},{29.5928732716,106.5230727196},{29.5930691873,106.5237540007},{29.5931951329,106.5242260695}
                ,{29.5933583955,106.5248215199},{29.5933397369,106.5249288082},{29.5934796761,106.5255993605},{29.5935216578,106.5257227421}
        };

        for (int i = 0; i < JLL_POINTS.length; i++) {
            latLngs.add(new LatLng(JLL_POINTS[i][0],JLL_POINTS[i][1]));
        }
        Polylines.add(aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(6).color(Color.argb(Constants.DEFAULTCOLOR_A, Constants.DEFAULTCOLOR_R, Constants.DEFAULTCOLOR_G, Constants.DEFAULTCOLOR_B))));
        //巴县大道
        latLngs.clear();
        double BXDD_POINTS[][] = new double[][]{{29.3854096906,106.5128803253},{29.3853440000,106.5137360000},{29.3852980000,106.5142990000},{29.3852370000,106.5150340000}
                ,{29.3851990000,106.5155380000},{29.3851570000,106.5159780000},{29.3851110000,106.5162840000},{29.3850500000,106.5165790000}
                ,{29.3849610000,106.5168470000},{29.3848720000,106.5170190000},{29.3845075611,106.5176546574},{29.3842317790,106.5180945396}
                ,{29.3836334694,106.5189957619},{29.3832595242,106.5195751190},{29.3828435084,106.5201705694},{29.3823807474,106.5208357573}
                ,{29.3821423545,106.5212059021},{29.3815814279,106.5221822262},{29.3811794286,106.5230512619},{29.3809129863,106.5235233307}
                ,{29.3805390311,106.5241295099},{29.3804455420,106.5242797136},{29.3803473785,106.5243333578},{29.3801931212,106.5248805285}
                ,{29.3801323532,106.5251755714},{29.3800575616,106.5257602930},{29.3799967935,106.5264254808},{29.3799360253,106.5270316601}
                ,{29.3798892805,106.5272086859}
        };

        for (int i = 0; i < BXDD_POINTS.length; i++) {
            latLngs.add(new LatLng(BXDD_POINTS[i][0],BXDD_POINTS[i][1]));
        }
        Polylines.add(aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(6).color(Color.argb(Constants.DEFAULTCOLOR_A, Constants.DEFAULTCOLOR_R, Constants.DEFAULTCOLOR_G, Constants.DEFAULTCOLOR_B))));

        //大江东路
        latLngs.clear();
        double DJDL_POINTS[][] = new double[][]{{29.3909625190,106.5129232407},{29.3904717509,106.5126281977},{29.3897846716,106.5122312307},{29.3892424833,106.5119361877}
                ,{29.3888218180,106.5116840601},{29.3880926607,106.5113031864},{29.3875457893,106.5110081434},{29.3869147801,106.5106540918}
                ,{29.3862510477,106.5102088451},{29.3859705961,106.5100210905},{29.3856620985,106.5099084377},{29.3854096906,106.5099084377}
                ,{29.3848721533,106.5099084377},{29.3841523162,106.5099138021},{29.3830445050,106.5098977089},{29.3822451907,106.5099138021}
                ,{29.3818338453,106.5100425482},{29.3813383587,106.5102893114},{29.3810111493,106.5104395151}
        };

        for (int i = 0; i < DJDL_POINTS.length; i++) {
            latLngs.add(new LatLng(DJDL_POINTS[i][0],DJDL_POINTS[i][1]));
        }
        Polylines.add(aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(6).color(Color.argb(Constants.DEFAULTCOLOR_A, Constants.DEFAULTCOLOR_R, Constants.DEFAULTCOLOR_G, Constants.DEFAULTCOLOR_B))));

        //朝天门大桥
        latLngs.clear();
        double CTMDQ_POINTS[][] = new double[][]{{29.5860579676,106.5876227617},{29.5860486376,106.5871399641},{29.5860533026,106.5870058537},{29.5859600030,106.5856754780}
                ,{29.5858433783,106.5843558311},{29.5857174235,106.5824943781},{29.5855914686,106.5806436539},{29.5855168286,106.5795761347}
                ,{29.5854235285,106.5783262253},{29.5853162332,106.5766900778},{29.5852275979,106.5755581856},{29.5851949428,106.5750163794}
                ,{29.5851156374,106.5741688013},{29.5850596572,106.5731817484},{29.5850036768,106.5721946955}
        };

        for (int i = 0; i < CTMDQ_POINTS.length; i++) {
            latLngs.add(new LatLng(CTMDQ_POINTS[i][0],CTMDQ_POINTS[i][1]));
        }
        Polylines.add(aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(6).color(Color.argb(Constants.DEFAULTCOLOR_A, Constants.DEFAULTCOLOR_R, Constants.DEFAULTCOLOR_G, Constants.DEFAULTCOLOR_B))));

        return Polylines;
    }

    private void changeLinesWid() {
        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                float perPixel = aMap.getScalePerPixel();
                Log.e(TAG, "onTouch: "+perPixel);
                if (0.175 < perPixel && perPixel<0.342){
                    setLinesWid(7);
                }
                if (0.349 < perPixel && perPixel<0.665){
                    setLinesWid(6);
                }
                if (0.697 < perPixel && perPixel<1.376){
                    setLinesWid(5);
                }

                if (1.387 < perPixel && perPixel<2.731){
                    setLinesWid(4);
                }
                if (2.789 < perPixel && perPixel<5.434){
                    setLinesWid(3);
                }
            }
        });
    }

    private void setLinesWid(int width) {
        for (int i = 0; i < polylines.size(); i++) {
            polylines.get(i).setWidth(width);
        }
        aMap.invalidate();
    }


    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update) {

//        aMap.moveCamera(update);
        aMap.animateCamera(update);
    }

    private void roadButtonLintenser() {
        button_road1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllBack();
                button_road1.setTextSize(Constants.ButtonTextAfter);
                button_road1.setTextColor(Color.BLACK);
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                Constants.CWL, Constants.ZOOMLEVEL, 0, 0)));

            }
        });
        button_road2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllBack();
                button_road2.setTextColor(Color.BLACK);
                button_road2.setTextSize(Constants.ButtonTextAfter);
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                Constants.DSMDQ, Constants.ZOOMLEVEL, 30, 30)));

            }
        });
        button_road3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllBack();
                button_road3.setTextColor(Color.BLACK);
                button_road3.setTextSize(Constants.ButtonTextAfter);
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                Constants.NBL, Constants.ZOOMLEVEL, 30, 30)));

            }
        });
        button_road4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllBack();
                button_road4.setTextColor(Color.BLACK);
                button_road4.setTextSize(Constants.ButtonTextAfter);
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                Constants.CQCJDQ, Constants.ZOOMLEVEL, 30, 30)));

            }
        });
        button_road5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllBack();
                button_road5.setTextColor(Color.BLACK);
                button_road5.setTextSize(Constants.ButtonTextAfter);
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                Constants.CYBDQ, Constants.ZOOMLEVEL, 30, 30)));

            }
        });
        button_road6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllBack();
                button_road6.setTextColor(Color.BLACK);
                button_road6.setTextSize(Constants.ButtonTextAfter);
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                Constants.ZWSSD, Constants.ZOOMLEVEL, 30, 30)));

            }
        });
        button_road7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllBack();
                button_road7.setTextColor(Color.BLACK);
                button_road7.setTextSize(Constants.ButtonTextAfter);
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                Constants.TLDD, Constants.ZOOMLEVEL, 30, 30)));

            }
        });
        button_road8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllBack();
                button_road8.setTextColor(Color.BLACK);
                button_road8.setTextSize(Constants.ButtonTextAfter);
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                Constants.TJDD, Constants.ZOOMLEVEL, 30, 30)));

            }
        });
        button_road9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllBack();
                button_road9.setTextColor(Color.BLACK);
                button_road9.setTextSize(Constants.ButtonTextAfter);
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                Constants.DXL, Constants.ZOOMLEVEL, 30, 30)));

            }
        });
        button_road10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllBack();
                button_road10.setTextColor(Color.BLACK);
                button_road10.setTextSize(Constants.ButtonTextAfter);
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                Constants.BBEL, Constants.ZOOMLEVEL, 30, 30)));


            }
        });
        button_road11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllBack();
                button_road11.setTextColor(Color.BLACK);
                button_road11.setTextSize(Constants.ButtonTextAfter);
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                Constants.BBYL, Constants.ZOOMLEVEL, 30, 30)));

            }
        });
        button_road12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllBack();
                button_road12.setTextColor(Color.BLACK);
                button_road12.setTextSize(Constants.ButtonTextAfter);
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                Constants.YLDD, Constants.ZOOMLEVEL, 30, 30)));

            }
        });
        button_road13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllBack();
                button_road13.setTextColor(Color.BLACK);
                button_road13.setTextSize(Constants.ButtonTextAfter);
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                Constants.HEL, Constants.ZOOMLEVEL, 30, 30)));

            }
        });
        button_road14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllBack();
                button_road14.setTextColor(Color.BLACK);
                button_road14.setTextSize(Constants.ButtonTextAfter);
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                Constants.SBL, Constants.ZOOMLEVEL, 30, 30)));

            }
        });
        button_road15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllBack();
                button_road15.setTextColor(Color.BLACK);
                button_road15.setTextSize(Constants.ButtonTextAfter);
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                Constants.JWDD, Constants.ZOOMLEVEL, 30, 30)));

            }
        });
        button_road16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllBack();
                button_road16.setTextColor(Color.BLACK);
                button_road16.setTextSize(Constants.ButtonTextAfter);
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                Constants.ZJL, Constants.ZOOMLEVEL, 30, 30)));

            }
        });
        button_road17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllBack();
                button_road17.setTextColor(Color.BLACK);
                button_road17.setTextSize(Constants.ButtonTextAfter);
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                Constants.JLL, Constants.ZOOMLEVEL, 30, 30)));

            }
        });
        button_road18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllBack();
                button_road18.setTextColor(Color.BLACK);
                button_road18.setTextSize(Constants.ButtonTextAfter);
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                Constants.BXDD, Constants.ZOOMLEVEL, 30, 30)));

            }
        });
        button_road19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllBack();
                button_road19.setTextColor(Color.BLACK);
                button_road19.setTextSize(Constants.ButtonTextAfter);
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                Constants.DJDL, Constants.ZOOMLEVEL, 30, 30)));

            }
        });
        button_road20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllBack();
                button_road20.setTextColor(Color.BLACK);
                button_road20.setTextSize(Constants.ButtonTextAfter);
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                Constants.CTMDQ, Constants.ZOOMLEVEL, 30, 30)));

            }
        });
    }


    private void setAllBack(){
        button_road1.setTextSize(Constants.ButtonTextBefore);
        button_road2.setTextSize(Constants.ButtonTextBefore);
        button_road3.setTextSize(Constants.ButtonTextBefore);
        button_road4.setTextSize(Constants.ButtonTextBefore);
        button_road5.setTextSize(Constants.ButtonTextBefore);
        button_road6.setTextSize(Constants.ButtonTextBefore);
        button_road7.setTextSize(Constants.ButtonTextBefore);
        button_road8.setTextSize(Constants.ButtonTextBefore);
        button_road9.setTextSize(Constants.ButtonTextBefore);
        button_road10.setTextSize(Constants.ButtonTextBefore);
        button_road11.setTextSize(Constants.ButtonTextBefore);
        button_road12.setTextSize(Constants.ButtonTextBefore);
        button_road13.setTextSize(Constants.ButtonTextBefore);
        button_road14.setTextSize(Constants.ButtonTextBefore);
        button_road15.setTextSize(Constants.ButtonTextBefore);
        button_road16.setTextSize(Constants.ButtonTextBefore);
        button_road17.setTextSize(Constants.ButtonTextBefore);
        button_road18.setTextSize(Constants.ButtonTextBefore);
        button_road19.setTextSize(Constants.ButtonTextBefore);
        button_road20.setTextSize(Constants.ButtonTextBefore);

        button_road1.setTextColor(Color.GRAY);
        button_road2.setTextColor(Color.GRAY);
        button_road3.setTextColor(Color.GRAY);
        button_road4.setTextColor(Color.GRAY);
        button_road5.setTextColor(Color.GRAY);
        button_road6.setTextColor(Color.GRAY);
        button_road7.setTextColor(Color.GRAY);
        button_road8.setTextColor(Color.GRAY);
        button_road9.setTextColor(Color.GRAY);
        button_road10.setTextColor(Color.GRAY);
        button_road11.setTextColor(Color.GRAY);
        button_road12.setTextColor(Color.GRAY);
        button_road13.setTextColor(Color.GRAY);
        button_road14.setTextColor(Color.GRAY);
        button_road15.setTextColor(Color.GRAY);
        button_road16.setTextColor(Color.GRAY);
        button_road17.setTextColor(Color.GRAY);
        button_road18.setTextColor(Color.GRAY);
        button_road19.setTextColor(Color.GRAY);
        button_road20.setTextColor(Color.GRAY);
    }

    /**
     * 设置地图类型选择时的选择框的隐藏与显示
     */
    private void mapTypeChooseLintenser() {
        rode_TypeA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rode_TypeA_Bg.setVisibility(View.VISIBLE);
                rode_TypeB_Bg.setVisibility(View.INVISIBLE);
                aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模式
            }
        });

        rode_TypeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rode_TypeA_Bg.setVisibility(View.INVISIBLE);
                rode_TypeB_Bg.setVisibility(View.VISIBLE);
                aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 卫星地图模式
            }
        });
    }

    private void init() {
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.getMap();
            muiSettings= aMap.getUiSettings();
            muiSettings.setScaleControlsEnabled(true);
        }
        muiSettings.setZoomControlsEnabled(false);
        muiSettings.setCompassEnabled(true);
        marker20Points();

    }

    private void marker20Points() {
        aMap.addMarker(new MarkerOptions().position(Constants.CWL)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        aMap.addMarker(new MarkerOptions().position(Constants.DSMDQ)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        aMap.addMarker(new MarkerOptions().position(Constants.NBL)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        aMap.addMarker(new MarkerOptions().position(Constants.CQCJDQ)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        aMap.addMarker(new MarkerOptions().position(Constants.CYBDQ)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        aMap.addMarker(new MarkerOptions().position(Constants.ZWSSD)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        aMap.addMarker(new MarkerOptions().position(Constants.TLDD)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        aMap.addMarker(new MarkerOptions().position(Constants.TJDD)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        aMap.addMarker(new MarkerOptions().position(Constants.DXL)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        aMap.addMarker(new MarkerOptions().position(Constants.BBEL)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        aMap.addMarker(new MarkerOptions().position(Constants.BBYL)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        aMap.addMarker(new MarkerOptions().position(Constants.YLDD)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        aMap.addMarker(new MarkerOptions().position(Constants.HEL)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        aMap.addMarker(new MarkerOptions().position(Constants.SBL)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        aMap.addMarker(new MarkerOptions().position(Constants.JWDD)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        aMap.addMarker(new MarkerOptions().position(Constants.ZJL)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        aMap.addMarker(new MarkerOptions().position(Constants.JLL)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        aMap.addMarker(new MarkerOptions().position(Constants.BXDD)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        aMap.addMarker(new MarkerOptions().position(Constants.DJDL)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        aMap.addMarker(new MarkerOptions().position(Constants.CTMDQ)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }

    public void initLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);

        //设置菜单内容之外其他区域的背景色
        drawerLayout.setScrimColor(Color.TRANSPARENT);

        //右边菜单
        main_right_drawer_layout = (LinearLayout) findViewById(R.id.main_right_drawer_layout);

    }


    // 右边菜单开关事件
    public void openRightLayout(View view) {
        if (drawerLayout.isDrawerOpen(main_right_drawer_layout)) {
            drawerLayout.closeDrawer(main_right_drawer_layout);
        } else {
            drawerLayout.openDrawer(main_right_drawer_layout);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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
                Log.e(TAG, "Main2Activity_onMoonEvent: "+data);
//                Toast.makeText(this,"HomeActivity_onMoonEvent: "+data,Toast.LENGTH_LONG).show();

                ArrayList<String[]> list_ones = new ArrayList<>();
                String[] datas = data.split("&");
                Log.e(TAG, "datas长度: "+datas.length);
                for (int i = 0; i < datas.length; i++) {
                    String[] data_one = datas[i].split("_");
                    list_ones.add(data_one);
//                    Log.e(TAG, "数据拆分"+ list_ones.get(i)[0]+"======="+list_ones.get(i)[1]);
                }

                drowColorLines(list_ones);
                aMap.invalidate();
            }
        }


        if (message == Constants.LOGO_POSITION_CHANGED_LEFT){
            Log.e(TAG, "onMoonEvent: logo左边" );
            muiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_LEFT);
        }
        if (message == Constants.LOGO_POSITION_CHANGED_MIDDLE){
            Log.e(TAG, "onMoonEvent: logo中间" );
            muiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_CENTER);
        }
        if (message == Constants.LOGO_POSITION_CHANGED_RIGHT){
            Log.e(TAG, "onMoonEvent: logo右边" );
            muiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);
        }

        if (message == Constants.ZOOMSWITCH_CHANGED_OPEN){
            Log.e(TAG, "onMoonEvent: 缩放改变" );
            muiSettings.setZoomGesturesEnabled(true);
        }
        if (message == Constants.ZOOMSWITCH_CHANGED_CLOSE){
            Log.e(TAG, "onMoonEvent: 缩放改变" );
            muiSettings.setZoomGesturesEnabled(false);
        }
        if (message == Constants.COMPASS_SWITCH_CHANGED_OPEN){
            Log.e(TAG, "onMoonEvent: 指南针改变" );
            muiSettings.setCompassEnabled(true);
        }
        if (message == Constants.COMPASS_SWITCH_CHANGED_CLOSE){
            Log.e(TAG, "onMoonEvent: 指南针改变" );
            muiSettings.setCompassEnabled(false);
        }
        if (message == Constants.LANGUAGE_SWITCH_CHANGED_OPEN){
            Log.e(TAG, "onMoonEvent: 语言改变" );
            aMap.setMapLanguage(AMap.ENGLISH);
        }
        if (message == Constants.LANGUAGE_SWITCH_CHANGED_CLOSE){
            Log.e(TAG, "onMoonEvent: 语言改变" );
            aMap.setMapLanguage(AMap.CHINESE);
        }

        if (message == Constants.SCALE_SWITCH_CHANGED_OPEN){
            Log.e(TAG, "onMoonEvent: 比例尺改变");
            muiSettings.setScaleControlsEnabled(true);//控制比例尺控件是否显示
        }
        if (message == Constants.SCALE_SWITCH_CHANGED_CLOSE){
            Log.e(TAG, "onMoonEvent: 比例尺改变");
            muiSettings.setScaleControlsEnabled(false);//控制比例尺控件是否显示
        }
        if (message == Constants.SWIPE_SWITCH_CHANGED_OPEN){
            Log.e(TAG, "onMoonEvent: 滑动改变" );
            muiSettings.setScrollGesturesEnabled(true);
        }
        if (message == Constants.SWIPE_SWITCH_CHANGED_CLOSE){
            Log.e(TAG, "onMoonEvent: 滑动改变" );
            muiSettings.setScrollGesturesEnabled(false);
        }
        aMap.invalidate();
    }

    private void drowColorLines(ArrayList<String[]> list_ones) {

        for (int i = 0; i < list_ones.size(); i++) {
            String[] road_data = list_ones.get(i);
            String cars_number = road_data[1];
            Polyline polyline = polylines.get(i);
            if (Integer.valueOf(cars_number) >= 255){
                polyline.setColor(Color.argb(255,255,0,0));
            }else {
                polyline.setColor(Color.argb(255,Integer.valueOf(cars_number),0,255-Integer.valueOf(cars_number)));
                Log.e(TAG, "drowColorLines: 修改了颜色");
            }
        }

    }
}
