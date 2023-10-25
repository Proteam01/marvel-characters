package org.marvel.characters.marvelcharacters.api;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class HttpRequestsClient {
	
	@Value("${publicKey}")
	private String publicKey;
	
	@Value("${privateKey}")
	private String privateKey;

	public String getCharacters() {
		String body = "";
		OkHttpClient client = new OkHttpClient();
		Map<String, String> headersValues = new HashMap<String, String>();
		headersValues.put("Accept", "application/json");
		headersValues.put("referrer", "*.marvel.com/apigateway");
		Headers headers = Headers.of(headersValues);
		HttpUrl url = null;
		String ts = String.valueOf(Instant.now().toEpochMilli());
		try {
			String hash = getHash(ts, publicKey, privateKey);
			url = new HttpUrl.Builder().scheme("http").host("gateway.marvel.com").addPathSegments("v1")
					.addPathSegments("public").addPathSegments("characters").addQueryParameter("ts", ts)
					.addQueryParameter("apikey", publicKey).addQueryParameter("hash", hash).build();
			Request request = new Request.Builder().headers(headers).url(url).get().build();
			Response response = client.newCall(request).execute();
			body = response.body().string();
			response.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return body;
	}
	
	
	public String getCharacterDetails(String id) {
		String body = "";
		OkHttpClient client = new OkHttpClient();
		Map<String, String> headersValues = new HashMap<String, String>();
		headersValues.put("Accept", "application/json");
		headersValues.put("referrer", "*.marvel.com/apigateway");
		Headers headers = Headers.of(headersValues);
		HttpUrl url = null;
		String ts = String.valueOf(Instant.now().toEpochMilli());
		try {
			String hash = getHash(ts, publicKey, privateKey);
			url = new HttpUrl.Builder().scheme("http")
					.host("gateway.marvel.com").addPathSegments("v1")
					.addPathSegments("public")
					.addPathSegments("characters")
					.addPathSegment(id)
					.addQueryParameter("ts", ts)
					.addQueryParameter("apikey", publicKey)
					.addQueryParameter("hash", hash)
					.build();
			Request request = new Request.Builder().headers(headers).url(url).get().build();
			Response response = client.newCall(request).execute();
			body = response.body().string();
			response.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return body;
	}
	

	private String getHash(String ts, String publicKey, String privateKey) {
		String toEncode = ts + privateKey + publicKey;
		return DigestUtils.md5Hex(toEncode);
	}

	
	public static void main(String[] args) {
		HttpRequestsClient test = new HttpRequestsClient();
		System.out.println(test.getCharacters());
		System.out.println(test.getCharacterDetails("1017100"));
	}
	

}
