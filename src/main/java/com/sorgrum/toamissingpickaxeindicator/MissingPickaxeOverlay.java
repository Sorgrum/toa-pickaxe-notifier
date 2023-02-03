package com.sorgrum.toamissingpickaxeindicator;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.*;
import net.runelite.client.ui.overlay.components.ComponentOrientation;

import javax.inject.Inject;
import java.awt.*;
import java.util.Arrays;

@Slf4j
class MissingPickaxeOverlay extends OverlayPanel {

    private final Client client;

    private final ToaMissingPickaxeIndicatorConfig config;

    @Inject
    private ItemManager itemManager;


    @Inject
    private MissingPickaxeOverlay(ToaMissingPickaxeIndicatorPlugin plugin, ToaMissingPickaxeIndicatorConfig config, Client client) {
        super(plugin);
        this.client = client;
        this.config = config;
        setPosition(OverlayPosition.TOP_CENTER);
        setPriority(OverlayPriority.HIGHEST);
    }

    public boolean inParty() {
        return client.getVarbitValue(Varbits.TOA_PARTY) == 1;
    }

    public boolean hasStoredPickaxe() {
        return client.getVarbitValue(Varbits.TOA_STORED_PICKAXE) != 0;
    }

    public boolean inLobby() {
        return Arrays.stream(client.getMapRegions()).anyMatch(region -> region == Varbits.TOA_LOBBY);
    }

    public boolean inBankArea() {
        return Arrays.stream(client.getMapRegions()).anyMatch(region -> region == Varbits.TOA_BANK_AREA);
    }

    public boolean hasPickaxeInInventory() {
        final ItemContainer container = client.getItemContainer(InventoryID.INVENTORY);

        if (container == null) {
            return false;
        }

        return Arrays.stream(container.getItems())
                .anyMatch(item -> itemManager.getItemComposition(item.getId()).getName().toLowerCase()
                        .contains("pickaxe"));
    }

    public boolean hasPickaxeEquipped() {
        final ItemContainer container = client.getItemContainer(InventoryID.EQUIPMENT);

        if (container == null) {
            return false;
        }

        return Arrays.stream(container.getItems())
                .anyMatch(item -> itemManager.getItemComposition(item.getId()).getName().toLowerCase()
                        .contains("pickaxe"));
    }

    @Override
    public Dimension render(Graphics2D graphics) {

        if (config.notifyInParty() && !inParty()) {
            return null;
        }

        if (hasStoredPickaxe()) {
            return null;
        }

        if (config.notifyInTombs() && (!inLobby() && !inBankArea())) {
            return null;
        }

        if (hasPickaxeInInventory() || hasPickaxeEquipped()) {
            return null;
        }

        panelComponent.setBackgroundColor(new Color(140, 10, 10));
        panelComponent.setPreferredSize(new Dimension(300, 0));
        panelComponent.getChildren().add(SplitComponent.builder().first(
                        new ImageComponent(itemManager.getImage(ItemID.DRAGON_PICKAXE))
                ).second(SplitComponent.builder().first(LineComponent.builder().build()).second(SplitComponent.builder()
                        .first(TitleComponent.builder().text("No pickaxe stored in Tombs of Amascut").build())
                        .second(LineComponent.builder().build()).build()).build()).orientation(ComponentOrientation.HORIZONTAL)
                .build());

        return super.render(graphics);
    }
}
