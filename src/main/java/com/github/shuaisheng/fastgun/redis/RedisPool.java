package com.github.shuaisheng.fastgun.redis;

import redis.clients.jedis.*;
import redis.clients.jedis.params.geo.GeoRadiusParam;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisPool {
    public JedisPool pool;
    private String password;

    public RedisPool(String host, Integer port, String password) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(10);
        config.setTestOnBorrow(false);
        config.setMaxTotal(200);
        config.setMaxWaitMillis(100 * 1000L);
        this.pool = new JedisPool(config, host, port);
        this.password = password;
    }

    public Jedis getJedis() {
        Jedis jedis = this.pool.getResource();
        if (null != this.password && 0 != this.password.length()) {
            jedis.auth(this.password);
        }
        return jedis;
    }

    public void closeJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    public Set<String> keys(String keyPtn) {
        Jedis jedis = this.getJedis();
        Set<String> set = null;
        try {
            set = jedis.keys(keyPtn);
        } catch (Exception e) {
            if (null != jedis) {
              //  this.closeJedis(jedis);
            }
        } finally {
            if (null != jedis) {
                this.closeJedis(jedis);
            }
        }
        return set;
    }

    /**
     * key 赋值
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        Jedis jedis = this.getJedis();
        try {
            jedis.set(key, value);
        } catch (Exception e) {
            if (null != jedis) {
             //  this.closeJedis(jedis);
            }
        } finally {
            if (null != jedis) {
                this.closeJedis(jedis);
            }
        }
    }

    /**
     * key 赋值并设置生命周期
     * @param key
     * @param value
     * @param second
     */
    public void setEx(String key, String value, int second) {
        Jedis jedis = this.getJedis();
        try {
            jedis.setex(key, second, value);
        } catch (Exception e) {
            if (null != jedis) {
             //  this.closeJedis(jedis);
            }
        } finally {
            if (null != jedis) {
                this.closeJedis(jedis);
            }
        }
    }

    /**
     * key不存在的时候才进行保存(key若已经存在不错任何改变，返回0，若不存在，则创建一个key并赋值，返回1)
     * @param key
     * @param value
     */
    public Long setNx(String key, String value) {
        Jedis jedis = this.getJedis();
        try {
         return jedis.setnx(key, value);
        }
        finally {
            if (null != jedis) {
                this.closeJedis(jedis);
            }
        }
    }

    /**
     * 获取 key的值
     * @param key
     * @return
     */
    public String get(String key) {
        Jedis jedis = this.getJedis();
        try {
            return jedis.get(key);
        }
        finally {
            if (null != jedis) {
                this.closeJedis(jedis);
            }
        }
    }

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值
     * @param key
     * @param value
     * @return
     */
    public String getSet(String key,String value) {
        Jedis jedis = this.getJedis();
        try {
            return jedis.getSet(key,value);
        }
        finally {
            if (null != jedis) {
                this.closeJedis(jedis);
            }
        }
    }


    public String hget(String key, String type) {
        Jedis jedis = this.getJedis();
        String value = "";
        try {
            value = jedis.hget(key, type);
        } catch (Exception e) {
            if (null != jedis) {
              // this.closeJedis(jedis);
            }
        } finally {
            if (null != jedis) {
                this.closeJedis(jedis);
            }
        }
        return value;
    }

    public void hset(String key, String type, String value) {
        Jedis jedis = this.getJedis();
        try {
            jedis.hset(key, type, value);
        } catch (Exception e) {
            if (null != jedis) {
              // this.closeJedis(jedis);
            }
        } finally {
            if (null != jedis) {
                this.closeJedis(jedis);
            }
        }
    }

    /**
     * 查看 key是否有值
     * @param key
     * @return
     */
    public boolean exist(String key) {
        Jedis jedis = this.getJedis();
        try {
            return jedis.exists(key);
        }
        finally {
            if (null != jedis) {
                this.closeJedis(jedis);
            }
        }
    }

    public void publish(String key, String message) {
        Jedis jedis = this.getJedis();
        try {
            jedis.publish(key, message);
        } catch (Exception e) {
            if (null != jedis) {
               //this.closeJedis(jedis);
            }
        } finally {
            if (null != jedis) {
                this.closeJedis(jedis);
            }
        }
    }

    public void hmset(String key, Map<String, String> redisMap) {
        Jedis jedis = this.getJedis();
        try {
            jedis.hmset(key, redisMap);
        } catch (Exception e) {
            if (null != jedis) {
              // this.closeJedis(jedis);
            }
        } finally {
            if (null != jedis) {
                this.closeJedis(jedis);
            }
        }
    }

    public void rpush(String key, String... value) {
        Jedis jedis = this.getJedis();
        try {
            jedis.rpush(key, value);
        } catch (Exception e) {
            if (null != jedis) {
               //this.closeJedis(jedis);
            }
        } finally {
            if (null != jedis) {
                this.closeJedis(jedis);
            }
        }
    }

    public List<String> lrange(String key, int start, int end) {
        Jedis jedis = this.getJedis();
        try {
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            if (null != jedis) {
               //this.closeJedis(jedis);
            }
        } finally {
            if (null != jedis) {
                this.closeJedis(jedis);
            }
        }
        return null;
    }

    public String lset(String key, int index, String value) {
        Jedis jedis = this.getJedis();
        try {
            return jedis.lset(key, index, value);
        } catch (Exception e) {
            if (null != jedis) {
               //this.closeJedis(jedis);
            }
        } finally {
            if (null != jedis) {
                this.closeJedis(jedis);
            }
        }
        return null;
    }

    public String ltrim(String key, long start, long end) {
        Jedis jedis = this.getJedis();
        try {
            return jedis.ltrim(key, start, end);
        } catch (Exception e) {
            if (null != jedis) {
               //this.closeJedis(jedis);
            }
        } finally {
            if (null != jedis) {
                this.closeJedis(jedis);
            }
        }
        return null;
    }

    /**
     * 删除 key的值
     * @param key
     * @return
     */
    public Integer del(String key) {
        Jedis jedis = this.getJedis();
        try {
            return jedis.del(key).intValue();
        } catch (Exception e) {
            if (null != jedis) {
               //this.closeJedis(jedis);
            }
        } finally {
            if (null != jedis) {
                this.closeJedis(jedis);
            }
        }
        return null;
    }

    public Map<String, String> hgetAll(String redisKey) {
        Jedis jedis = this.getJedis();
        try {
            return jedis.hgetAll(redisKey);
        } catch (Exception e) {
            if (null != jedis) {
               //this.closeJedis(jedis);
            }
        } finally {
            if (null != jedis) {
                this.closeJedis(jedis);
            }
        }
        return null;
    }

    /**
     * key设置生命周期
     * @param key
     * @param second
     */
    public void expire(String key, int second) {
        Jedis jedis = this.getJedis();
        try {
            jedis.expire(key, second);
        }
        finally {
            if (null != jedis) {
                this.closeJedis(jedis);
            }
        }
    }

    /**
     * 自增加 1
     * @param key
     * @return
     */
    public Long incr(String key){
        Jedis jedis = this.getJedis();
        try {
            return jedis.incr(key);
        }
        finally {
            if (null != jedis) {
                this.closeJedis(jedis);
            }
        }
    }

    /**
     * 自增 指定count数
     * @param key
     * @param count
     * @return
     */
    public Long incrBy(String key,Long count){
        Jedis jedis =this.getJedis();
        try {
            return jedis.incrBy(key,count);
        }
        finally {
            if (null != jedis) {
                this.closeJedis(jedis);
            }
        }
    }

    /**
     * 自减 减 1
     * @param key
     * @return
     */
    public Long decr(String key){
        Jedis jedis = this.getJedis();
        try {
            return jedis.decr(key);
        }
        finally {
            if (null != jedis) {
                this.closeJedis(jedis);
            }
        }
    }

    /**
     * 自减 指定count数
     * @param key
     * @param count
     * @return
     */
    public Long decrBy(String key,Long count){
        Jedis jedis =this.getJedis();
        try {
            return jedis.decrBy(key,count);
        }
        finally {
            if (null != jedis) {
                this.closeJedis(jedis);
            }
        }
    }


    //----------------------geo start------------------------------------------

    /**
     * 增加 地理位置坐标
     * @param key
     * @param coordinate
     * @param member
     * @return
     */
    public  Long geoadd(String key, GeoCoordinate coordinate, String member) {
        Jedis jedis = this.getJedis();
        try {

            return jedis.geoadd(key, coordinate.getLongitude(), coordinate.getLatitude(), member);
        } finally {
            this.closeJedis(jedis);
        }
    }

    /**
     * 批量添加地理位置坐标
     * @param key
     * @param memberCoordinateMap
     * @return
     */
    public  Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
        Jedis jedis = this.getJedis();
        try {

            return jedis.geoadd(key, memberCoordinateMap);
        } finally {
            this.closeJedis(jedis);
        }
    }

    /**
     * 根据给定地理位置坐标获取指定范围内的地理位置集合（返回匹配位置的经纬度 + 匹配位置与给定地理位置的距离 + 从近到远排序，）
     *
     * @param key
     * @param coordinate
     * @param radius
     * @return List<GeoRadiusResponse>
     */
    public  List<GeoRadiusResponse> geoRadius(String key, GeoCoordinate coordinate, double radius) {
        Jedis jedis = this.getJedis();
        try {

            return jedis.georadius(key, coordinate.getLongitude(), coordinate.getLatitude(), radius, GeoUnit.KM, GeoRadiusParam.geoRadiusParam().withDist().withCoord().sortAscending());
        } finally {
            this.closeJedis(jedis);
        }
    }

    /**
     * 根据给定地理位置获取指定范围内的地理位置集合（返回匹配位置的经纬度 + 匹配位置与给定地理位置的距离 + 从近到远排序，）
     *
     * @param key
     * @param member
     * @param radius
     * @return List<GeoRadiusResponse>
     */
    public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius) {
        Jedis jedis = this.getJedis();
        try {

            return jedis.georadiusByMember(key, member, radius, GeoUnit.KM, GeoRadiusParam.geoRadiusParam().withDist().withCoord().sortAscending());
        } finally {
            this.closeJedis(jedis);
        }
    }

    /**
     * 查询2位置距离
     *
     * @param key
     * @param member1
     * @param member2
     * @param unit
     * @return Double
     */
    public  Double geoDist(String key, String member1, String member2, GeoUnit unit) {
        Jedis jedis = this.getJedis();
        try {

            Double dist = jedis.geodist(key, member1, member2, unit);
            return dist;
        } finally {
            this.closeJedis(jedis);
        }
    }

    /**
     * 查询位置的geohash
     *
     * @param key
     * @param members
     * @return List<String>
     */
    public  List<String> geoHash(String key, String... members) {
        Jedis jedis = this.getJedis();
        try {

            List<String> resultList = jedis.geohash(key, members);
            return resultList;
        } finally {
            this.closeJedis(jedis);
        }
    }

    /**
     * 获取地理位置的坐标
     *
     * @param key
     * @param member
     * @return List<GeoCoordinate>
     */
    public  List<GeoCoordinate> geopos(String key, String... member) {
        Jedis jedis = this.getJedis();
        try {
            List<GeoCoordinate> result = jedis.geopos(key, member);
            return result;
        } finally {
            this.closeJedis(jedis);
        }
    }


}
