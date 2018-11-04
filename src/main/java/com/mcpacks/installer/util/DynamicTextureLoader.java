package com.mcpacks.installer.util;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.lwjgl.opengl.GL11;

import com.mcpacks.installer.Main;
import com.renderengine.gfx.texture.Texture;
import com.renderengine.util.Loader;
import com.renderengine.util.ResourceLocation;

public class DynamicTextureLoader {

	private static final Map<BufferedImage, ResourceLocation> TEXTURES = new HashMap<BufferedImage, ResourceLocation>();

	public static ResourceLocation get(BufferedImage image) {
		if (!TEXTURES.containsKey(image)) {
			ResourceLocation location = new ResourceLocation("dynamic/" + UUID.randomUUID().toString());
			TEXTURES.put(image, location);
			Texture texture = Loader.loadTexture(image);
			Main.getInstance().getTextureManager().load(location, texture);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getId());
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		}
		return TEXTURES.get(image);
	}
}