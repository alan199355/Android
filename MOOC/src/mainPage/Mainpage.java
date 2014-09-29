package mainPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;





import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.demo.mooc.R;




public class Mainpage extends Activity implements OnScrollListener,OnItemClickListener{
	private ListView classList;
	//private ArrayAdapter<String> arr_adapter;
	private SimpleAdapter sim_adapter;
	
	private List<Map<String,Object>> datalist;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainpage);
		findViews();
		//String[] arr_data={"yeqiang1","yeqiang2","yeqiang3","yeqiang4"};
		datalist=new ArrayList<Map<String,Object>>();  
		//arr_adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr_data);
		sim_adapter=new SimpleAdapter(this, getData(), R.layout.item, new String[]{"pic","text"}, new int[]{R.id.pic,R.id.text});
		//classList.setAdapter(arr_adapter);
		classList.setAdapter(sim_adapter);
		classList.setOnItemClickListener(this);
		classList.setOnScrollListener(this);
	}
	
	private void findViews(){
		classList=(ListView) findViewById(R.id.classlist);
		
	}
	
	private List<Map<String,Object>> getData(){
			for(int i=0;i<30;i++){
				Map<String, Object> map=new HashMap<String,Object>();
				map.put("pic", R.drawable.ic_launcher);
				map.put("text", "demo"+i);
				datalist.add(map);
			}
		
		
		return datalist;
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		switch(scrollState){
			case SCROLL_STATE_FLING:
				Log.i("info", "由于惯性滑动");
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("pic", R.drawable.ic_launcher);
				map.put("text", "增加项");
				datalist.add(map);
				sim_adapter.notifyDataSetChanged();
				break;
			case SCROLL_STATE_IDLE:
				Log.i("info", "停止滑动");
				break;
			case SCROLL_STATE_TOUCH_SCROLL:
				Log.i("info", "仍在滑动");
				break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long aid) {
		// TODO Auto-generated method stub
		String text=classList.getItemAtPosition(position)+"";
		Toast.makeText(this, "position="+position+"  text="+text, Toast.LENGTH_SHORT).show();
	}

	
	
		
}
