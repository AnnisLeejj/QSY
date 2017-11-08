package com.heking.qsy.activity.ConvenienceService;

import com.heking.SPJK.resource.ResourceListAdapter;
import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.FirmShow.MonitorsAdapter;
import com.heking.qsy.util.FirmTypeBean;
import com.heking.qsy.util.Tool;
import com.heking.qsy.util.FirmTypeBean.Data;

import com.heking.qsy.base.BaseActivity;
import com.hikvision.vmsnetsdk.CameraInfo;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ListActivity extends BaseActivity {
    private FirmTypeBean.Data data;
    private ListView mListView;
    private MonitorsAdapter adapter;
    ArrayList<CameraInfo> cameraInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppContext.THEME) {
            setTheme(R.style.SwitchTheme_1);
        } else {
            setTheme(R.style.SwitchTheme_2);
        }
        setContentView(R.layout.list_activity);
        Tool.endActivity(this);
        data = (Data) getIntent().getExtras().getSerializable("FIRMTYPE");

        mListView = (ListView) findViewById(R.id.list_activity_view_sss);

        cameraInfos = new ArrayList<CameraInfo>();
        if (AppContext.shipins_toshow != null) {

            for (Data.Monitors item : data.getMonitors()) {
                for (com.hikvision.vmsnetsdk.CameraInfo item1 : AppContext.shipins_toshow) {
                    if (item.getName().equals(item1.getName())) {
                        cameraInfos.add(item1);
                        continue;
                    }
                }
            } ResourceListAdapter.mList = cameraInfos;
        }

        adapter = new MonitorsAdapter(cameraInfos, this,true);

        mListView.setAdapter(adapter);
    }

}
