package com.heking.qsy.activity.FirmShow;

import java.util.ArrayList;
import com.heking.qsy.R;
import com.heking.qsy.util.MessageAddress;
import address_view_layout.AddressTab;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FiltrateMenu {
	private  RelativeLayout
//	   mButtonAreaAll,
//	   mButtonAreaDongQu,
//	   mButtonAreaXiQu,
//	   mButtonAreaMiYiXian,
//	   mButtonAreaYanBian,
//	   mButtonAreaRenHe,
	   
	   mButtonFirmAll,
	   mButtonFirmMonitoring,
	   mButtonFirmCY,
	   mButtonFirmLT,
	   
	   mButtonLevelAllGrade,
	   mButtonLevelAGrade,
	   mButtonLevelBGrade,
	   mButtonLevelCGrade,
	   mButtonLevelnotGrade;
		
	
	public  TextView  
//	   mSigeAreaAll,
//	   mSigeAreaDongQu,
//	   mSigeAreaXiQu,
//	   mSigeAreaMiYiXian,
//	   mSigeAreaRenHe,
//	   mSigeAreaYanBian,
	   
	   mSigeFirmAll,
	   mSigeFirmAllName,
	   mSigeFirmlName,
	   mSigeFirmMonitoring,
	   mSigeFirmcy,
	   mSigeFirmlt,
	   mSigeFirmYLJG,
	   
	   mSigeLevelAllGrade,
	   mSigeLevelAGrade,
	   mSigeLevelBGrade,
	   mSigeLevelCGrade,
	   mSigeLevelnotGrade;

	private View mView;
	
	private LinearLayout mArealayout;
	
	private String[] str = { "全部区域","玉津镇", "清溪镇", "罗城镇", "芭沟镇", "石溪镇", "新民镇", "孝姑镇",
			"龙孔镇", "定文镇", "敖家镇", "金石井镇", "泉水镇", "双溪乡", "九井乡", "同兴乡", "榨鼓乡",
			"铁炉乡", "大兴乡", "南阳乡", "纪家乡", "新盛乡", "寿保乡", "舞雩乡", "下渡乡", "玉屏乡",
			"岷东乡", "塘坝乡", "马庙乡", "公平乡", "伏龙乡"};
	
	private Context context;
	private MessageAddress messageAddress;
	
	private ArrayList<AddressTab> addresslsit=new ArrayList<AddressTab>();
	
public FiltrateMenu(View view){
	
	this.mView=view;
	FiltrateMenuView();
	
}
public FiltrateMenu(View view,Context context,MessageAddress messageAddress){
	
	this.context=context;
	this.mView=view;
	this.messageAddress=messageAddress;
	FiltrateMenuView();
	
}
public View getView(){
	
		return mView;
		
	}


/**
* 筛选器——》视图
*/
private void FiltrateMenuView(){
//	   mButtonAreaAll=(RelativeLayout) mView.findViewById(R.id.area_list_site_all_view);
//	   mButtonAreaDongQu=(RelativeLayout) mView.findViewById(R.id.area_list_site_dongqu_view);
//	   mButtonAreaXiQu=(RelativeLayout) mView.findViewById(R.id.area_list_site_xiqu_view);
//	   mButtonAreaMiYiXian=(RelativeLayout) mView.findViewById(R.id.area_list_site_miyixian_view);
//	   mButtonAreaYanBian=(RelativeLayout) mView.findViewById(R.id.area_list_site_yanbian_view);
//	   mButtonAreaRenHe=(RelativeLayout) mView.findViewById(R.id.area_list_site_renhe_view);
	   
	   mButtonFirmAll=(RelativeLayout) mView.findViewById(R.id.firm_list_site_all_firm_view);
	   mButtonFirmMonitoring=(RelativeLayout) mView.findViewById(R.id.firm_list_site_monitoring_view);
	   mButtonFirmCY=(RelativeLayout) mView.findViewById(R.id.firm_list_site_monitoring_cy_view);
	   mButtonFirmLT=(RelativeLayout) mView.findViewById(R.id.firm_list_site_monitoring_lt_view);
	   
	   mButtonLevelAllGrade=(RelativeLayout) mView.findViewById(R.id.level_list_all_grade_view);
	   mButtonLevelAGrade=(RelativeLayout) mView.findViewById(R.id.level_list_A_view);
	   mButtonLevelBGrade=(RelativeLayout) mView.findViewById(R.id.level_list_B_view);
	   mButtonLevelCGrade=(RelativeLayout) mView.findViewById(R.id.level_list_C_view);
	   mButtonLevelnotGrade=(RelativeLayout) mView.findViewById(R.id.level_list_not_grade_view);
	   
	   
//	   mSigeAreaAll=(TextView) mView.findViewById(R.id.area_list_site_all_sige);
//	   mSigeAreaDongQu=(TextView) mView.findViewById(R.id.area_list_site_dongqu_sige);
//	   mSigeAreaXiQu=(TextView) mView.findViewById(R.id.area_list_site_xiqu_sige);
//	   mSigeAreaMiYiXian=(TextView) mView.findViewById(R.id.area_list_site_miyixian_sige);
//	   mSigeAreaYanBian=(TextView) mView.findViewById(R.id.area_list_site_yanbian_sige);
//	   mSigeAreaRenHe=(TextView) mView.findViewById(R.id.area_list_site_renhe_sige);
	   mArealayout = (LinearLayout) mView.findViewById(R.id.area_list_view);
	   
	   for (String str : this.str) {
			final AddressTab addressTab = new AddressTab(context, str);
		
			addresslsit.add(addressTab);
		}
	   for( int i=0;i<addresslsit.size();i++){
		   AddressTab addressTab=addresslsit.get(i);
		   addressTab.setAddress(new MessageAddress() {
			
			@Override
			public void MassageData(String arg0, int ID) {
				messageAddress.MassageData(arg0, ID);
				mArealayout.removeAllViews();
				for(int i=0;i<addresslsit.size();i++){
					AddressTab tab=addresslsit.get(i);
					if(!tab.getName().equals(arg0)){
						tab.state();
						tab.setBool(true);
						addresslsit.set(i, tab);
					}
					   mArealayout.addView(tab.getView());
				}
			}
		});
		   mArealayout.addView(addressTab.getView());
	   }
	   mSigeFirmAll=(TextView) mView.findViewById(R.id.firm_list_site_all_firm_sige);
	   mSigeFirmAllName=(TextView) mView.findViewById(R.id.firm_list_site_name);
	   mSigeFirmlName=(TextView) mView.findViewById(R.id.firm_list_site__lt_name);
	   mSigeFirmAllName=(TextView) mView.findViewById(R.id.firm_list_site_name);
	   mSigeFirmYLJG=(TextView) mView.findViewById(R.id.firm_list_cy_site_name);
	
	   mSigeFirmMonitoring=(TextView) mView.findViewById(R.id.firm_list_site_all_monitoring_sige);
	   mSigeFirmcy=(TextView) mView.findViewById(R.id.firm_list_site_all_cy_monitoring_sige);
	   mSigeFirmlt=(TextView) mView.findViewById(R.id.firm_list_site_all_lt__monitoring_sige);
	   
	   mSigeLevelAllGrade=(TextView) mView.findViewById(R.id.level_list_all_grade_sige);
	   mSigeLevelAGrade=(TextView) mView.findViewById(R.id.level_list_A_sige);
	   mSigeLevelBGrade=(TextView) mView.findViewById(R.id.level_list_B_sige);
	   mSigeLevelCGrade=(TextView) mView.findViewById(R.id.level_list_C_sige);
	   mSigeLevelnotGrade=(TextView) mView.findViewById(R.id.level_list_not_grade_sige);
	   
}
public void setName(String var,String lt){
		mSigeFirmAllName.setText(var);
		mSigeFirmlName.setText(lt);
		mSigeFirmYLJG.setText("医疗机构");
}
public void setOnClickListener(OnClickListener listener){
//	   mButtonAreaAll.setOnClickListener(listener);
//	   mButtonAreaDongQu.setOnClickListener(listener);
//	   mButtonAreaXiQu.setOnClickListener(listener);
//	   mButtonAreaMiYiXian.setOnClickListener(listener);
//	   mButtonAreaYanBian.setOnClickListener(listener);
	   
	   mButtonFirmAll.setOnClickListener(listener);
	   mButtonFirmMonitoring.setOnClickListener(listener);
	   mButtonFirmCY.setOnClickListener(listener);
	   mButtonFirmLT.setOnClickListener(listener);
	   
	   mButtonLevelAllGrade.setOnClickListener(listener);
	   mButtonLevelAGrade.setOnClickListener(listener);
	   mButtonLevelBGrade.setOnClickListener(listener);
	   mButtonLevelCGrade.setOnClickListener(listener);
	   mButtonLevelnotGrade.setOnClickListener(listener);
	   
	   
	   
//	   mButtonAreaRenHe.setOnClickListener(listener);
	   
}

public void toview(int key){
	switch (key) {
//	   case R.id.area_list_site_all_view:
//		   mSigeAreaAll.setVisibility(LinearLayout.VISIBLE);
//		   mSigeAreaDongQu.setVisibility(LinearLayout.GONE);
//		   mSigeAreaXiQu.setVisibility(LinearLayout.GONE);
//		   mSigeAreaMiYiXian.setVisibility(LinearLayout.GONE);
//		   mSigeAreaYanBian.setVisibility(LinearLayout.GONE);
//		   mSigeAreaRenHe.setVisibility(LinearLayout.GONE);
//		  
//		   break;
//	   case R.id.area_list_site_dongqu_view:
//		   mSigeAreaAll.setVisibility(LinearLayout.GONE);
//		   mSigeAreaDongQu.setVisibility(LinearLayout.VISIBLE);
//		   mSigeAreaXiQu.setVisibility(LinearLayout.GONE);
//		   mSigeAreaMiYiXian.setVisibility(LinearLayout.GONE);
//		   mSigeAreaYanBian.setVisibility(LinearLayout.GONE);
//		   mSigeAreaRenHe.setVisibility(LinearLayout.GONE);
//		   break;
//	   case R.id.area_list_site_xiqu_view:
//		   mSigeAreaAll.setVisibility(LinearLayout.GONE);
//		   mSigeAreaDongQu.setVisibility(LinearLayout.GONE);
//		   mSigeAreaXiQu.setVisibility(LinearLayout.VISIBLE);
//		   mSigeAreaMiYiXian.setVisibility(LinearLayout.GONE);
//		   mSigeAreaYanBian.setVisibility(LinearLayout.GONE);
//		   mSigeAreaRenHe.setVisibility(LinearLayout.GONE);
//		   break;
//	   case R.id.area_list_site_miyixian_view: 
//		   mSigeAreaAll.setVisibility(LinearLayout.GONE);
//		   mSigeAreaDongQu.setVisibility(LinearLayout.GONE);
//		   mSigeAreaXiQu.setVisibility(LinearLayout.GONE);
//		   mSigeAreaMiYiXian.setVisibility(LinearLayout.VISIBLE);
//		   mSigeAreaYanBian.setVisibility(LinearLayout.GONE);
//		   mSigeAreaRenHe.setVisibility(LinearLayout.GONE);
//		   break;
//	   case R.id.area_list_site_yanbian_view: 
//		   mSigeAreaAll.setVisibility(LinearLayout.GONE);
//		   mSigeAreaDongQu.setVisibility(LinearLayout.GONE);
//		   mSigeAreaXiQu.setVisibility(LinearLayout.GONE);
//		   mSigeAreaMiYiXian.setVisibility(LinearLayout.GONE);
//		   mSigeAreaYanBian.setVisibility(LinearLayout.VISIBLE);
//		   mSigeAreaRenHe.setVisibility(LinearLayout.GONE);
//		   break;
//	   case R.id.area_list_site_renhe_view: 
//		   mSigeAreaAll.setVisibility(LinearLayout.GONE);
//		   mSigeAreaDongQu.setVisibility(LinearLayout.GONE);
//		   mSigeAreaXiQu.setVisibility(LinearLayout.GONE);
//		   mSigeAreaMiYiXian.setVisibility(LinearLayout.GONE);
//		   mSigeAreaYanBian.setVisibility(LinearLayout.GONE);
//		   mSigeAreaRenHe.setVisibility(LinearLayout.VISIBLE);
//		   break;
//	   
	   case R.id.firm_list_site_all_firm_view:
		   mSigeFirmAll.setVisibility(LinearLayout.VISIBLE);
		   mSigeFirmMonitoring.setVisibility(LinearLayout.GONE);
		   mSigeFirmcy.setVisibility(LinearLayout.GONE);		   
		   mSigeFirmlt.setVisibility(LinearLayout.GONE);		   
		 
		   break;
	   case R.id.firm_list_site_monitoring_view:
		   mSigeFirmAll.setVisibility(LinearLayout.GONE);
		   mSigeFirmMonitoring.setVisibility(LinearLayout.VISIBLE);
		   mSigeFirmcy.setVisibility(LinearLayout.GONE);		   
		   mSigeFirmlt.setVisibility(LinearLayout.GONE);	
		   break;
	   case R.id.firm_list_site_monitoring_cy_view:
		   mSigeFirmAll.setVisibility(LinearLayout.GONE);
		   mSigeFirmMonitoring.setVisibility(LinearLayout.GONE);
		   mSigeFirmcy.setVisibility(LinearLayout.VISIBLE);		   
		   mSigeFirmlt.setVisibility(LinearLayout.GONE);	
		   break;
	   case R.id.firm_list_site_monitoring_lt_view :
		   mSigeFirmAll.setVisibility(LinearLayout.GONE);
		   mSigeFirmMonitoring.setVisibility(LinearLayout.GONE);
		   mSigeFirmcy.setVisibility(LinearLayout.GONE);		   
		   mSigeFirmlt.setVisibility(LinearLayout.VISIBLE);	
		   break;
	   
	   case R.id.level_list_all_grade_view:
		   mSigeLevelAllGrade.setVisibility(LinearLayout.VISIBLE);
		   mSigeLevelAGrade.setVisibility(LinearLayout.GONE);
		   mSigeLevelBGrade.setVisibility(LinearLayout.GONE);
		   mSigeLevelCGrade.setVisibility(LinearLayout.GONE);
		   mSigeLevelnotGrade.setVisibility(LinearLayout.GONE);
		   break;
	   case R.id.level_list_A_view:
		   mSigeLevelAllGrade.setVisibility(LinearLayout.GONE);
		   mSigeLevelAGrade.setVisibility(LinearLayout.VISIBLE);
		   mSigeLevelBGrade.setVisibility(LinearLayout.GONE);
		   mSigeLevelCGrade.setVisibility(LinearLayout.GONE);
		   mSigeLevelnotGrade.setVisibility(LinearLayout.GONE);
		   break;
	   case R.id.level_list_B_view: 
		   mSigeLevelAllGrade.setVisibility(LinearLayout.GONE);
		   mSigeLevelAGrade.setVisibility(LinearLayout.GONE);
		   mSigeLevelBGrade.setVisibility(LinearLayout.VISIBLE);
		   mSigeLevelCGrade.setVisibility(LinearLayout.GONE);
		   mSigeLevelnotGrade.setVisibility(LinearLayout.GONE);
		   break;
	   case R.id.level_list_C_view: 
		   mSigeLevelAllGrade.setVisibility(LinearLayout.GONE);
		   mSigeLevelAGrade.setVisibility(LinearLayout.GONE);
		   mSigeLevelBGrade.setVisibility(LinearLayout.GONE);
		   mSigeLevelCGrade.setVisibility(LinearLayout.VISIBLE);
		   mSigeLevelnotGrade.setVisibility(LinearLayout.GONE);
		   break;
	   case R.id.level_list_not_grade_view:
		   mSigeLevelAllGrade.setVisibility(LinearLayout.GONE);
		   mSigeLevelAGrade.setVisibility(LinearLayout.GONE);
		   mSigeLevelBGrade.setVisibility(LinearLayout.GONE);
		   mSigeLevelCGrade.setVisibility(LinearLayout.GONE);
		   mSigeLevelnotGrade.setVisibility(LinearLayout.VISIBLE);
break;
}
}

}
