package com.electronic.view;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.electronic.communicate.ClientThread;
import com.electronic.communicate.Position;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

public class BoardDrawActivity extends Activity {

	private ImageView myImageView;
	private TextView colorRed;
	private TextView colorBlue;
	private TextView colorBlack;
	private TextView colorGray;
	private TextView colorGreen;
	private TextView clearImg;
	
	private Bitmap baseBitmap;
	private Canvas canvas;
	private Paint paint = new Paint();
	private Position position;
	private Socket socket;
	private float startX;
	private float startY;
	private float endX;
	private float endY;
	private int color=Color.BLACK;
	private int strokeWidth=2;
	private int type=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.boarddraw);
		
		paint.setStrokeWidth(strokeWidth);
		paint.setColor(color);
		myImageView=(ImageView) findViewById(R.id.drawImageView);
		myImageView.setOnTouchListener(touch);
		
		colorRed=(TextView)findViewById(R.id.colorRed);
		colorRed.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
            color=Color.RED;	
            paint.setColor(color);
			}
			
		});
		colorBlue=(TextView)findViewById(R.id.colorBlue);
		colorBlue.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
            color=Color.BLUE;	
            paint.setColor(color);
			}
			
		});
		

		colorBlack=(TextView)findViewById(R.id.colorBlack);
		colorBlack.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
            color=Color.BLACK;	
            paint.setColor(color);
			}
			
		});
		
		colorGray=(TextView)findViewById(R.id.colorGray);
		colorGray.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
            color=Color.GRAY;	
            paint.setColor(color);
			}
			
		});
		
		colorGreen=(TextView)findViewById(R.id.colorGreen);
		colorGreen.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
            color=Color.GREEN;	
            paint.setColor(color);
			}
			
		});
		clearImg=(TextView)findViewById(R.id.clearImg);
		clearImg.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(BoardDrawActivity.this, BoardDrawActivity.class);
				BoardDrawActivity.this.startActivity(intent);
				BoardDrawActivity.this.finish();
			}
			
		});
//		try {
//			socket=new Socket("10.0.2.2",2000);
//			System.out.println("......");
//			ClientThread thread=new ClientThread(socket);
//			thread.start();
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
	
	}

	OnTouchListener touch=new OnTouchListener()
	{

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction())
			{
			case MotionEvent.ACTION_DOWN:
				if (baseBitmap == null) {
					System.out.println("this id a new bitMap");
                 baseBitmap = Bitmap.createBitmap(myImageView.getWidth(),
                		 myImageView.getHeight(), Bitmap.Config.ARGB_8888);
				 canvas = new Canvas(baseBitmap);
				 canvas.drawColor(Color.WHITE);
		       }
				startX=event.getX();
				startY=event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				endX=event.getX();
				endY=event.getY();
				position=new Position(startX,startY,endX,endY,type,color);
//				try {
//					ObjectOutputStream objOut= new ObjectOutputStream(socket.getOutputStream());
//					objOut.writeObject(position);
//					objOut.flush();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
				canvas.drawLine(startX, startY, endX, endY, paint);
				startX=endX;
				startY=endY;
				myImageView.setImageBitmap(baseBitmap);
			    break;
			case MotionEvent.ACTION_UP:
				break;
			
			
			}
			return true;
		}
		
	};


}
