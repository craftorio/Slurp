package com.github.slurp;

import com.github.slurp.network.RightClickMessage;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.common.FMLLog;
import toughasnails.api.thirst.ThirstHelper;
import toughasnails.thirst.ThirstHandler;

public class EventManager {
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onPlayerInteract(RightClickBlock event) {

		if (event.getEntityPlayer() == null) {
			return;
		}

		if (event.getEntityPlayer().isCreative()) {
			return;
		} // Not interfering with creative mode
		if (event.getHand() != EnumHand.MAIN_HAND) {
			return;
		} // Only for the main hand

		if (null != event.getEntityPlayer().getHeldItem(event.getHand())) {
			return;
		}

		if (event.getEntityPlayer().isSneaking()) {
			return;
		} // Not interfering like this.
		EntityPlayer player = (EntityPlayer) event.getEntityLiving();
		RayTraceResult result = event.getWorld().rayTraceBlocks(player.getPositionEyes(1),
				player.getPositionEyes(0).addVector(player.getLook(1).xCoord * 4, player.getLook(1).yCoord  * 4, player.getLook(1).zCoord * 4 ), true);
		
		if (event.getWorld().isRemote) {
			// Not doing this on client side. (Seems to be fired on server side
			// only by default already.)
			if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
				IBlockState iblockstate = event.getWorld().getBlockState(result.getBlockPos());
				Material material = iblockstate.getMaterial();
				if (material == Material.WATER) {
					ThirstHandler thirstHandler = (ThirstHandler) ThirstHelper.getThirstData(player);
					if (thirstHandler.isThirsty()) {
						Slurp.network.sendToServer(new RightClickMessage(result.getBlockPos(), result.sideHit,
								(float) result.hitVec.xCoord - result.getBlockPos().getX(),
								(float) result.hitVec.yCoord - result.getBlockPos().getY(),
								(float) result.hitVec.zCoord - result.getBlockPos().getZ()));
						player.playSound(SoundEvents.ENTITY_GENERIC_DRINK, 1, 1);
					}
				}
			}
		}
	}
}
