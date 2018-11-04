package com.mcpacks.installer.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;

import com.google.common.collect.Lists;
import com.mcpacks.installer.Main;
import com.mcpacks.installer.resource.Resource;
import com.mcpacks.installer.util.thread.ThreadSaveFile;
import com.renderengine.gfx.font.Font;
import com.renderengine.gfx.font.Font.FontCharacter;

public class Utils {

	private static final List<Resource> DOWNLOADING = Lists.<Resource>newArrayList();

	public static BufferedImage loadImage(String location) {
		try {
			return ImageIO.read(Class.class.getResourceAsStream("/" + location));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static File getAppData() {
		ProcessBuilder builder = new ProcessBuilder("cmd", "/C echo %APPDATA%");

		BufferedReader br = null;
		try {
			Process start = builder.start();
			br = new BufferedReader(new InputStreamReader(start.getInputStream()));
			String path = br.readLine();
			if (path.endsWith("\"")) {
				path = path.substring(0, path.length() - 1);
			}
			return new File(path.trim());

		} catch (IOException e) {
			Main.LOGGER.warn("Cannot get Application Data Folder", e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					Main.LOGGER.warn((Object) null, e);
				}
			}
		}

		return null;
	}

	public static String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}

	public static String shortenString(Font font, String text, float scale, float width) {
		char[] charArray = text.toCharArray();

		float currentWidth = 0;
		for (int i = 0; i < charArray.length; i++) {
			FontCharacter character = font.getCharacter(charArray[i]);
			if (character != null) {
				if (currentWidth + character.getXAdvance() * scale > width) {
					return text.substring(0, i - 3) + "...";
				}
				currentWidth += character.getXAdvance() * scale;
			}
		}

		return text;
	}

	@Nullable
	public static File retrieveResource(Resource resource, boolean forceDownload) {
		File folder = new File(Main.DOWNLOADS_FOLDER, resource.getFormattedTitle() + "/" + resource.getVersion());
		File archive = new File(folder, "resource.zip");

		try {
			if (!DOWNLOADING.contains(resource)) {
				if (!archive.exists() || forceDownload) {
					DOWNLOADING.add(resource);
					ThreadSaveFile thread = new ThreadSaveFile(new URL(resource.getDownloadLink()), archive);
					thread.setErrorListener((error) -> {
						Main.LOGGER.info("Could not download \'" + resource.getTitle() + "\' from \'" + resource.getDownloadLink() + "\'", error);
						return false;
					});
					thread.start();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return archive.exists() ? archive : null;
	}
}