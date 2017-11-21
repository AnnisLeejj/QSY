package com.heking.qsy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.heking.qsy.activity.FoodCompanies;
import com.heking.qsy.activity.IWantToReview_baidu;
import com.heking.qsy.activity.Lighthouse;
import com.heking.qsy.activity.OpenGovernment;
import com.heking.qsy.activity.PharmaceuticalCompanies;
import com.heking.qsy.base.BaseFragment;
import com.heking.qsy.complaintreporting.ComplaintReportingHome;
import com.heking.qsy.util.FirmTypeBean;
import com.heking.qsy.util.GlideImageLoader;
import com.heking.qsy.util.HttpHelper.HttpHelper;
import com.heking.qsy.util.JsonFile;
import com.heking.qsy.util.ParsonJson;
import com.heking.qsy.util.Tool;
import com.heking.qsy.util.ViewPageAdapters;
import com.heking.qsy.util.WriteToSD;
import com.heking.snoa.WPConfig;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.zbar.lib.CaptureActivity;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends BaseFragment implements OnClickListener {

    private static String TAG = "LEFTTORIGHT";
    private static String LEFT = "LEFTTORIGHT";
    private int which = 0;
    private final static int BAIDU_READ_PHONE_STATE = 101;

    private ViewPageAdapters adapter;
    private ArrayList<Integer> list = new ArrayList<Integer>();
    private LinearLayout mSaoMa;
    private LinearLayout mComplaintReporting, mFoodCompanies, mPharmaceuticalCompanies, mIWantToReview;
    private RelativeLayout mOpenGovernment, mLighthouse;
    private String[] fileName = {"88801OpenGoverBean.dll",
            "88802OpenGoverBean.dll", "88803OpenGoverBean.dll",
            "88804OpenGoverBean.dll", "BSZNAdata.dll",
            "ComplaintReportingBean.dll", "FirmTypeBean.dll",
            "OpenGoverBean.dll", "Z10930004.dll"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public String getMyTag() {
        return "HomeFragment";
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fileData();
        iniView();
        iniData();
        lunbo();
    }

    private void lunbo() {
//        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
//        int width = wm.getDefaultDisplay().getWidth();
//        int height = wm.getDefaultDisplay().getHeight();
//        LogUtils.w("load", "height:" + height);
//
//        banner.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) (width * BannerHightRatio)));
//        LogUtils.w("lunbo", (int) (width * BannerHightRatio));
        // banner = (Banner) findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置轮播时间
        banner.setDelayTime(4000);

//        banner.setOnBannerListener(position -> {
//            Intent intent = new Intent(getActivity(), WebActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putString("title", lunboList.get(position).getTitle());
//            bundle.putString("url", lunboList.get(position).getLink());
//            intent.putExtras(bundle);
//            getActivity().startActivity(intent);
//        });

        //设置图片集合
        List<Integer> list1 = new ArrayList<Integer>();
        list1.add(R.drawable.image_1);
        list1.add(R.drawable.image_2);
        list1.add(R.drawable.image_3);
        list1.add(R.drawable.image_4);
        list1.add(R.drawable.image_5);

        List<String> titles = new ArrayList<String>();
        titles.add("");
        titles.add("");
        titles.add("");
        titles.add("");
        titles.add("");
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        banner.setImages(list1);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }


    private void fileData() {
        for (String name : fileName) {
            WriteToSD.write(getActivity(), name);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.main_fragment_home, null);
    }


    Banner banner;

    private void iniView() {
//        mViewPager = (CycleViewPager) getView().findViewById(R.id.show_view_pager);
        banner = (Banner) getView().findViewById(R.id.banner);
        // mlayout = (LinearLayout) getView().findViewById(R.id.show_linear_layout);
        mSaoMa = (LinearLayout) getView().findViewById(R.id.sousuo_text_button);

        mComplaintReporting = (LinearLayout) getView().findViewById(R.id.mComplaintReporting);
        mOpenGovernment = (RelativeLayout) getView().findViewById(R.id.mOpenGovernment);
        mLighthouse = (RelativeLayout) getView().findViewById(R.id.mLighthouse);
        mFoodCompanies = (LinearLayout) getView().findViewById(R.id.mFoodCompanies);
        mPharmaceuticalCompanies = (LinearLayout) getView().findViewById(R.id.mPharmaceuticalCompanies);
        mIWantToReview = (LinearLayout) getView().findViewById(R.id.mIWantToReview);
    }

    private void iniData() {
        // String message=Tool.getUrLString("get_Message_Firm", 1, this, true);
        if (AppContext.List.FirmTapeDataList == null || AppContext.List.FirmTapeDataList.size() <= 0) {

            HttpHelper.getInstance().service.get(WPConfig.URL_API_INTRANET + AppContext.Parameter.GET_FIRM_TYPE + AppContext.Parameter.ALL)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onSuccess(String json) {
                    String JsonData = json;
                    JsonData = "{\"Data\":" + json + "}";
                    JsonFile jsonFile = new JsonFile();
                    File file;
                    File file2 = null;
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        file = Environment.getExternalStorageDirectory();
                        file2 = new File(file + "/PZhMessageList/");
                        if (!file2.exists()) {
                            file2.mkdirs();
                        }
                    }
                    File ss = new File(file2 + "/FirmTypeBean.dll");
                    if (JsonData.equals("连接失败")) {
                        try {
                            JsonData = new String(jsonFile.getFlieJson(ss));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            jsonFile.FileJson(JsonData, ss);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (JsonData.equals("连接失败")) {
                        Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    FirmTypeBean bean = ParsonJson.jsonToBeanObj(JsonData, FirmTypeBean.class);
                    AppContext.List.FirmTapeDataList = bean != null ? bean.getData() : null;
                }

                @Override
                public void onError(Throwable e) {

                }
            });
        }

        if (list.size() > 0) {
            list.clear();
        }
        list.add(R.drawable.image_1);
        list.add(R.drawable.image_2);
        list.add(R.drawable.image_3);
        list.add(R.drawable.image_4);
        list.add(R.drawable.image_5);
        // AppContext.ONE = false;
        // }
        mSaoMa.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivity(intent);
            }
        });
        //   adapter = new ViewPageAdapters(list, getActivity());
        //  mViewPager.setAdapter(adapter);

