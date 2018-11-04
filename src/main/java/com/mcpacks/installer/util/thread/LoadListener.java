package com.mcpacks.installer.util.thread;

import java.io.InputStream;

public interface LoadListener {

	void load(InputStream data) throws Exception;
}