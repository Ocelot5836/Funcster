package com.mcpacks.installer.util.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
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
	public void load(InputStream data) throws Exception {
		if (!saveFile.getParentFile().exists()) {
			saveFile.getParentFile().mkdirs();
		}
		
		IOUtils.write(IOUtils.toString(data, Charset.defaultCharset()), new FileOutputStream(this.saveFile), Charset.defaultCharset());
	}
}