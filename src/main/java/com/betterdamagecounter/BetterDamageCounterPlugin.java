package com.betterdamagecounter;

import com.betterdamagecounter.display.BetterDamageCounterPanel;
import com.betterdamagecounter.objects.DamagedNpc;
import com.google.inject.Provides;

import javax.annotation.Nullable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.HitsplatApplied;
import net.runelite.api.events.NpcDespawned;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.task.Schedule;

import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@PluginDescriptor(
	name = "Better Damage Counter"
)
public class BetterDamageCounterPlugin extends Plugin
{

	@Inject
	private Client client;

	@Inject
	private BetterDamageCounterConfig config;

	@Inject
	private DamageCollectorService damageCollectorService;

	private BetterDamageCounterPanel betterDamageCounterPanel;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Better Damage Counter started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Better Damage Counter stopped!");
	}

	@Subscribe
	public void onHitsplatApplied(HitsplatApplied hitsplatApplied){
		Player curPlayer = client.getLocalPlayer();
		Actor actor = hitsplatApplied.getActor();
		Hitsplat hitsplat = hitsplatApplied.getHitsplat();

		if(hitsplat.isMine()){ //TODO do we need special logic for Vengeance or recoils?
			int damage = hitsplat.getAmount();
			NPC npc = ((NPC) actor);
			//TODO: should all this logic be placed into the service?
			damageCollectorService.addDamagedNpc(new DamagedNpc(npc.getName(), damage, npc.getId(), 0));
			String chatMessage = String.format("You did damage! You dealt %d to %s! way to go!", damage, npc.getName());
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", chatMessage, null);
		}
	}

	@Subscribe
	public void onNpcDespawned(NpcDespawned npcDespawned) { //TODO: what should the behaviour be if we tele out?
		NPC npc = npcDespawned.getNpc();

		if(npc.isDead() && damageCollectorService.isInAttackedList(npc.getId())){
			//TODO multi phase bosses may be registered as dead when they move, like Zulrah. Also may need some Olm logics
			String chatMessage = String.format("You KILLED %s BROTHER!!! You did a total of %d damage", npc.getName());
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", chatMessage, null);

			damageCollectorService.removeUniqueId(npc.getId());
		}

	}

	@Provides
	BetterDamageCounterConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BetterDamageCounterConfig.class);
	}


}
