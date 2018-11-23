package com.mcpacks.installer.util.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;

public class ThreadSaveFile extends ThreadLoadFromURL implements LoadListener {

	private File saveFile;

	public ThreadSaveFile(URL url, File saveFile) {
		super(url);
		this.setLoadListener(this);
		this.saveFile = saveFile;
	}

	@Override
	public void load(HttpURLConnection connection) throws Exception {
		if (!this.saveFile.getParentFile().exists()) {
			this.saveFile.getParentFile().mkdirs();
		}
		
		FileOutputStream os = new FileOutputStream(this.saveFile);
		os.getChannel().transferFrom(Channels.newChannel(connection.getInputStream()), 0, Long.MAX_VALUE);
		os.close();
	}

	public File getSaveFile() {
		return saveFile;
	}
}