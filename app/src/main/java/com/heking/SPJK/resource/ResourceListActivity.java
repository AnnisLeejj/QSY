package com.heking.SPJK.resource;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heking.SPJK.data.Config;
import com.heking.qsy.R;
import com.heking.qsy.base.BaseActivity;
import com.heking.qsy.util.FirmTypeBean;
import com.heking.qsy.util.FirmTypeBean.Data;
import com.heking.SPJK.callback.MsgCallback;
import com.heking.SPJK.callback.MsgIds;
import com.heking.SPJK.constants.Constants;
import com.heking.SPJK.data.TempData;
import com.heking.SPJK.listviewlayout.ListViewForScrollView;
import com.heking.SPJK.live.LiveActivity;
import com.heking.SPJK.playback.PlayBackActivity;
import com.hikvision.vmsnetsdk.CameraInfo;
import com.hikvision.vmsnetsdk.ControlUnitInfo;
import com.hikvision.vmsnetsdk.RegionInfo;

import MyUtils.LogUtils.LogUtils;

//这个类用于获取视图,应当去掉其 Activity 的属性,继承 View就行了
public class ResourceListActivity extends BaseActivity implements MsgCallback, OnItemClickListener {

    /**
     * 资源列表
     */
    private ListViewForScrollView resourceListView;
    private ArrayList mList;
    private ResourceListAdapter mAdapter;
    // private ScrollView sv;
    /**
     * 父节点资源类型，TYPE_UNKNOWN表示首次获取资源列表
     */
    private int pResType = Constants.Resource.TYPE_UNKNOWN;
//    /**
//     * 父控制中心的id，只有当parentResType为TYPE_CTRL_UNIT才有用
//     */
//    private int pCtrlUnitId;
//    /**
//     * 父区域的id，只有当parentResType为TYPE_REGION才有用
//     */
//    private int pRegionId;
    /**
     * 消息处理Handler
     */
    private MsgHandler handler;
    /**
     * 获取资源逻辑控制类
     */
    private ResourceControl resourceControl;
    private Dialog mDialog;

    private static final int GOTO_LIVE_OR_PLAYBACK = 0x0b;
    private FirmTypeBean.Data Firmdata;

    /**
     * 企业分类
     */
    private String Firm_FL;
    /**
     * 企业类型
     */
    private String FirmType;
    /**
     * 企业名称
     */
    private String FirmName;

//	/** 企业经营类型 */
//	private String FirmNess;

    private String FirmNames;

