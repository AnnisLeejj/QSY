package com.heking.qsy.activity.Patrol;

import java.io.File;
import java.util.List;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.activity.Patrol.PatrolDetailActivity.ContentType;
import com.lidroid.xutils.BitmapUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import MyUtils.LogUtils.LogUtils;

public class PatrolDetailAdapter extends BaseExpandableListAdapter {
    OnClickRemarkListener onClickRemarkListener;
    private List<PatrolTableGroup> projects;
    private ContentType contentType;
    PatrolDetailActivity context;

    public PatrolDetailAdapter(OnClickRemarkListener onClickRemarkListener, List<PatrolTableGroup> projects,
                               ContentType contentType, PatrolDetailActivity context) {
        this.onClickRemarkListener = onClickRemarkListener;
        this.projects = projects;
        this.contentType = contentType;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return projects.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return projects.get(groupPosition).getItems().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return projects.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return projects.get(groupPosition).getItems().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patrol_detail_group, null);

        TextView tvGropTitle = (TextView) convertView.findViewById(R.id.tvGropTitle);
        PatrolTableGroup patrolTableGroup = projects.get(groupPosition);
        tvGropTitle.setText(patrolTableGroup.getProjectName());
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patrol_detail_child, null);
        TextView tvContext = (TextView) convertView.findViewById(R.id.tvContext);
        RadioGroup Rg = (RadioGroup) convertView.findViewById(R.id.Rg);
        final Button btnRemark = (Button) convertView.findViewById(R.id.btnRemark);

        final PatrolTableChild patrolTableChild = projects.get(groupPosition).getItems().get(childPosition);
        if (patrolTableChild.getRate() == 1) {
            Rg.check(R.id.rbYes);
        } else if (patrolTableChild.getRate() == 2) {
            Rg.check(R.id.rbNo);
        }

        Rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rbYes:
                        patrolTableChild.setRate(1);
                        break;
                    case R.id.rbNo:
                        patrolTableChild.setRate(2);
                        break;
                    default:
                        break;
                }

            }
        });
        LogUtils.w("adpter", "getChildView");
        if ((!TextUtils.isEmpty(patrolTableChild.getRemark())&&!patrolTableChild.getRemark().contains("(null)")) || (patrolTableChild.getInspectAttachments() != null
                && patrolTableChild.getInspectAttachments().size() > 0)) {
            btnRemark.setActivated(true);
            btnRemark.setTextColor(Color.parseColor("#3cafdf"));
        } else {
            btnRemark.setActivated(false);
            btnRemark.setTextColor(Color.parseColor("#808080"));
        }

        LogUtils.w("xuanjian", patrolTableChild.getRemark());
        if (contentType == ContentType.update) {
            for (int i = 0; i < Rg.getChildCount(); i++) {
                Rg.getChildAt(i).setEnabled(false);
            }
        }
        tvContext.setText(patrolTableChild.getItemCode() + "  " + patrolTableChild.getInspectContent());
        btnRemark.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRemarkListener.onClick(patrolTableChild);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    interface OnClickRemarkListener {
        void onClick(PatrolTableChild patrolTableChild);
    }

    interface OnSaveListener {
        void onClick();
    }

    List<InspectAttachmentsBean> InspectAttachmentsBeanList;
    LinearLayout llImageRemark;
    ImageButton ibAdd;
    PatrolTableChild selectPatrolTableChild;// 现在被选中item的数据

}
