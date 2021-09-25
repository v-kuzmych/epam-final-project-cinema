package com.vasilisa.cinema.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurePassword {

    private static final Logger logger = LogManager.getLogger(SecurePassword.class);

    public static String md5Hex(String st) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            // it occurs if the passed algorithm does not exist in getInstance (,,,)
            logger.error("MD5 algorithm does not exist in getInstance");
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while( md5Hex.length() < 32 ){
            md5Hex = "0" + md5Hex;
        }

        logger.debug("MD5 algorithm finished");
        return md5Hex;
    }
}
