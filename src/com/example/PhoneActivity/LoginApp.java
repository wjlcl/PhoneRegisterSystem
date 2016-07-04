package com.example.PhoneActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import model.UserInfo;

public class LoginApp extends Activity {
	private ProgressDialog m_Dialog;
	private UserInfo userInfo;
	private EditText et_UserName;
	private EditText et_UserPwd;
	private Button buttonOk;
	private Button buttonCancel;
	private String UrlLogin;
	private RadioButton rb_save, rb_notSave;
	private RadioGroup m_RadioGroup;
	private Button buttonRegister;
	private String resultData = "";

	private String saveofUserName = null;
	private boolean issave = false;
	private final String DEBUT_TAG = "LoginApp";
	public static final String PREFS_NAME = "GanaSky用户信息";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		userInfo = new UserInfo();
		et_UserName = (EditText) findViewById(R.id.et_UserName);
		et_UserPwd = (EditText) findViewById(R.id.et_UserPsw);
		buttonOk = (Button) findViewById(R.id.btn_OK);
		buttonCancel = (Button) findViewById(R.id.btn_Cancel);
		m_RadioGroup = (RadioGroup) findViewById(R.id.RadioGroup01);
		rb_save=(RadioButton) findViewById(R.id.RadioButtonofSave);
		rb_notSave = (RadioButton) findViewById(R.id.RadioButtonofNotSave);
		buttonRegister=(Button) findViewById(R.id.btn_Register);
	}

}
