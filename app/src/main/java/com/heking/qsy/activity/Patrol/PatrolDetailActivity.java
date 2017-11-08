package com.heking.qsy.activity.Patrol;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.base.BaseActivity;
import com.heking.qsy.util.ImageUtil;
import com.heking.snoa.WPConfig;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import MyUtils.LogUtils.LogUtils;

public class PatrolDetailActivity extends BaseActivity
        implements OnClickListener, PatrolDetailAdapter.OnClickRemarkListener {
    ImageButton ibtnR;
    ExpandableListView elv;
    PatrolDetailAdapter patrolDetailAdapter;
    View footerView;
    ContentType contentType = ContentType.create;

    EditText etResult, etOpinion;
    ImageView ivQianzi1, ivQianzi2;

    public final static int CONSULT_DOC_PICTURE = 1000;
    public final static int CONSULT_DOC_CAMERA = 1001;
    private int SELECT_PICTURE = 0;
    private int SELECT_CAMERA = 1;
    private Uri outputFileUri;
    // 从相册或照相机选择出的文件，可以用来判断是否上传
    File selectImg;
    LinearLayout llImageRemark;
    ImageButton ibAdd;
    Button btnPrintl, btnSubmit;
    TextView textview01;
    String enterpriseType;
    String enterpriseName;
    String xieguan;
    String xieTongRen;
    String inspectTable = "";
    String enterpriseId;
    InspectTableBean inspectTableBean;
    private List<PatrolTableGroup> projects;
    PatrolTableChild selectPatrolTableChild;// 现在被选中item的数据
    String FirmSign;
    String InspectSign;
    List<InspectAttachmentsBean> InspectAttachmentsBeanList;

    String InspectRecoredID = "";
    // ContentTy

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_detail);
        ibtnR = (ImageButton) findViewById(R.id.ibtnR);
        ibtnR.setOnClickListener(this);
        textview01 = (TextView) findViewById(R.id.textview01);
        elv = (ExpandableListView) findViewById(R.id.elv);
        elv.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition,
                                        long id) {
                return false;
            }
        });

        footerView = LayoutInflater.from(this).inflate(R.layout.layout_patrol_detail_footer, null);
        elv.addFooterView(footerView);
        ivQianzi1 = (ImageView) footerView.findViewById(R.id.ivQianzi1);
        ivQianzi2 = (ImageView) footerView.findViewById(R.id.ivQianzi2);
        btnPrintl = (Button) footerView.findViewById(R.id.btnPrintl);
        btnSubmit = (Button) footerView.findViewById(R.id.btnSubmit);

        etResult = (EditText) footerView.findViewById(R.id.etResult);
        etOpinion = (EditText) footerView.findViewById(R.id.etOpinion);
        ivQianzi1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (contentType == ContentType.create) {
                    qianzi(1);
                }
            }
        });
        ivQianzi2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contentType == ContentType.create) {
                    qianzi(2);
                }
            }
        });

        btnPrintl.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                downFile(inspectTableBean.getInspectResultTable());

            }
        });
        if (getIntent().getSerializableExtra("contentType") != null) {
            contentType = (ContentType) getIntent().getSerializableExtra("contentType");
        }

        projects = new ArrayList<PatrolTableGroup>();
        patrolDetailAdapter = new PatrolDetailAdapter(this, projects, contentType, this);
        elv.setAdapter(patrolDetailAdapter);
        elv.setGroupIndicator(null);

        for (int i = 0; i < patrolDetailAdapter.getGroupCount(); i++) {
            elv.expandGroup(i);
        }

        if (contentType == ContentType.create) {
            enterpriseType = getIntent().getStringExtra("enterpriseType");
            if (!TextUtils.isEmpty(getIntent().getStringExtra("enterpriseName"))) {
                enterpriseName = getIntent().getStringExtra("enterpriseName");
            }
            if (!TextUtils.isEmpty(getIntent().getStringExtra("xieguan"))) {
                xieguan = getIntent().getStringExtra("xieguan");
            }
            if (!TextUtils.isEmpty(getIntent().getStringExtra("xieTongRen"))) {
                xieTongRen = getIntent().getStringExtra("xieTongRen");
            }
            if (!TextUtils.isEmpty(getIntent().getStringExtra("inspectTable"))) {
                inspectTable = getIntent().getStringExtra("inspectTable");
            }
            if (!TextUtils.isEmpty(getIntent().getStringExtra("enterpriseId"))) {
                enterpriseId = getIntent().getStringExtra("enterpriseId");
            }
        } else {
            btnSubmit.setVisibility(View.GONE);

            etOpinion.setEnabled(false);
            etResult.setEnabled(false);

            if (!TextUtils.isEmpty(getIntent().getStringExtra("InspectRecoredID"))) {
                InspectRecoredID = getIntent().getStringExtra("InspectRecoredID");
            }
        }
        getData(inspectTable, InspectRecoredID);
        btnSubmit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("TableID", inspectTable);
                    jsonObject.put("FirmID", TextUtils.isEmpty(enterpriseId) ? "" : enterpriseId);
                    jsonObject.put("FirmTypeName", enterpriseType);

                    if (!TextUtils.isEmpty(enterpriseName)) {
                        jsonObject.put("FirmName", enterpriseName);
                    } else {
                        Toast.makeText(PatrolDetailActivity.this, "企业不能空的", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!TextUtils.isEmpty(xieguan)) {
                        jsonObject.put("InspectPeople", xieguan);
                    } else {
                        Toast.makeText(PatrolDetailActivity.this, "食药监管员不能空的", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!TextUtils.isEmpty(etResult.getText().toString())) {
                        jsonObject.put("CheckResultContent", etResult.getText().toString());
                    } else {
                        Toast.makeText(PatrolDetailActivity.this, "请填写检查结果", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!TextUtils.isEmpty(etOpinion.getText().toString())) {
                        jsonObject.put("InspectOpinion", etOpinion.getText().toString());
                    }
                    if (!TextUtils.isEmpty(FirmSign)) {
                        jsonObject.put("FirmSign", FirmSign);
                    } else {
                        Toast.makeText(PatrolDetailActivity.this, "受检方没有签名！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!TextUtils.isEmpty(InspectSign)) {
                        jsonObject.put("InspectSign", InspectSign);
                    } else {
                        Toast.makeText(PatrolDetailActivity.this, "巡查方没有签名！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    JSONArray recordItemsJsonArray = new JSONArray();
                    for (int i = 0; i < projects.size(); i++) {
                        PatrolTableGroup patrolTableGroup = projects.get(i);
                        if (patrolTableGroup.getItems() != null && patrolTableGroup.getItems().size() > 0) {
                            for (int j = 0; j < patrolTableGroup.getItems().size(); j++) {


                                PatrolTableChild patrolTableChild = patrolTableGroup.getItems().get(j);

                                if (patrolTableChild.getRate() != 1 && patrolTableChild.getRate() != 2) {

                                    Toast.makeText(PatrolDetailActivity.this, "请勾选【" + patrolTableChild.getItemCode() + "】项", Toast.LENGTH_SHORT).show();

                                    return;
                                }
                                JSONObject recordJSONObject = new JSONObject();
                                recordJSONObject.put("ItemID", patrolTableChild.getID());
                                recordJSONObject.put("Rate", patrolTableChild.getRate());
                                recordJSONObject.put("Remark", patrolTableChild.getRemark());
                                JSONArray InspectAttachmentJsonArray = new JSONArray();
                                if (patrolTableChild.getInspectAttachments() != null
                                        && patrolTableChild.getInspectAttachments().size() > 0) {
                                    for (int x = 0; x < patrolTableChild.getInspectAttachments().size(); x++) {
                                        InspectAttachmentsBean inspectAttachmentsBean = patrolTableChild
                                                .getInspectAttachments().get(x);
                                        JSONObject InspectAttachmentJSONObject = new JSONObject();
                                        InspectAttachmentJSONObject.put("FileID", inspectAttachmentsBean.getFileID());
                                        InspectAttachmentJsonArray.put(InspectAttachmentJSONObject);
                                    }
                                }
                                recordJSONObject.put("InspectAttachments", InspectAttachmentJsonArray);
                                recordItemsJsonArray.put(recordJSONObject);
                            }
                        }
                    }
                    jsonObject.put("InspectRecordItems", recordItemsJsonArray);
                    saveInpectionRecord(jsonObject.toString());

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtnR:
                finish();
                break;

        }
    }

    @Override
    public void onClick(PatrolTableChild patrolTableChild) {
        //ExpandableListAdapter 备注的填写
        selectPatrolTableChild = patrolTableChild;
        if (patrolTableChild.getInspectAttachments() != null && patrolTableChild.getInspectAttachments().size() > 0) {

            InspectAttachmentsBeanList = patrolTableChild.getInspectAttachments();
        } else {
            InspectAttachmentsBeanList = null;
        }
        View v = LayoutInflater.from(this).inflate(R.layout.layout_patrol_detail_remark, null);
        final PopupWindow popupWindow = new PopupWindow(v, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        final EditText etRemark = (EditText) v.findViewById(R.id.etRemark);
        llImageRemark = (LinearLayout) v.findViewById(R.id.llImageRemark);
        ibAdd = (ImageButton) v.findViewById(R.id.ibAdd);
        Button btnSave = (Button) v.findViewById(R.id.btnSave);
        Button btnCancel = (Button) v.findViewById(R.id.btnCancel);

        if (contentType == ContentType.update) {
            ibAdd.setVisibility(View.GONE);
            btnSave.setVisibility(View.GONE);
            etRemark.setEnabled(false);
        }

        if (!TextUtils.isEmpty(selectPatrolTableChild.getRemark())) {

            etRemark.setText(selectPatrolTableChild.getRemark());
        }
        if (InspectAttachmentsBeanList != null && InspectAttachmentsBeanList.size() > 0) {
            if (InspectAttachmentsBeanList.size() >= 3) {
                ibAdd.setVisibility(View.GONE);
            }
            for (int i = 0; i < InspectAttachmentsBeanList.size(); i++) {
                final View imgContent = LayoutInflater.from(this).inflate(R.layout.layout_patrol_detail_remark_image,
                        null);
                ImageView ivContent = (ImageView) imgContent.findViewById(R.id.ivContent);
                ImageButton ibdlt = (ImageButton) imgContent.findViewById(R.id.ibdlt);
                ibdlt.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int index = llImageRemark.indexOfChild(imgContent);

                        llImageRemark.removeViewAt(index);
                        InspectAttachmentsBeanList.remove(index);

                    }
                });
                llImageRemark.addView(imgContent, 0);
                BitmapUtils bitmapUtils = new BitmapUtils(this);
                String imgurl = WPConfig.IMAGE_VIEW_01 + InspectAttachmentsBeanList.get(i).getFileID();
                // 加载网络图片
                bitmapUtils.display(ivContent, imgurl);
            }
        }

        ibAdd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                createSelectImageDialog();
            }
        });

        btnCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        btnSave.setOnClickListener(new OnClickListener() {
            //备注填写完成后  ,提交
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if (!TextUtils.isEmpty(etRemark.getText().toString())) {
                    selectPatrolTableChild.setRemark(etRemark.getText().toString());
                }

                if (InspectAttachmentsBeanList != null && InspectAttachmentsBeanList.size() > 0) {
                    selectPatrolTableChild.setInspectAttachments(InspectAttachmentsBeanList);
                    patrolDetailAdapter.notifyDataSetChanged();
                }
            }
        });

        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        WindowManager.LayoutParams lp = getWindow().getAttributes();

        lp.alpha = 0.7f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
        // 点击外面popupWindow消失
        popupWindow.setOutsideTouchable(true);
        // popupWindow获取焦点
        popupWindow.setFocusable(true);
        // 设置popupWindow消失时的监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    protected void createSelectImageDialog() {
        CharSequence[] items = {"相册", "照相机"};
        new AlertDialog.Builder(this).setTitle("选择图片来源").setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == SELECT_PICTURE) {
                    Intent intent = new Intent();
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                    } else {
                        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                    }
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "选择图片"), CONSULT_DOC_PICTURE);
                } else {
                    File file = new File(Environment.getExternalStorageDirectory(), "avator.jpg");
                    outputFileUri = Uri.fromFile(file);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                    startActivityForResult(intent, CONSULT_DOC_CAMERA);
                }
            }
        }).create().show();
    }

    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bmp = null;
        if (requestCode == CONSULT_DOC_PICTURE) {

            if (data == null) {
                return;
            }

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                Uri contentUri = data.getData();
                String wholeID = DocumentsContract.getDocumentId(contentUri);
                String id = wholeID.split(":")[1];
                String[] column = {MediaStore.Images.Media.DATA};
                String sel = MediaStore.Images.Media._ID + "=?";
                Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel,
                        new String[]{id}, null);
                int columnIndex = cursor.getColumnIndex(column[0]);
                if (cursor.moveToFirst()) {
                    String path = cursor.getString(columnIndex);
                    bmp = BitmapFactory.decodeFile(path);
                    // avator.setImageBitmap(bmp);
                    selectImg = new File(path);
                    addBmp(bmp);
                }
                cursor.close();
            } else {
                Uri uri = data.getData();
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = this.getContentResolver().query(uri, proj, // Which
                        null, // WHERE clause; which rows to return (all rows)
                        null, // WHERE clause selection arguments (none)
                        null); // Order-by clause (ascending by name)
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String path = cursor.getString(column_index);
                bmp = BitmapFactory.decodeFile(path);
                // avator.setImageBitmap(bmp);
                addBmp(bmp);
                selectImg = new File(path);
                cursor.close();
            }
        } else if (requestCode == CONSULT_DOC_CAMERA) {
            bmp = BitmapFactory.decodeFile(outputFileUri.getPath());
            // avator.setImageBitmap(bmp);
            addBmp(bmp);
            selectImg = new File(outputFileUri.getPath());
        } else {
            Toast.makeText(this, "请重新选择图片", Toast.LENGTH_SHORT).show();
        }
    }

    private void addBmp(Bitmap bmp) {
        final View imgContent = LayoutInflater.from(this).inflate(R.layout.layout_patrol_detail_remark_image, null);
        ImageView ivContent = (ImageView) imgContent.findViewById(R.id.ivContent);
        ImageButton ibdlt = (ImageButton) imgContent.findViewById(R.id.ibdlt);
        ivContent.setImageBitmap(bmp);

        ibdlt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = llImageRemark.indexOfChild(imgContent);

                llImageRemark.removeViewAt(index);
                InspectAttachmentsBeanList.remove(index);

            }
        });
        llImageRemark.addView(imgContent, 0);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bs = baos.toByteArray();
        InputStream isBm = new ByteArrayInputStream(bs);
        saveFile(isBm, bs.length, new UpLoadListener() {
            @Override
            public void result(String fileId) {

                if (InspectAttachmentsBeanList == null) {
                    InspectAttachmentsBeanList = new ArrayList<InspectAttachmentsBean>();
                }
                InspectAttachmentsBean inspectAttachmentsBean = new InspectAttachmentsBean();
                inspectAttachmentsBean.setFileID(fileId);
                InspectAttachmentsBeanList.add(inspectAttachmentsBean);
            }
        });
    }

    void qianzi(final int flag) {
        View v = LayoutInflater.from(this).inflate(R.layout.layout_sign_dialog, null);
        final PopupWindow popupWindow = new PopupWindow(v, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        final LinePathView linePathView = (LinePathView) v.findViewById(R.id.linePathView);
        Button btnCancel = (Button) v.findViewById(R.id.btnCancel);
        Button btnClear = (Button) v.findViewById(R.id.btnClear);
        Button btnOK = (Button) v.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linePathView.getTouched()) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    Bitmap bitMap = linePathView.getBitMap();
                    bitMap = ImageUtil.drawTextToCenter(getApplication(), bitMap, "仅用于犍食药", 30, Color.argb(80, 82, 82, 82));
                    bitMap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] bs = baos.toByteArray();
                    InputStream isBm = new ByteArrayInputStream(bs);
                    if (flag == 1) {
                        ivQianzi1.setImageBitmap(bitMap);
                        saveFile(isBm, bs.length, new UpLoadListener() {
                            @Override
                            public void result(String fileId) {
                                FirmSign = fileId;
                            }
                        });
                    } else {
                        ivQianzi2.setImageBitmap(bitMap);
                        saveFile(isBm, bs.length, new UpLoadListener() {
                            @Override
                            public void result(String fileId) {
                                InspectSign = fileId;
                            }
                        });
                    }
                    popupWindow.dismiss();
                } else {
                    Toast.makeText(PatrolDetailActivity.this, "您没有签名~", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnClear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                linePathView.clear();
            }
        });

        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        // 点击外面popupWindow消失
        popupWindow.setOutsideTouchable(true);
        // popupWindow获取焦点
        popupWindow.setFocusable(true);
        // 设置popupWindow消失时的监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    private void getData(String inspectTable1, String InspectRecoredID) {
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("TableID", inspectTable1);
        params.addQueryStringParameter("InspectRecoredID", InspectRecoredID);
        HttpUtils http = new HttpUtils();
        http.send(HttpMethod.GET, WPConfig.URL_API_INTRANET + "Inspection/GetInspectProjects", params,
                new RequestCallBack<String>() {
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        cancelProgress();
                        if (!TextUtils.isEmpty(responseInfo.result)) {
                            try {
                                JSONObject jsonObject = new JSONObject(responseInfo.result);
                                int code = jsonObject.getInt("code");
                                if (code == 200) {
                                    JSONObject data = jsonObject.optJSONObject("data");
                                    if (data != null) {
                                        Gson gson = new GsonBuilder().serializeNulls().create();
                                        inspectTableBean = gson.fromJson(data.toString(), InspectTableBean.class);

                                        projects.clear();
                                        projects.addAll(inspectTableBean.getProjects());
                                        String tableName = inspectTableBean.getTableName();
                                        if(tableName.length()>15){
                                            tableName =  tableName.substring(0,12)+"...";
                                        }
                                        textview01.setText(tableName);
                                        patrolDetailAdapter.notifyDataSetChanged();

                                        if (contentType == ContentType.update) {
                                            enterpriseType = inspectTableBean.getFirmType();
                                            enterpriseName = inspectTableBean.getFirmName();
                                            xieguan = inspectTableBean.getInspectPeople();
                                            enterpriseId = inspectTableBean.getFirmID();
                                            FirmSign = inspectTableBean.getFirmSign();
                                            InspectSign = inspectTableBean.getInspectSign();
                                            inspectTable = inspectTableBean.getTableName();

                                            BitmapUtils bitmapUtils = new BitmapUtils(PatrolDetailActivity.this);
                                            String imgurl = WPConfig.IMAGE_VIEW_01 + FirmSign;
                                            // 加载网络图片
                                            bitmapUtils.display(ivQianzi1, imgurl);
                                            String imgurl2 = WPConfig.IMAGE_VIEW_01 + InspectSign;
                                            // 加载网络图片
                                            bitmapUtils.display(ivQianzi2, imgurl2);
                                            String InspectOpinion = inspectTableBean.getInspectOpinion();
                                            String CheckResultContent = inspectTableBean.getCheckResultContent();

                                            etOpinion.setText(InspectOpinion);
                                            etResult.setText(CheckResultContent);
                                            if (TextUtils.isEmpty(inspectTableBean.getInspectResultTable())) {
                                                btnPrintl.setVisibility(View.GONE);
                                            } else {
                                                btnPrintl.setVisibility(View.VISIBLE);
                                            }
                                        } else {
                                            btnPrintl.setVisibility(View.GONE);
                                        }
                                        for (int i = 0; i < patrolDetailAdapter.getGroupCount(); i++) {
                                            elv.expandGroup(i);
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onStart() {
                        showProgress();
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        cancelProgress();
                    }
                });

    }

    private void saveFile(InputStream is, long length, final UpLoadListener upLoadListener) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("file", is, length, "file", "image/*");
        HttpUtils http = new HttpUtils();
        http.send(HttpMethod.POST, WPConfig.URL_API_HOST + "yztqw/InspectAPI/Inspection/SavefILE", params,//AppContext.Url.URL_API_HOST
                new RequestCallBack<String>() {
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        cancelProgress();
                        if (!TextUtils.isEmpty(responseInfo.result)) {
                            try {
                                JSONObject jsonObject = new JSONObject(responseInfo.result);
                                int code = jsonObject.getInt("code");
                                if (code == 200) {
                                    JSONObject data = jsonObject.optJSONObject("data");
                                    String FileID = data.getString("FileID");
                                    upLoadListener.result(FileID);

                                }
                            } catch (JSONException e) {

                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onStart() {
                        showProgress();
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        cancelProgress();
                    }
                });
    }

    public interface UpLoadListener {
        void result(String fileId);
    }

    private void saveInpectionRecord(String json) {
        RequestParams params = new RequestParams("UTF-8");
        params.addBodyParameter("InspectionRecord", json);
        LogUtils.w("xuanjian", json);
        HttpUtils http = new HttpUtils();
        http.send(HttpMethod.POST, WPConfig.URL_API_INTRANET + "Inspection/SaveInpectionRecord", params,
                new RequestCallBack<String>() {
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        if (!TextUtils.isEmpty(responseInfo.result)) {
                            try {
                                JSONObject jsonObject = new JSONObject(responseInfo.result);
                                int code = jsonObject.getInt("code");
                                if (code == 200) {
                                    Toast.makeText(PatrolDetailActivity.this, "操作成功！", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(PatrolDetailActivity.this, "操作失败！", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        cancelProgress();
                    }

                    @Override
                    public void onStart() {
                        showProgress();
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        cancelProgress();
                    }
                });
    }

    public void downFile(String FileId) {

        String localPath = getExternalFilesDir("down").getPath() + System.currentTimeMillis() + ".doc";
        HttpUtils http = new HttpUtils();
        http.download(WPConfig.IMAGE_VIEW_01 + FileId, localPath, true,
                // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
                true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
                new RequestCallBack<File>() {
                    @Override
                    public void onStart() {
                        showProgress();
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {

                    }

                    @Override
                    public void onSuccess(ResponseInfo<File> responseInfo) {
                        cancelProgress();
                        if (responseInfo.result != null) {
                            File file = responseInfo.result;
                            try {

                                String data_type = "application/msword";
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setPackage("com.dynamixsoftware.printershare");// 测试版
                                i.setDataAndType(Uri.fromFile(file), data_type);
                                startActivity(i);
                            } catch (Exception e) {
                                // 没有找到printershare
                                Toast.makeText(PatrolDetailActivity.this, "没有找到printershare", Toast.LENGTH_SHORT)
                                        .show();
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        cancelProgress();
                    }
                });
    }

    enum ContentType {
        create, update
    }

    public void showProgress() {
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = ProgressDialog.show(this, "提示", "正在加载中。。。");
        }
    }

    public void cancelProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

}