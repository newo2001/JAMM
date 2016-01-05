package newo2001.JAMM.guis;

import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.client.gui.inventory.GuiContainer;
import newo2001.JAMM.container.ContainerAssembler;
import newo2001.JAMM.tileentities.TileEntityAssembler;

import org.lwjgl.opengl.GL11;

public class GuiAssembler extends GuiContainer {
	private IInventory playerInv;
	private TileEntityAssembler te;
	
	public GuiAssembler(IInventory playerInv, TileEntityAssembler te) {
		super(new ContainerAssembler(playerInv, te));
		
		this.playerInv = playerInv;
		this.te = te;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRendererObj.drawString("Assembler", 88 - this.fontRendererObj.getStringWidth("Charger") / 2, 6, 4210752); //#404040
	    this.fontRendererObj.drawString("Inventory", 8, 72, 4210752);
	    if (mouseX-this.guiLeft > 8 && mouseX-this.guiLeft < 22 && mouseY-this.guiTop > 12 && mouseY-this.guiTop < 70)
	    	this.fontRendererObj.drawString("rf: " + te.getEnergyStored(ForgeDirection.UNKNOWN) + " / " + te.getMaxEnergyStored(ForgeDirection.UNKNOWN), mouseX-this.guiLeft+10, mouseY-this.guiTop-5, 0); //#000000
	}	
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
	    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	    this.mc.getTextureManager().bindTexture(new ResourceLocation("JAMM:textures/gui/container/tileEntityAssembler.png"));
	    this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	    this.drawTexturedModalRect(this.guiLeft+9, this.guiTop+70-te.getPixelsEnergyBar(), 177, 3, 13, te.getPixelsEnergyBar());
	    this.drawTexturedModalRect(this.guiLeft+155, this.guiTop+70-te.getPixelsProgressBar(), 191, 3, 13, te.getPixelsProgressBar());
	}
}
