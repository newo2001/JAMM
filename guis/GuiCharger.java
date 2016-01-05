package newo2001.JAMM.guis;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import newo2001.JAMM.container.ContainerCharger;
import newo2001.JAMM.tileentities.TileEntityCharger;

public class GuiCharger extends GuiContainer {
	private IInventory playerInv;
	private TileEntityCharger te;
	
	public GuiCharger(IInventory playerInv, TileEntityCharger te) {
		super(new ContainerCharger(playerInv, te));
		
		this.playerInv = playerInv;
		this.te = te;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	    this.fontRendererObj.drawString("Charger", 88 - this.fontRendererObj.getStringWidth("Charger") / 2, 6, 4210752); //#404040
	    this.fontRendererObj.drawString("Inventory", 8, 72, 4210752);
	    if (mouseX-this.guiLeft > 8 && mouseX-this.guiLeft < 22 && mouseY-this.guiTop > 7 && mouseY-this.guiTop < 65)
	    	this.fontRendererObj.drawString("rf: " + te.getEnergyStored(ForgeDirection.UNKNOWN) + " / " + te.getMaxEnergyStored(ForgeDirection.UNKNOWN), mouseX-this.guiLeft+10, mouseY-this.guiTop-5, 0); //#000000
	}	
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
	    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	    this.mc.getTextureManager().bindTexture(new ResourceLocation("JAMM:textures/gui/container/tileEntityCharger.png"));
	    this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	    this.drawTexturedModalRect(this.guiLeft+9, this.guiTop+65-te.getPixelsEnergyBar(), 177, 3, 13, te.getPixelsEnergyBar());
	    this.drawTexturedModalRect(this.guiLeft+155, this.guiTop+65-te.getPixelsProgressBar(), 191, 3, 13, te.getPixelsProgressBar());
	}
}
