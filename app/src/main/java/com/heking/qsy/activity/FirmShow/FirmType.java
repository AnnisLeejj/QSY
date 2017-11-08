package com.heking.qsy.activity.FirmShow;

import java.util.ArrayList;

import com.baidu.mapapi.search.poi.PoiSearch;
import com.heking.SPJK.resource.ResourceListAdapter;
import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.regulatory.RegulatoryActivity;
import com.heking.qsy.activity.review.WaitDialog;
import com.heking.qsy.util.FirmTypeBean;
import com.heking.qsy.util.FirmTypeBean.Data;
import com.hikvision.vmsnetsdk.CameraInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class FirmType extends Activity implements OnViewData {
    private Bundle bundle;
    private FirmTypeBean.Data data;
    private LinearLayout Monitors;
    private ListView Monitorslayout;
    private TextView TextFirmName;
    private TextView TextAddress;
    private TextView TextEmail;
    private TextView TextLegalRepresentative;
    private TextView TextLegalRepresentativePhones;
    private TextView TextManager;
    private TextView TextManagerPhones;
    private TextView TextHeadOfQuality;
    private TextView TextQualityHeadPhones;
    private TextView TextFirmTypeName;
    private TextView TextAnnualRating;
    private TextView TextMonitors;
    private ListView mListLicense;
    private TextView TextNavigation;
    private PoiSearch poiSearch;
    //	private AMapLocationClient mlocationClient;
//	private AMapLocationClientOption mLocationOption = null;
    private static TextView mFiryTypes;
    private int intS;
    private static WaitDialog dialog;

    // private int mRating;
    // private boolean ioos;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppContext.THEME) {
            setTheme(R.style.SwitchTheme_1);
        } else {
            setTheme(R.style.SwitchTheme_2);
        }
        bundle = getIntent().getExtras();
        data = (Data) bundle.getSerializable("FIRMTYPE");
        intS = bundle.getInt("State");
        setContentView(R.layout.firm_type);
        findViewById(R.id.textview).setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                finish();
            }
        });
        dialog = new WaitDialog(this);
        dialog.setContent("正在加载...");
        iniView();
        iniData();
        LocationSearch();
        Log.i("FirmType", "---------sssss---------------------------");
     //   new LogInHk(this, savedInstanceState, data);
    }

    public static void enddialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    private void LocationSearch() {
//		mlocationClient = new AMapLocationClient(this);
//		// 初始化定位参数
//		mLocationOption = new AMapLocationClientOption();
//		// 设置定位监听
//		mlocationClient.setLocationListener(this);
//		// 设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
//		mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
//		// 设置定位间隔,单位毫秒,默认为2000ms
//		mLocationOption.setInterval(2000);
//		// 设置定位参数
//		mlocationClient.setLocationOption(mLocationOption);
//		// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
//		// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
//		// 在定位结束后，在合适的生命周期调用onDestroy()方法
//		// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
//		// 启动定位
//		mlocationClient.startLocation();
//		// PoiSearch.Query query = new PoiSearch.Query(data.getAddress(),
//		// "地名地址信息", "犍为县");
//		// String addstr = data.getAddress() != null ? data.getAddress()
//		// .equals("") ? "" : data.getAddress() : "";
//		PoiSearch.Query query = new PoiSearch.Query(data.getFirmName().trim(), "", "乐山市");
//
//		// keyWord表示搜索字符串，
//		// 第二个参数表示POI搜索类型，二者选填其一，
//		// POI搜索类型共分为以下20种：汽车服务|汽车销售|
//		// 汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
//		// 住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
//		// 金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
//		// cityCode表示POI搜索区域的编码，是必须设置参数
//
//		query.setPageSize(100);// 设置每页最多返回多少条poiitem
//		poiSearch = new PoiSearch(this, query);// 初始化poiSearch对象
//		poiSearch.setOnPoiSearchListener(this);// 设置回调数据的监听器
//		poiSearch.searchPOIAsyn();// 开始搜索
//
//		TextNavigation.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				if (TextAddress.getText().toString().trim() == null
//						|| TextAddress.getText().toString().trim().equals("")) {
//					Toast.makeText(FirmType.this, "该企业尚未提供地址，尚不能提供导航", Toast.LENGTH_SHORT).show();
//				} else {
//					Intent intent = new Intent(FirmType.this, DHPoriMap.class);
//					startActivity(intent);
//				}
//
//			}
//		});
    }
