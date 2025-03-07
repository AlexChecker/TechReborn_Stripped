/*
 * This file is part of TechReborn, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2020 TechReborn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package techreborn.client.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import reborncore.client.gui.GuiBase;
import reborncore.common.fluid.FluidUtils;
import reborncore.common.fluid.container.FluidInstance;
import reborncore.common.screen.BuiltScreenHandler;
import techreborn.blockentity.storage.fluid.TankUnitBaseBlockEntity;

public class GuiTankUnit extends GuiBase<BuiltScreenHandler> {

	final TankUnitBaseBlockEntity tankEntity;

	public GuiTankUnit(int syncID, final PlayerEntity player, final TankUnitBaseBlockEntity tankEntity) {
		super(player, tankEntity, tankEntity.createScreenHandler(syncID, player));
		this.tankEntity = tankEntity;
	}

	@Override
	protected void drawBackground(DrawContext drawContext, final float f, final int mouseX, final int mouseY) {
		super.drawBackground(drawContext, f, mouseX, mouseY);
		final GuiBase.Layer layer = GuiBase.Layer.BACKGROUND;

		// Draw slots
		drawSlot(drawContext, 100, 53, layer);
		drawSlot(drawContext, 140, 53, layer);
	}

	@Override
	protected void drawForeground(DrawContext drawContext, final int mouseX, final int mouseY) {
		super.drawForeground(drawContext, mouseX, mouseY);

		// Draw input/out
		builder.drawText(drawContext, this, Text.translatable("gui.techreborn.unit.in"), 100, 43, theme.titleColor().rgba());
		builder.drawText(drawContext, this, Text.translatable("gui.techreborn.unit.out"), 140, 43, theme.titleColor().rgba());


		FluidInstance fluid = tankEntity.getTank().getFluidInstance();

		if (fluid.isEmpty()) {
			drawContext.drawText(textRenderer, Text.translatable("techreborn.tooltip.unit.empty"), 10, 20, theme.titleColor().rgba(), false);
		} else {
			drawContext.drawText(textRenderer, Text.translatable("gui.techreborn.tank.type"), 10, 20, theme.titleColor().rgba(), false);
			drawContext.drawText(textRenderer, FluidUtils.getFluidName(fluid).replace("_", " "), 10, 30, theme.titleColor().rgba(), false);


			drawContext.drawText(textRenderer, Text.translatable("gui.techreborn.tank.amount"), 10, 50, theme.titleColor().rgba(), false);
			drawContext.drawText(textRenderer, fluid.getAmount().toString(), 10, 60, theme.titleColor().rgba(), false);

			String percentFilled = String.valueOf((int) ((double) fluid.getAmount().getRawValue() / (double) tankEntity.getTank().getFluidValueCapacity().getRawValue() * 100));

			drawContext.drawText(textRenderer, Text.translatable("gui.techreborn.unit.used").append(percentFilled + "%"), 10, 70, theme.titleColor().rgba(), false);

			drawContext.drawText(textRenderer, Text.translatable("gui.techreborn.unit.wrenchtip"), 10, 80, theme.warningTextColor().rgba(), false);
		}
	}

	@Override
	public void render(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
		super.render(drawContext, mouseX, mouseY, partialTicks);
		drawMouseoverTooltip(drawContext, mouseX, mouseY);
	}
}
