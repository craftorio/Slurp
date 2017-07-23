package com.github.slurp;

import com.github.slurp.network.RightClickMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventManager {
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPlayerInteract(RightClickEmpty event) {

        if (event.getEntityPlayer() == null) {
            return;
        }

        if (event.getEntityPlayer().capabilities.isCreativeMode) {
            return;
        } // Not interfering with creative mode
        if (event.getHand() != EnumHand.MAIN_HAND) {
            return;
        } // Only for the main hand

    	if (event.getEntityPlayer().isSneaking()) {
			return;
		} // Not interfering like this.
		EntityPlayer player = (EntityPlayer) event.getEntityLiving();
		RayTraceResult result = event.getWorld().rayTraceBlocks(player.getPositionEyes(1),
				player.getPositionEyes(0).addVector(player.getLook(1).x * 4, player.getLook(1).y  * 4, player.getLook(1).z * 4 ), true);
		
		if (event.getWorld().isRemote) {
			// Not doing this on client side. (Seems to be fired on server side
			// only by default already.)
			if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {

                Slurp.network.sendToServer(new RightClickMessage(result.getBlockPos(), result.sideHit,
                        (float) result.hitVec.x - result.getBlockPos().getX(),
                        (float) result.hitVec.y - result.getBlockPos().getY(),
                        (float) result.hitVec.z - result.getBlockPos().getZ()));

            }
        }
    }
}
