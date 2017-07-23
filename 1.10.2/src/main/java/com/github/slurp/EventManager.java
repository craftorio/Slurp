package com.github.slurp;

import com.github.slurp.network.RightClickMessage;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import toughasnails.api.TANPotions;
import toughasnails.api.thirst.ThirstHelper;
import toughasnails.thirst.ThirstHandler;

public class EventManager {
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
				player.getPositionVector().add(player.getLookVec()), true);
		if (event.getWorld().isRemote) {
			// Not doing this on client side. (Seems to be fired on server side
			// only by default already.)
			if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {

				Slurp.network.sendToServer(new RightClickMessage(result.getBlockPos(), result.sideHit,
						(float) result.hitVec.xCoord - result.getBlockPos().getX(),
						(float) result.hitVec.yCoord - result.getBlockPos().getY(),
						(float) result.hitVec.zCoord - result.getBlockPos().getZ()));

			}
			return;

		}
		BlockPos blockpos = result.getBlockPos();
		IBlockState iblockstate = event.getWorld().getBlockState(blockpos);
		Material material = iblockstate.getMaterial();
		if (event.getEntityLiving() instanceof EntityPlayer) {

			ThirstHandler thirstHandler = (ThirstHandler) ThirstHelper.getThirstData(player);

			if (thirstHandler.isThirsty()) {

				if (material == Material.WATER && ((Integer) iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0) {

					thirstHandler.addStats(10, 6.7F);
					player.addPotionEffect(new PotionEffect(TANPotions.thirst, 600));
					player.playSound(SoundEvents.BLOCK_WATER_AMBIENT, 10, 10);
				}
			}

		}

	}
}
