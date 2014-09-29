package loginAndregister;

import userData.UserData;
import com.demo.mooc.R;
import userData.UserDataManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {
	private Button back;
	private Button register_btn;
	private AutoCompleteTextView username;
	private EditText password;
	private EditText confirmpassword;
	private UserDataManager mUserDataManager;
	private AutoCompleteTextView registerEmail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		findViews();
		setListeners();
		
		onfocusChange();
		if (mUserDataManager == null) {
			mUserDataManager = new UserDataManager(this);
			mUserDataManager.openDataBase();
        }
	}
	
	private void findViews(){
		back=(Button) findViewById(R.id.Back);
		username=(AutoCompleteTextView) findViewById(R.id.registerUsername);
		password=(EditText) findViewById(R.id.registerPassword);
		confirmpassword=(EditText) findViewById(R.id.confirmPassword);
		register_btn=(Button) findViewById(R.id.Register);
		registerEmail=(AutoCompleteTextView) findViewById(R.id.registerEmail);
	}
	
	
	private void setListeners(){
			back.setOnClickListener(backToMain);
			register_btn.setOnClickListener(mListener);
			
		
	}
	
	OnClickListener mListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.Register:
				register();
				break;
			}
		}
	};
	
	
	
	private Button.OnClickListener backToMain=new Button.OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent backIntent=new Intent();
			backIntent.setClass(Register.this, Login.class);
			startActivity(backIntent);
		}
		
	};
	
// 	private void demo(){
//		SQLiteDatabase userInfo=openOrCreateDatabase("user.db", MODE_PRIVATE, null);
//		userInfo.execSQL("create table if not exists userInfo(_id integer primary key autoincrement,name text not null,age integer not null,sex text not null)");
//		userInfo.execSQL("insert into userInfo(name,sex,age) values('李四','女','20')");
//		userInfo.execSQL("insert into userInfo(name,sex,age) values('李四','男','30')");
//		userInfo.execSQL("insert into userInfo(name,sex,age) values('王五','女','40')");
//		
//		Cursor c=userInfo.rawQuery("select * from userInfo", null);
//			if(c!=null){
//				while(c.moveToNext()){
//					Log.i("info", "_id:"+c.getInt(c.getColumnIndex("_id")));
//					Log.i("info", "name:"+c.getString(c.getColumnIndex("name")));
//					Log.i("info", "age:"+c.getInt(c.getColumnIndex("age")));
//					Log.i("info", "sex:"+c.getString(c.getColumnIndex("sex")));
//					Log.i("info", "!!!!!!!!!!!!!!");
//				}
//				c.close();
//			}
//			userInfo.close();
//	}
		
// 	private void checkEdittext(){
// 		int x=username.getText().toString().trim().length();
// 		int y=password.getText().toString().trim().length();
// 		boolean z=password.getText().toString().trim().equals(confirmpassword.getText().toString().trim());
// 			if(x<4 || y<6 || !z){
// 				register_btn.setClickable(false);
// 			}
// 			else if(x>=4 && y>=6 && z){
// 				register_btn.setClickable(true);
// 			}
// 		
// 	}
 	
	private void onfocusChange(){
		username.setOnFocusChangeListener(new OnFocusChangeListener(){

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus){
					if(username.getText().toString().trim().length()<4){
						Toast.makeText(Register.this, "用户名不能小于4个字符", Toast.LENGTH_SHORT).show();
					}
					
				}
				
			}
			
		});
		password.setOnFocusChangeListener(new OnFocusChangeListener(){

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus){
					if(password.getText().toString().trim().length()<6){
						Toast.makeText(Register.this, "密码不能少于6个字符", Toast.LENGTH_SHORT).show();
					}
				}
			}
			
		});
		confirmpassword.setOnFocusChangeListener(new OnFocusChangeListener(){

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus){
					if(!password.getText().toString().trim().equals(confirmpassword.getText().toString().trim())){
						Toast.makeText(Register.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
					}
				}
			}
			
		});
		
		
		
	}

	private void register() {
		if (isUserNameAndPwdValid()) {
			String userName = username.getText().toString().trim();
			String userPwd = password.getText().toString().trim();
			String userEmail=registerEmail.getText().toString().trim();
			
			//check if user name is already exist
			
			
			UserData mUser = new UserData(userName, userPwd,userEmail);
			mUserDataManager.openDataBase();
			long flag = mUserDataManager.insertUserData(mUser);
			if (flag == -1) {
				Toast.makeText(this, getString(R.string.register_fail),
						Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this, getString(R.string.register_success),
						Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private boolean isUserNameAndPwdValid() {
		if (username.getText().toString().trim().equals("")) {
			Toast.makeText(this, getString(R.string.account_empty),
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (password.getText().toString().trim().equals("")) {
			Toast.makeText(this, getString(R.string.password_empty),
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	@Override
	protected void onResume() {
		if (mUserDataManager == null) {
			mUserDataManager = new UserDataManager(this);
			mUserDataManager.openDataBase();
        }
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		if (mUserDataManager != null) {
			mUserDataManager.closeDataBase();
			mUserDataManager = null;
        }
		super.onPause();
	}
	
}
