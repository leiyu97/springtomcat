package com.example.demo;

public enum BouncyCastleMapper {
    BouncyCastleProvider("org.bouncycastle.jce.provider.BouncyCastleProvider"),
    GCMBlockCipher("org.bouncycastle.crypto.modes.GCMBlockCipher"),
    AESEngine("org.bouncycastle.crypto.engines.AESEngine"),
    CipherInputStream("org.bouncycastle.crypto.io.CipherInputStream"),
    AEADParameters("org.bouncycastle.crypto.params.AEADParameters"),
    KeyParameter("org.bouncycastle.crypto.params.KeyParameter"),
    BlockCipher("org.bouncycastle.crypto.BlockCipher"),
    CipherParameters("org.bouncycastle.crypto.CipherParameters"),
    AEADBlockCipher("org.bouncycastle.crypto.modes.AEADBlockCipher"),
    BufferedBlockCipher("org.bouncycastle.crypto.BufferedBlockCipher"),
    DataLengthException("org.bouncycastle.crypto.DataLengthException"),
    InvalidCipherTextException("org.bouncycastle.crypto.InvalidCipherTextException"),
    SEEDEngine("org.bouncycastle.crypto.engines.SEEDEngine"),
    CBCBlockCipher("org.bouncycastle.crypto.modes.CBCBlockCipher"),
    PaddedBufferedBlockCipher("org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher"),
    ParametersWithIV("org.bouncycastle.crypto.params.ParametersWithIV");

    private String providerName;

    BouncyCastleMapper(String name) {
        this.providerName = name;
        //throw new RuntimeException("blabalaasdfhaldjghakg");
    }

    public static String getClassName(BouncyCastleMapper mapper) {
        return mapper.providerName;

    }
}
