package com.mcpacks.installer.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mcpacks.installer.Main;
import com.mcpacks.installer.resource.Resource;
import com.mcpacks.installer.resource.Resource.ResourceType;

public class ResourceLoader {

	public static final List<Resource> RESOURCES = new ArrayList<Resource>();

	public static final List<Resource> DATAPACKS = new ArrayList<Resource>();
	public static final List<Resource> RESOURCEPACKS = new ArrayList<Resource>();
	public static final List<Resource> TOOLS = new ArrayList<Resource>();

	public static void load(JsonArray data) throws Exception {
		RESOURCES.clear();

		for (int i = 0; i < data.size(); i++) {
			JsonObject object = data.get(i).getAsJsonObject();

			try {
				Resource.ResourceType resourceType = Resource.ResourceType.fromId(Integer.parseInt(object.get("resource_category_id").getAsString()));

				if (resourceType != ResourceType.UNKNOWN) {
					int resourceId = Integer.parseInt(object.get("resource_id").getAsString());
					String title = object.get("title").getAsString();
					String tagLine = object.get("tag_line").getAsString();
					String username = object.get("username").getAsString();
					String version = object.get("current_version_id").getAsString();
					int userId = Integer.parseInt(object.get("user_id").getAsString());
					long date = Integer.parseInt(object.get("resource_date").getAsString());
					long lastUpdate = Integer.parseInt(object.get("last_update").getAsString());
					int downloads = Integer.parseInt(object.get("download_count").getAsString());
					double rating = Double.parseDouble(object.get("rating_avg").getAsString());
					Map<String, String> customFields = parseCustomFields(object.get("custom_fields").getAsString());
					Resource resource = new Resource(resourceId, title, tagLine, username, version, userId, date, lastUpdate, downloads, rating, customFields, resourceType);

					switch (resourceType) {
					case DATA:
						DATAPACKS.add(resource);
						RESOURCES.add(resource);
						break;
					case RESOURCE:
						RESOURCEPACKS.add(resource);
						RESOURCES.add(resource);
						break;
					case TOOL:
						TOOLS.add(resource);
						break;
					default:
						break;
					}
				}
			} catch (Exception e) {
				Main.LOGGER.warn("Could not load resource \'" + object + "\', Skipping", e);
				continue;
			}
		}
	}

	private static Map<String, String> parseCustomFields(String customFields) {
		Map<String, String> map = new HashMap<String, String>();

		String[] split = customFields.split(":");
		List<String> json = new ArrayList<String>();

		String last = null;
		for (int i = 0; i < split.length; i++) {
			String param = split[i].replaceAll("\\{", "").replaceAll("\\}", "");
			if (param.length() > 2) {
				if (param.substring(1).startsWith("http")) {
					last = param;
					continue;
				} else {
					json.add(last != null ? last + ":" + param : param.split(";")[0]);
					last = null;
				}
			}
		}

		String key = null;
		boolean requirements = false;
		for (String param : json) {
			param = param.replaceAll("\"", "");
			if (!param.equalsIgnoreCase("mcreplacevanilla") && !param.equalsIgnoreCase("yesreplacevanilla")) {
				if (param.equalsIgnoreCase("mcpackrequirements")) {
					requirements = true;
					continue;
				}

				if (requirements) {
					if (param.startsWith("choice")) {
						continue;
					} else {
						requirements = false;
					}
				}

				if (!requirements) {
					if (key == null) {
						key = param;
					} else {
						map.put(key, param);
						key = null;
					}
				}
			}
		}

		return map;
	}
}