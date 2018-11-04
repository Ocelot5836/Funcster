package com.mcpacks.installer.util.thread;

import java.net.HttpURLConnection;

public interface LoadListener {

	void load(HttpURLConnection connection) throws Exception;
}