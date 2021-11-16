package dev.ricecx.frostygamerzone.common.redis;

import io.lettuce.core.pubsub.RedisPubSubListener;

public abstract class RedisListener implements RedisPubSubListener<String, String> {

    private final String name;
    private final String[] channels;

    public RedisListener(String name, String ...channels) {
        this.name = name;
        this.channels = channels;
    }

    protected abstract void onMessage(String channel, String message);

    /* Override methods */

    @Override
    public void message(String channel, String message) {
        onMessage(channel, message);
    }

    @Override
    public void message(String s, String k1, String s2) {

    }

    @Override
    public void subscribed(String s, long l) {

    }

    @Override
    public void psubscribed(String s, long l) {

    }

    @Override
    public void unsubscribed(String s, long l) {

    }

    @Override
    public void punsubscribed(String s, long l) {

    }


    /* Setters and Getters */

    public String getName() {
        return name;
    }

    public String[] getChannels() {
        return channels;
    }

}