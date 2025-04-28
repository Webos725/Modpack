package sakalti.swamplands.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.entity.player.Player;
import sakalti.swamplands.registry.UGSoundEvents;

public class UndergardensClient {

	public static void playPortalSound() {
		Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forLocalAmbience(UGSoundEvents.UNDERGARDEN_PORTAL_TRAVEL.get(), 1.0F, 1.0F));
	}

	public static RegistryAccess registryAccess() {
		return Minecraft.getInstance().level.registryAccess();
	}
}
