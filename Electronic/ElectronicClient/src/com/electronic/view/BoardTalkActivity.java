package com.electronic.view;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BoardTalkActivity extends Activity {

	private EditText messageEdit;
	private Button messageSend;
	private TextView messageShow;
	private String message;
	
	private SimpleDateFormat formatter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.boardtalk);
		formatter=new SimpleDateFormat("MM‘¬dd»’  hh:mm  ");
		messageEdit=(EditText) findViewById(R.id.messageEdit);
		messageShow=(TextView) findViewById(R.id.messageShow);
		messageShow.setTypeface(Typeface.MONOSPACE);
		messageShow.setTextSize(18);
		messageSend=(Button)findViewById(R.id.messageSend);
		messageSend.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				 Date curDate=new Date(System.currentTimeMillis());
				 String str=formatter.format(curDate);
				 message=messageEdit.getText().toString(); 
				 messageShow.append(str+message);
				 messageShow.append("\n");
				 messageEdit.setText("");
//				 
//				 try {
//					Socket socket=new Socket("10.0.2.2",2003);
//					ObjectOutputStream objOut= new ObjectOutputStream(socket.getOutputStream());
//					objOut.writeObject(str+message+"\n");
//					objOut.flush();
//				} catch (UnknownHostException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
			}
			
		});
		
	
	}
	

}
