package com.betterdamagecounter;

import com.betterdamagecounter.display.BetterDamageCounterPanel;
import com.betterdamagecounter.objects.DamagedNpc;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.task.Schedule;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.swing.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * The collector will handle all the damage done calculations and collections in it's own terms, then serve it
 * out to the different components that need it. It will also do all the JSON writing for saving the data to
 * a local folder
 */
@Slf4j
public class DamageCollectorService extends Plugin { //TODO: probably shouldn't be a plugin?

    @Inject
    private EventBus eventBus;

    @Inject
    private BetterDamageCounterClient client;

    private List<DamagedNpc> damagedNpcList = new ArrayList<>();
    private List<Integer> currentAttackedIds = new ArrayList<>();

    public void addDamagedNpc(DamagedNpc damagedNpc, BetterDamageCounterPanel panel){
        synchronized (damagedNpcList){
            if(currentAttackedIds.contains(damagedNpc.getUniqueNpcId())) {
                for(DamagedNpc npc : damagedNpcList){
                    if(npc.getUniqueNpcId().equals(damagedNpc.getUniqueNpcId())){
                        npc.addDamage(damagedNpc.getDamageDone());
                    }
                }
            } else {
                currentAttackedIds.add(damagedNpc.getUniqueNpcId());
                damagedNpcList.add(damagedNpc);
            }
            SwingUtilities.invokeLater(() -> panel.addNpc(damagedNpc));
        }
        eventBus.post(damagedNpc);
    }

    public Boolean isInAttackedList(Integer id){
        return currentAttackedIds.contains(id);
    }

    public void removeUniqueId(Integer id){ //TODO: Need to make some tests for this one to ensure the id != position in list
        currentAttackedIds.remove(id);
        //TODO: should also do the writes when an NPC dies
    }

    @Schedule(
            period = 5,
            unit = ChronoUnit.MINUTES,
            asynchronous = true
    )
    public void submitDamage()
    {
        submitDamageToClient();
    }

    @Nullable
    private CompletableFuture<Void> submitDamageToClient(){
        List<DamagedNpc> copy;
        synchronized (damagedNpcList){
            if(damagedNpcList.isEmpty()){
                return null;
            }
            copy = new ArrayList<>(damagedNpcList);
            damagedNpcList.clear();
        }
        log.debug("Returning from the submit damage");
        return client.submit(copy);
    }
}
