package com.algolia.search.helpers;

import com.algolia.search.exceptions.AlgoliaException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacShaHelper {

  private static String hmac(String key, String msg) throws AlgoliaException {
    Mac hmac;
    try {
      hmac = Mac.getInstance("HmacSHA256");
      hmac.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));
    } catch (NoSuchAlgorithmException e) {
      throw new AlgoliaException("Can not find HmacSHA256 algorithm", e);
    } catch (InvalidKeyException e) {
      throw new AlgoliaException("Can not init HmacSHA256 algorithm", e);
    }
    byte[] rawHmac = hmac.doFinal(msg.getBytes());
    StringBuilder sb = new StringBuilder(rawHmac.length * 2);
    for (byte b : rawHmac) {
      sb.append(String.format("%02x", b & 0xff));
    }
    return sb.toString();
  }
}
