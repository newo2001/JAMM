package newo2001.JAMM.guis;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import newo2001.JAMM.container.ContainerCutter;
import newo2001.JAMM.tileentities.TileEntityCutter;

import org.lwjgl.opengl.GL11;

public class GuiCutter extends GuiContainer {
	private IInventory playerInv;
	private TileEntityCutter te;

	public GuiCutter(IInventory playerInv, TileEntityCutter te) {
		super(new ContainerCutter(playerInv, te));

		this.playerInv = playerInv;
		this.te = te;

		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRendererObj.drawString(te.getCustomName(), 88 - this.fontRendererObj.getStringWidth(te.getCustomName()) / 2, 6, 4210752); // #404040
		this.fontRendererObj.drawString("Inventory", 8, 72, 4210752);

		int k = (this.width - this.xSize) / 2; // X asis on GUI
		int l = (this.height - this.ySize) / 2; // Y asis on GUI

		//Energy bar tooltip
		if (mouseX > 8 + k && mouseX < 22 + k) {
			if (mouseY > 7 + l && mouseY < 65 + l) {
				List list = new ArrayList();
				list.add("RF: " + te.getEnergyStored(ForgeDirection.UNKNOWN) + " / " + te.getMaxEnergyStored(ForgeDirection.UNKNOWN));
				this.drawHoveringText(list, (int) mouseX - k, (int) mouseY - l, fontRendererObj);
			}
		}

		//Progress bar tooltip
		if (mouseX > this.xSize - 22 + k && mouseX < this.xSize - 8 + k) {
			if (mouseY > 7 + l && mouseY < 65 + l) {
				List list = new ArrayList();
				if (te.getCutTime() > 0) {
					list.add("Progress: " + (int) (te.getProgress() / (float) (te.getCutTime() / 100f)) + "%");
				} else
					list.add("Progress: 0%");
				this.drawHoveringText(list, (int) mouseX - k, (int) mouseY - l, fontRendererObj);
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("JAMM:textures/gui/container/tileEntityCutter.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		this.drawTexturedModalRect(this.guiLeft + 9, this.guiTop + 65 - te.getPixelsEnergyBar(), 177, 3, 13, te.getPixelsEnergyBar());
		this.drawTexturedModalRect(this.guiLeft + 155, this.guiTop + 65 - te.getPixelsProgressBar(), 191, 3, 13, te.getPixelsProgressBar());
	}
}
