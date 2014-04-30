package com.electronic.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.electronic.http.AboutFriend;
import com.electronic.http.AboutGroup;
import com.electronic.model.FriendTable;
import com.electronic.model.Groups;
import com.electronic.util.PaserToFriendTable;
import com.electronic.util.PaserToGroup;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

public class GroupActivity extends ExpandableListActivity {

	private ExpandableListView mExpListView;
	private List<Map<String, String>> groups ;
	private List<List<Map<String, String>>> childs;
	private TextView addGroup;
	private TextView addFriend;
	private String user_name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.allgroup);
		Intent userintent=getIntent();
		user_name=userintent.getStringExtra("user_name");
		
		addGroup=(TextView)findViewById(R.id.addgroup);
		addGroup.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
		    Intent intent=new Intent();
		    intent.putExtra("user_name", user_name);
			intent.setClass(GroupActivity.this, AddGroupActivity.class);
			GroupActivity.this.startActivity(intent);
				
			}
			
		});
		addFriend=(TextView)findViewById(R.id.addfriend);
		addFriend.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
		    Intent intent=new Intent();
		    intent.putExtra("user_name", user_name);
    		intent.setClass(GroupActivity.this, AddFriendActivity.class);
    		GroupActivity.this.startActivity(intent);
				
			}
			
		});

		AboutGroup aboutGroup=new AboutGroup();
		AboutFriend aboutFriend=new AboutFriend();
		try {
			String strRet=aboutGroup.processListAllGroup(user_name);
			PaserToGroup paserToGroup=new PaserToGroup();
			ArrayList<Groups> groupAll=paserToGroup.getAllGroups(strRet);
			groups = new ArrayList<Map<String,String>>();
			childs= new ArrayList<List<Map<String,String>>>();
			for(int i=0;i<groupAll.size();i++)
			{
				Map<String, String> group = new HashMap<String, String>();
				group.put("group", groupAll.get(i).getGroup_name());
				groups.add(group);
				
				//即使组内无好友，也是需要有child的，只不过child的数据为空
				int group_id=groupAll.get(i).getGroup_id();
				String friendStrRet=aboutFriend.processListAllFriends(user_name, group_id);
				System.out.println(friendStrRet+"组内好友");
				List<Map<String, String>> child ;
				if(friendStrRet.equals(""))
				{
					child= new ArrayList<Map<String,String>>();
				}
				else
				{
					child = new ArrayList<Map<String,String>>();
					PaserToFriendTable paserToFriendTable=new PaserToFriendTable();
					ArrayList<FriendTable>friendAll=paserToFriendTable.getAllFriends(friendStrRet);
					for(int j=0;j<friendAll.size();j++)
					{
						Map<String, String> childData = new HashMap<String, String>();
						childData.put("child", friendAll.get(j).getFriend_id());
						child.add(childData);
					}
					
				}
				childs.add(child);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
/*		groups = new ArrayList<Map<String,String>>();
		Map<String, String> group1 = new HashMap<String, String>();
		group1.put("group", "协同工作组1");
		Map<String, String> group2 = new HashMap<String, String>();
		group2.put("group", "协同工作组2");
		groups.add(group1);
		groups.add(group2);
		
		List<Map<String, String>> child1 = new ArrayList<Map<String,String>>();
		Map<String, String> child1Data1 = new HashMap<String, String>();
		child1Data1.put("child", "好友1");
		Map<String, String> child1Data2 = new HashMap<String, String>();
		child1Data2.put("child", "好友2");
		child1.add(child1Data1);
		child1.add(child1Data2);
		
		List<Map<String, String>> child2 = new ArrayList<Map<String,String>>();
*/		
		
//		Map<String, String> child2Data1 = new HashMap<String, String>();
//		child2Data1.put("child", "好友3");
//		Map<String, String> child2Data2 = new HashMap<String, String>();
//		child2Data2.put("child", "好友4");
//		child2.add(child2Data1);
//		child2.add(child2Data2);
//
/*		childs= new ArrayList<List<Map<String,String>>>();
		childs.add(child1);
		childs.add(child2);
	*/	
	
	
		SimpleExpandableListAdapter sela=new SimpleExpandableListAdapter(this, 
				groups, R.layout.group, new String[]{"group"}, new int[]{R.id.groupTo},
				childs, R.layout.child, new String[]{"child"}, new int[]{R.id.childTo});
		
		this.setListAdapter(sela);

		
	}
	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		
		Intent intent=new Intent();
		intent.setClass(GroupActivity.this, BoardActivity.class);
		GroupActivity.this.startActivity(intent);
		return super.onChildClick(parent, v, groupPosition, childPosition, id);
	}


}
