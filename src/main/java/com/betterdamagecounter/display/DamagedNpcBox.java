package com.betterdamagecounter.display;

import com.betterdamagecounter.objects.DamagedNpc;
import net.runelite.client.game.NPCManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;


/**
 * The container that holds all the damage records
 */
public class DamagedNpcBox extends JPanel {

    private List<DamagedNpc> damages;

    private NPCManager npcManager;
    private Integer npcUniqueId;



    public DamagedNpcBox(){
        setLayout(new BorderLayout(0, 1));
        setBorder(new EmptyBorder(5, 0, 0, 0));
    }

    public void addDamage(DamagedNpc npc){

    }

    public Boolean matchesNpcRecord(Integer id){
        return id.equals(npcUniqueId);
    }



}
