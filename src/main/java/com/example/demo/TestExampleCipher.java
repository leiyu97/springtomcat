package com.example.demo;

import java.security.Provider;
import java.security.Security;

public class TestExampleCipher {

    public static void init() {
        K();
    }

    private static void K() {
        try {
           /* ClassLoader.getSystemClassLoader().loadClass(BouncyCastleMapper.getClassName(BouncyCastleMapper.BouncyCastleProvider));
            ClassLoader.getSystemClassLoader().loadClass(BouncyCastleMapper.getClassName(BouncyCastleMapper.GCMBlockCipher));
            ClassLoader.getSystemClassLoader().loadClass(BouncyCastleMapper.getClassName(BouncyCastleMapper.AESEngine));
            ClassLoader.getSystemClassLoader().loadClass(BouncyCastleMapper.getClassName(BouncyCastleMapper.CipherInputStream));
            ClassLoader.getSystemClassLoader().loadClass(BouncyCastleMapper.getClassName(BouncyCastleMapper.AEADParameters));
            ClassLoader.getSystemClassLoader().loadClass(BouncyCastleMapper.getClassName(BouncyCastleMapper.KeyParameter));
            ClassLoader.getSystemClassLoader().loadClass(BouncyCastleMapper.getClassName(BouncyCastleMapper.AEADBlockCipher));*/
            Security.addProvider((Provider)Class.forName(BouncyCastleMapper.getClassName(BouncyCastleMapper.BouncyCastleProvider)).newInstance());
        } catch (Exception var1) {
            throw new RuntimeException(var1); // our request getting exception at this line.
        }
    }
}
