package com.ganasky.Help;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
/**
 * �������̨ASPͨ���йص���
 * @author 
 *
 */
public abstract class ConnUrlHelper {
	private static String DEBUG_TAG = "ConnUrlHelper";
     //��һ���ִ����ڵ�������ʵ�ֹ������ﲻ�ظ�չ��
	public static boolean hasInternet(Activity activity) {

		ConnectivityManager manager = (ConnectivityManager) activity

		.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo info = manager.getActiveNetworkInfo();

		if (info == null || !info.isConnected()) {

			return false;
		}
		if (info.isRoaming()) {
			// here is the roaming option you can change it if you want to

			// disable internet while roaming, just return false

			return true;
		}
		return true;
	}

	/**
	 * ���ڽ���ת��Ϊ�ַ���
	 */
	public static String convertStreamToString1(InputStreamReader is) {
		BufferedReader reader = new BufferedReader(is);
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			Log.e(DEBUG_TAG, "the convertStreamToString1 methods IOException");
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				Log.e(DEBUG_TAG,
						"the convertStreamToString1 methods IOException");
			}
		}
		return sb.toString();
	}

	/**
	 * ͨ��HttpURLConnection get��ʽ����ͨ��
	 * 
	 * @param url1
	 * @return
	 */
	public static String getHttpURLConnByUrl(String url1) {
		URL url = null;
		InputStreamReader in = null;
		String resultData = "";
		HttpURLConnection urlConn = null;
		try {
			// ����һ��URL����
			url = new URL(url1);
		} catch (MalformedURLException e) {
			Log.e(DEBUG_TAG,
					"getHttpURLConnByUrl method MalformedURLException erro");
		}
		if (url != null) {
			try {
				// ʹ��HttpURLConnection������
				urlConn = (HttpURLConnection) url.openConnection();
				// �������ӳ�ʱʱ��
				urlConn.setConnectTimeout(6 * 1000);
				if (urlConn.getResponseCode() != 200)
					throw new RuntimeException("����url����ʧ�ܣ�");
				// �õ���ȡ������(��)
				in = new InputStreamReader(urlConn.getInputStream());
				resultData = convertStreamToString1(in);
				// �ر�InputStreamReader
				in.close();
				// �ر�http����
				urlConn.disconnect();
			} catch (IOException e) {
				Log.e(DEBUG_TAG, "getHttpURLConnByUrl method IOException erro");
			} finally {
				urlConn.disconnect();
			}
		} else {
			Log.e(DEBUG_TAG, "getHttpURLConnByUrl method Url NULL erro");
		}
		return resultData;
	}

	/**
	 * ͨ��HttpURLConnection post��ʽ����ͨ��
	 * 
	 * @param url1
	 * @return
	 */
	public static String getPostHttpURLConnByUrl(String httpUrl, String params) {
		// ��õ�����
		String resultData = "";
		URL url = null;
		try {
			// ����һ��URL����
			url = new URL(httpUrl);
		} catch (MalformedURLException e) {
			Log.e(DEBUG_TAG,
					" MalformedURLException erro");
		}
		if (url != null) {
			try {
				// ʹ��HttpURLConnection������
				HttpURLConnection urlConn = (HttpURLConnection) url
						.openConnection();
				//urlConn.setConnectTimeout(6 * 10000000);
				//urlConn.setReadTimeout(6 * 100000); 
				// ��Ϊ�����post����,������Ҫ����Ϊtrue
				urlConn.setDoOutput(true);
				// ������POST��ʽ
				urlConn.setRequestMethod("POST");
				// Post ������ʹ�û���
				urlConn.setUseCaches(false);
				// URLConnection.setInstanceFollowRedirects�ǳ�Ա�������������ڵ�ǰ����
				urlConn.setInstanceFollowRedirects(true);
				// ���ñ������ӵ�Content-type������Ϊapplication/x-www-form-urlencoded��
				/*urlencoded�������form����
				* �������ǿ��Կ������Ƕ���������ʹ��URLEncoder.encode���б���*/
				urlConn.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				// ���ӣ���postUrl.openConnection()���˵����ñ���Ҫ��connect֮ǰ��ɣ�
				// Ҫע�����connection.getOutputStream�������Ľ���connect��
				urlConn.connect();
				// DataOutputStream��
				DataOutputStream out = new DataOutputStream(
						urlConn.getOutputStream());
				// ��Ҫ�ϴ�������д������
				out.writeBytes(params);
				// ˢ�¡��ر�
				out.flush();
				out.close();
				// ��ȡ����
				InputStreamReader in = null;
				in = new InputStreamReader(urlConn.getInputStream());
				resultData = convertStreamToString1(in);
				// �ر�InputStreamReader
				in.close();
				// �ر�http����
				urlConn.disconnect();
			} catch (IOException e) {
				// �鿴����쳣
				Log.e(DEBUG_TAG,
						"the getPostHttpURLConnByUrl methods  IOException erro");
			}
		} else {
			Log.e(DEBUG_TAG,
					"the getPostHttpURLConnByUrl methods  Url NULL erro");
		}
		return resultData;
	}

	/**
	 * ͨ��HttpURLConnection post��ʽ����ͨ�ţ�������Խϳ��ĳ�ʱ����ʱ�䣩
	 *
	 * @param url1
	 * @return
	 */
	public static String getLongPostHttpURLConnByUrl(String httpUrl,
			String params) {
		// ��õ�����
		StringBuilder resultData = new StringBuilder();
		URL url = null;
		try {
			// ����һ��URL����
			url = new URL(httpUrl);
		} catch (MalformedURLException e) {
			Log.e(DEBUG_TAG,
					"  MalformedURLException erro");
		}
		if (url != null) {
			try {
				// ʹ��HttpURLConnection������
				HttpURLConnection urlConn = (HttpURLConnection) url
						.openConnection();
				// ��Ϊ�����post����,������Ҫ����Ϊtrue
				urlConn.setDoOutput(true);
				// ������POST��ʽ
				urlConn.setRequestMethod("POST");
				// Post ������ʹ�û���
				urlConn.setUseCaches(false);
				// URLConnection.setInstanceFollowRedirects�ǳ�Ա�������������ڵ�ǰ����
				urlConn.setInstanceFollowRedirects(true);
				// ���ñ������ӵ�Content-type������Ϊapplication/x-www-form-urlencoded
				/*urlencoded�������form����
				* �������ǿ��Կ������Ƕ���������ʹ��URLEncoder.encode���б���*/
				urlConn.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				// ���ӣ���postUrl.openConnection()���˵����ñ���Ҫ��connect֮ǰ��ɣ�
				// Ҫע�����connection.getOutputStream�������Ľ���connect��
				urlConn.setConnectTimeout(6 * 1000); // ������ô������ʱ����
				urlConn.setReadTimeout(6 * 1000000000);
				urlConn.connect();
				// DataOutputStream��
				DataOutputStream out = new DataOutputStream(
						urlConn.getOutputStream());
				// ��Ҫ�ϴ�������д������
				out.writeBytes(params);
				// ˢ�¡��ر�
				out.flush();
				out.close();
				// ��ȡ����
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(urlConn.getInputStream()));
				String inputLine = null;
				// ʹ��ѭ������ȡ��õ�����
				while (((inputLine = reader.readLine()) != null)) {
					// ������ÿһ�к������һ��"\n"������
					resultData.append(inputLine).append("\r\n");
				}
				reader.close();
				// �ر�http����
				urlConn.disconnect();
			} catch (IOException e) {
				// �鿴����쳣
				Log.e(DEBUG_TAG,
						"the  methods  IOException erro");
			}
		} else {
			Log.e(DEBUG_TAG,
					"the getPostHttpURLConnByUrl methods  Url NULL erro");
		}
		return resultData.toString();
	}

	/**
	 * HttpClient Getͨ�ŷ�ʽ��ʽ
	 * 
	 * @param url1
	 * @return
	 */
	public static String getHttpClientConnByUrl(String url1) {
		// HttpGet���Ӷ���
		HttpGet httpRequest = new HttpGet(url1);
		String resultData = "";
		try {
			// ȡ��HttpClient����
			HttpClient httpclient = new DefaultHttpClient();
			// ����HttpClient��ȡ��HttpResponse
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			// ����ɹ�
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// ȡ�÷��ص��ַ���
				resultData = EntityUtils.toString(httpResponse.getEntity());
			} else {
				resultData = "�������!";
			}
		} catch (ClientProtocolException e) {
			Log.e(DEBUG_TAG,
					"the  methods  ClientProtocolException erro");
		} catch (IOException e) {
			Log.e(DEBUG_TAG,
					"the getHttpClientConnByUrl methods  IOException erro");
		} catch (Exception e) {
			Log.e(DEBUG_TAG,
					"the getHttpClientConnByUrl methods  Exception erro");
		}
		return resultData;
	}

	/**
	 * ͨ��HttpClient post��ʽͨ��
	 * 
	 * @param httpUrl�����http��������
	 * @param postData
	 *            �������poatData�Ĳ�����
	 * @return
	 */
	public static String getPostHttpClientConnByUrl(String httpUrl,
			List<BasicNameValuePair> postData) {

		// HttpPost���Ӷ���
		HttpPost httpRequest = new HttpPost(httpUrl);
		String resultData = "";
		try {
			// �����ַ���utf-8
			HttpEntity httpentity = new UrlEncodedFormEntity(postData, "utf-8");
			// ����httpRequest
			httpRequest.setEntity(httpentity);
			// ȡ��Ĭ�ϵ�HttpClient
			HttpClient httpclient = new DefaultHttpClient();
			// ȡ��HttpResponse
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			// HttpStatus.SC_OK��ʾ���ӳɹ�
			// ����ɹ�
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// ȡ�÷��ص��ַ���
				resultData = EntityUtils.toString(httpResponse.getEntity());
			} else {
				resultData = "�������!";
			}
		} catch (ClientProtocolException e) {
			Log.e(DEBUG_TAG,
					"the  methods  ClientProtocolException erro");
		} catch (IOException e) {
			Log.e(DEBUG_TAG,
					"the  methods  IOException erro");
		} catch (Exception e) {
			Log.e(DEBUG_TAG,
					"the  methods  Exception erro");
		}
		return resultData;
	}
}
