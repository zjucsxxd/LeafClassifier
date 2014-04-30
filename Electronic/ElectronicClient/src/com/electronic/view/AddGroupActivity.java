package com.electronic.view;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.electronic.http.AboutGroup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddGroupActivity extends Activity {

	private EditText addGroupEdit;
	private Button addGroupCommit;
	private String user_name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.addgroup);
		
		Intent userintent=getIntent();
		user_name=userintent.getStringExtra("user_name");
		addGroupEdit=(EditText)findViewById(R.id.add_group_edit);
		addGroupCommit=(Button)findViewById(R.id.add_group_commit);
		addGroupCommit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
			String group_name=addGroupEdit.getText().toString();
            AboutGroup aboutGroup=new AboutGroup();
            try {
				boolean isAdd=aboutGroup.processAddGroup(group_name, user_name);
				if(isAdd)
				{
					Toast toast=Toast.makeText(AddGroupActivity.this, "添加工作组成功",Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					
					Intent intent=new Intent();
					intent.putExtra("user_name", user_name);
			        intent.setClass(AddGroupActivity.this, GroupActivity.class);
			        AddGroupActivity.this.startActivity(intent);
			        AddGroupActivity.this.finish();
			        
				}
				else
				{
					Toast toast=Toast.makeText(AddGroupActivity.this, "添加工作组失败",Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
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
