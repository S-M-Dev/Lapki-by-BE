package com.smdev.lapkibe.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CodeUtil {

    public static String generate(){
        Random random = new Random();
        return String.format("%d%s%d%s", random.nextInt(9),
                generateString(3, random),
                random.nextInt(100),
                generateString(3, random));
    }

    private static String generateString(int size, Random random){
        StringBuilder buffer = new StringBuilder();
        while(size-- > 0){
            buffer.append((char)('a' + random.nextInt(20)));
        }
        return buffer.toString();
    }

}
