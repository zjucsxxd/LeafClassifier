package com.electronic.view;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.electronic.http.AboutUser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

	EditText usernameEdit=null;
	EditText passwordEdit=null;
	Button loginButton=null;
	TextView registerView=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		usernameEdit=(EditText)findViewById(R.id.UsernameEdit);
		passwordEdit=(EditText)findViewById(R.id.PasswordEdit);
		loginButton=(Button)findViewById(R.id.LoginButton);
		registerView=(TextView)findViewById(R.id.RegisterTextView);
		
		loginButton.setOnClickListener(new MybuttonListner());
		registerView.setOnClickListener(new MyRegisterListener());
		
	}
	class MyRegisterListener implements OnClickListener{

		@Override
		public void onClick(View v) {
           Intent intent=new Intent();
           intent.setClass(LoginActivity.this, RegisterActivity.class);
           LoginActivity.this.startActivity(intent);
		}
		
	}

	class MybuttonListner implements OnClickListener{

		@Override
		public void onClick(View v) {
			String user_name=usernameEdit.getText().toString();
			String pass_word=passwordEdit.getText().toString();
			AboutUser aboutUser=new AboutUser();
			try {
				boolean islogin=aboutUser.processInternetLogin(user_name, pass_word);
				if(islogin)
				{
					Intent intent=new Intent();
					intent.putExtra("user_name", user_name);
					intent.setClass(LoginActivity.this, GroupActivity.class);
					LoginActivity.this.startActivity(intent);
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	

}
