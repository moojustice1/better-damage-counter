package com.betterdamagecounter.display;

import com.betterdamagecounter.DamageCollectorService;
import net.runelite.api.Client;
import net.runelite.client.account.SessionManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.ui.overlay.OverlayPanel;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.awt.*;
import java.util.concurrent.ScheduledExecutorService;

public class BetterDamageCounterOverlay extends OverlayPanel {

    @Inject
    @Nullable
    private Client client;

    @Inject
    private EventBus eventBus;

    @Inject
    private SessionManager sessionManager;

    @Inject
    private ScheduledExecutorService executor;

    @Inject
    private ConfigManager configManager;

    @Inject
    private DamageCollectorService damageCollectorService;

    @Override
    public Dimension render(Graphics2D graphics)
    {

        super.render(graphics);
    }
}
