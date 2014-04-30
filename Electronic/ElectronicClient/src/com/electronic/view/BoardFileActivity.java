package com.electronic.view;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.electronic.communicate.FileChooserAdapter;
import com.electronic.communicate.FileChooserAdapter.FileInfo;
import com.electronic.communicate.SendFile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class BoardFileActivity extends Activity {

	private GridView mGridView;
	private View mBackView;
	private View mBtExit;
	private Button affirmButton;
	private TextView mTvPath ;
	
	private String mSdcardRootPath ;
	private String mCurFilePath ;
	private ArrayList<FileInfo> mFileLists  ;
	private FileChooserAdapter mAdatper ;
	
	public static final String EXTRA_FILE_CHOOSER = "file_chooser";
	public static String fullPath="";
	public static String fullName="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.boardfile);
		
		mSdcardRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		mBackView = findViewById(R.id.imgBackFolder);
		mBackView.setOnClickListener(mClickListener);
		mBtExit = findViewById(R.id.btExit);
		mBtExit.setOnClickListener(mClickListener);
		
		affirmButton=(Button) findViewById(R.id.affirm);
		affirmButton.setOnClickListener(affirmListener);
		mTvPath = (TextView)findViewById(R.id.tvPath);
		
		mGridView = (GridView)findViewById(R.id.gvFileChooser);
		mGridView.setEmptyView(findViewById(R.id.tvEmptyHint));
		mGridView.setOnItemClickListener(mItemClickListener);
		setGridViewAdapter(mSdcardRootPath);
		
	}
	private OnClickListener affirmListener=new OnClickListener()
	{

		@Override
		public void onClick(View v) {
			SendFile sendfile=new SendFile(fullPath);
//			try {
//				sendfile.send();
//				Toast toast=Toast.makeText(BoardFileActivity.this,"成功发送"+fullName,Toast.LENGTH_LONG);
//				toast.setGravity(Gravity.CENTER, 0, 0);
//			    toast.show();
//			    finish();
//			} catch (UnknownHostException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
		
	};
	//配置适配器
	private void setGridViewAdapter(String filePath) {
		updateFileItems(filePath);
		mAdatper = new FileChooserAdapter(this , mFileLists);
		mGridView.setAdapter(mAdatper);
			
		}
   //根据路径更新数据，并且通知Adatper数据改变
	private void updateFileItems(String filePath)
	{
		mCurFilePath = filePath ;
		mTvPath.setText(mCurFilePath);
		if(mFileLists == null)
			mFileLists = new ArrayList<FileInfo>() ;
		if(!mFileLists.isEmpty())
			mFileLists.clear() ;
		File[] files = folderScan(filePath);
		for (int i = 0; i < files.length; i++) {
			if(files[i].isHidden())  // 不显示隐藏文件
				continue ;
		    String fileAbsolutePath = files[i].getAbsolutePath() ;
			String fileName = files[i].getName();
		    boolean isDirectory = false ;
			if (files[i].isDirectory()){
				isDirectory = true ;
			}
		    FileInfo fileInfo = new FileInfo(fileAbsolutePath , fileName , isDirectory) ;
			mFileLists.add(fileInfo);
		}
		//When first enter , the object of mAdatper don't initialized
		if(mAdatper != null)
		    mAdatper.notifyDataSetChanged();  //重新刷新
			
	}
	//获得当前路径的所有文件
	private File[] folderScan(String path) {
		File file = new File(path);
		File[] files = file.listFiles();
		return files;
		}
	private View.OnClickListener mClickListener = new  OnClickListener() {

		@Override
		public void onClick(View v) {

			switch(v.getId())
			{
			case R.id.imgBackFolder:
				backProcess();
				break;
			case R.id.btExit:
				setResult(RESULT_CANCELED);
				finish();
				break;
			}
		}
	};
	private AdapterView.OnItemClickListener mItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position,long id) {
		FileInfo fileInfo = (FileInfo)(((FileChooserAdapter)adapterView.getAdapter()).getItem(position));
		if(fileInfo.isDirectory())   //点击项为文件夹, 显示该文件夹下所有文件
			updateFileItems(fileInfo.getFilePath()) ;
		else if(fileInfo.isPPTFile())
		{  //是ppt文件 

			fullPath=fileInfo.getFilePath();
			fullName=fileInfo.getFileName();
			Toast toast=Toast.makeText(BoardFileActivity.this,fullPath,Toast.LENGTH_LONG);
		    toast.show();
		    

		}
		else if(fileInfo.isTxtFile())
		{
			fullPath=fileInfo.getFilePath();
			fullName=fileInfo.getFileName();
			Toast toast=Toast.makeText(BoardFileActivity.this,fullPath,Toast.LENGTH_LONG);
			toast.show();
		}
		else
		{
			fullPath=fileInfo.getFilePath();
			fullName=fileInfo.getFileName();
			Toast toast=Toast.makeText(BoardFileActivity.this, "这是神马格式？",Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	  }
		
	};
	public boolean onKeyDown(int keyCode , KeyEvent event){
		if(event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode()
			== KeyEvent.KEYCODE_BACK){
			backProcess();   
			return true ;
		}
		return super.onKeyDown(keyCode, event);
	}
	public void backProcess()
	{
		if (!mCurFilePath.equals(mSdcardRootPath))
		{
			File thisFile = new File(mCurFilePath);
			String parentFilePath = thisFile.getParent();
			updateFileItems(parentFilePath);
			
		}
		else
		{
			setResult(RESULT_CANCELED);
			finish();
		}
	}

}
