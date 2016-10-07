/**
 *
 */
package com.unique.core.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * 心电图绘图工具类
 * 创建人:余宁
 * 修改人:余宁
 * 创建日期:2015年12月2日上午9:36:38
 * 修改日期:2015年12月2日上午9:36:38
 */
public class EcgViewUtil {
	private int viewWidth;// 视图宽度
	private int viewHeight;// 视图高度
	/**
	 * 绘图参数
	 */
	private final static float ECG_UNIT_VOLTAGE = 0.1f; // 标准一小格代表的电压值  0.1mv*1000
	private final static float ECG_UNIT_TIME = 40f;// 标准一小格表示的时间长度 40ms
//	private final static float ECG_POINTS_INTERVAL_TIME = 1000f / 250;// ECG两数据间隔 ms--从数据库USER_ELECTROCARDIO_TEST_DET中取频率
//	private static float ECG_POINTS_INTERVAL_UNIT_NUM = ECG_POINTS_INTERVAL_TIME
//			/ ECG_UNIT_TIME; // ECG 两数据间隔对应格子数
	private final static int MAX_Y_UNIT_NUM = 8 * 5;
	private int yUnitNum = MAX_Y_UNIT_NUM;// y轴小格子数， 8个大格子
	private float ecgUnitSize;// 一格高度 px viewHeight/yUnitNum
	private int xUnitNum;// x轴可画格子数 viewWidth/ecgUnitSize
	private float ecgPointsIntervalWidth;// ECG
											// ECG 两数据间像素宽度，ECG_INTERVAL_GRID_NUM *
											// ecgUnitSize
											// private float baseLineY;// base 线
											// y坐标 viewHeight/2
	
	
	/**
	 * 心电数据
	 */
	private final static int VALID_DATA_SIZE = 19;
	private final static int ECG_BASE_LINE = 50;
	private int orgEcgValue = ECG_BASE_LINE;
	private int changedEcgValue;
	boolean startMoveMonitor = true;
	int monitorCount = 0;
	int parserCount = 0;
	int lastTemperature = 0;
	int unTouchCount = 0;
	int MAX_VOLTAGE = 10000; // 最大电压 ，超过最大电压将被滤掉 ，mv*1000

	private final static int DEFAULT_ECG_LINE_NUM = 1;
	int ecgLineNum = 1;

	class Point {
		float startX = 0.0f;
		float startY = 0.0f;
		float stopX = 0.0f;
		float stopY = 0.0f;
		float baseLineY = 0.0f;
	}
	List<Point> channelPointList = new ArrayList<Point>();
	
	
	private Graphics2D canvas;
	public BufferedImage img;
	private Graphics2D paint = null;
	private Graphics2D paintGrid = null;
	private Graphics2D paintBoldGrid = null;
	private Graphics2D paintBaseLine = null;
	
	public void init(int width, int height,int hz) {
		init(width, height, DEFAULT_ECG_LINE_NUM,hz);
	}

	public void init(int width, int height, int ecgLineNum,int hz) {
		this.ecgLineNum = ecgLineNum;
		initParams(width, height,hz);
		initChannel();
		//创建一张画布
		img = createCompatibleImage(viewWidth, viewHeight, BufferedImage.TYPE_USHORT_565_RGB);
//		img = createCompatibleImage(viewWidth, viewHeight, BufferedImage.TYPE_USHORT_565_RGB);
		initPaint();
		
		paintNewGrid();// 绘制背景网格
	}
	
	private void initChannel() {
		channelPointList = new ArrayList<Point>();
		for (int i = 0; i < ecgLineNum; i++) {
			Point point = new Point();
			point.baseLineY = (i + 0.5f) * MAX_Y_UNIT_NUM * ecgUnitSize;
			point.startY = point.baseLineY;
			point.stopY = point.baseLineY;
			channelPointList.add(point);
		}
	}
	
