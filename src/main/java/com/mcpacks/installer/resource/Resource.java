package com.mcpacks.installer.resource;

import java.io.File;
import java.util.Map;

import com.google.common.collect.Maps;
import com.mcpacks.installer.Main;

public class Resource {

	private int id;
	private String title;
	private String tagLine;
	private String username;
	private String version;
	private int userId;
	private long date;
	private long lastUpdate;
	private int downloads;
	private double rating;
	private Map<String, String> customFields;
	private ResourceType type;

	public Resource(int id, String title, String tagLine, String username, String version, int userId, long date, long lastUpdate, int downloads, double rating, Map<String, String> customFields, ResourceType type) {
		this.id = id;
		this.title = title;
		this.tagLine = tagLine;
		this.username = username;
		this.version = version;
		this.userId = userId;
		this.date = date;
		this.lastUpdate = lastUpdate;
		this.downloads = downloads;
		this.rating = rating;
		this.customFields = customFields;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getFormattedTitle() {
		return this.getTitle().toLowerCase().replaceAll(" ", "-");
	}

	public String getTagLine() {
		return tagLine;
	}

	public String getUser() {
		return username;
	}

	public String getVersion() {
		return version;
	}

	public int getUserId() {
		return userId;
	}

	public long getDate() {
		return date;
	}

	public long getLastUpdate() {
		return lastUpdate;
	}

	public int getDownloads() {
		return downloads;
	}

	public double getRating() {
		return rating;
	}

	public String getIconLink() {
		return "https://minecraftpacks.net/data/resource_icons/0/" + this.getId() + ".jpg";
	}

	public String getUserIconLink() {
		return "https://www.minecraftpacks.net/data/avatars/o/0/" + this.getUserId() + ".jpg";
	}

	public String getDownloadLink() {
		return "https://www.minecraftpacks.net/resources/" + this.getId() + "/download";
	}

	public File getLocalLoation() {
		return new File(Main.DOWNLOADS_FOLDER, this.getId() + "/" + this.getVersion() + "/resource.zip");
	}

	public Map<String, String> getCustomFields() {
		return customFields;
	}

	public ResourceType getType() {
		return type;
	}

	public enum ResourceType {
		UNKNOWN(-1), DATA(2), RESOURCE(3), TOOL(4), MAP(5);

		private static final Map<Integer, ResourceType> ID_LOOKUP = Maps.<Integer, ResourceType>newHashMap();

		private int id;

		private ResourceType(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}

		public static ResourceType fromId(int id) {
			return ID_LOOKUP.getOrDefault(id, UNKNOWN);
		}

		static {
			for (ResourceType type : values()) {
				ID_LOOKUP.put(type.getId(), type);
			}
		}
	}
}