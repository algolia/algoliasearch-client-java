package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.Query;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Utils {

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

  static String generateSecuredApiKey(
      @Nonnull String privateApiKey, @Nonnull Query query, String userToken)
      throws AlgoliaException {
    if (userToken != null && userToken.length() > 0) {
      query.setUserToken(userToken);
    }
    String queryStr = query.toParam();
    String key = hmac(privateApiKey, queryStr);

    return new String(
        Base64.getEncoder()
            .encode(String.format("%s%s", key, queryStr).getBytes(Charset.forName("UTF8"))));
  }

  public static <T> T parseAs(ObjectMapper mapper, Reader content, Class<T> klass)
      throws IOException {
    return mapper.readValue(content, mapper.getTypeFactory().constructType(klass));
  }

  public static <T> T parseAs(ObjectMapper mapper, Reader content, JavaType type)
      throws IOException {

    return mapper.readValue(content, type);
  }

  public static <T> CompletableFuture<T> completeExceptionally(Throwable t) {
    CompletableFuture<T> future = new CompletableFuture<>();
    future.completeExceptionally(t);
    return future;
  }
}
