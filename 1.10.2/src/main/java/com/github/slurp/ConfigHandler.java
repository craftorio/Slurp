package com.github.slurp;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler {
	  public static Configuration CONFIG;
	    private static String DEF_CAT = "Options";

	    @SubscribeEvent
	    public void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent e) {
	        if (e.getModID().equals(Globals.ID)) {
	            init();
	        }
	    }

	    public static void init() {
	        if (CONFIG == null) {
	            CONFIG = new Configuration(new File(Globals.CONFIG_FILE));
	            MinecraftForge.EVENT_BUS.register(new ConfigHandler());
	        }

	        Globals.DRINK_AMOUNT = CONFIG.getInt("DrinkAmount", DEF_CAT, 1, 1, 20, "Increase or decrease amount of water you can drink at once");
	        Globals.DRINK_HYDRATIONS = CONFIG.getFloat("DrinkHydrations", DEF_CAT, 0.2f, 0.0f, 20f, "Increase or decrease amount of hydration each drink give");
	        Globals.THIRST_EFFECT_DURATION = CONFIG.getInt("ThirstEffectDuration", DEF_CAT, 600, 0, 6400, "Increase or decrease amount of time the Thirst effect lasts");
	        Globals.THIRST_EFFECT_POTENCY = CONFIG.getInt("ThirstEffectPotency", DEF_CAT, 5, 1, 10, "Increase or decrease potency of the Thirst effect");
		    Globals.SHOULD_TAKE_BLOCK = CONFIG.getBoolean("ShouldTakeSourceBlock", DEF_CAT, false, "If set to true the water block will be taken");
	        if (CONFIG.hasChanged()) {
	            CONFIG.save();
	        }
	    }
}
