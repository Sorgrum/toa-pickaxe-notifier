package com.sorgrum.toamissingpickaxeindicator;

import com.google.inject.Provides;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.ItemID;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.VarbitChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;

@Slf4j
@PluginDescriptor(name = "ToA Missing Pickaxe Indicator")
public class ToaMissingPickaxeIndicator extends Plugin {

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private MissingPickaxeOverlay pickaxeOverlay;

    @Override
    protected void startUp() throws Exception {
        overlayManager.add(pickaxeOverlay);
    }

    @Override
    protected void shutDown() throws Exception {
        overlayManager.remove(pickaxeOverlay);
    }

    @Provides
    ToaMissingPickaxeIndicatorConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(ToaMissingPickaxeIndicatorConfig.class);
    }
}
