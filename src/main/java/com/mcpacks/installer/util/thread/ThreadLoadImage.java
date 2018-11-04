package com.mcpacks.installer.util.thread;

import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

public class ThreadLoadImage extends ThreadLoadFromURL implements LoadListener {

	public ThreadLoadImage(URL url) {
		super(url);
		this.setLoadListener(this);
	}

	@Override
	public void load(InputStream data) throws Exception {
		ImageLoader.load(this.getUrl(), ImageIO.read(data));
	}
}