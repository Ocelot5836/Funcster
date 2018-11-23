package com.mcpacks.installer;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mcpacks.installer.util.Utils;
import com.renderengine.api.Application;
import com.renderengine.api.Display;
import com.renderengine.api.scenes.Scene;
import com.renderengine.gfx.font.Font;
import com.renderengine.gfx.font.FontRenderer;
import com.renderengine.gfx.font.StandardFontRenderer;
import com.renderengine.gfx.texture.TextureManager;
import com.renderengine.util.LoadingUtils;
import com.renderengine.util.ResourceLocation;

public class Main extends Application implements Runnable {

	public static final String TITLE = "Functster - Resource Manager";
	public static final String VERSION = "1.0";
	public static final int WIDTH = 1000;
	public static final int HEIGHT = WIDTH / 12 * 9;

	public static final Logger LOGGER = LogManager.getLogger(TITLE);
	public static final File DATA_FOLDER = new File("data");
	public static final File DOWNLOADS_FOLDER = new File(DATA_FOLDER, "downloads");
	public static final File DEFAULT_MINECRAFT_FOLDER = new File(Utils.getAppData(), ".minecraft");
	public static final String API_LINK = "https://minecraftpacks.net/api/resources.php";

	public static File minecraftDirectory = null;
	public static File currentWorldFolder = null;

	public static final ResourceLocation UNKNOWN = new ResourceLocation("textures/unknown.png");
	public static final ResourceLocation UI = new ResourceLocation("textures/ui.png");
	public static final ResourceLocation WIDGETS = new ResourceLocation("textures/widgets.png");
	public static final ResourceLocation ICONS = new ResourceLocation("textures/icons.png");
	public static final float SCALE = 2f;

	private static Main instance;

	private Frame frame;
	private TextureManager textureManager;
	private FontRenderer fontRenderer;

	public Main() {
		instance = this;
		
		this.setClearColor(1, 0, 1, 1);
	}

	@Override
	public void init() {
		Display.setIcon(LoadingUtils.loadImage("icon", "/icon/icon.png"));

		this.frame = new Frame();
		this.textureManager = new TextureManager();
		this.fontRenderer = new StandardFontRenderer(this.textureManager, new ResourceLocation("textures/font/calibri.png"), new ResourceLocation("font/calibri.fnt"));

		try {
			this.frame.init();
		} catch (Exception e) {
			LOGGER.fatal("Could not initialize frame", e);
			this.stop();
		}

		if (!DATA_FOLDER.exists()) {
			DATA_FOLDER.mkdirs();
		}
		
		if(!DOWNLOADS_FOLDER.exists()) {
			DOWNLOADS_FOLDER.mkdirs();
		}
	}

	@Override
	public void update() {
		super.update();

		this.frame.update();
	}

	@Override
	public void render(float partialTicks) {
		super.render(partialTicks);

		this.frame.render(partialTicks);
	}

	@Override
	public void cleanUp() {
		super.cleanUp();

		this.frame.stop();
	}

	public Frame getFrame() {
		return frame;
	}

	public TextureManager getTextureManager() {
		return textureManager;
	}

	public FontRenderer getFontRenderer() {
		return fontRenderer;
	}

	public Font getFont() {
		return ((StandardFontRenderer) fontRenderer).getFont();
	}

	public void setScene(Scene sceneDatapackSelection) {
		this.getSceneManager().setCurrentScene(sceneDatapackSelection);
	}

	public static Main getInstance() {
		return instance;
	}

	public static void main(String[] args) {
		launch(new Main(), 60, 120, TITLE + " v" + VERSION, WIDTH, HEIGHT, args);
	}
}