package com.example.demo;


import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigInteger;
import java.security.*;
import java.util.Iterator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.spec.DHPrivateKeySpec;
import javax.crypto.spec.DHPublicKeySpec;


@RestController
public class HelloController {


    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";

    }

    @RequestMapping("/testProvider")
    public String bouncyCastleProvider() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        // Security.addProvider((Provider) Class.forName(BouncyCastleMapper.getClassName(BouncyCastleMapper.BouncyCastleProvider)).newInstance());
       // Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
       // Security.addProvider((Provider)Class.forName(BouncyCastleMapper.getClassName(BouncyCastleMapper.BouncyCastleProvider)).newInstance());
       TestExampleCipher.init();
       int count=0;
        String providerName = "BC";
        String returnString="";
        if (Security.getProvider(providerName) == null) {
            System.out.println(providerName + " provider not installed");
            returnString="BC provider not installed";
        } else {
            System.out.println(providerName + " is installed.");
            returnString="BC provider is installed";
        }

        return returnString + count++ +"\n";
    }

    @RequestMapping("/listBC")
    public String listBCCapabilities() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
       // Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
       // Security.addProvider((Provider)Class.forName(BouncyCastleMapper.getClassName(BouncyCastleMapper.BouncyCastleProvider)).newInstance());
       TestExampleCipher.init();
        Provider provider = Security.getProvider("BC");
        System.out.println("cryptoLearn.listBCCapabilities:"+provider.getInfo());
        StringBuffer st = new StringBuffer();
        Iterator it = provider.keySet().iterator();

        while (it.hasNext()) {
            String entry = (String) it.next();

            // this indicates the entry refers to another entry

            if (entry.startsWith("Alg.Alias.")) {
                entry = entry.substring("Alg.Alias.".length());
            }

            String factoryClass = entry.substring(0, entry.indexOf('.'));
            String name = entry.substring(factoryClass.length() + 1);

            System.out.println(factoryClass + ": " + name);
            st.append(name+"\n");
        }
        return st.toString();
    }

    @RequestMapping("/testInitialise")
    public String testInitialise()
            throws Exception
    {
        //Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
      //  Security.addProvider((Provider)Class.forName(BouncyCastleMapper.getClassName(BouncyCastleMapper.BouncyCastleProvider)).newInstance());
        TestExampleCipher.init();

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DH", "BC");

        keyGen.initialize(512);

        keyGen.generateKeyPair();

        testTwoParty("DH", 512, 0, keyGen);
        return "test initialise, keyGen is "+keyGen.getProvider() +"\n";
    }

    @RequestMapping("/testSmallSecret")
    public String testSmallSecret()
            throws Exception
    {
        //Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
       // Security.addProvider((Provider)Class.forName(BouncyCastleMapper.getClassName(BouncyCastleMapper.BouncyCastleProvider)).newInstance());
        //bouncyCastleProvider();
        TestExampleCipher.init();
        BigInteger p = new BigInteger("ff3b512a4cc0961fa625d6cbd9642c377ece46b8dbc3146a98e0567f944034b5e3a1406edb179a77cd2539bdb74dc819f0a74d486606e26e578ff52c5242a5ff", 16);
        BigInteger g = new BigInteger("58a66667431136e99d86de8199eb650a21afc9de3dd4ef9da6dfe89c866e928698952d95e68b418becef26f23211572eebfcbf328809bdaf02bba3d24c74f8c0", 16);

        DHPrivateKeySpec aPrivSpec = new DHPrivateKeySpec(
                new BigInteger("30a6ea4e2240a42867ad98bd3adbfd5b81aba48bd930f20a595983d807566f7cba4e766951efef2c6c0c1be3823f63d66e12c2a091d5ff3bbeb1ea6e335d072d", 16), p, g);
        DHPublicKeySpec aPubSpec = new DHPublicKeySpec(
                new BigInteger("694dfea1bfc8897e2fcbfd88033ab34f4581892d7d5cc362dc056e3d43955accda12222bd651ca31c85f008a05dea914de68828dfd83a54a340fa84f3bbe6caf", 16), p, g);

        DHPrivateKeySpec bPrivSpec = new DHPrivateKeySpec(
                new BigInteger("775b1e7e162190700e2212dd8e4aaacf8a2af92c9c108b81d5bf9a14548f494eaa86a6c4844b9512eb3e3f2f22ffec44c795c813edfea13f075b99bbdebb34bd", 16), p, g);

        DHPublicKeySpec bPubSpec = new DHPublicKeySpec(
                new BigInteger("d8ddd4ff9246635eadbfa0bc2ef06d98a329b6e8cd2d1435d7b4921467570e697c9a9d3c172c684626a9d2b6b2fa0fc725d5b91f9a9625b717a4169bc714b064", 16), p, g);

        KeyFactory kFact = KeyFactory.getInstance("DH", "BC");

        byte[] secret = testTwoParty("DH", 512, 0, new KeyPair(kFact.generatePublic(aPubSpec), kFact.generatePrivate(aPrivSpec)), new KeyPair(kFact.generatePublic(bPubSpec), kFact.generatePrivate(bPrivSpec)));

        if (secret.length != ((p.bitLength() + 7) / 8))
        {
            System.out.println("HelloController.testSmallSecret: short secret wrong length");
        }

        if (!Arrays.areEqual(Hex.decode("00340d3309ddc86e99e2f0be4fc212837bfb5c59336b09b9e1aeb1884b72c8b485b56723d0bf1c1d37fc89a292fc1cface9125106f1df15f55f22e4f77c5879b"), secret))
        {
            System.out.println("short secret mismatch");
        }
        System.out.println("HelloController.testSmallSecret: Security provider is "+kFact.getProvider());
        return "secret is "+ java.util.Arrays.toString(secret)  + "\n";
    }


    private void testTwoParty(String algName, int size, int privateValueSize, KeyPairGenerator keyGen)
            throws Exception
    {
        testTwoParty(algName, size, privateValueSize, keyGen.generateKeyPair(), keyGen.generateKeyPair());
    }

    private byte[] testTwoParty(String algName, int size, int privateValueSize, KeyPair aKeyPair, KeyPair bKeyPair)
            throws Exception
    {
        //
        // a side
        //
        KeyAgreement aKeyAgree = KeyAgreement.getInstance(algName, "BC");

        checkKeySize(privateValueSize, aKeyPair);

        aKeyAgree.init(aKeyPair.getPrivate());

        //
        // b side
        //
        KeyAgreement bKeyAgree = KeyAgreement.getInstance(algName, "BC");

        checkKeySize(privateValueSize, bKeyPair);

        bKeyAgree.init(bKeyPair.getPrivate());

        //
        // agreement
        //
        aKeyAgree.doPhase(bKeyPair.getPublic(), true);
        bKeyAgree.doPhase(aKeyPair.getPublic(), true);

        byte[] aSecret = aKeyAgree.generateSecret();
        byte[] bSecret = bKeyAgree.generateSecret();

        if (!Arrays.areEqual(aSecret, bSecret))
        {
            System.out.println(size + " bit 2-way test failed");
        }

        return aSecret;
    }

    private void checkKeySize(int privateValueSize, KeyPair aKeyPair)
    {
        if (privateValueSize != 0)
        {
            DHPrivateKey key = (DHPrivateKey)aKeyPair.getPrivate();

            if (key.getX().bitLength() != privateValueSize)
            {
                System.out.println("HelloController.checkKeySize limited key check failed for key size " + privateValueSize);
            }
        }
    }


}