package loginAndregister;

import mainPage.Mainpage;
import userData.UserDataManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.demo.mooc.R;

public class Login extends ActionBarActivity {
	private AutoCompleteTextView ac;
	private TextView tv;
	private EditText password;
	private EditText username;
	private Button registerBtn;
	private Button loginBtn;
	private VideoView video;
	private String[] res={"beijing","2271088240@163.com","@gmail.com"};
	private UserDataManager mUserDataManager;
	private boolean SDexist=false;
	private String VideoPath=" ";
	private Button play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        findViews();
        focusChange();
        completeUsername();
        setListeners();
        register();
        if (mUserDataManager == null) {
			mUserDataManager = new UserDataManager(this);
			mUserDataManager.openDataBase();
        }
        
        if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
        	SDexist=true;
        	Toast.makeText(this, R.string.sd, Toast.LENGTH_SHORT).show();
        }
        else{
        	SDexist=false;
        	Toast.makeText(this, R.string.no_sd, Toast.LENGTH_SHORT).show();
        }
    }

    private void findViews(){
    	ac=(AutoCompleteTextView) findViewById(R.id.username);
    	tv=(TextView) findViewById(R.id.text);
    	password=(EditText) findViewById(R.id.password);
    	username=(EditText) findViewById(R.id.username);
    	loginBtn=(Button) findViewById(R.id.login);
    	registerBtn=(Button) findViewById(R.id.register);
    	video=(VideoView) findViewById(R.id.video);
    	play=(Button) findViewById(R.id.play);
    }
    
    private void completeUsername(){
    	
        ArrayAdapter<String> ada=new ArrayAdapter<String>(this, 
        		android.R.layout.simple_list_item_1, res);
        ac.setAdapter(ada);
    }
    
    private void setListeners(){
    	tv.setOnClickListener(forget);
    	loginBtn.setOnClickListener(mListener);
    	loginBtn.setOnClickListener(loginsuccess);
    	play.setOnClickListener(playVideo);
    	
    }
	OnClickListener mListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.login:
				login();
				break;
			}
		}
	};
	
	/*public void loginSuccess(){
		loginBtn.setOnClickListener(loginsuccess);
	}*/
	
	private Button.OnClickListener playVideo=new Button.OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(SDexist){
				VideoPath="file:///sdcard/red-mp4.mp4";
				playVideoView(VideoPath);
			}
		}
		
	};
	
	private void playVideoView(String Path){
		if(Path!=""){
			video.setVideoURI(Uri.parse(Path));
			video.setMediaController(new MediaController(Login.this));
			video.requestFocus();
			video.start();
			if(video.isPlaying()){
				Toast.makeText(this, "Now Playing"+Path, Toast.LENGTH_SHORT).show();
				Log.i("info",Path);
			}
		}
	}
	
	public Button.OnClickListener loginsuccess=new Button.OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent loginSuccess=new Intent();
			loginSuccess.setClass(Login.this, Mainpage.class);
			startActivity(loginSuccess);
		}
		
	};
	
    private TextView.OnClickListener forget=new TextView.OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent forgetPassword=new Intent();
			forgetPassword.setClass(Login.this, Forgetpassword.class);
			startActivity(forgetPassword);
		}
    	
    };
    public void login() {
		if (isUserNameAndPwdValid()) {
			String userName = username.getText().toString().trim();
			String userPwd = password.getText().toString().trim();
			boolean result=mUserDataManager.findUserByNameAndPwd(userName, userPwd);
			if(result){
				//login success
				
				//Toast.makeText(this, getString(R.string.login_success),
					//	Toast.LENGTH_SHORT).show();
				
			}else {
				//login failed,user does't exist
				Toast.makeText(this, getString(R.string.login_fail),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

    
    private void register(){
    	registerBtn.setOnClickListener(register);
    }
    
    private Button.OnClickListener register=new Button.OnClickListener(){

		@Override
		public void onClick(View widget) {
			try {
				// TODO Auto-generated method stub
				Intent registerIntent = new Intent();
				registerIntent.setClass(Login.this, Register.class);
				startActivity(registerIntent);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
    	
    };

    private void focusChange(){
    	ac.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus){
					if(ac.getText().toString().trim().length()<4){
						Toast.makeText(Login.this, "用户名不能小于4个字符", Toast.LENGTH_SHORT).show();
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
						Toast.makeText(Login.this, "密码不能小于6个字符", Toast.LENGTH_SHORT).show();
					}
				}
			}
    		
    	});
    	
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
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mooc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.login, container, false);
            return rootView;
        }
    }

}
