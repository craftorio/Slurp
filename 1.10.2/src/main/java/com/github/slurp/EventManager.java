package com.github.slurp;

public class EventManager {
	@SubscribeEvent
    public void onPlayerInteract(RightClickEmpty event) {
 
        // Not doing this on client side. (Seems to be fired on server side
        // only by default already.)
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
                player.getPositionVector().add(player.getLookVec()));
        if (event.getWorld().isRemote) {
 
            if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
 
                CommonProxy.network.sendToServer(new RightClickMessage(result.getBlockPos(), result.sideHit,
                        (float) result.hitVec.xCoord - result.getBlockPos().getX(),
                        (float) result.hitVec.yCoord - result.getBlockPos().getY(),
                        (float) result.hitVec.zCoord - result.getBlockPos().getZ()));
 
            }
            return;
 
        } else {
            BlockPos blockpos = result.getBlockPos();
 
            IBlockState iblockstate = event.getWorld().getBlockState(blockpos);
            Material material = iblockstate.getMaterial();
            if (event.getEntityLiving() instanceof EntityPlayer) {
 
                ThirstHandler thirstHandler = (ThirstHandler) ThirstHelper.getThirstData(player);
 
                if (thirstHandler.isThirsty()) {
 
                    if (material == Material.WATER
                            && ((Integer) iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0) {
 
                        thirstHandler.addStats(10, 6.7F);
                        player.addPotionEffect(new PotionEffect(TANPotions.thirst, 600));
                        player.playSound(SoundEvents.BLOCK_WATER_AMBIENT, 10, 10);
                    }
                }
 
            }
 
        }
    }
}
}
