package com.example.PhoneActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ganasky.Help.ConnUrlHelper;
import com.ganasky.Help.DoXmlHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.DocumentsContract.Document;
import android.renderscript.Element;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import model.UserInfo;

public class LoginApp extends Activity implements OnClickListener {
	private ProgressDialog m_Dialog;
	private UserInfo userInfo;
	private EditText et_UserName;
	private EditText et_UserPwd;
	private Button buttonOk;
	private Button buttonCancel;
	private String UrlLogin = "http://220.165.15.216:7777/MobilePage/LoginHtmlPage.aspx";
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
		rb_save = (RadioButton) findViewById(R.id.RadioButtonofSave);
		rb_notSave = (RadioButton) findViewById(R.id.RadioButtonofNotSave);
		buttonRegister = (Button) findViewById(R.id.btn_Register);

		buttonOk.setOnClickListener(this);
		buttonCancel.setOnClickListener(this);
		buttonRegister.setOnClickListener(this);
		readUserName();
	}

	private void readUserName() {
		// TODO Auto-generated method stub
		SharedPreferences seetings = getSharedPreferences(PREFS_NAME, Context.MODE_WORLD_READABLE);
		saveofUserName = seetings.getString("userName", userInfo.getuserName());
		issave = seetings.getBoolean("isSave", issave);
		if (issave && saveofUserName != null) {
			et_UserName.setText(saveofUserName);
			rb_save.setChecked(true);
		} else {
			et_UserName.setText("");
			rb_notSave.setChecked(true);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_OK:
			if (ConnUrlHelper.hasInternet(LoginApp.this)) {
				if (setLoginParams()) {
					login();
				}
			} else {
				Toast.makeText(getApplicationContext(), "网络连接有问题，请连接网络！！！！！", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btn_Cancel:
			saveuserInfo();
			returnMainPage();
			break;
		case R.id.btn_Register:
			Intent intent = new Intent(LoginApp.this, RegisterApp.class);
			startActivity(intent);
			LoginApp.this.finish();
			break;
		}
	}

	private void returnMainPage() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(LoginApp.this, MainActivity.class);
		startActivity(intent);
		LoginApp.this.finish();

	}

	private void saveuserInfo() {
		// TODO Auto-generated method stub
		SharedPreferences uiState = getSharedPreferences(PREFS_NAME, Context.MODE_WORLD_READABLE);
		SharedPreferences.Editor editor = uiState.edit();
		if (userInfo != null) {
			editor.putBoolean("isSave", issave);
			editor.putString("userName", userInfo.getuserName());
			editor.putString("userId", userInfo.getuserId());
			editor.putString("userMobile", userInfo.getuserMobilePhone());
			editor.putString("userEmail", userInfo.getuserEmail());
		}
		editor.commit();
	}

	private void login() {
		// TODO Auto-generated method stub
		String params = null;
		try {
			params = URLEncoder.encode("strNmae", "UTF-8") + "=" + URLEncoder.encode(userInfo.getuserName(), "UTF-8");
			params = URLEncoder.encode("strPwd", "UTF-8") + "=" + URLEncoder.encode(userInfo.getuserPwd(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resultData = ConnUrlHelper.getPostHttpURLConnByUrl(UrlLogin, params);
		userInfo = getUserInfo(resultData);

	}

	private UserInfo getUserInfo(String resultDatas) {
		// TODO Auto-generated method stub
		org.w3c.dom.Document doc = DoXmlHelper.StringToxml(resultDatas);
		org.w3c.dom.Element root = doc.getDocumentElement();
		NodeList nl = root.getElementsByTagName("Users");
		UserInfo userInfo = new UserInfo();
		for (int i = 0; i < nl.getLength(); i++) {
			// 得到Account中的所有元素(可以有n个account)
			Element userInfoElement = (Element) nl.item(i);
			// 读到具体account中的元素的第一个节点
			Element userId = (Element) ((org.w3c.dom.Document) userInfoElement).getElementsByTagName("Id").item(0);
			Element loginName = (Element) ((org.w3c.dom.Document) userInfoElement).getElementsByTagName("LoginName")
					.item(0);
			Element mobileNo = (Element) ((org.w3c.dom.Document) userInfoElement).getElementsByTagName("MobileNo")
					.item(0);
			Element email = (Element) ((org.w3c.dom.Document) userInfoElement).getElementsByTagName("E_Mail").item(0);

			userInfo.setuserId(((Node) userId).getFirstChild().getNodeValue());
			userInfo.setuserName(((Node) loginName).getFirstChild().getNodeValue());
			userInfo.setuserMobilePhone(((Node) mobileNo).getFirstChild().getNodeValue());
			userInfo.setuserEmail(((Node) email).getFirstChild().getNodeValue());
		}
		return userInfo;
	}

	private boolean setLoginParams() {
		String user_name = et_UserName.getText().toString();
		if (user_name != null && user_name.length() > 0) {
			userInfo.setuserName(user_name);

		} else {
			Toast.makeText(getApplicationContext(), "用户名不能为空！", Toast.LENGTH_SHORT).show();
			return false;
		}
		String user_pwd = et_UserPwd.getText().toString();
		if (user_pwd != null && user_pwd.length() > 0) {
			userInfo.setuserPwd(user_pwd);

		} else {
			Toast.makeText(getApplicationContext(), "密码不能为空！", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
		// TODO Auto-generated method stub
	}

}
