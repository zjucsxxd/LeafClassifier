package com.electronic.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class BoardActivity extends Activity {
	TextView drawView=null;
	TextView talkView=null;
	TextView fileView=null;
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.board);
		drawView=(TextView)findViewById(R.id.main_draw);
		drawView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
			Intent intent=new Intent();
			intent.setClass(BoardActivity.this, BoardDrawActivity.class);
			BoardActivity.this.startActivity(intent);

				
			}
			
		});
		talkView=(TextView)findViewById(R.id.main_talk);
		talkView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(BoardActivity.this, BoardTalkActivity.class);
				BoardActivity.this.startActivity(intent);
				
			}
			
		});
		fileView=(TextView)findViewById(R.id.main_file);
		fileView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
				{
					System.out.println("i can connect sdcard......");
					Intent intent=new Intent();
					intent.setClass(BoardActivity.this, BoardFileActivity.class);
					BoardActivity.this.startActivity(intent);
					
				}
				else
				{
					Toast toast=Toast.makeText(BoardActivity.this, "",Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
				
			}
			
		});
		
		
		
	}
	

}
