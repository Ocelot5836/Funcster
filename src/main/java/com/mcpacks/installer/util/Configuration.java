package com.mcpacks.installer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import com.mcpacks.installer.Main;

public class Configuration {

	private File location;
	private Properties properties;

	public Configuration(File location) {
		this.location = location;
		this.properties = new Properties();
	}

	public void set(String key, String value) {
		this.properties.setProperty(key, value);
	}

	public void set(String key, byte value) {
		this.set(key, Byte.toString(value));
	}

	public void set(String key, short value) {
		this.set(key, Short.toString(value));
	}

	public void set(String key, int value) {
		this.set(key, Integer.toString(value));
	}

	public void set(String key, float value) {
		this.set(key, Float.toString(value));
	}

	public void set(String key, double value) {
		this.set(key, Double.toString(value));
	}

	public void set(String key, boolean value) {
		this.set(key, Boolean.toString(value));
	}

	public String get(String key, String defaultValue) {
		return this.properties.getProperty(key, defaultValue);
	}

	public byte get(String key, byte defaultValue) {
		try {
			return Byte.parseByte(this.get(key, Byte.toString(defaultValue)));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public short get(String key, short defaultValue) {
		try {
			return Short.parseShort(this.get(key, Short.toString(defaultValue)));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public int get(String key, int defaultValue) {
		try {
			return Integer.parseInt(this.get(key, Integer.toString(defaultValue)));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public float get(String key, float defaultValue) {
		try {
			return Float.parseFloat(this.get(key, Float.toString(defaultValue)));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public double get(String key, double defaultValue) {
		try {
			return Double.parseDouble(this.get(key, Double.toString(defaultValue)));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public boolean get(String key, boolean defaultValue) {
		try {
			return Boolean.parseBoolean(this.get(key, Boolean.toString(defaultValue)));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public void readConfig() {
		if (this.location.exists()) {
			try {
				this.properties.loadFromXML(new FileInputStream(this.location));
			} catch (Exception e) {
				Main.LOGGER.warn("Could not read config file from \'" + this.location.toString() + "\'", e);
			}
		}
	}

	public void saveConfig() {
		this.saveConfig(null);
	}

	public void saveConfig(String comment) {
		try {
			if (this.location.getParentFile() != null && !this.location.getParentFile().exists()) {
				this.location.getParentFile().mkdirs();
			}

			if (!this.location.exists()) {
				this.location.createNewFile();
			}

			this.properties.storeToXML(new FileOutputStream(this.location), comment);
		} catch (Exception e) {
			Main.LOGGER.warn("Could not save config to \'" + this.location.toString() + "\'", e);
		}
	}
}