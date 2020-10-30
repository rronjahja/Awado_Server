package com.awado.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServiceAccess {

	private String userName;
	private String password;
	private boolean ssl;

	ServiceAccess() {
		this.userName = null;
		this.password = null;
		this.ssl = false;
	}

	public ServiceAccess(String userName, String password, boolean ssl) {
		this.userName = userName;
		this.password = password;
		this.ssl = ssl;
	}

	private SSLContext setSSL() {
		try {
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					// Not implemented
				}

				@Override
				public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					// Not implemented
				}
			} };

			SSLContext sslSettings = SSLContext.getInstance("TLS");

			sslSettings.init(null, trustAllCerts, new java.security.SecureRandom());
			return sslSettings;
		} catch (NoSuchAlgorithmException e) {
			e.getStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String doRequest(String request) {

		String response = null;
		HttpsURLConnection connection = null;

		try {
			URL requestURL = new URL(request);
			connection = (HttpsURLConnection) requestURL.openConnection();

			if (ssl) {
				SSLContext sslSettings = setSSL();
				if (sslSettings != null) {
					connection.setSSLSocketFactory(sslSettings.getSocketFactory());
				}
			}

			connection.setRequestMethod("GET");
			if (password != null && userName != null) {
				String encoded = Base64.getEncoder()
						.encodeToString((userName + ":" + password).getBytes(StandardCharsets.UTF_8)); // Java 8
				connection.setRequestProperty("Authorization", "Basic " + encoded);
			}
			connection.setDoInput(true);

			connection.connect();

			boolean httpOk = connection.getResponseCode() == HttpURLConnection.HTTP_OK;
			try (InputStream stream = (httpOk ? connection.getInputStream() : connection.getErrorStream());
					Reader reader = new InputStreamReader(stream);
					BufferedReader buffer = new BufferedReader(reader)) {

				StringBuilder sb = new StringBuilder();
				String line = buffer.readLine();
				while (line != null) {
					sb.append(line).append('\n');
					line = buffer.readLine();
				}
				if (httpOk)
					response = sb.substring(0, sb.length() - 1);
				else {
					System.err.println("Bad Response: " + response + "\n");
					response = null;
				}

			} catch (IOException e) {
				System.err.println("Error: " + e.getMessage());
			}

		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		} finally {
			if (connection != null)
				connection.disconnect();
		}

		return response;
	}

	public JSONObject requestObject(String request) throws JSONException {
		String result = this.doRequest(request);

		if (result == null)
			return null;

		JSONObject jsonObject = new JSONObject(result);

		return (jsonObject);
	}

	public JSONArray requestArray(String request) throws JSONException {
		String result = this.doRequest(request);
		if (result == null)
			return null;
		return new JSONArray(result);
	}
}
