package com.mcpacks.installer.component;

import java.awt.image.BufferedImage;

import com.mcpacks.installer.Main;
import com.mcpacks.installer.resource.Resource;
import com.mcpacks.installer.util.DynamicTextureLoader;
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

		this.button = new ResourceModifyButton(171 * Main.SCALE, 3 * Main.SCALE, ResourceModifyButton.EnumSize.NORMAL, ResourceModifyButton.EnumIcon.LARGE_DELETE, 0xffE8823F);
		this.button.setListener((mouseButton, mouseX, mouseY) -> {
			System.out.println(this.resource.getId());
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