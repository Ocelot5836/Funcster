package com.mcpacks.installer.util;

import java.awt.image.BufferedImage;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;

public class ThreadLoadImage extends Thread {

	private URL url;

	public ThreadLoadImage(URL url) {
		this.url = url;
	}

	@Override
	public void run() {
		System.out.println("s");
		try {
			HttpURLConnection connection = (HttpURLConnection) this.url.openConnection();
			connection.addRequestProperty("User-Agent", "MCPacks");
			BufferedImage image = ImageIO.read(connection.getInputStream());
			ImageLoader.load(this.url, image);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}