package com.mcpacks.installer;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

import com.google.gson.JsonParser;
import com.mcpacks.installer.component.LayoutResourceItem;
import com.mcpacks.installer.component.LayoutResourceSelection;
import com.mcpacks.installer.resource.Resource;
import com.mcpacks.installer.util.Configuration;
import com.mcpacks.installer.util.ResourceLoader;
import com.mcpacks.installer.util.thread.ThreadLoadFromURL;
import com.renderengine.api.Display;
import com.renderengine.api.components.ScrollableLayout;
import com.renderengine.api.scenes.Scene;
import com.renderengine.gfx.font.FontRenderer;
import com.renderengine.gfx.texture.TextureManager;

public class Frame {

	private Main main;
	@SuppressWarnings("unused")
	private TextureManager textureManager;
	private FontRenderer fontRenderer;
	private Configuration config;

	private boolean loaded;
	private File minecraftDir;

	private Scene sceneDatapackSelection;

	public void init() throws Exception {
		this.main = Main.getInstance();
		this.textureManager = this.main.getTextureManager();
		this.fontRenderer = this.main.getFontRenderer();
		this.config = new Configuration(new File(Main.DATA_FOLDER, "settings.xml"));
		this.config.readConfig();
		this.minecraftDir = new File(this.config.get("minecraftDir", Main.DEFAULT_MINECRAFT_FOLDER.toString()));
		this.config.set("minecraftDir", this.minecraftDir.toString());
		this.loaded = false;

		this.sceneDatapackSelection = new Scene();

		ScrollableLayout layoutDatapackSelection = new LayoutResourceSelection();

		ThreadLoadFromURL thread = new ThreadLoadFromURL(new URL(Main.API_LINK));
		thread.setLoadListener((connection) -> {
			ResourceLoader.load(new JsonParser().parse("[" + IOUtils.toString(connection.getInputStream(), Charset.defaultCharset()).replaceAll("\\}\\{", "\\}\\,\\{") + "]").getAsJsonArray());
			this.loaded = true;
			for (int i = 0; i < ResourceLoader.DATAPACKS.size(); i++) {
				Resource resource = ResourceLoader.DATAPACKS.get(i);
				layoutDatapackSelection.addComponent(new LayoutResourceItem(resource, 0, i * 32 * Main.SCALE));
			}
			layoutDatapackSelection.setMaxScroll(ResourceLoader.DATAPACKS.size() * 32 * Main.SCALE - Display.getHeight());
			this.sceneDatapackSelection.setLayout(layoutDatapackSelection);
			Main.getInstance().setScene(this.sceneDatapackSelection);
		});
		thread.start();
	}

	public void update() {
		if (ResourceLoader.RESOURCES.size() > 0) {
			for (int i = 0; i < ResourceLoader.RESOURCES.size(); i++) {
				System.out.println(ResourceLoader.retrieveResource(ResourceLoader.RESOURCES.get(i), false));
			}
		}
	}

	public void render(float partialTicks) {
		if (this.loaded) {
		} else {
			this.fontRenderer.render("Loading...", Display.getWidth() / 2 - this.fontRenderer.getStringWidth("Loading...", 1) / 2, Display.getHeight() / 2 - this.fontRenderer.getStringHeight("Loading...", 1) / 2);
		}
	}

	public void stop() {
		this.config.saveConfig("Options for " + Main.TITLE);
	}
}