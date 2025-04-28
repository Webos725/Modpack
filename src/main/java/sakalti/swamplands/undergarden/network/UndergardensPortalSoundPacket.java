package sakalti.swamplands.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import sakalti.swamplands.Undergardens;
import sakalti.swamplands.client.UndergardensClient;

public class UndergardensPortalSoundPacket implements CustomPacketPayload {

	public static final UndergardensPortalSoundPacket INSTANCE = new UndergardensPortalSoundPacket();
	public static final Type<UndergardensPortalSoundPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Undergardens.MODID, "portal_sound"));
	public static final StreamCodec<RegistryFriendlyByteBuf, UndergardensPortalSoundPacket> STREAM_CODEC = StreamCodec.unit(INSTANCE);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	public static void handle(IPayloadContext context) {
		context.enqueueWork(UndergardensClient::playPortalSound);
	}
}
