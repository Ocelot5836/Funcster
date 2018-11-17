package com.mcpacks.installer.util;

import java.io.File;

public interface ResourceDownloadListener {

	void onDownload(boolean success, File downloadedFile);
}