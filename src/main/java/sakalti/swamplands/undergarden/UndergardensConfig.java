package sakalti.swamplands;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class UndergardensConfig {

	public static class Common {
		public static ModConfigSpec.ConfigValue<String> return_portal_frame_block_id;

		public Common(ModConfigSpec.Builder builder) {
			return_portal_frame_block_id = builder
				.comment("""
					Determines what block the game will generate Undergardens return portals out of
					Use the block tag swamplands:portal_frame_blocks to determine what blocks portals can be built with
					If value entered here is not a valid block it will default to generating minecraft:stone_bricks""")
				.translation("config.swamplands.return_portal_frame_block_id")
				.define("Return Portal Frame Block ID", BuiltInRegistries.BLOCK.getKey(Blocks.STONE_BRICKS).toString());
			builder.build();
		}
	}

	public static class Client {

		public static ModConfigSpec.ConfigValue<Boolean> toggle_swamplands_fog;

		public Client(ModConfigSpec.Builder builder) {
			toggle_swamplands_fog = builder
				.comment("""
					Toggles the Undergardens's special fog parameters. Set  false to disable it, and return to the vanilla fog.""")
				.translation("config.swamplands.toggle_swamplands_fog")
				.define("Toggle Undergardens Fog", true);
			builder.build();
		}
	}

	static final ModConfigSpec COMMON_SPEC;
	public static final Common COMMON;

	static final ModConfigSpec CLIENT_SPEC;
	public static final Client CLIENT;

	static {
		final Pair<UndergardensConfig.Common, ModConfigSpec> common = new ModConfigSpec.Builder().configure(UndergardensConfig.Common::new);
		COMMON_SPEC = common.getRight();
		COMMON = common.getLeft();
		final Pair<UndergardensConfig.Client, ModConfigSpec> client = new ModConfigSpec.Builder().configure(UndergardensConfig.Client::new);
		CLIENT_SPEC = client.getRight();
		CLIENT = client.getLeft();
	}
}
