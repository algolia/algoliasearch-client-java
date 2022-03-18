package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaRuntimeException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;

public class SSLUtils {

  // Suppress default constructor for noninstantiability
  private SSLUtils() {
    throw new AssertionError();
  }

  /** Default SSL parameters without specifying protocols */
  public static SSLParameters getDefaultSSLParameters() {
    SSLParameters sslParameters = getDefaultSSLContext().getSupportedSSLParameters();
    sslParameters.setProtocols(null);
    return sslParameters;
  }

  /** Get default SSL Context */
  private static SSLContext getDefaultSSLContext() {
    try {
      return SSLContext.getDefault();
    } catch (NoSuchAlgorithmException ex) {
      throw new AlgoliaRuntimeException(ex);
    }
  }
}
