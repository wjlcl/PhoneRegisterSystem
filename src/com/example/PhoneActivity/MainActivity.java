package com.example.PhoneActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	private GridView gridView;
	private List<Map<String, Object>> data_list;
	private int[] imagesId = new int[] { R.drawable.register, R.drawable.find, R.drawable.exit, R.drawable.smoney,
			R.drawable.newget, R.drawable.newgoout, R.drawable.sendmsga };
	private String[] iconName = new String[] { "注册登录", "查票", "退出系统", "出差报账申请", "新建出差报账申请", "新建出差申请", "发送短信" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		gridView = (GridView) findViewById(R.id.gridView01);
		data_list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < imagesId.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("images", imagesId[i]);
			map.put("text", iconName[i]);
			data_list.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data_list, R.layout.list_item,
				new String[] { "images", "text" }, new int[] { R.id.imageView01, R.id.textView01 });
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				// Toast toast = Toast.makeText(getApplicationContext(), "您单击了"
				// + (position + 1 + "号图片"),
				// Toast.LENGTH_SHORT);
				// toast.show();
				int item = position + 1;
				switch (item) {
				case 1:
					Intent intent = new Intent(MainActivity.this, LoginApp.class);
					startActivity(intent);
					MainActivity.this.finish();
					break;
				case 2:
					Intent intent2 = new Intent(MainActivity.this, TicketMainApp.class);
					break;
				}
			}
		});
	}
}
