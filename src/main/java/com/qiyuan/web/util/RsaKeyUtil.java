package com.qiyuan.web.util;

import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.RSAPrivateCrtKeySpec;

@Component
public class RsaKeyUtil {

    public PrivateKey loadRSAPrivateKey(String pemPath) throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(pemPath);
        if (inputStream == null) {
            throw new IllegalArgumentException("PEM file not found: " + pemPath);
        }

        PemReader pemReader = new PemReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        PemObject pemObject = pemReader.readPemObject();
        byte[] content = pemObject.getContent();
        pemReader.close();

        RSAPrivateKey rsaPrivateKey = RSAPrivateKey.getInstance(content);
        RSAPrivateCrtKeySpec keySpec = new RSAPrivateCrtKeySpec(
                rsaPrivateKey.getModulus(),
                rsaPrivateKey.getPublicExponent(),
                rsaPrivateKey.getPrivateExponent(),
                rsaPrivateKey.getPrime1(),
                rsaPrivateKey.getPrime2(),
                rsaPrivateKey.getExponent1(),
                rsaPrivateKey.getExponent2(),
                rsaPrivateKey.getCoefficient()
        );

        return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
    }
}
