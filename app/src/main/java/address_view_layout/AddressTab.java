package address_view_layout;

import com.heking.qsy.AppContext;
import com.heking.qsy.R;
import com.heking.qsy.util.MessageAddress;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AddressTab {
	private String name;
	private View mView;
	private TextView mTextName;
	private TextView mImage;
	private RelativeLayout mAddressButton;
	private MessageAddress address;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	private boolean bool=true;
	
	public boolean isBool() {
		return bool;
	}
	public void setBool(boolean bool) {
		this.bool = bool;
	}
	public MessageAddress getAddress() {
		return address;
	}
	public void setAddress(MessageAddress address) {
		this.address = address;
	}
	public  AddressTab(Context context,String name){
		this.name=name;
		mView=LayoutInflater.from(context).inflate(R.layout.address_view_layout, null);
		mTextName=(TextView) mView.findViewById(R.id.to_message_name);
		if(AppContext.THEME){
			
			mTextName.setTextColor(Color.parseColor("#808080"));
//			mTextName.setTextColor(context.getResources().getColor(R.color.app_head_background_1));
		}else{
			mTextName.setTextColor(context.getResources().getColor(R.color.app_head_background_2));
		}
		mImage=(TextView) mView.findViewById(R.id.level_list_not_grade_sige);
		mTextName.setText(name);
		mAddressButton=(RelativeLayout) mView.findViewById(R.id.level_list_not_grade_view);
		if(name.equals("全部区域")){
			mImage.setVisibility(TextView.VISIBLE);
		}
		
		iniData();
	}
	private void iniData() {
		mAddressButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mImage.setVisibility(TextView.VISIBLE);
				address.MassageData(name,mImage.getId());
			}
		});
		
	}
	
	
	public View getView(){
		
		return mView;
	}
	
	public void state(){
		mImage.setVisibility(TextView.GONE);
		
	}
	
}
