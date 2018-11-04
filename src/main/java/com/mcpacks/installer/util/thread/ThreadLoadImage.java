package com.mcpacks.installer.util.thread;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;

public class ThreadLoadImage extends ThreadLoadFromURL implements LoadListener {

	public ThreadLoadImage(URL url) {
		super(url);
		this.setLoadListener(this);
	}

	@Override
	public void load(HttpURLConnection connection) throws Exception {
		ImageLoader.load(this.getUrl(), ImageIO.read(connection.getInputStream()));
	}
}