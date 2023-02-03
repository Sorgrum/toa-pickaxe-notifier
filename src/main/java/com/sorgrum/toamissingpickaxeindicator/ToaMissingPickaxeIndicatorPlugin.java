package com.sorgrum.toamissingpickaxeindicator;

import com.google.inject.Provides;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(name = "ToA Missing Pickaxe Indicator")
public class ToaMissingPickaxeIndicatorPlugin extends Plugin {

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
