package org.nsbm.dea.student_management_system.state;

import org.nsbm.dea.student_management_system.config.Env;
import redis.clients.jedis.JedisPool;

public class Redis {
  public static JedisPool getPool() {
    JedisPool jedis = new JedisPool(
        Env.getRedisURL());
    return jedis;
  }
}
