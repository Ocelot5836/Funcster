package com.mcpacks.installer.util.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

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

		IOUtils.write(IOUtils.toString(connection.getInputStream(), Charset.defaultCharset()), new FileOutputStream(this.saveFile), Charset.defaultCharset());
	}
}