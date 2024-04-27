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

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import reborncore.client.gui.GuiBase;
import reborncore.client.gui.GuiBuilder;
import reborncore.common.screen.BuiltScreenHandler;
import techreborn.blockentity.machine.tier1.RollingMachineBlockEntity;
import techreborn.packets.ServerboundPackets;
import techreborn.packets.serverbound.RollingMachineLockPayload;

public class GuiRollingMachine extends GuiBase<BuiltScreenHandler> {

	final RollingMachineBlockEntity rollingMachine;

	public GuiRollingMachine(int syncID, final PlayerEntity player, final RollingMachineBlockEntity blockEntityRollingmachine) {
		super(player, blockEntityRollingmachine, blockEntityRollingmachine.createScreenHandler(syncID, player));
		this.rollingMachine = blockEntityRollingmachine;
	}

	@Override
	protected void drawBackground(DrawContext drawContext, final float f, final int mouseX, final int mouseY) {
		super.drawBackground(drawContext, f, mouseX, mouseY);
		final GuiBase.Layer layer = GuiBase.Layer.BACKGROUND;

		int gridYPos = 22;
		drawSlot(drawContext, 30, gridYPos, layer);
		drawSlot(drawContext, 48, gridYPos, layer);
		drawSlot(drawContext, 66, gridYPos, layer);
		drawSlot(drawContext, 30, gridYPos + 18, layer);
		drawSlot(drawContext, 48, gridYPos + 18, layer);
		drawSlot(drawContext, 66, gridYPos + 18, layer);
		drawSlot(drawContext, 30, gridYPos + 36, layer);
		drawSlot(drawContext, 48, gridYPos + 36, layer);
		drawSlot(drawContext, 66, gridYPos + 36, layer);

		drawSlot(drawContext, 8, 70, layer);
		drawOutputSlot(drawContext, 124, gridYPos + 18, layer);

		builder.drawLockButton(drawContext, this, 130, 4, mouseX, mouseY, layer, rollingMachine.locked);
	}

	@Override
	protected void drawForeground(DrawContext drawContext, final int mouseX, final int mouseY) {
		super.drawForeground(drawContext, mouseX, mouseY);
		final GuiBase.Layer layer = GuiBase.Layer.FOREGROUND;

		builder.drawProgressBar(drawContext, this, rollingMachine.getProgressScaled(100), 100, 92, 43, mouseX, mouseY, GuiBuilder.ProgressDirection.RIGHT, layer);
		builder.drawMultiEnergyBar(drawContext, this, 9, 17, (int) rollingMachine.getEnergy(), (int) rollingMachine.getMaxStoredPower(), mouseX, mouseY, 0, layer);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		if (isPointInRect(130, 4, 20, 12, mouseX, mouseY)) {
			ClientPlayNetworking.send(new RollingMachineLockPayload(rollingMachine.getPos(), !rollingMachine.locked));
			return true;
		}
		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}

}
