package com.mcpacks.installer.resource;

import java.util.Map;

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
		return "https://www.minecraftpacks.net/resources/" + this.getFormattedTitle() + "." + this.getId() + "/download";
	}

	public Map<String, String> getCustomFields() {
		return customFields;
	}

	public ResourceType getType() {
		return type;
	}

	public enum ResourceType {
		DATA(2), RESOURCE(3), TOOL(4), UNKNOWN(-1);

		private int id;

		private ResourceType(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}

		public static ResourceType fromId(int id) {
			if (id <= 1 || id >= 5)
				return ResourceType.UNKNOWN;
			for (int i = 0; i < ResourceType.values().length; i++) {
				if (ResourceType.values()[i].id == id)
					return ResourceType.values()[i];
			}
			return ResourceType.UNKNOWN;
		}
	}
}