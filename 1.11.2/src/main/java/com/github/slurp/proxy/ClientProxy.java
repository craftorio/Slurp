package com.github.slurp.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent preEvent) {
        super.preInit(preEvent);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

    }

    @Override
    public void registerRenders(FMLInitializationEvent event) {

    }

    @Override
    public void registerEntities(FMLPreInitializationEvent preEvent) {
        super.registerEntities(preEvent);

    }
}
