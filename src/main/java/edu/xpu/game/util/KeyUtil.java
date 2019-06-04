package edu.xpu.game.util;

import java.util.Random;

/**
 * @ClassName KeyUtil
 * @Description
 * @Author tim
 * @Date 2019-05-26 17:19
 * @Version 1.0
 */
public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式: 时间+随机数
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(number);
    }
}