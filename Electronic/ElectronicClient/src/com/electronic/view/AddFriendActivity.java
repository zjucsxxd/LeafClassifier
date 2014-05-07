package com.electronic.view;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import com.electronic.http.AboutFriend;
import com.electronic.http.AboutGroup;
import com.electronic.http.AboutUser;
import com.electronic.model.Groups;
import com.electronic.util.PaserToGroup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddFriendActivity extends Activity {

	private String user_name;
	private EditText addFriendEdit;
	private EditText addFriendList;
	private Button addFriendCommit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.addfriend);
		Intent userintent=getIntent();
		user_name=userintent.getStringExtra("user_name");
		
		addFriendEdit=(EditText)findViewById(R.id.add_friend_edit);
		addFriendList=(EditText)findViewById(R.id.add_friend_list);
		addFriendCommit=(Button)findViewById(R.id.add_friend_commit);
		addFriendCommit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
			String friendName=addFriendEdit.getText().toString();
			String groupName=addFriendList.getText().toString();
			//判断好友存在否？
			AboutUser aboutUser=new AboutUser();
			AboutGroup aboutGroup=new AboutGroup();
			boolean isHaveThisUser;
			boolean isHaveThisGroup;
			try {
				isHaveThisUser = aboutUser.processIsHaveThisUser(friendName);
				isHaveThisGroup=aboutGroup.processIsHaveThisGroup(user_name, groupName);
				if(isHaveThisUser&&isHaveThisGroup)
				{//有此好友，又有此组，那就将该好友添加到该组
					AboutFriend aboutFriend=new AboutFriend();
					boolean addFriend=aboutFriend.processAddFriend(user_name, friendName, groupName);
					if(addFriend)
					{
						
				        Toast toast=Toast.makeText(AddFriendActivity.this, "添加好友成功",Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						//这里再加个跳转就好了。
						Intent intent=new Intent();
						intent.putExtra("user_name", user_name);
				        intent.setClass(AddFriendActivity.this, GroupActivity.class);
				        AddFriendActivity.this.startActivity(intent);
				        AddFriendActivity.this.finish();
					}
					else
					{
						Toast toast=Toast.makeText(AddFriendActivity.this, "该组已存在该好友，添加好友失败",Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
				}
				if(!isHaveThisUser)
				{
					Toast toast=Toast.makeText(AddFriendActivity.this, "该好友不存在，请重新输入",Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					addFriendEdit.setText("");
				}
				if(!isHaveThisGroup)
				{
					Toast toast=Toast.makeText(AddFriendActivity.this, "您没有这个组，请重新输入",Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					addFriendList.setText("");
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	
				
			}
			
		});
		
		
	}
	

}
