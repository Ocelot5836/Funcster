package com.mcpacks.installer.component;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;

import com.mcpacks.installer.Main;
import com.mcpacks.installer.component.ResourceModifyButton.EnumIcon;
import com.mcpacks.installer.resource.Resource;
import com.mcpacks.installer.util.DynamicTextureLoader;
import com.mcpacks.installer.util.ResourceLoader;
import com.mcpacks.installer.util.Utils;
import com.mcpacks.installer.util.thread.ImageLoader;
import com.renderengine.api.components.Layout;
import com.renderengine.gfx.font.FontRenderer;
import com.renderengine.gfx.renderer.Renderer;
import com.renderengine.gfx.texture.TextureManager;

public class LayoutResourceItem extends Layout {

	private Resource resource;
	private ResourceModifyButton button;

	public LayoutResourceItem(Resource resource, float x, float y) {
		super(x, y, 200 * Main.SCALE, 32 * Main.SCALE);
		this.resource = resource;
	}

	@Override
	public void init(Layout layout) {
		super.init(layout);

		this.button = new ResourceModifyButton(171 * Main.SCALE, 3 * Main.SCALE, ResourceModifyButton.EnumSize.NORMAL, this.resource.getLocalLoation().exists() ? EnumIcon.LARGE_DELETE : EnumIcon.LARGE_DOWNLOAD, 0xffE8823F);
		this.button.setListener((mouseButton, mouseX, mouseY) -> {
			if (!ResourceLoader.isDownloading(this.resource)) {
				if (!this.resource.getLocalLoation().exists()) {
					ResourceLoader.retrieveResource(this.resource, false, (success, archive) -> {
						try {
							if (!success) {
								this.button.setIcon(EnumIcon.NONE);
								return;
							}

							if (archive != null) {
								this.button.setIcon(EnumIcon.LARGE_DELETE);
								Main.LOGGER.info("Successfully downloaded \'" + this.resource.getTitle() + "\' from \'" + this.resource.getDownloadLink() + "\'");
								if (Main.currentWorldFolder != null) {
									File datapackArchive = new File(Main.currentWorldFolder, "datapacks/" + this.resource.getFormattedTitle() + ".zip");
									if (!datapackArchive.getParentFile().exists()) {
										datapackArchive.mkdirs();
									}
									if (datapackArchive.exists()) {
										datapackArchive.delete();
									}

									FileOutputStream os = new FileOutputStream(datapackArchive);
									os.getChannel().transferFrom(Channels.newChannel(new FileInputStream(archive)), 0, Long.MAX_VALUE);
									os.close();
								} else {
									// TODO remove dis
									String worldName = "1.13 Survival";
									Main.currentWorldFolder = new File(Main.minecraftDirectory, "saves/" + worldName);
								}
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
				}
			}
		});
		super.addComponent(this.button);
	}

	@Override
	public void render(float x, float y, double mouseX, double mouseY, float partialTicks) {
		Main main = Main.getInstance();
		TextureManager textureManager = main.getTextureManager();
		FontRenderer fontRenderer = main.getFontRenderer();

		textureManager.bind(Main.UI);
		Renderer.renderTexturedRect(x, y, 0, 0, this.getWidth(), this.getHeight(), 200, 32, 256, 256);

		BufferedImage icon = ImageLoader.get(this.resource.getIconLink(), false);
		textureManager.bind(icon == null ? Main.UNKNOWN : DynamicTextureLoader.get(icon));
		if (icon != null) {
			Renderer.renderTexturedRect(x + 4 * Main.SCALE, y + 4 * Main.SCALE, 0, 0, 24 * Main.SCALE, 24 * Main.SCALE, icon.getWidth(), icon.getHeight(), icon.getWidth(), icon.getHeight());
		} else {
			Renderer.renderTexturedRect(x + 4 * Main.SCALE, y + 4 * Main.SCALE, 0, 0, 24 * Main.SCALE, 24 * Main.SCALE, 128, 128, 128, 128);
		}

		fontRenderer.render(Utils.shortenString(main.getFont(), this.resource.getTitle(), 0.2f, this.getWidth() - 112), x + 30 * Main.SCALE, y + 2 * Main.SCALE, 0.2f);
		fontRenderer.render(Utils.shortenString(main.getFont(), this.resource.getTagLine(), 0.2f, this.getWidth() - 112), x + 30 * Main.SCALE, y + 11 * Main.SCALE, 0.2f);
		fontRenderer.render(Utils.shortenString(main.getFont(), this.resource.getUser(), 0.2f, this.getWidth() - 112), x + 30 * Main.SCALE, y + 20 * Main.SCALE, 0.2f);

		super.render(x, y, mouseX, mouseY, partialTicks);
	}
}