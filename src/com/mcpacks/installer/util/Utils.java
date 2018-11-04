package com.mcpacks.installer.util;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Utils {

	public static BufferedImage loadImage(String location) {
		try {
			return ImageIO.read(Class.class.getResourceAsStream("/" + location));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}