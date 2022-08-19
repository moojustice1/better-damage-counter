package com.betterdamagecounter.display;

import com.betterdamagecounter.DamageCollectorService;
import net.runelite.api.Client;
import net.runelite.client.account.SessionManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.ui.PluginPanel;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.concurrent.ScheduledExecutorService;

public class BetterDamageCounterPanel extends PluginPanel {

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


}
