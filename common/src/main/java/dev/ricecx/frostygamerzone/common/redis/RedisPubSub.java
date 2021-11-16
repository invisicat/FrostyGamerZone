package dev.ricecx.frostygamerzone.common.redis;
import io.lettuce.core.LettuceFutures;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


public class RedisPubSub {

    private final RedisClient client;
    private final StatefulRedisPubSubConnection<String, String> pubSubConnection;
    private final RedisPubSubAsyncCommands<String, String> pubsubAsync;

    public RedisPubSub(RedisClient client) {
        this.client = client;
        this.pubSubConnection = client.connectPubSub();
        this.pubsubAsync = pubSubConnection.async();
    }

    public void registerListeners(RedisListener ...listeners) {
        List<RedisFuture<Void>> futures = new ArrayList<>();
        for (RedisListener listener : listeners) {
            pubSubConnection.addListener(listener);
            futures.add(pubsubAsync.subscribe(listener.getChannels()));
        }
        LettuceFutures.awaitAll(1, TimeUnit.MINUTES, futures.toArray(new RedisFuture[0]));
    }

    public CompletableFuture<Long> publish(String channel, String value) {
        return pubsubAsync.publish(channel, value).toCompletableFuture();
    }

    /* Getters and Setters */
    public RedisClient getClient() {
        return client;
    }

    public StatefulRedisPubSubConnection<String, String> getPubSubConnection() {
        return pubSubConnection;
    }

    public RedisPubSubAsyncCommands<String, String> getPubsubAsync() {
        return pubsubAsync;
    }
}