    private TextView mReurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.resource_list_activity_hkws);
        // TODO
        LayoutInflater factory = LayoutInflater.from(ResourceListActivity.this);
        final View textEntryView = factory.inflate(R.layout.firm_type, null);
        Firmdata = (Data) getIntent().getExtras().get("FirmType");
        if (Firmdata != null) {
            FirmType = Firmdata.getFirmTypeName1();
            FirmName = Firmdata.getFirmName();
            if (Firmdata.getFirmTypeName().trim().contains("食品经营")
                    || Firmdata.getFirmTypeName().trim().contains("餐饮经营")
                    || Firmdata.getFirmTypeName().trim().contains("食品生产")) {
                Firm_FL = "食品企业";

//				if (Firmdata.getFirmTypeName().trim().contains("食品经营")) {
//					FirmNess = "流通企业";
//				} else {
//					FirmNess = "餐饮服务";
//				}
                if (Firmdata.getFirmTypeName().trim().contains("食品生产")) {
                    FirmNames = "生产";
                } else if (Firmdata.getFirmTypeName().trim().contains("餐饮经营")) {
                    FirmNames = "餐饮";
                } else if (Firmdata.getFirmTypeName().trim().contains("食品经营")) {
                    FirmNames = "流通";
                }
            }
            if (Firmdata.getFirmTypeName().trim().contains("药品经营")
                    || Firmdata.getFirmTypeName().trim().contains("药品生产")) {
                Firm_FL = "药品企业";
                if (Firmdata.getFirmTypeName().trim().contains("医疗")) {
                    FirmNames = "医疗";
                } else if (Firmdata.getFirmTypeName().trim().contains("生产")) {
                    FirmNames = "生产";
                } else if (Firmdata.getFirmTypeName().trim().contains("经营")) {
                    FirmNames = "流通";
                }
            }
        }

        mReurn = (TextView) findViewById(R.id.return_text);
        resourceListView = (ListViewForScrollView) findViewById(R.id.ctrlunit_list);

        resourceListView.setOnItemClickListener(this);

        // TODO
        // firmType.return_back.setOnClickListener(this);

        // sv = (ScrollView) findViewById(R.id.mScrollView);
        // sv.smoothScrollTo(0, 0);
        mList = new ArrayList();
        handler = new MsgHandler();
        resourceControl = new ResourceControl();
        resourceControl.setCallback(this);
        // initData();

        mAdapter = new ResourceListAdapter(ResourceListActivity.this, mList);
        resourceListView.setAdapter(mAdapter);

        /**
         * 请求资源列表
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                int pId = 0;
                if (Constants.Resource.TYPE_CTRL_UNIT == pResType) {
                    //   pId = pCtrlUnitId;
                } else if (Constants.Resource.TYPE_REGION == pResType) {
                    //  pId = pRegionId;
                }
                resourceControl.reqResList(pResType, pId);
            }
        }).start();
    }
//废弃代码移动到尾部

    @SuppressLint("HandlerLeak")
    private final class MsgHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                // 获取控制中心列表成功
                case MsgIds.GET_C_F_NONE_SUC:
                    // 从控制中心获取下级资源列表成功
                case MsgIds.GET_SUB_F_C_SUC:
                    // 从区域获取下级列表成功
                case MsgIds.GET_SUB_F_R_SUC:
                    refreshResList((ArrayList) msg.obj);
                    break;
                // 获取控制中心列表失败
                case MsgIds.GET_C_F_NONE_FAIL:
                    // 调用getControlUnitList失败
                case MsgIds.GET_CU_F_CU_FAIL:
                    // 调用getRegionListFromCtrlUnit失败
                case MsgIds.GET_R_F_C_FAIL:
                    // 调用getCameraListFromCtrlUnit失败
                case MsgIds.GET_C_F_C_FAIL:
                    // 从控制中心获取下级资源列表成失败
                case MsgIds.GET_SUB_F_C_FAIL:
                    // 调用getRegionListFromRegion失败
                case MsgIds.GET_R_F_R_FAIL:
                    // 调用getCameraListFromRegion失败
                case MsgIds.GET_C_F_R_FAIL:
                    // 从区域获取下级列表失败
                case MsgIds.GET_SUB_F_R_FAILED:
                    // onGetResListFailed();
                    break;
                case GOTO_LIVE_OR_PLAYBACK:
                    CameraInfo cInfo = (CameraInfo) msg.obj;
                    gotoLiveorPlayBack(cInfo);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 调用接口失败时，界面弹出提示
     */
    // private void onGetResListFailed() {
    // UIUtil.showToast(this,
    // getString(R.string.fetch_reslist_failed, UIUtil.getErrorDesc()));
    // }

    /**
     * 获取数据成功后刷新列表
     *
     * @param data
     */
    // TODO
    @SuppressWarnings("unchecked")
    private void refreshResList(ArrayList data) {
        String json = new Gson().toJson(data);
        LogUtils.w("shipin", "刷新的数据:" + json);

        if (data == null || data.isEmpty()) {
            showToast(R.string.no_data_tip);
            return;
        }
        // UIUtil.showToast(this, R.string.fetch_resource_suc);
        if (!isName) {

            for (int i = 0; i < data.size(); i++) {
                final Object itemData = data.get(i);
                if (itemData instanceof CameraInfo) {//CameraInfo extends Camera     获取到了摄像头的信息,结束dialog
                    CameraInfo info = (CameraInfo) itemData;
                    com.heking.qsy.activity.FirmShow.FirmType.enddialog();
                }
                //这个代码明显无用呀???????????????????
                if (itemData instanceof RegionInfo) {
                    RegionInfo info = (RegionInfo) itemData;
                    info.getName();
                }
                if (itemData instanceof ControlUnitInfo) {// ControlUnitInfo extends ControlUnit
                    ControlUnitInfo info = (ControlUnitInfo) itemData;

                    if (info.getName().equals("主控中心")) {
                        //第一次请求
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                if (itemData instanceof CameraInfo) {
                                    CameraInfo info = (CameraInfo) itemData;
                                    // 得到摄像头，进行预览或者回放
                                    onInfoResponse(GOTO_LIVE_OR_PLAYBACK, info);
                                } else {
                                    int pId = 0;
                                    if (itemData instanceof ControlUnitInfo) {
                                        ControlUnitInfo info = (ControlUnitInfo) itemData;
                                        pResType = Constants.Resource.TYPE_CTRL_UNIT;
                                        pId = Integer.parseInt(info.getControlUnitID());
                                    }
                                    if (itemData instanceof RegionInfo) {
                                        RegionInfo info = (RegionInfo) itemData;
                                        pResType = Constants.Resource.TYPE_REGION;
                                        pId = Integer.parseInt(info.getRegionID());
                                    }
                                    resourceControl.reqResList(pResType, pId);
                                }
                            }
                        }).start();
                        return;
                    }

                    if (info.getName().equals(Firm_FL)) {
                        //第二次循环
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                if (itemData instanceof CameraInfo) {
                                    CameraInfo info = (CameraInfo) itemData;
                                    // 得到摄像头，进行预览或者回放

                                    onInfoResponse(GOTO_LIVE_OR_PLAYBACK, info);
                                } else {
                                    int pId = 0;
                                    if (itemData instanceof ControlUnitInfo) {
                                        ControlUnitInfo info = (ControlUnitInfo) itemData;
                                        pResType = Constants.Resource.TYPE_CTRL_UNIT;
                                        pId = Integer.parseInt(info.getControlUnitID());
                                    }

                                    if (itemData instanceof RegionInfo) {
                                        RegionInfo info = (RegionInfo) itemData;
                                        pResType = Constants.Resource.TYPE_REGION;
                                        pId = Integer.parseInt(info.getRegionID());
                                    }

                                    resourceControl.reqResList(pResType, pId);
                                }

                            }

                        }).start();
                        return;
                    }
                    if (info.getName().equals(Firmdata.getAreaName())) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                if (itemData instanceof CameraInfo) {
                                    CameraInfo info = (CameraInfo) itemData;
                                    // 得到摄像头，进行预览或者回放

                                    onInfoResponse(GOTO_LIVE_OR_PLAYBACK, info);
                                } else {
                                    int pId = 0;
                                    if (itemData instanceof ControlUnitInfo) {
                                        ControlUnitInfo info = (ControlUnitInfo) itemData;
                                        pResType = Constants.Resource.TYPE_CTRL_UNIT;
                                        pId = Integer.parseInt(info.getControlUnitID());
                                    }
                                    if (itemData instanceof RegionInfo) {
                                        RegionInfo info = (RegionInfo) itemData;
                                        pResType = Constants.Resource.TYPE_REGION;
                                        pId = Integer.parseInt(info.getRegionID());
                                    }
                                    resourceControl.reqResList(pResType, pId);
                                }
                            }
                        }).start();
                        return;
                    }
                    if (info.getName().equals(FirmName)) {
                        isName = true;
                        // TODO
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if (itemData instanceof CameraInfo) {
                                    CameraInfo info = (CameraInfo) itemData;
                                    // 得到摄像头，进行预览或者回放

                                    onInfoResponse(GOTO_LIVE_OR_PLAYBACK, info);
                                } else {
                                    int pId = 0;
                                    if (itemData instanceof ControlUnitInfo) {
                                        ControlUnitInfo info = (ControlUnitInfo) itemData;
                                        pResType = Constants.Resource.TYPE_CTRL_UNIT;
                                        pId = Integer.parseInt(info.getControlUnitID());
                                    }

                                    if (itemData instanceof RegionInfo) {
                                        RegionInfo info = (RegionInfo) itemData;
                                        pResType = Constants.Resource.TYPE_REGION;
                                        pId = Integer.parseInt(info.getRegionID());
                                    }

                                    resourceControl.reqResList(pResType, pId);
                                }

                            }

                        }).start();
                        return;
                    }
                    if (info.getName().equals(FirmType)) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                if (itemData instanceof CameraInfo) {
                                    CameraInfo info = (CameraInfo) itemData;
                                    // 得到摄像头，进行预览或者回放
                                    Log.i(Constants.LOG_TAG,
                                            "get Camera:" + info.getName() + "---"
                                                    + info.getDeviceID());
                                    onInfoResponse(GOTO_LIVE_OR_PLAYBACK, info);
                                } else {
                                    int pId = 0;
                                    if (itemData instanceof ControlUnitInfo) {
                                        ControlUnitInfo info = (ControlUnitInfo) itemData;
                                        pResType = Constants.Resource.TYPE_CTRL_UNIT;
                                        pId = Integer.parseInt(info
                                                .getControlUnitID());
                                    }

                                    if (itemData instanceof RegionInfo) {
                                        RegionInfo info = (RegionInfo) itemData;
                                        pResType = Constants.Resource.TYPE_REGION;
                                        pId = Integer.parseInt(info.getRegionID());
                                    }

                                    resourceControl.reqResList(pResType, pId);
                                }

                            }

                        }).start();
                        return;

                    }
                    if (Firm_FL.equals("食品企业")) {
                        if (info.getName().contains(FirmNames)) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    if (itemData instanceof CameraInfo) {
                                        CameraInfo info = (CameraInfo) itemData;
                                        // 得到摄像头，进行预览或者回放
                                        Log.i(Constants.LOG_TAG,
                                                "get Camera:" + info.getName()
                                                        + "---"
                                                        + info.getDeviceID());
                                        onInfoResponse(GOTO_LIVE_OR_PLAYBACK, info);
                                    } else {
                                        int pId = 0;
                                        if (itemData instanceof ControlUnitInfo) {
                                            ControlUnitInfo info = (ControlUnitInfo) itemData;
                                            pResType = Constants.Resource.TYPE_CTRL_UNIT;
                                            pId = Integer.parseInt(info
                                                    .getControlUnitID());
                                        }

                                        if (itemData instanceof RegionInfo) {
                                            RegionInfo info = (RegionInfo) itemData;
                                            pResType = Constants.Resource.TYPE_REGION;
                                            pId = Integer.parseInt(info
                                                    .getRegionID());
                                        }

                                        resourceControl.reqResList(pResType, pId);
                                    }

                                }
                            }).start();
                            return;

                        }

                    }
                    if (Firm_FL.equals("药品企业") && !FirmType.equals("生产企业")
                            && !FirmType.equals("医疗机构")) {
                        if (info.getName().contains(FirmNames)) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    if (itemData instanceof CameraInfo) {
                                        CameraInfo info = (CameraInfo) itemData;
                                        // 得到摄像头，进行预览或者回放
                                        Log.i(Constants.LOG_TAG,
                                                "get Camera:" + info.getName()
                                                        + "---"
                                                        + info.getDeviceID());
                                        onInfoResponse(GOTO_LIVE_OR_PLAYBACK, info);
                                    } else {
                                        int pId = 0;
                                        if (itemData instanceof ControlUnitInfo) {
                                            ControlUnitInfo info = (ControlUnitInfo) itemData;
                                            pResType = Constants.Resource.TYPE_CTRL_UNIT;
                                            pId = Integer.parseInt(info
                                                    .getControlUnitID());
                                        }

                                        if (itemData instanceof RegionInfo) {
                                            RegionInfo info = (RegionInfo) itemData;
                                            pResType = Constants.Resource.TYPE_REGION;
                                            pId = Integer.parseInt(info
                                                    .getRegionID());
                                        }
                                        resourceControl.reqResList(pResType, pId);
                                    }
                                }
                            }).start();
                            return;
                        }
                    }
                }

            }
            Objectdatalist.clear();//开始添加摄像头信息
            for (int i = 0; i < data.size(); i++) {
                Object itemData = data.get(i);
                if (itemData instanceof CameraInfo) {
                    CameraInfo info = (CameraInfo) itemData;
                    if (info.getName().equals(FirmName)) {
                        Objectdatalist.add(info);
                    }
                }
            }

            if (Objectdatalist.size() <= 0) {
                dismissWaitDialog();
                showToast(R.string.no_camera_suc);
                mReurn.setVisibility(LinearLayout.GONE);
            } else {
                if (isReturn) {
                    isReturn = false;
                    Savelist = Objectdatalist;
                } else {
                    mReurn.setVisibility(LinearLayout.VISIBLE);
                }
                dismissWaitDialog();
                mAdapter.setData(Objectdatalist);
            }
        } else {
            if (data.size() <= 0) {
                mReurn.setVisibility(LinearLayout.GONE);
                showToast(R.string.no_camera_suc);
            } else {
                if (isReturn) {
                    isReturn = false;
                    Savelist = data;
                } else {
                    mReurn.setVisibility(LinearLayout.VISIBLE);
                }
                dismissWaitDialog();
                mAdapter.setData(data);
            }
        }
        mReurn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mAdapter.setData(Savelist);
            }
        });
    }

    private ArrayList Objectdatalist = new ArrayList<Object>();
    private boolean isName = false;
    private boolean isReturn = true;
    private ArrayList<Object> Savelist = new ArrayList<Object>();

    @Override
    public void onInfoResponse(int msgId, Object data) {
        Message msg = Message.obtain();
        msg.what = msgId;
        msg.obj = data;
        handler.sendMessage(msg);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Object itemData = mAdapter.getItem(position);
        // String itemName = getItemName(itemData);
        if (itemData instanceof CameraInfo) {
            /*
             * CameraInfo info = (CameraInfo) itemData; // 得到摄像头，进行预览或者回放
			 * Log.i(Constants.LOG_TAG, "get Camera:" + info.getName() + "---" +
			 * info.getDeviceID()); onInfoResponse(GOTO_LIVE_OR_PLAYBACK, info);
			 */
            CameraInfo info = (CameraInfo) itemData;
            if (info == null) {
                Log.e(Constants.LOG_TAG, "gotoLive():: fail");
                return;
            }
            Intent intent = new Intent(ResourceListActivity.this, LiveActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Constants.IntentKey.CAMERA_ID, info.getId());
            bundle.putInt("position", position);
            bundle.putString("serAddr", Config.getIns().getServerAddr());
            bundle.putString("mServInfo", new Gson().toJson(TempData.getIns().getLoginData()));
            intent.putExtras(bundle);

            TempData.getIns().setCameraInfo(info);
            startActivity(intent);
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int pId = 0;
                    if (itemData instanceof ControlUnitInfo) {
                        ControlUnitInfo info = (ControlUnitInfo) itemData;
                        pResType = Constants.Resource.TYPE_CTRL_UNIT;
                        pId = Integer.parseInt(info.getControlUnitID());
                    }

                    if (itemData instanceof RegionInfo) {
                        RegionInfo info = (RegionInfo) itemData;
                        pResType = Constants.Resource.TYPE_REGION;
                        pId = Integer.parseInt(info.getRegionID());
                    }
                    resourceControl.reqResList(pResType, pId);
                }

            }).start();
        }
    }

    private void gotoLiveorPlayBack(final CameraInfo info) {
        gotoLive(info);
        //
        // String[] datas = new String[] { "预览" };
        // mDialog = new AlertDialog.Builder(ResourceListActivity.this)
        // .setSingleChoiceItems(datas, 0,
        // new DialogInterface.OnClickListener() {
        //
        // @Override
        // public void onClick(DialogInterface dialog,
        // int which) {
        // mDialog.dismiss();
        // switch (which) {
        // case 0:
        // gotoLive(info);
        // break;
        // case 1:
        // gotoPlayback(info);
        // break;
        // default:
        // break;
        // }
        // }
        //
        // }).create();
        // mDialog.show();
    }

    /**
     * 实时预览
     *
     * @param info
     */
    private void gotoLive(CameraInfo info) {
        if (info == null) {
            Log.e(Constants.LOG_TAG, "gotoLive():: fail");
            return;
        }
        Intent intent = new Intent(ResourceListActivity.this,
                LiveActivity.class);
        intent.putExtra(Constants.IntentKey.CAMERA_ID, info.getId());
        TempData.getIns().setCameraInfo(info);
        ResourceListActivity.this.startActivity(intent);
    }

    /**
     * 远程回放
     *
     * @param info
     */
    private void gotoPlayback(CameraInfo info) {
        if (info == null) {
            Log.e(Constants.LOG_TAG, "gotoPlayback():: fail");
            return;
        }
        Intent intent = new Intent(ResourceListActivity.this,
                PlayBackActivity.class);
        intent.putExtra(Constants.IntentKey.CAMERA_ID, info.getId());
        startActivity(intent);
    }

    // private void setListViewHeightBasedOnChildren(ListView listView) {
    // ListAdapter listAdapter = listView.getAdapter();
    // if (listAdapter == null) {
    // return;
    // }
    //
    // int totalHeight = 0;
    // for (int i = 0; i < listAdapter.getCount(); i++) {
    // View listItem = listAdapter.getView(i, null, listView);
    // listItem.measure(0, 0);
    // totalHeight += listItem.getMeasuredHeight();
    // }
    //
    // ViewGroup.LayoutParams params = listView.getLayoutParams();
    // params.height = totalHeight
    // + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
    // listView.setLayoutParams(params);
    // }

    // /**
    // * 初始化数据
    // */
    // private void initData() {
    // Intent intent = getIntent();
    // if (intent.hasExtra(Constants.IntentKey.CONTROL_UNIT_ID)) {
    // pResType = Constants.Resource.TYPE_CTRL_UNIT;
    // pCtrlUnitId = intent
    // .getIntExtra(Constants.IntentKey.CONTROL_UNIT_ID, 0);
    // Log.i(Constants.LOG_TAG,
    // "Getting resource from ctrlunit.parent id is "
    // + pCtrlUnitId);
    // } else if (intent.hasExtra(Constants.IntentKey.REGION_ID)) {
    // pResType = Constants.Resource.TYPE_REGION;
    // pRegionId = intent.getIntExtra(Constants.IntentKey.REGION_ID, 0);
    // Log.i(Constants.LOG_TAG,
    // "Getting resource from region. parent id is " + pRegionId);
    // } else {
    // pResType = Constants.Resource.TYPE_UNKNOWN;
    // Log.i(Constants.LOG_TAG, "Getting resource for the first time.");
    // }
    // }
}
