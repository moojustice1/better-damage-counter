package com.betterdamagecounter;

import com.betterdamagecounter.objects.DamagedNpc;
import com.google.inject.Binder;
import com.google.inject.Module;
import net.runelite.client.plugins.Plugin;

import java.util.Map;

/**
 * The collector will handle all the damage done calculations and collections in it's own terms, then serve it
 * out to the different components that need it. It will also do all the JSON writing for saving the data to
 * a local folder
 */
public class DamageCollectorService extends Plugin { //TODO: probably shouldn't be a plugin?

    private Map<Integer, DamagedNpc> damagedNpcMap; // k-id, v-DamagedNpc

    public void addDamagedNpc(DamagedNpc damagedNpc){
        damagedNpcMap.put(damagedNpc.getUniqueNpcId(), damagedNpc);
    }

    public DamagedNpc getDamagedNpc(Integer npcId){
        return damagedNpcMap.get(npcId);
    }

    public boolean isDamagedNpcInMap(Integer npcId){
        return damagedNpcMap.containsKey(npcId);
    }

    public void addDamage(Integer npcId, Integer damage){
        damagedNpcMap.get(npcId).addDamage(damage);
    }

    public void removeDamagedNpc(Integer npcId){
        damagedNpcMap.remove(npcId);
    }
}
