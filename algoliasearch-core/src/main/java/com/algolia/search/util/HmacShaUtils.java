package com.algolia.search.util;

import com.algolia.search.models.apikeys.SecuredApiKeyRestriction;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.annotation.Nonnull;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacShaUtils {

  public static String generateSecuredApiKey(
      @Nonnull String privateApiKey, @Nonnull SecuredApiKeyRestriction restriction)
      throws Exception {

    String queryStr = QueryStringUtils.buildRestrictionQueryString(restriction);
    String key = hmac(privateApiKey, queryStr);

    return new String(
        Base64.getEncoder()
            .encode(String.format("%s%s", key, queryStr).getBytes(Charset.forName("UTF8"))));
  }

  private static String hmac(String key, String msg)
      throws NoSuchAlgorithmException, InvalidKeyException {
    Mac hmac;
    hmac = Mac.getInstance("HmacSHA256");
    hmac.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));
    byte[] rawHmac = hmac.doFinal(msg.getBytes());
    StringBuilder sb = new StringBuilder(rawHmac.length * 2);
    for (byte b : rawHmac) {
      sb.append(String.format("%02x", b & 0xff));
    }
    return sb.toString();
  }
}
