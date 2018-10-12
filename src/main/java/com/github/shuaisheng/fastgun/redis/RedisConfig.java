package com.github.shuaisheng.fastgun.redis;

public class RedisConfig {

    public String host;
    public int port;
    public String password;
    public int maxIdle = 10;
    public boolean testOnBorrow = false;
    public int maxTotal = 200;
    public long maxWaitMillis = 100 * 1000L;
}
