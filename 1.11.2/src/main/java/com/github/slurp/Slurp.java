package com.github.slurp;

import com.github.slurp.network.MessageHandler;
import com.github.slurp.network.RightClickMessage;
import com.github.slurp.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Globals.ID, name = Globals.NAME, version = Globals.VER, dependencies = Globals.DEPENDENCIES)
public class Slurp {


    @Instance(value = Globals.ID)
    public static Slurp instance;
    public static SimpleNetworkWrapper network;

    @SidedProxy(clientSide = Globals.CLIENT_PROXY, serverSide = Globals.SERVER_PROXY)
    public static CommonProxy proxy;


    @EventHandler
    private void preInit(FMLPreInitializationEvent preEvent) {
        network = NetworkRegistry.INSTANCE.newSimpleChannel("Slurp");
        network.registerMessage(MessageHandler.class, RightClickMessage.class, 0, Side.SERVER);
        proxy.preInit(preEvent);
    }

    @EventHandler
    private void init(FMLInitializationEvent event) {
        proxy.init(event);
    }


}