	/**
	 * 初始化画笔
	 * 创建人:余宁
	 * 修改人:余宁
	 * 创建日期:2015年12月2日上午10:34:54
	 * 修改日期:2015年12月2日上午10:34:54
	 */
	private void initPaint() {
		// 心电画笔
		paint = img.createGraphics();
		paint.setColor(new Color(0x000000FF));
		BasicStroke stroke = new BasicStroke(1.0f);
		paint.setStroke(stroke);
		paint.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// 网格画笔
		paintGrid = img.createGraphics();
		paintGrid.setColor(new Color(0x50AAAAAA));
		paintGrid.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		paintBoldGrid = img.createGraphics();
		paintBoldGrid.setColor(new Color(0x30C80A64));
		BasicStroke stroke2 = new BasicStroke(2f);
		paintBoldGrid.setStroke(stroke2);
		paintBoldGrid.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// 基线画笔
		paintBaseLine = img.createGraphics();
		paintBaseLine.setColor(new Color(0x58D3C1));
		paintBaseLine.setStroke(stroke2);
		paintBaseLine.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	/**
	 * 初始化画布
	 */
	private void paintNewGrid() {
		//获得画笔
		canvas = img.createGraphics();
		canvas.setColor(Color.WHITE);
		canvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		canvas.fillRect(0, 0, viewWidth, viewHeight);
		drawGrid(this.xUnitNum, this.yUnitNum, this.ecgUnitSize);
	}
	
	/**
	 * 绘制心电图
	 * 
	 * @param voltage
	 */
	public void updatePaint(int channelIndex, short[] voltages) {
		Point curPoint = channelPointList.get(channelIndex);
		for( int voltage : voltages){
			float ecgVoltage = voltage / 1000.0f;// 当前电压 mv
			/*
			 * if(ecgVoltage>MAX_Y_UNIT_NUM*ECG_UNIT_VOLTAGE ||
			 * ecgVoltage<-MAX_Y_UNIT_NUM*ECG_UNIT_VOLTAGE){ ecgVoltage =
			 * ecgVoltage-(((int)ecgVoltage)%1000) ; }
			 */
			curPoint.stopY = curPoint.baseLineY
					- (ecgVoltage * ecgUnitSize / ECG_UNIT_VOLTAGE);
			
			curPoint.stopX = curPoint.startX + ecgPointsIntervalWidth;
			paint.draw(new Line2D.Double(curPoint.startX, curPoint.startY, curPoint.stopX,
					curPoint.stopY));
			// Log.d(TAG,
			// channelIndex+":	"+curPoint.startX+"->"+curPoint.stopX+","+curPoint.startY+"->"+curPoint.stopY);
			curPoint.startX = curPoint.stopX;
			curPoint.startY = curPoint.stopY;
			channelPointList.set(channelIndex, curPoint);
		}

//		if (curPoint.startX > viewWidth) {
//			if (!isMultiPages)
//				return;
//			add2Bitmap();
//			paintNewGrid();
//		}
	}
	
	/**
	 * 根据画布初始化参数。
	 * @param width
	 * @param height
	 */
	public void initParams(int width, int height,int hz) {
		this.viewWidth = width;
		this.viewHeight = height;
		this.yUnitNum = MAX_Y_UNIT_NUM * ecgLineNum;
		this.ecgUnitSize = (float) this.viewHeight / this.yUnitNum;
		this.xUnitNum = Math.round(viewWidth / ecgUnitSize);
		this.ecgPointsIntervalWidth = (1000f / hz / ECG_UNIT_TIME) * this.ecgUnitSize;
	}
	
	/**
	 * 绘制网格
	 * 
	 * @param baselineY
	 * @param widthNum
	 * @param heightNum
	 * @param gridWidth
	 */
	private void drawGrid(float widthNum, float heightNum, float gridWidth) {
		Graphics2D paintTmp = paintBoldGrid;
		for (int startY = 0; startY < heightNum; startY++) {
			if ((startY) % 5 == 0) {
				paintTmp = paintBoldGrid;
			} else {
				paintTmp = paintGrid;
			}
			paintTmp.draw(new Line2D.Double(0, startY * gridWidth, widthNum * gridWidth, startY
					* gridWidth));
		}
		for (int startX = 0; startX < widthNum; startX++) {
			if ((startX) % 5 == 0) {
				paintTmp = paintBoldGrid;
			} else {
				paintTmp = paintGrid;
			}
			paintTmp.draw(new Line2D.Double(startX * gridWidth, 0, startX * gridWidth,
					heightNum * gridWidth));
		}
	}
	
    // 创建硬件适配的缓冲图像，为了能显示得更快速  
    public static BufferedImage createCompatibleImage(int w, int h, int type) {  
//    	return new BufferedImage(w, h, type);
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();  
        GraphicsDevice device = env.getDefaultScreenDevice();  
        GraphicsConfiguration gc = device.getDefaultConfiguration();
        return gc.createCompatibleImage(w, h);  
    }
    
    
    public static void main(String[] args) {
    	short[] f = new short[]{311,319,320,327,335,340,340,350,357,364,378,389,398,407,412,416,424,431,429,430,440,454,470,481,490,501,519,536,549,566,596,627,649,662,676,689,696,705,709,722,739,755,780,801,793,975,1669,1780,996,360,489,607,637,745,769,751,817,819,839,857,859,876,877,881,884,880,879,878,873,870,870,869,867,864,866,867,866,864,866,872,884,893,893,899,898,889,885,882,877,878,879,878,879,880,880,882,884,884,893,899,906,913,919,928,939,948,954,958,956,959,965,972,975,980,985,990,991,995,1001,1006,1014,1023,1028,1028,1031,1036,1044,1046,1045,1044,1047,1054,1063,1066,1069,1074,1078,1078,1080,1089,1094,1101,1106,1112,1117,1120,1125,1135,1141,1147,1164,1187,1198,1198,1206,1210,1214,1221,1233,1247,1259,1277,1301,1315,1310,1499,2006,2303,1644,946,964,1108,1133,1238,1268,1267,1338,1352,1380,1403,1415,1437,1446,1448,1449,1444,1447,1454,1469,1488,1506,1518,1522,1520,1514,1511,1503,1489,1480,1478,1477,1487,1496,1493,1486,1483,1478,1478,1480,1476,1477,1484,1491,1500,1511,1526,1537,1543,1554,1568,1587,1607,1626,1642,1650,1651,1651,1649,1640,1639,1636,1638,1640,1642,1638,1633,1632,1629,1623,1619,1613,1608,1605,1589,1572,1559,1549,1549,1554,1559,1561,1559,1560,1553,1542,1533,1515,1508,1502,1502,1499,1503,1511,1511,1496,1477,1465,1449,1436,1426,1418,1409,1404,1410,1409,1403,1604,2048,2158,1290,854,1023,1082,1108,1226,1248,1221,1263,1259,1270,1275,1270,1270,1266,1267,1264,1266,1271,1272,1278,1285,1288,1291,1291,1292,1289,1285,1287,1286,1285,1281,1279,1279,1275,1270,1268,1263,1252,1246,1241,1231,1223,1218,1217,1216,1210,1211,1214,1213,1216,1221,1224,1229,1234,1237,1237,1238,1242,1241,1241,1245,1249,1254,1256,1258,1261,1260,1260,1257,1253,1251,1254,1256,1251,1247,1248,1251,1252,1251,1250,1248,1247,1244,1244,1242,1243,1245,1250,1255,1255,1255,1266,1275,1276,1263,1257,1256,1250,1244,1243,1250,1253,1261,1276,1287,1280,1680,2208,1981,1172,758,936,1031,1063,1191,1184,1205,1247,1249,1279,1280,1288,1296,1301,1308,1314,1324,1333,1342,1357,1363,1371,1377,1381,1386,1393,1396,1400,1403,1402,1401,1406,1407,1400,1384,1372,1366,1359,1349,1340,1336,1330,1321,1317,1312,1312,1310,1312,1313,1313,1312,1314,1318,1314,1317,1321,1323,1325,1325,1323,1322,1324,1323,1321,1322,1324,1321,1316,1318,1317,1313,1312,1309,1309,1311,1310,1306,1306,1305,1301,1300,1298,1297,1294,1294,1293,1295,1290,1290,1292,1291,1294,1296,1294,1289,1294,1299,1296,1299,1313,1322,1306,1295,1284,1276,1270,1262,1260,1264,1267,1275,1292,1278,1357,1770,2256,1623,850,794,940,1008,1092,1144,1119,1177,1182,1193,1208,1212,1220,1220,1222,1218,1221,1225,1228,1228,1231,1232,1227,1226,1226,1229,1227,1222,1222,1218,1218,1223,1228,1227,1222,1217,1209,1199,1189,1185,1179,1172,1166,1161,1161,1158,1157,1155,1158,1162,1165,1168,1178,1183,1186,1189,1189,1192,1199,1202,1203,1203,1199,1194,1194,1197,1196,1195,1193,1192,1189,1192,1196,1197,1196,1197,1195,1197,1202,1203,1210,1216,1216,1219,1222,1224,1223,1218,1217,1215,1213,1210,1209,1209,1209,1209,1208,1207,1203,1202,1202,1200,1203,1217,1223,1213,1202,1189,1174,1162,1155,1154,1154,1157,1170,1171,1174,1425,1979,2051,1260,632,785,903,938,1019,1024,1027,1081,1074,1098,1105,1107,1110,1110,1116,1121,1122,1126,1127,1128,1132,1132,1129,1128,1127,1125,1127,1129,1131,1132,1134,1141,1146,1147,1143,1138,1130,1121,1112,1104,1100,1095,1093,1094,1089,1090,1090,1089,1087,1088,1093,1099,1105,1108,1109,1110,1112,1114,1114,1113,1113,1115,1115,1113,1107,1104,1103,1101,1101,1101,1103,1102,1102,1106,1109,1108,1106,1108,1102,1101,1100,1098,1092,1089,1089,1086,1084,1085,1085,1085,1085,1086,1087,1087,1089,1088,1089,1089,1092,1095,1097,1110,1121,1124,1115,1108,1101,1089,1079,1075,1080,1086,1099,1111,1113,1106,1580,2087,1698,875,585,760,858,899,986,966,1006,1030,1028,1050,1049,1062,1064,1067,1073,1075,1077,1084,1087,1089,1089,1087,1087,1085,1083,1084,1082,1082,1079,1079,1085,1090,1094,1089,1085,1077,1068,1062,1055,1051,1048,1044,1037,1032,1028,1029,1028,1032,1037,1042,1048,1055,1058,1061,1059,1059,1063,1064,1063,1062,1061,1060,1062,1058,1056,1056,1056,1060,1062,1061,1061,1059,1056,1054,1051,1049,1047,1049,1051,1051,1049,1048,1049,1046,1050,1051,1048,1049,1051,1050,1052,1053,1051,1050,1053,1056,1055,1066,1081,1081,1071,1062,1057,1047,1037,1031,1031,1033,1046,1060,1058,1070,1358,1917,1871,1053,495,654,770,822,920,905,918,967,958,985,988,995,996,1000,1003,1009,1016,1022,1026,1030,1035,1036,1034,1039,1041,1036,1035,1036,1036,1039,1045,1050,1055,1055,1050,1042,1031,1023,1013,1002,994,992,988,984,981,983,984,987,987,991,998,998,999,1000,1001,1000,997,995,997,996,996,997,994,992,993,993,991,995,995,997,998,1001,1004,999,995,990,985,982,985,987,985,988,986,980,975,976,977,973,971,971,970,967,970,973,972,971,981,997,1004,995,988,987,978,967,964,966,969,975,988,998,981,1136,1632,1983,1350,590,545,687,753,833,896,875,936,944,963,982,985,1004,1009,1017,1022,1027,1036,1048,1058,1065,1077,1082,1088,1096,1107,1115,1126,1137,1144,1161,1180,1204,1222,1238,1253,1261,1270,1281,1293,1300,1305,1320,1331,1335,1341,1346,1350,1353,1358,1362,1360,1357,1357,1355,1351,1347,1340,1327,1313,1304,1291,1283,1273,1263,1254,1248,1239,1232,1221,1216,1209,1204,1197,1190,1187,1183,1177,1171,1167,1161,1156,1149,1146,1143,1136,1126,1119,1114,1113,1109,1109,1114,1116,1118,1134,1145,1146,1135,1131,1128,1120,1116,1112,1112,1120,1128,1142,1147,1142,1358,1884,2037,1284,607,696,819,866,973,1000,971,1026,1025,1037,1045,1043,1047,1043,1045,1048,1047,1047,1044,1045,1046,1045,1041,1033,1031,1034,1033,1035,1034,1027,1027,1027,1030,1031,1027,1017,1004,996,986,973,964,954,950,947,943,942,942,945,948,950,955,960,971,979,985,990,996,1000,1001,1002,1002,1002,1005,1008,1011,1013,1015,1019,1020,1018,1021,1025,1023,1022,1020,1024,1025,1019,1017,1019,1016,1014,1016,1013,1007,1006,1004,998,996,994,995,1000,1004,1014,1032,1046,1041,1033,1032,1027,1020,1017,1015,1018,1023,1034,1041,1027,1174,1735,1853,1332,552,673,771,812,912,961,940,996,1006,1018,1037,1041,1052,1052,1055,1064,1065,1062,1059,1059,1062,1068,1072,1071,1070,1069,1071,1071,1070,1066,1068,1069,1066,1061,1053,1037,1023,1013,1004,994,986,982,977,971,969,963,961,958,952,949,951,952,952,950,946,945,942,937,931,923,917,914,909,907,903,898,896,899,897,896,898,897,899,901,901,896,893,891,889,893,892,891,891,888,881,874,870,871,872,870,877,890,891,883,878,873,861,854,851,850,850,861,873,883,865,961,1506,1817,1160,434,420,557,620,698,769,739,787,807,806,832,832,840,845,847,848,848,853,853,856,860,863,865,869,874,879,877,880,885,888,891,894,899,900,894,885,879,872,861,854,850,845,841,835,835,832,831,832,834,839,845,851,856,860,863,865,864,865,866,868,866,864,867,866,868,867,866,867,870,870,870,867,865,864,866,865,860,858,859,861,861,861,862,863,867,871,880,888,876,870,866,859,852,845,841,842,843,854,873,878,905,1235,1782,1608,780,314,503,618,652,770,775,791,830,829,852,850,861,868,868,871,871,870,873,876,879,877,874,870,869,873,878,879,882,885,886,890,897,900,899,891,882,870,862,854,844,841,834,828,825,821,818,817,820,822,827,830,833,839,843,850,856,858,859,860,861,861,862,868,869,867,866,866,863,867,871,868,866,866,864,862,863,861,860,858,856,855,853,848,845,844,843,840,841,844,848,858,870,875,861,853,854,844,833,831,831,837,848,865,875,857,972,1429,1863,1314,362,342,545,597,697,742,720,776,791,794,817,815,825,828,830,829,832,831,829,831,834,839,843,842,839,841,846,848,850,857,864,867,874,874,867,857,848,837,829,824,821,817,813,810,813,815,812,812,813,814,814,816,817,822,826,828,831,830,825,821,822,825,826,826,823,825,828,831,832,834,827,824,827,830,834,834,835,835,831,827,827,828,828,826,828,829,825,824,827,831,834,835,843,857,865,852,848,845,833,818,809,812,814,821,836,841,846,1097,1648,1748,968,317,431,550,600,691,709,710,766,755,780,789,796,806,803,808,814,817,823,830,837,844,847,849,850,852,859,862,863,862,865,872,881,888,888,886,877,868,859,854,845,838,834,828,822,818,818,817,820,824,829,836,842,841,844,850,854,860,866,871,880,884,886,886,885,886,887,888,886,886,886,887,888,890,888,886,885,886,884,885,885,883,883,883,880,881,882,879,877,878,876,876,883,900,905,891,881,875,862,851,839,832,833,840,851,860,847,1019,1485,1782,1118,404,410,539,594,681,751,717,763,781,780,800,800,807,807,806,808,811,815,817,818,820,820,820,821,824,823,823,818,817,820,820,824,830,832,826,816,807,801,786,777,770,763,764,766,765,761,758,759,764,768,771,780,783,786,790,795,795,795,793,794,798,801,802,799,799,801,802,801,798,797,795,795,792,790,792,795,794,792,792,792,793,792,790,789,788,787,782,778,782,790,790,794,804,807,797,789,784,778,771,769,772,774,776,789,805,793,878,1305,1816,1389,379,267,479,551,638,703,675,725,748,750,773,776,793,798,801,809,809,815,824,825,828,831,837,840,842,848,850,856,858,857,859,863,868,871,867,859,848,837,826,819,810,805,803,798,794,795,797,796,797,802,810,816,820,822,830,838,842,842,846,848,851,851,849,848,846,846,848,845,840,836,834,832,832,833,839,842,839,837,837,838,837,837,832,829,829,826,822,818,815,813,812,818,821,821,834,844,847,839,834,828,820,809,801,798,801,809,823,832,811,969,1473,1822,1166,383,341,489,557,644,695,667,724,721,734,751,750,758,759,768,770,770,773,783,786,790,794,795,796,796,796,795,799,799,801,807,817,833,839,838,829,817,805,794,783,774,765,760,757,757,757,756,755,759,760,759,764,771,772,773,775,773,774,777,775,777,778,776,776,776,776,774,774,773,774,772,772,774,775,774,772,770,768,763,760,752,746,746,748,753,757,758,757,759,762,763,764,769,777,786,793,803,826,841,835,835,844,840,836,837,840,845,862,884,890,909,1179,1847,1623,807,382,578,681,708,824,824,827,883,880,914,917,923,934,939,944,948,959,964,968,974,982,982,988,993,994,996,1001,1001,1004,1009,1014,1020,1028,1031,1034,1027,1017,1005,997,992,986,980,975,972,975,974,977,982,985,989,998,1008,1015,1016,1018,1024,1022,1023,1026,1026,1024,1024,1025,1022,1018,1015,1012,1009,1003,1002,999,995,990,982,979,973,965,963,959,950,943,939,930,924,921,919,917,913,922,930,916,896,889,870,855,851,848,847,848,856,863,851,920,1290,1737,1355,600,369,510,573,634,729,700,733,756,755,778,775,779,779,778,777,781,786,790,791,791,791,792,789,786,786,789,791,792,791,787,789,796,797,794,786,777,767,754,742,737,732,721,715,715,715,715,719,723,727,732,736,736,741,745,744,745,746,744,746,744,741,739,737,735,734,734,730,730,735,737,736,739,741,740,743,744,746,751,750,751,750,752,760,765,770,771,786,798,796,785,781,773,766,758,749,748,752,761,771,770,812,1169,1713,1641,637,210,395,494,525,641,634,641,695,687,714,722,728,731,734,740,740,744,750,753,757,757,763,764,762,759,763,763,761,762,762,763,769,769,766,759,749,738,728,723,712,705,698,694,691,688,686,687,687,684,687,693,697,703,705,708,714,716,716,716,716,714,713,708,703,705,706,704,703,701,702,703,702,700,697,696,695,692,693,697,698,702,702,703,706,708,707,707,707,710,711,717,734,744,736,730,729,724,715,703,701,704,708,720,736,721,787,1186,1724,1170,530,186,396,478,554,606,599,640,666,664,687,682,689,692,691,688,686,690,691,692,698,700,702,705,706,708,711,713,712,713,716,721,730,734,731,728,718,709,701,692,683,676,672,671,669,665,660,662,665,664,668,672,678,686,690,699,704,706,707,708,707,707,702,699,699,696,695,696,699,699,701,700,701,704,705,703,701,695,694,694,693,696,698,699,701,701,704,706,706,720,729,728,715,708,703,698,691,690,691,691,705,718,706,757,1130,1694,1442,600,181,363,463,527,612,584,618,649,647,673,670,678,682,682,681,687,692,692,693,693,698,700,701,699,703,710,717,719,724,728,731,741,749,746,740,734,726,717,710,705,699,694,689,685,687,689,694,698,703,708,716,721,727,732,739,745,749,749,751,751,751,751,746,746,747,746,748,750,752,753,752,751,753,757,759,761,760,760,759,764,768,765,765,779,793,800,788,781,776,768,759,754,752,753,763,781,794,787,941,1360,1630,1013,367,368,485,542,620,710,683,733,740,748,760,757,767,764,765,771,771,770,771,774,774,775,774,774,774,771,768,762,755,751,748,746,744,739,727,715,704,689,682,676,668,665,658,653,651,651,648,646,649,651,653,655,658,660,661,657,654,653,654,649,644,645,644,641,641,640,639,644,649,648,647,648,647,645,643,643,644,644,643,641,639,642,646,652,655,652,665,676,677,672,665,659,650,641,637,634,634,644,658,664,660,864,1391,1613,881,170,229,348,404,500,536,521,580,578,599,610,611,623,621,627,632,631,632,635,638,641,641,640,640,640,642,644,647,650,652,654,657,663,666,658,651,644,638,630,623,619,615,611,604,604,603,604,606,610,614,618,624,629,632,631,630,636,638,636,629,630,628,627,629,632,634,633,633,632,632,634,632,630,631,630,628,630,631,634,635,636,638,641,645,649,649,654,671,684,676,672,658,652,646,639,634,636,638,644,661,665,673,936,1500,1546,738,116,290,402,432,542,534,541,594,587,612,618,621,627,631,636,636,638,640,647,649,647,648,652,655,659,662,666,667,673,679,682,686,688,681,673,658,644,639,634,628,622,621,616,611,608,607,604,607,610,612,615,616,619,623,630,633,634,636,639,640,640,640,641,641,641,640,640,639,640,638,638,643,645,640,638,642,644,648,647,646,646,643,643,647,648,649,650,644,644,645,649,649,652,674,686,677,673,671,659,658,654,649,650,651,661,675,660,719,1109,1661,1347,482,159,321,397,462,532,504,545,573,568,593,593,608,617,617,622,628,633,637,640,642,648,651,655,659,665,668,669,674,676,681,689,699,699,693,684,676,664,655,652,647,643,641,640,638,638,642,647,648,649,651,655,655,656,661,665,670,676,679,684,680,680,682,682,681,682,689,691,686,687,689,690,691,690,690,692,697,698,697,695,698,698,699,699,697,694,696,694,694,698,699,707,724,730,718,714,709,698,694,691,688,693,705,719,729,719,866,1303,1611,996,309,281,412,472,544,634,608,653,655,666,679,678,683,685,687,690,690,695,698,697,696,697,698,697,698,696,696,693,690,687,684,688,690,686,678,670,659,649,642,635,628,620,612,608,603,598,597,601,603,601,606,608,610,613,616,619,619,619,617,617,615,612,609,607,606,605,601,598,600,599,597,594,595,596,595,593,589,588,591,594,595,595,597,602,604,604,601,606,608,613,624,635,637,635,632,626,617,610,607,605,605,615,629,636,645,825,1600,1477,451,73,261,362,394,488,494,504,554,544,570,571,577,587,591,593,596,599,606,609,611,613,613,611,612,612,610,613,617,619,620,619,624,625,619,618,614,603,593,589,584,580,573,569,568,563,559,565,567,569,569,570,577,582,584,589,589,592,595,596,596,596,596,595,596,596,595,598,596,595,598,597,597,599,601,606,607,607,607,606,606,610,609,608,609,607,607,612,612,614,618,621,623,639,650,645,637,634,621,617,609,604,606,611,624,632,615,710,1158,1647,1160,302,74,254,352,410,491,461,514,532,538,561,554,564,569,572,572,574,576,577,577,580,585,586,587,591,595,599,605,606,607,609,617,625,630,627,619,611,600,588,580,573,564,559,553,548,544,540,535,531,533,535,538,541,545,548,553,556,560,562,564,562,560,562,563,562,560,563,565,564,569,573,572,572,575,576,575,575,576,577,583,586,583,582,582,585,586,587,583,582,585,589,590,601,618,625,614,610,606,596,591,589,587,594,602,618,630,619,813,1345,1625,919,178,199,328,377,470,510,493,556,555,575,588,593,606,609,613,614,617,621,624,628,628,632,634,636,636,635,637,639,637,638,644,650,657,660,654,650,647,641,633,624,618,612,600,593,590,591,592,592,597,602,606,607,612,617,618,617,619,623,624,626,623,623,626,627,623,626,630,634,637,636,636,639,643,646,646,647,648,649,653,655,657,664,666,665,664,662,666,663,660,673,692,697,689,685,683,666,656,646,641,643,649,658,663,707,1051,1502,1200,461,135,327,399,437,563,555,571,602,596,616,613,618,621,622,624,621,620,618,623,625,626,625,623,621,618,619,619,618,613,611,609,610,613,609,600,592,584,573,565,561,557,554,549,545,540,537,541,541,542,544,548,551,553,554,554,557,557,553,552,550,550,551,551,549,548,548,547,543,543,543,542,542,543,543,543,540,542,543,543,542,540,538,539,543,548,549,548,558,576,579,568,563,558,550,538,527,526,526,528,540,553,551,797,1360,1466,672,-13,134,259,315,434,439,440,497,498,528,531,543,549,549,550,555,560,561,560,563,565,562,559,560,563,562,564,568,573,572,577,582,584,582,574,567,554,547,535,529,523,513,507,503,500,497,495,501,506,510,514,524,527,532,537,538,541,545,547,547,543,541,541,542,542,543,542,541,542,542,541,542,541,541,544,549,552,549,544,542,540,537,539,542,545,547,555,563,569,558,550,546,533,521,518,521,522,527,546,561,543,668,1154,1588,1002,174,53,214,296,403,441,413,472,481,492,514,521,531,532,535,539,545,550,552,556,558,557,557,558,561,561,561,567,570,573,577,584,590,588,585,576,568,559,550};
    	EcgViewUtil util = new EcgViewUtil();
    	util.init((400 * 1000 * f.length)/ (40 * 40 * 125), 400,125);
    	util.updatePaint(0,f);
    	try {
			ImageIO.write(util.img, "bmp", new File("D:/test.bmp"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
