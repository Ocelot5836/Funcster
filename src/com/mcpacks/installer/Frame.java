package com.mcpacks.installer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.mcpacks.installer.util.ImageLoader;

public class Frame {

	public void update() {
	}

	public void render(Graphics g) {
		BufferedImage loadedImage = ImageLoader.get("https://minecraftpacks.net/data/resource_icons/0/17.jpg?1538080058", false);
		if (loadedImage != null) {
			g.drawImage(loadedImage, 0, 0, null);
		}
	}
}