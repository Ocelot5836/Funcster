package com.mcpacks.installer.util.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;

public class ThreadSaveZipFile extends ThreadSaveFile implements LoadListener {

	public ThreadSaveZipFile(URL url, File saveFile) {
		super(url, saveFile);
	}

	@Override
	public void load(HttpURLConnection connection) throws Exception {
		File saveFile = this.getSaveFile();
		if (!saveFile.getParentFile().exists()) {
			saveFile.getParentFile().mkdirs();
		}

		FileOutputStream os = new FileOutputStream(saveFile);
		os.getChannel().transferFrom(Channels.newChannel(connection.getInputStream()), 0, Long.MAX_VALUE);
		os.close();
	}
}