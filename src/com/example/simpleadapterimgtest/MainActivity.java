package com.example.simpleadapterimgtest;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.v7.app.ActionBarActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;

public class MainActivity extends ActionBarActivity
{
	private List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
	private Map<String, Object> map;
	private ListView listView; 

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		GetVersion.isHighVerForWebservice();// 2.3版本以上的
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		
		
		getinfomation();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void getinfomation()
	{

		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				Message msg = new Message();
				msg.what = 0;
				mHandler.sendMessage(msg); // 向Handler发送消息,更新UI
			}
		}).start();

	}
	private Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case 0:
				for(int i = 0; i < 10; i++)
				{
					
					map = new HashMap<String, Object>();
					String imgUrl = "http://g.hiphotos.baidu.com/image/w%3D230/sign=8a1d6878e9f81a4c2632ebcae72a6029/77094b36acaf2edd25e293e58f1001e9390193af.jpg";
					String imgName = "name"+i;
					URL myFileUrl = null;
			        Bitmap bitmap = null;
			        try {
			            myFileUrl = new URL(imgUrl);
			        } catch (MalformedURLException e) {
			            e.printStackTrace();
			        }
			        try {
			            HttpURLConnection conn = (HttpURLConnection) myFileUrl
			                    .openConnection();
			            conn.setDoInput(true);
			            conn.connect();
			            InputStream is = conn.getInputStream();
			            bitmap = BitmapFactory.decodeStream(is);
			            is.close();
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
					map.put("img", bitmap);
					
					map.put("imgName", imgName);
					
					list.add(map);
				}
				
				SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, list, R.layout.items, new String[]{}, new int[]{R.id.imageView1,R.id.textView1});
				adapter.setViewBinder(new ViewBinder()
				{
					
					@Override
					public boolean setViewValue(View view, Object data,
							String textRepresentation)
					{
						// TODO Auto-generated method stub
						 if ((view instanceof ImageView) && (data instanceof Bitmap)) {
							   ImageView iv = (ImageView) view;
							   Bitmap bm = (Bitmap) data;
							   iv.setImageBitmap(bm);
							   return true;
							  }
						return false;
					}
				});
				listView.setAdapter(adapter);
			}
		};
	};
}
