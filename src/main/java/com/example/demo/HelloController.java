package com.example.demo;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Provider;
import java.security.Security;
import java.util.Iterator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";

    }

    @RequestMapping("/testProvider")
    public String bouncyCastleProvider() {
        // Security.addProvider((Provider) Class.forName(BouncyCastleMapper.getClassName(BouncyCastleMapper.BouncyCastleProvider)).newInstance());
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        String providerName = "BC";
        String returnString="";
        if (Security.getProvider(providerName) == null) {
            System.out.println(providerName + " provider not installed");
            returnString="BC provider not installed";
        } else {
            System.out.println(providerName + " is installed.");
            returnString="BC provider is installed";
        }

        return returnString +"\n";
    }

    @RequestMapping("/listBC")
    public String listBCCapabilities() {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
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

}