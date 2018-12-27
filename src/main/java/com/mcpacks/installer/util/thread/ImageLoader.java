package com.mcpacks.installer.util.thread;

import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageLoader {

	private static final Map<String, BufferedImage> CACHE = new HashMap<String, BufferedImage>();
	private static final List<String> REQUESTED = new ArrayList<String>();

	public static void request(String url, boolean forceDownload) {
		if (!REQUESTED.contains(url) || forceDownload) {
			REQUESTED.add(url);
			try {
				new ThreadLoadImage(new URL(url)).start();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}

	public static BufferedImage get(String url, boolean forceDownload) {
		if (!forceDownload && CACHE.containsKey(url))
			return CACHE.get(url);
		if (!REQUESTED.contains(url)) {
			request(url, forceDownload);
		}
		return null;
	}
	
	public static void delete() {
		CACHE.clear();
	}

	protected static void load(URL url, BufferedImage image) {
		REQUESTED.remove(url.toString());
		CACHE.put(url.toString(), image);
	}
}