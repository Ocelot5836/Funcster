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
		Renderer.renderTexturedRect(this.getX() + this.getOffsetX(), this.getY() + this.getOffsetY(), this.size.getU(), this.size.getV(), this.getWidth(), this.getHeight(), this.size.getWidth(), this.size.getHeight(), 256, 256, this.color);

		if (this.isHovered(mouseX, mouseY)) {
			Renderer.renderColoredRect(this.getX() + this.getOffsetX(), this.getY() + this.getOffsetY(), this.getWidth(), this.getHeight(), 0xaaffffff);
		}
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