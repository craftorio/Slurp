package com.github.slurp.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * @author CJm721 Code for Overloaded Multitool
 *         None of this code belongs to me!
 *         https://minecraft.curseforge.com/projects/overloaded?gameCategorySlug=mc-mods&projectID=264601
 */
public class RightClickMessage implements IMessage {

    private EnumFacing hitSide;
    private BlockPos pos;
    private float hitX;
    private float hitY;
    private float hitZ;

    public RightClickMessage() {
    }

    public RightClickMessage(BlockPos pos, EnumFacing hitSide,float hitX, float hitY, float hitZ) {
        this.pos = pos;
        this.hitSide = hitSide;
        this.hitX = hitX;
        this.hitY = hitY;
        this.hitZ = hitZ;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        int facing = buf.readInt();

        this.pos = new BlockPos(x, y, z);
        this.hitSide = EnumFacing.getFront(facing);

        hitX = buf.readFloat();
        hitY = buf.readFloat();
        hitZ = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeInt(hitSide.getIndex());

        buf.writeFloat(hitX);
        buf.writeFloat(hitY);
        buf.writeFloat(hitZ);
    }

    public BlockPos getPos() {
        return pos;
    }

    public EnumFacing getHitSide() {
        return hitSide;
    }

    public float getHitX() {
        return hitX;
    }

    public float getHitY() {
        return hitY;
    }

    public float getHitZ() {
        return hitZ;
    }
}