package com.betterdamagecounter.display;

import com.betterdamagecounter.DamageCollectorService;
import com.betterdamagecounter.objects.DamagedNpc;
import net.runelite.api.Client;
import net.runelite.client.account.SessionManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

import javax.annotation.Nullable;
import javax.inject.Singleton;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

@Singleton
public class BetterDamageCounterPanel extends PluginPanel {

    private JPanel damageContainer;
    private List<DamagedNpcBox> boxes = new ArrayList<DamagedNpcBox>();

    private Client client;

    private EventBus eventBus;

    private SessionManager sessionManager;

    private ScheduledExecutorService executor;

    private ConfigManager configManager;

    private DamageCollectorService damageCollectorService;

    public BetterDamageCounterPanel(JPanel damageContainer, @Nullable Client client,
                                    EventBus eventBus, SessionManager sessionManager,
                                    ScheduledExecutorService executor, ConfigManager configManager,
                                    DamageCollectorService damageCollectorService) {
        this.damageContainer = damageContainer;
        this.client = client;
        this.eventBus = eventBus;
        this.sessionManager = sessionManager;
        this.executor = executor;
        this.configManager = configManager;
        this.damageCollectorService = damageCollectorService;
        this.init();
    }

    void init(){
        setBorder(new EmptyBorder(6, 6, 6, 6));
        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setLayout(new BorderLayout());
        damageContainer = buildDamageContainer();
    }

    private JPanel buildDamageContainer(){
        JPanel returnPanel = new JPanel();
        returnPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(5, 0, 0, 0, ColorScheme.DARK_GRAY_COLOR),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        returnPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        returnPanel.setLayout(new BorderLayout());
        returnPanel.setVisible(false);

        return returnPanel;
    }

    /**
     * Adds an entry for the NPC being damaged and figures out how to display the data
     * @param npc - the NPC that was damaged
     */
    public void addNpc(DamagedNpc npc){

    }

    public void buildDamagedNpcBox(DamagedNpc npc){
        for(DamagedNpcBox box : boxes){
            if(box.matchesNpcRecord(npc.getUniqueNpcId())){

            }
        }
    }

}
