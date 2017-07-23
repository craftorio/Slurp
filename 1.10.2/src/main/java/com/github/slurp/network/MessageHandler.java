package com.github.slurp.network;

public class MessageHandler implements IMessageHandler<RightClickMessage, IMessage> {

    /**
     * Called when a message is received of the appropriate type. You can
     * optionally return a reply message, or null if no reply is needed.
     *
     * @param message
     *            The message
     * @param ctx
     *            the context of the message
     * @return an optional return message
     */
    @Override
    @Nullable
    public IMessage onMessage(RightClickMessage message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().playerEntity;
        WorldServer world = player.getServerWorld();
        return message;

    }