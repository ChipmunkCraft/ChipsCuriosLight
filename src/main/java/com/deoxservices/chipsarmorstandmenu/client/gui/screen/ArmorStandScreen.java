package com.deoxservices.chipsarmorstandmenu.client.gui.screen;

import com.deoxservices.chipsarmorstandmenu.client.gui.components.CustomCheckbox;
import com.deoxservices.chipsarmorstandmenu.menu.ArmorStandMenu;
import com.deoxservices.chipsarmorstandmenu.network.ToggleArmorStandPacket;
import com.deoxservices.chipsarmorstandmenu.utils.Constants;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;

public class ArmorStandScreen extends AbstractContainerScreen<ArmorStandMenu> {
    Minecraft minecraft = Minecraft.getInstance();
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/gui/container/armor_stand_menu.png");

    private CustomCheckbox showArmsCheckbox;
    private CustomCheckbox showBaseCheckbox;
    private CustomCheckbox showStandCheckbox;
    protected int playerLabelX;
    protected int playerLabelY;
    protected int armorLeftLabelX;
    protected int armorLeftLabelY;
    protected final Component playerInventoryTitle;

    public ArmorStandScreen(ArmorStandMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.imageWidth = 256;
        this.imageHeight = 256;
        this.playerInventoryTitle = playerInv.getDisplayName();
        this.playerLabelX = 8;
        this.playerLabelY = 4;
        this.armorLeftLabelX = 180;
        this.armorLeftLabelY = 4;
    }

    @Override
    protected void init() {
        super.init();
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        showStandCheckbox = CustomCheckbox.builder(Component.translatable("gui.chipsarmorstandmenu.show_stand"), minecraft.font)
            .pos(x + 180, y + 110)
            .maxWidth(60)
            .selected(menu.getShowStand())
            .onValueChange((checkbox, value) -> {
                if (menu.getArmorStand() != null) {
                    PacketDistributor.sendToServer(new ToggleArmorStandPacket(menu.getArmorStand().getId(), "stand", value));
                }}).build();

        showArmsCheckbox = CustomCheckbox.builder(Component.translatable("gui.chipsarmorstandmenu.show_arms"), minecraft.font)
            .pos(x + 180, y + 122)
            .maxWidth(60)
            .selected(menu.getShowArms())
            .onValueChange((checkbox, value) -> {
                if (menu.getArmorStand() != null) {
                    PacketDistributor.sendToServer(new ToggleArmorStandPacket(menu.getArmorStand().getId(), "arms", value));
                }}).build();

        showBaseCheckbox = CustomCheckbox.builder(Component.translatable("gui.chipsarmorstandmenu.show_base"), minecraft.font)
            .pos(x + 180, y + 134)
            .maxWidth(60)
            .selected(menu.getShowBase())
            .onValueChange((checkbox, value) -> {
                if (menu.getArmorStand() != null) {
                    PacketDistributor.sendToServer(new ToggleArmorStandPacket(menu.getArmorStand().getId(), "base", value));
                }}).build();

        addRenderableWidget(showStandCheckbox);
        addRenderableWidget(showArmsCheckbox);
        addRenderableWidget(showBaseCheckbox);
    }

    @SuppressWarnings("null")
    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }

    @SuppressWarnings("null")
    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, minecraft.player.getDisplayName(), this.playerLabelX, this.playerLabelY, 4210752, false);
        guiGraphics.drawString(this.font, this.title, this.armorLeftLabelX, this.armorLeftLabelY, 4210752, false);

        guiGraphics.drawString(this.font, Component.translatable("gui.chipsarmorstandmenu.player_hot_bar"), 8, 145, 4210752, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.chipsarmorstandmenu.stand_hot_bar"), 8, 173, 4210752, false);
    }

    @SuppressWarnings("null")
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        this.showArmsCheckbox.setSelected(this.menu.getShowArms());
        this.showBaseCheckbox.setSelected(this.menu.getShowBase());
        this.showStandCheckbox.setSelected(this.menu.getShowStand());
    }
}