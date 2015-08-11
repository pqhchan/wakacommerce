package com.wakacommerce.wechat.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import com.wakacommerce.wechat.WakaAsstError;
import com.wakacommerce.wechat.WakaAsstException;
import com.wakacommerce.wechat.domain.base.BaseEntity;

public class AppUtil {

	private static final Logger log = Logger.getLogger(AppUtil.class);
	
	private AppUtil() { }

	public static final Long currentTimeInSec() {
		return Long.valueOf(new Date().getTime() / 1000);
	}

	@SuppressWarnings("unchecked")
	public static final <T> T sendRequest(String url, HttpMethod method, Map<String, String> params, HttpEntity requestEntity,
			Class<T> resultClass) throws WakaAsstException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpRequestBase request = null;

		try {
			if (HttpMethod.GET.equals(method)) {
				request = new HttpGet();
			} else if (HttpMethod.POST.equals(method)) {
				request = new HttpPost();
				if (requestEntity != null) {
					((HttpPost) request).setEntity(requestEntity);
				}
			}
			URIBuilder builder = new URIBuilder(url);

			if (params != null) {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					builder.addParameter(entry.getKey(), entry.getValue());
				}
			}
			request.setURI(builder.build());

			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			String respBody = EntityUtils.toString(entity);
			
			log.debug(respBody);
			
			if (entity != null) {
				EntityUtils.consume(entity);
			}

			if (String.class.isAssignableFrom(resultClass)) {
				return (T) respBody;
			} else {
				Gson gson = new Gson();

				if (respBody.indexOf("{\"errcode\"") == 0 || respBody.indexOf("{\"errmsg\"") == 0) {
					WakaAsstError exJson = gson.fromJson(respBody, WakaAsstError.class);
					if (WakaAsstError.class.getName().equals(resultClass.getName()) && exJson.getCode() == 0) {
						return (T) exJson;
					} else {
						throw new WakaAsstException(exJson);
					}
				}
				T result = gson.fromJson(respBody, resultClass);
				if (result instanceof BaseEntity) {
					((BaseEntity) result).setCreatedDate(new Date());
				}
				return result;
			}

		} catch (IOException e) {
			throw new WakaAsstException(e);
		} catch (URISyntaxException e) {
			throw new WakaAsstException(e);
		}
	}

	public static StringEntity toJsonStringEntity(Object obj) {
		Gson gson = new Gson();
		return new StringEntity(gson.toJson(obj), Consts.UTF_8);
	}

	public static Map<String, String> getAccessTokenParams(String accessToken) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("access_token", accessToken);
		return result;
	}

	public static String getParameterizedUrl(String url, String... args) {
		String result = url;
		for (int i = 0; i < args.length; i += 2) {
			String p = args[i];
			String v = args[i + 1];
			result = result.replaceAll(p, v);
		}
		return result;
	}

}