//        tool = new Tool(getActivity(), mlayout, list.size());
//        tool.drawsImg();

        which = 0;

//        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                Log.d("测试数据:", "------------->" + position);
//                tool.setItem(position);
//                which = position + 1;
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

        mComplaintReporting.setOnClickListener(this);
        mOpenGovernment.setOnClickListener(this);
        mLighthouse.setOnClickListener(this);
        mFoodCompanies.setOnClickListener(this);
        mPharmaceuticalCompanies.setOnClickListener(this);
        mIWantToReview.setOnClickListener(this);
    }


    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.mComplaintReporting:
                toActivity(ComplaintReportingHome.class);
                break;
            case R.id.mOpenGovernment:
                toActivity(OpenGovernment.class);
                break;
            case R.id.mLighthouse:
                toActivity(Lighthouse.class);
                break;
            case R.id.mFoodCompanies:
                toActivity(FoodCompanies.class);
                break;
            case R.id.mPharmaceuticalCompanies:
                toActivity(PharmaceuticalCompanies.class);
                break;
            case R.id.mIWantToReview:
//                // toActivity(IWantToReview.class);
                toActivity(IWantToReview_baidu.class);
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        AppContext.ONE = true;
    }

    private void toActivity(Class<?> cla) {
        Intent intent = new Intent(getActivity(), cla);
        startActivity(intent);
    }

    private Map<String, String> login = null;

    @Override
    public void onDestroy() {
        // hander.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
