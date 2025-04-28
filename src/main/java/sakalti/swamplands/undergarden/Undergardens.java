package sakalti.swamplands;

import com.mojang.logging.LogUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.server.MinecraftServer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.slf4j.Logger;
import sakalti.swamplands.client.UndergardensClient;
import sakalti.swamplands.data.*;
import sakalti.swamplands.event.UndergardensClientEvents;
import sakalti.swamplands.event.UndergardensCommonEvents;
import sakalti.swamplands.registry.*;

import java.util.concurrent.CompletableFuture;

@Mod(Undergardens.MODID)
public class Undergardens {

	public static final String MODID = "swamplands";
	public static final Logger LOGGER = LogUtils.getLogger();

	public Undergardens(IEventBus bus, Dist dist, ModContainer container) {

		if (dist.isClient()) {
			UndergardensClientEvents.initClientEvents(bus);
			container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
		}

		UndergardensCommonEvents.initCommonEvents(bus);
		bus.addListener(this::gatherData);

		DeferredRegister<?>[] registers = {
			UGBlockEntities.BLOCK_ENTITIES,
			UGBlocks.BLOCKS,
			UGCarvers.CARVERS,
			UGCreativeModeTabs.TABS,
			UGCriteria.CRITERIA,
			UGEffects.EFFECTS,
			UGEntityTypes.ENTITIES,
			UGFeatures.FEATURES,
			UGFluids.FLUIDS,
			UGFluids.TYPES,
			UGFoliagePlacers.FOLIAGE_PLACERS,
			UGItems.ITEMS,
			UGParticleTypes.PARTICLES,
			UGPointOfInterests.POI,
			UGPotions.POTIONS,
			UGSoundEvents.SOUNDS,
			UGStructures.STRUCTURES,
			UGTreeDecoratorTypes.TREE_DECORATORS,
			UGTrunkPlacerTypes.TRUNK_PLACERS,
			UGArmorMaterials.ARMOR_MATERIALS
		};

		for (DeferredRegister<?> register : registers) {
			register.register(bus);
		}

		container.registerConfig(ModConfig.Type.COMMON, UndergardensConfig.COMMON_SPEC);
		container.registerConfig(ModConfig.Type.CLIENT, UndergardensConfig.CLIENT_SPEC);
	}

	public void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();
		ExistingFileHelper helper = event.getExistingFileHelper();

		generator.addProvider(event.includeClient(), new UGBlockStates(output, helper));
		generator.addProvider(event.includeClient(), new UGItemModels(output, helper));
		generator.addProvider(event.includeClient(), new UGLang(output));
		generator.addProvider(event.includeClient(), new UGSoundDefinitions(output, helper));

		DatapackBuiltinEntriesProvider datapackProvider = new UGRegistries(output, event.getLookupProvider());
		CompletableFuture<HolderLookup.Provider> lookupProvider = datapackProvider.getRegistryProvider();
		generator.addProvider(event.includeServer(), datapackProvider);
		generator.addProvider(event.includeServer(), new UGRecipes(output, lookupProvider));
		generator.addProvider(event.includeServer(), new UGLootTables(output, lookupProvider));
		UGBlockTags blockTags = new UGBlockTags(output, lookupProvider, helper);
		generator.addProvider(event.includeServer(), blockTags);
		generator.addProvider(event.includeServer(), new UGItemTags(output, lookupProvider, blockTags.contentsGetter(), helper));
		generator.addProvider(event.includeServer(), new UGEntityTags(output, lookupProvider, helper));
		generator.addProvider(event.includeServer(), new UGAdvancements(output, datapackProvider.getRegistryProvider(), helper));
		generator.addProvider(event.includeServer(), new UGFluidTags(output, lookupProvider, helper));
		generator.addProvider(event.includeServer(), new UGBiomeTags(output, lookupProvider, helper));
		generator.addProvider(event.includeServer(), new UGDamageTypeTags(output, lookupProvider, helper));
		generator.addProvider(event.includeServer(), new UGStructureUpdater("structures", output, helper));
		generator.addProvider(event.includeServer(), new UGDataMaps(output, lookupProvider));
		generator.addProvider(event.includeClient(), new UGEnchantmentTags(output, lookupProvider, helper));
	}

	public static RegistryAccess registryAccessStatic() {
		final MinecraftServer currentServer = ServerLifecycleHooks.getCurrentServer();
		if(currentServer != null)
			return currentServer.registryAccess();
		else
			return UndergardensClient.registryAccess();
	}
}
