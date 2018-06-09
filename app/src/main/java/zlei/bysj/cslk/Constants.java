package zlei.bysj.cslk;

import com.amap.api.maps2d.model.LatLng;

public class Constants {

	public static final LatLng BEIJING = new LatLng(39.90403, 116.407525);// 北京市经纬度
	public static final LatLng ZHONGGUANCUN = new LatLng(39.983456, 116.3154950);// 北京市中关村经纬度
	public static final LatLng SHANGHAI = new LatLng(31.238068, 121.501654);// 上海市经纬度
	public static final LatLng FANGHENG = new LatLng(39.989614, 116.481763);// 方恒国际中心经纬度
	public static final LatLng CHENGDU = new LatLng(30.679879, 104.064855);// 成都市经纬度
	public static final LatLng XIAN = new LatLng(34.341568, 108.940174);// 西安市经纬度
	public static final LatLng ZHENGZHOU = new LatLng(34.7466, 113.625367);// 郑州市经纬度

	public static final LatLng CWL = new LatLng(29.5330545653,106.6039305925);// 崇文路
	public static final LatLng DSMDQ = new LatLng(29.5578402320,106.5906053782);// 东水门大桥
	public static final LatLng NBL = new LatLng(29.5394580725,106.5568631887);// 南滨路
	public static final LatLng CQCJDQ = new LatLng(29.5453198197,106.5631985664);// 重庆长江大桥
	public static final LatLng CYBDQ = new LatLng(29.5434857268,106.5513110161);// 菜园坝大桥
	public static final LatLng ZWSSD = new LatLng(29.5086783247,106.6063499451);// 真武山隧道
	public static final LatLng TLDD = new LatLng(29.5985546721,106.5975201130);// 腾龙大道
	public static final LatLng TJDD = new LatLng(29.5379179074,106.6751003265);// 通江大道
	public static final LatLng DXL = new LatLng(29.6024493719,106.5540307760);// 丁香路
	public static final LatLng BBEL = new LatLng(29.5815654929,106.5753060579);// 北滨二路
	public static final LatLng BBYL = new LatLng(29.5712922294,106.47469103347);// 北滨一路
	public static final LatLng YLDD = new LatLng(29.6026219479,106.5606880188);// 渝鲁大道
	public static final LatLng HEL = new LatLng(29.6058868442,106.6594576836);// 海尔路
	public static final LatLng SBL = new LatLng(29.5660105626,106.4739775658);// 沙滨路
	public static final LatLng JWDD = new LatLng(29.5453898226,106.4880108833);// 经纬大道
	public static final LatLng ZJL = new LatLng(29.5923228398,106.5324765444);// 紫荆路
	public static final LatLng JLL = new LatLng(29.5913199266,106.5196502209);// 金龙路
	public static final LatLng BXDD = new LatLng(29.3827359985,106.5203583241);// 巴县大道
	public static final LatLng DJDL = new LatLng(29.3860033155,106.5100479126);// 大江东路
	public static final LatLng CTMDQ = new LatLng(29.5854468535,106.5794312954);// 朝天门大桥

	public static final int ZOOMLEVEL = 17;//缩放级别
	public static final int ButtonTextBefore = 12;//Button字体点击前
	public static final int ButtonTextAfter = 17;//Button字体点击后


	public static final int DATABASE_CHANGE = 1;
	public static final int LOGO_POSITION_CHANGED_LEFT = 2;
	public static final int LOGO_POSITION_CHANGED_MIDDLE = 3;
	public static final int LOGO_POSITION_CHANGED_RIGHT = 4;

	public static final int ZOOMSWITCH_CHANGED_OPEN = 5;
	public static final int COMPASS_SWITCH_CHANGED_OPEN = 6;
	public static final int LANGUAGE_SWITCH_CHANGED_OPEN = 7;
	public static final int SCALE_SWITCH_CHANGED_OPEN = 8;
	public static final int SWIPE_SWITCH_CHANGED_OPEN = 9;

	public static final int ZOOMSWITCH_CHANGED_CLOSE = 10;
	public static final int COMPASS_SWITCH_CHANGED_CLOSE = 11;
	public static final int LANGUAGE_SWITCH_CHANGED_CLOSE = 12;
	public static final int SCALE_SWITCH_CHANGED_CLOSE = 13;
	public static final int SWIPE_SWITCH_CHANGED_CLOSE = 14;

	public static String LANGUAHE = "EN";


	public static String IP = "169.254.245.131";//ip
	public static int PORT = 10000;//端口号


	public static int DEFAULTCOLOR_A = 0;//端口号
	public static int DEFAULTCOLOR_R = 255;//端口号
	public static int DEFAULTCOLOR_G = 255;//端口号
	public static int DEFAULTCOLOR_B = 255;//端口号

	public static int checkedRadioButtonId = R.id.rb_left;
	public static boolean isZoomChecked = true;
	public static boolean isCompassChecked = true;
	public static boolean isLanguageChecked = false;
	public static boolean isScaleChecked = true;
	public static boolean isSwipeChecked = true;
}
