package com.mcpacks.installer.util.thread;

import java.net.HttpURLConnection;
import java.net.URL;

public class ThreadLoadFromURL extends Thread {

	private URL url;
	private LoadListener loadListener;
	private ErrorListener errorListener;

	public ThreadLoadFromURL(URL url) {
		this.url = url;
		this.loadListener = null;
		this.errorListener = null;
	}

	@Override
	public void run() {
		try {
			HttpURLConnection connection = (HttpURLConnection) this.url.openConnection();
			connection.addRequestProperty("User-Agent", "MCPacks");
			if (this.loadListener != null) {
				this.loadListener.load(connection);
			}
		} catch (Throwable t) {
			if (this.errorListener.onError(t)) {
				t.printStackTrace();
			}
		}
	}

	public URL getUrl() {
		return url;
	}

	public LoadListener getLoadListener() {
		return loadListener;
	}

	public ErrorListener getErrorListener() {
		return errorListener;
	}

	public void setLoadListener(LoadListener loadListener) {
		this.loadListener = loadListener;
	}

	public void setErrorListener(ErrorListener errorListener) {
		this.errorListener = errorListener;
	}
}