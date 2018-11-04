package com.mcpacks.installer.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageLoader {

	private static final Map<String, BufferedImage> CACHE = new HashMap<String, BufferedImage>();
	private static final Map<String, BufferedImage> LOCAL_CACHE = new HashMap<String, BufferedImage>();
	private static final List<String> REQUESTED = new ArrayList<String>();
	private static final File CACHE_FOLDER = new File("cache");

	static {
		if (!CACHE_FOLDER.exists()) {
			CACHE_FOLDER.mkdirs();
		}

		File[] images = CACHE_FOLDER.listFiles();
		for (int i = 0; i < images.length; i++) {
			try {
				LOCAL_CACHE.put(URLDecoder.decode(images[i].getName(), "UTF-8"), ImageIO.read(new FileInputStream(images[i])));
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	public static void request(String url, boolean forceDownload) {
		if (!REQUESTED.contains(url) && (forceDownload || !LOCAL_CACHE.containsKey(url))) {
			REQUESTED.add(url);
			try {
				new ThreadLoadImage(new URL(url)).start();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}

	public static BufferedImage get(String url, boolean forceDownload) {
		if(!forceDownload && LOCAL_CACHE.containsKey(url))
			return LOCAL_CACHE.get(url);
		if (CACHE.containsKey(url))
			return CACHE.get(url);
		if (!REQUESTED.contains(url)) {
			request(url, forceDownload);
		}
		return null;
	}

	protected static void load(URL url, BufferedImage image) {
		REQUESTED.remove(url.toString());
		CACHE.put(url.toString(), image);

		try {
			if (!CACHE_FOLDER.exists()) {
				CACHE_FOLDER.mkdirs();
			}

			File imageFile = new File(CACHE_FOLDER, URLEncoder.encode(url.toString(), "UTF-8"));

			if (!imageFile.getParentFile().exists()) {
				imageFile.getParentFile().mkdirs();
			}

			ImageIO.write(image, "PNG", imageFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}