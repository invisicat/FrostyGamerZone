package dev.ricecx.frostygamerzone.common.redis;

import io.lettuce.core.LettuceFutures;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisStringAsyncCommands;
import io.lettuce.core.api.sync.RedisStreamCommands;
import io.lettuce.core.api.sync.RedisStringCommands;
import reactor.util.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Redis implements AutoCloseable {

    private long timeout = 1;
    private TimeUnit timeoutUnit = TimeUnit.MINUTES;
    private final RedisClient client;
    private final RedisPubSub pubSub;
    private final StatefulRedisConnection<String, String> connection;
    private final RedisStringAsyncCommands<String, String> async;
    private final RedisStringCommands<String, String> sync;

    public Redis(String uri) {
        this.client = RedisClient.create(uri);
        this.connection = client.connect();
        this.pubSub = new RedisPubSub(client);
        this.async = connection.async();
        this.sync = connection.sync();
    }

    /**
     * Get a value asynchronously
     * @param key Redis Key Value
     * @return Future of specified KV
     */
    public CompletableFuture<String> getAsync(@NonNull String key) {
        return async.get(key).toCompletableFuture();
    }

    public CompletableFuture<String> setAsync(@NonNull String key, @NonNull String value) {
        return async.set(key, value).toCompletableFuture();
    }

    public boolean getMultipleSync(String ...keys) {
        List<RedisFuture<String>> futures = new ArrayList<>();

        for (String key : keys) {
            futures.add(async.get(key));
        }

        return LettuceFutures.awaitAll(timeout, timeoutUnit, futures.toArray(new RedisFuture[0]));
    }

    /**
     * Set multiple key values synchronously
     * @param values Map of key values to set
     * @return boolean of success or failure
     */
    public boolean setMultipleSync(Map<String, String> values) {
        List<RedisFuture<String>> futures = new ArrayList<>();

        for (String key : values.keySet()) {
            futures.add(async.set(key, values.get(key)));
        }
        return LettuceFutures.awaitAll(timeout, timeoutUnit, futures.toArray(new RedisFuture[0]));
    }

    public CompletableFuture<Long> publish(String channel, String value) {
        return getPubSub().publish(channel, value);
    }

    @Override
    public void close() {
        getConnection().close();
    }

    /* Setters and Getters */

    public RedisClient getClient() {
        return client;
    }

    public RedisStringAsyncCommands<String, String> getAsync() {
        return async;
    }

    public RedisStringCommands<String, String> getSync() {
        return sync;
    }

    public long getTimeout() {
        return timeout;
    }

    public TimeUnit getTimeoutUnit() {
        return timeoutUnit;
    }

    public RedisPubSub getPubSub() {
        return pubSub;
    }

    public StatefulRedisConnection<String, String> getConnection() {
        return connection;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public void setTimeoutUnit(TimeUnit timeoutUnit) {
        this.timeoutUnit = timeoutUnit;
    }
}