//
//	@Override
//	public void onPoiItemSearched(PoiItem arg0, int arg1) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onPoiSearched(PoiResult result, int arg1) {
//
//		ArrayList<PoiItem> list = result.getPois();
//		if (list.size() > 0) {
//			AppContext.BundelPoiMess.poiItem = list.get(0);
//			TextNavigation.setVisibility(TextView.VISIBLE);
//		}
//	}
//
//	@Override
//	public void onLocationChanged(AMapLocation aLocation) {
//		AppContext.BundelPoiMess.toLatlng = new NaviLatLng(aLocation.getLatitude(), aLocation.getLongitude());
//	}

    private void iniView() {

        TextNavigation = (TextView) findViewById(R.id.Text_Navigation);

        Monitors = (LinearLayout) findViewById(R.id.monitoring);
        Monitorslayout = (ListView) findViewById(R.id.monitoring_layout);

        TextFirmName = (TextView) findViewById(R.id.firm_name_message);
        TextAddress = (TextView) findViewById(R.id.firm_address_message);
        TextEmail = (TextView) findViewById(R.id.firm_email_message);

        TextLegalRepresentative = (TextView) findViewById(R.id.Legal_Representative_message);
        TextLegalRepresentativePhones = (TextView) findViewById(R.id.Legal_Representative_phones_message);

        TextManager = (TextView) findViewById(R.id.Manager_message);
        TextManagerPhones = (TextView) findViewById(R.id.Manager_Phones_message);
        TextHeadOfQuality = (TextView) findViewById(R.id.Head_Of_Quality_message);

        TextQualityHeadPhones = (TextView) findViewById(R.id.Head_Quality_Phones_message);
        TextFirmTypeName = (TextView) findViewById(R.id.firm_annual_rating_message);

        TextAnnualRating = (TextView) findViewById(R.id.ping_ji_message);
        TextMonitors = (TextView) findViewById(R.id.monitoring_name);

        mFiryTypes = (TextView) findViewById(R.id.firm_data_message);

        TextFirmName.setText(data.getFirmName());
        TextAddress.setText(data.getAddress());
        TextEmail.setText(data.getEmail());

        TextLegalRepresentative.setText(data.getLegalRepresentative());
        TextLegalRepresentativePhones.setText(data.getLegalRepresentativePhones());

        TextManager.setText(data.getManager());
        TextManagerPhones.setText(data.getManagerPhones());

        TextHeadOfQuality.setText(data.getHeadOfQuality());
        TextQualityHeadPhones.setText(data.getQualityHeadPhones());
        TextFirmTypeName.setText(data.getFirmTypeName1());
//
//		mListLicense = (ListView) findViewById(R.id.zhengzhao_layout);

        switch (data.getmRating()) {
            case 1:
                TextAnnualRating.setBackgroundDrawable(getResources().getDrawable(R.drawable.a));

                break;
            case 2:
                TextAnnualRating.setBackgroundDrawable(getResources().getDrawable(R.drawable.b));

                break;
            case 3:
                TextAnnualRating.setBackgroundDrawable(getResources().getDrawable(R.drawable.c));
                break;
        }
    }

    ArrayList<CameraInfo> cameraInfos;

    private void iniData() {
        // if (!AppContext.LoginUserMessage.messageUse) {
        // mFiryTypes.setVisibility(LinearLayout.GONE);
        // }
        mFiryTypes.setVisibility(LinearLayout.GONE);

        if (AppContext.LoginUserMessage.bean != null && AppContext.LoginUserMessage.bean.getSystemMenus() != null) {
            for (int i = 0; i < AppContext.LoginUserMessage.bean.getSystemMenus().size(); i++) {
                if (AppContext.LoginUserMessage.bean.getSystemMenus().get(i).getCode().equals("1")) {
                    mFiryTypes.setVisibility(View.VISIBLE);
                }
            }
        }
        mFiryTypes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // if(AppContext.LoginUserMessage.messageUse){
                Intent intent = new Intent(FirmType.this, RegulatoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", data);
                bundle.putInt("State", intS);
                intent.putExtras(bundle);
                FirmType.this.startActivity(intent);
                // }
            }
        });

        if (data.getMonitors() != null) {
            if (data.getMonitors().size() > 0) {
                if (data.getFirmTypeName().equals("餐饮企业") || data.getFirmTypeName().equals("食品企业")) {
                    TextMonitors.setText("视频");
                    mFiryTypes.setVisibility(LinearLayout.GONE);
                } else {
                    TextMonitors.setText("视频");
                }
                cameraInfos = new ArrayList<CameraInfo>();
                if (AppContext.cameraInfos_hk != null) {
                    for (Data.Monitors item : data.getMonitors()) {
                        for (com.hikvision.vmsnetsdk.CameraInfo item1 : AppContext.cameraInfos_hk) {
                            if (item.getName().equals(item1.getName())) {
                                cameraInfos.add(item1);
                                continue;
                            }
                        }
                    }
                    ResourceListAdapter.mList = cameraInfos;
                }
                MonitorsAdapter adapter = new MonitorsAdapter(cameraInfos, this, true);
                Monitorslayout.setAdapter(adapter);
                dialog.show();
            } else {
                Monitors.setVisibility(LinearLayout.GONE);
                enddialog();
            }
        }
        if (data.getFirmLicenseData() != null) {
            if (data.getFirmLicenseData().size() > 0) {
                FirmTypeAdapter adapter = new FirmTypeAdapter(data.getFirmLicenseData(), this);
                mListLicense.setAdapter(adapter);
            }
            // else{
            // License.setVisibility(LinearLayout.GONE);
            // }
        }
    }

    @Override
    public void setOnView(View view) {
        Monitors.addView(view);
    }

}
