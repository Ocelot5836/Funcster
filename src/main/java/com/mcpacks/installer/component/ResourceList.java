package com.mcpacks.installer.component;

import com.mcpacks.installer.resource.Resource;
import com.renderengine.api.components.ListComponent;

public class ResourceList extends ListComponent<Resource> {

	public ResourceList(float x, float y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void renderBackground(float x, float y, double mouseX, double mouseY, float partialTicks) {
		
	}

	@Override
	public void renderDefaultItem(Resource item, float x, float y, float width, float height, double mouseX, double mouseY, boolean selected) {
	}
}