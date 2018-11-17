package com.mcpacks.installer.component;

import com.mcpacks.installer.Main;
import com.renderengine.api.components.ButtonComponent;
import com.renderengine.gfx.renderer.Renderer;

public class ResourceModifyButton extends ButtonComponent {

	private EnumSize size;
	private EnumIcon icon;
	private int color;

	public ResourceModifyButton(float x, float y, EnumSize size, EnumIcon icon, int color) {
		super(x, y, size.getWidth() * Main.SCALE, size.getHeight() * Main.SCALE);
		this.size = size;
		this.icon = icon;
		this.color = color;
	}

	@Override
	public void update() {
	}

	@Override
	public void render(float x, float y, double mouseX, double mouseY, float partialTicks) {
		Main.getInstance().getTextureManager().bind(Main.WIDGETS);
		Renderer.renderTexturedRect(x, y, this.size.getU(), this.size.getV(), this.getWidth(), this.getHeight(), this.size.getWidth(), this.size.getHeight(), 256, 256, this.color);

		Main.getInstance().getTextureManager().bind(Main.ICONS);
		Renderer.renderTexturedRect(x + this.getWidth() / 2 - this.icon.getWidth() * Main.SCALE / 2, y + this.getHeight() / 2 - this.icon.getHeight() * Main.SCALE / 2, this.icon.getU(), this.icon.getV(), this.icon.getWidth() * Main.SCALE, this.icon.getHeight() * Main.SCALE, this.icon.getWidth(), this.icon.getHeight(), 256, 256);

		if (this.isHovered(mouseX, mouseY)) {
			Renderer.renderColoredRect(x + Main.SCALE, y + Main.SCALE, this.getWidth() - Main.SCALE * 2, this.getHeight() - Main.SCALE * 2, 0x22000000);
		}
	}

	public EnumSize getSize() {
		return size;
	}

	public EnumIcon getIcon() {
		return icon;
	}

	public int getColor() {
		return color;
	}

	public void setSize(EnumSize size) {
		this.size = size;
		this.setSize(size.getWidth() * Main.SCALE, size.getHeight() * Main.SCALE);
	}

	public void setIcon(EnumIcon icon) {
		this.icon = icon;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public enum EnumSize {
		NORMAL(0, 0, 26, 26), SMALL(26, 0, 26, 14);

		private float u;
		private float v;
		private float width;
		private float height;

		private EnumSize(float u, float v, float width, float height) {
			this.u = u;
			this.v = v;
			this.width = width;
			this.height = height;
		}

		public float getU() {
			return u;
		}

		public float getV() {
			return v;
		}

		public float getWidth() {
			return width;
		}

		public float getHeight() {
			return height;
		}
	}

	public enum EnumIcon {
		NONE(0, 0, 0, 0), LARGE_DOWNLOAD(0, 0, 10, 18), LARGE_DELETE(10, 0, 18, 18);

		private float u;
		private float v;
		private float width;
		private float height;

		private EnumIcon(float u, float v, float width, float height) {
			this.u = u;
			this.v = v;
			this.width = width;
			this.height = height;
		}

		public float getU() {
			return u;
		}

		public float getV() {
			return v;
		}

		public float getWidth() {
			return width;
		}

		public float getHeight() {
			return height;
		}
	}
}