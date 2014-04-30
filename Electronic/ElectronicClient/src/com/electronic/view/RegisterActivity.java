package com.electronic.view;


import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import com.electronic.http.AboutUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	EditText R_username=null;
	EditText R_password=null;
	EditText R_old=null;
	EditText R_sex=null;
	Button R_commit=null;
	Button R_back=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		R_username=(EditText)findViewById(R.id.R_username);
		R_password=(EditText)findViewById(R.id.R_password);
		R_old=(EditText)findViewById(R.id.R_old);
		R_sex=(EditText)findViewById(R.id.R_sex);
		R_commit=(Button)findViewById(R.id.R_commit);
		R_commit.setOnClickListener(new OnClickListener(){
     	@Override
			public void onClick(View v) {
				String user_name=R_username.getText().toString();
				String password=R_password.getText().toString();
				int old=Integer.valueOf(R_old.getText().toString());
				String sex=R_sex.getText().toString();
	//			System.out.println(user_name+" "+password+" "+sex+" "+old);
				AboutUser aboutUser=new AboutUser();
				try {
					boolean isRegister=aboutUser.processInternetRegister(user_name, password, sex, old);
					if(isRegister)
					{
						Toast toast=Toast.makeText(RegisterActivity.this, "×¢²á³É¹¦",Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						
						Intent intent=new Intent();
				        intent.setClass(RegisterActivity.this, LoginActivity.class);
				        RegisterActivity.this.startActivity(intent);

					}
					else
					{
						Toast toast=Toast.makeText(RegisterActivity.this, "×¢²áÊ§°Ü",Toast.LENGTH_LONG);
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
		R_back=(Button)findViewById(R.id.R_back);
		R_back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				  Intent intent=new Intent();
		          intent.setClass(RegisterActivity.this, LoginActivity.class);
		          RegisterActivity.this.startActivity(intent);
			}
			
		});
	
		
	}
	

}
