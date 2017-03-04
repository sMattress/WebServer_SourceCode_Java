package com.mattress.utils;

import org.apache.commons.codec.binary.Base64;
import redis.clients.jedis.Jedis;

public class CacheUtils {

    private static final Jedis jedis = new Jedis("127.0.0.1", 6378);

    private CacheUtils() {
    }

    private static String getRandNum() {
        int randNum = 1 + (int)(Math.random() * ((999999 - 1) + 1));
        return String.format("%d", randNum);
    }

    public static String getCode(String account) {
        String code = jedis.get(String.format("code:%s",account));
        if (code == null || code.isEmpty()) {
            code = getRandNum();
            setCode(account, code);
        }
        return code;
    }

    public static void setCode(String account, String code) {
        jedis.setex(String.format("code:%s", account), 60, code);
    }

    public static void removeCode(String account) {
        if (jedis.exists(String.format("code:%s", account))) {
            jedis.del(String.format("code:%s", account));
        }
    }

    public static String getToken(String account) {
        return jedis.get(String.format("token:%s", account));
    }

    public static void setToken(String account, String token) {
        jedis.set(String.format("token:%s", account), token, "NX","EX", 30 * 24 * 60 * 60);
    }

    public static void refreshToken(String account) {
        jedis.expire(String.format("token:%s", account), 30 * 24 * 60 * 60);
    }

    public static void removeToken(String account) {
        if (jedis.exists(String.format("token:%s", account))) {
            jedis.del(String.format("token:%s", account));
        }
    }

    public static String getTokenExpiresIn(String account) {
        return String.valueOf(jedis.ttl(String.format("token:%s", account)));
    }

    public static String makeToken(String account) {
        String token = getToken(account);
        if (token == null || token.isEmpty()) {
            token = Base64.encodeBase64URLSafeString(getRandNum().getBytes());
            setToken(account, token);
        }else {
            refreshToken(account);
        }
        return token;
    }
}
