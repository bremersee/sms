/*
 * Copyright 2015-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bremersee.sms;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bremersee.sms.model.GoyyaSmsSendResponseDto;
import org.bremersee.sms.model.SmsSendRequestDto;
import org.bremersee.sms.model.SmsSendResponseDto;

/**
 * A SMS service implementation that uses the Goyya SMS Services (
 * <a href="https://www.goyya.com/sms-services">https://www.goyya.com/sms-services</a>).
 *
 * @author Christian Bremer
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class GoyyaSmsService extends AbstractSmsService implements SmsService {

  /**
   * The default URL of the Goyya SMS service.
   */
  private static final String DEFAULT_URL = "https://gate1.goyyamobile.com/sms/sendsms.asp";

  /**
   * The default pattern to convert a time.
   */
  public static final String DEFAULT_SEND_TIME_PATTERN = "HHmmddMMyyyy";

  /**
   * The message type for normal text messages.
   */
  public static final String MESSAGE_TYPE_TEXT_VALUE = "t";

  /**
   * The message type for long text messages.
   */
  public static final String MESSAGE_TYPE_LONG_TEXT_VALUE = "c";

  /**
   * The message type for blinking messages.
   */
  public static final String MESSAGE_TYPE_BLINK_VALUE = "b";

  /**
   * The message type for flash messages.
   */
  public static final String MESSAGE_TYPE_FLASH_VALUE = "f";

  private static final String GATEWAY_USER_ID_KEY = "id";

  private static final String GATEWAY_USER_PASSWORD_KEY = "pw";

  private static final String SENDER_KEY = "sender";

  private static final String RECEIVER_KEY = "receiver";

  private static final String MESSAGE_KEY = "msg";

  private static final String MESSAGE_TYPE_KEY = "msgtype";

  private static final String TIME_KEY = "time";

  private static final String GET_MSG_ID_KEY = "getId";
  private static final String GET_MSG_ID_VALUE = "1";

  private static final String GET_COUNT_MSG_KEY = "countMsg";
  private static final String GET_COUNT_MSG_VALUE = "1";

  private static final String GET_LIMIT_KEY = "getLimit";
  private static final String GET_LIMIT_VALUE = "1";

  private static final String GET_STATUS_KEY = "getStatus";
  private static final String GET_STATUS_VALUE = "1";

  private String url = DEFAULT_URL;

  private String username;

  private String password;

  private String proxyHost;

  private Integer proxyPort;

  private String proxyUsername;

  private String proxyPassword;

  private String sendTimePattern = DEFAULT_SEND_TIME_PATTERN;

  /**
   * The default message type.
   *
   * <p>It can be {@code t|f|b|c} (t=Text-SMS, f=Flash-SMS, b=Blink-SMS*, c=long SMS).
   */
  private String defaultMessageType = MESSAGE_TYPE_TEXT_VALUE;

  /**
   * Default constructor.
   */
  public GoyyaSmsService() {
    super();
  }

  /**
   * Constructs a SMS service the the specified user name and password.
   *
   * @param username the user name
   * @param password the password
   */
  public GoyyaSmsService(String username, String password) {
    this(username, password, null);
  }

  /**
   * Constructs a SMS service the the specified user name, password and URL.
   *
   * @param username the user name
   * @param password the password
   * @param url      the URL to use
   */
  public GoyyaSmsService(String username, String password, String url) {
    this.username = username;
    this.password = password;
    if (StringUtils.isNotBlank(url)) {
      this.url = url;
    }
  }

  /**
   * Sets the user name.
   *
   * @param username the user name
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Sets the password.
   *
   * @param password the password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Returns the URL which is used.
   *
   * @return the URL
   */
  public String getUrl() {
    return url;
  }

  /**
   * Sets the URL to use.
   *
   * @param url the URL to use
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * Gets the host name of the proxy.
   *
   * @return the host name of the proxy
   */
  public String getProxyHost() {
    return proxyHost;
  }

  /**
   * Sets the host name of the proxy.
   *
   * @param proxyHost the host name of the proxy
   */
  public void setProxyHost(String proxyHost) {
    this.proxyHost = proxyHost;
  }

  /**
   * Gets the port of the proxy.
   *
   * @return the port of the proxy
   */
  public Integer getProxyPort() {
    return proxyPort;
  }

  /**
   * Sets the port of the proxy.
   *
   * @param proxyPort the port of the proxy
   */
  public void setProxyPort(Integer proxyPort) {
    this.proxyPort = proxyPort;
  }

  /**
   * Sets the user name for proxy authentication.
   *
   * @param proxyUsername the user name for proxy authentication
   */
  public void setProxyUsername(String proxyUsername) {
    this.proxyUsername = proxyUsername;
  }

  /**
   * Sets the password for proxy authentication.
   *
   * @param proxyPassword the password for proxy authentication
   */
  public void setProxyPassword(String proxyPassword) {
    this.proxyPassword = proxyPassword;
  }

  /**
   * Gets the pattern to convert the time.
   *
   * @return the pattern to convert the time
   */
  public String getSendTimePattern() {
    return sendTimePattern;
  }

  /**
   * Sets the pattern to convert the time.
   *
   * @param sendTimePattern the pattern to convert the time
   */
  public void setSendTimePattern(String sendTimePattern) {
    this.sendTimePattern = sendTimePattern;
  }

  /**
   * Gets the default message type.
   *
   * @return the default message type
   */
  public String getDefaultMessageType() {
    return defaultMessageType;
  }

  /**
   * Sets the default message type.
   *
   * @param defaultMessageType the default message type
   */
  public void setDefaultMessageType(String defaultMessageType) {
    this.defaultMessageType = defaultMessageType;
  }

  @Override
  public SmsSendResponseDto doSendSms(final SmsSendRequestDto smsSendRequest) {

    final String sender = getSender(smsSendRequest);
    final String receiver = getReceiver(smsSendRequest);
    final String message = getMessage(smsSendRequest);
    final String messageType = getMessageType(message);
    final String time = createSendTime(smsSendRequest.getSendTime());
    final Charset charset = createCharset();
    final StringBuilder uriBuilder = new StringBuilder(url);
    if (url.contains("?")) {
      uriBuilder.append('&');
    } else {
      uriBuilder.append('?');
    }
    uriBuilder.append(GATEWAY_USER_ID_KEY).append('=').append(encode(username, charset));
    uriBuilder.append('&').append(GATEWAY_USER_PASSWORD_KEY).append('=')
        .append(encode(password, charset));
    uriBuilder.append('&').append(SENDER_KEY).append('=').append(encode(sender, charset));
    uriBuilder.append('&').append(RECEIVER_KEY).append('=').append(encode(receiver, charset));
    uriBuilder.append('&').append(MESSAGE_KEY).append('=').append(encode(message, charset));
    if (StringUtils.isNotBlank(messageType)) {
      uriBuilder.append('&').append(MESSAGE_TYPE_KEY).append('=').append(messageType);
    }
    if (time != null) {
      uriBuilder.append('&').append(TIME_KEY).append('=').append(time);
    }
    uriBuilder.append('&').append(GET_MSG_ID_KEY).append('=').append(GET_MSG_ID_VALUE);
    uriBuilder.append('&').append(GET_COUNT_MSG_KEY).append('=').append(GET_COUNT_MSG_VALUE);
    uriBuilder.append('&').append(GET_LIMIT_KEY).append('=').append(GET_LIMIT_VALUE);
    uriBuilder.append('&').append(GET_STATUS_KEY).append('=').append(GET_STATUS_VALUE);

    String response;
    HttpURLConnection con = null;
    try {
      con = createHttpUrlConnection(uriBuilder.toString());
      con.connect();
      int statusCode = con.getResponseCode();
      if (statusCode >= 400) {
        try (InputStream errorStream = con.getErrorStream()) {
          response = IOUtils.toString(errorStream, charset);

        } catch (IOException e) {
          SmsException se = new SmsException(e);
          log.error("Reading error stream failed.", se);
          throw se;
        }
      } else {
        try (InputStream inputStream = con.getInputStream()) {
          response = IOUtils.toString(inputStream, charset);

        } catch (IOException e) {
          SmsException se = new SmsException(e);
          log.error("Reading input stream failed.", se);
          throw se;
        }
      }
    } catch (IOException e) {
      SmsException se = new SmsException(e);
      log.error("Sending SMS specified by " + smsSendRequest + " failed.", se);
      throw se;

    } finally {
      if (con != null) {
        con.disconnect();
      }
    }

    final GoyyaSmsSendResponseDto goyyaSmsSendResponse = new GoyyaSmsSendResponseDto(response);
    return new SmsSendResponseDto(
        smsSendRequest,
        goyyaSmsSendResponse.isOk(),
        goyyaSmsSendResponse);
  }

  /**
   * Returns the message type.
   *
   * @param message the message
   * @return the message type
   */
  protected String getMessageType(final String message) {
    if (StringUtils.isBlank(defaultMessageType) || MESSAGE_TYPE_TEXT_VALUE
        .equalsIgnoreCase(defaultMessageType)
        || MESSAGE_TYPE_LONG_TEXT_VALUE.equalsIgnoreCase(defaultMessageType)) {
      return message.length() > getMaxLengthOfOneSms() ? MESSAGE_TYPE_LONG_TEXT_VALUE
          : MESSAGE_TYPE_TEXT_VALUE;
    }
    return defaultMessageType;
  }

  /**
   * Creates the send time URL parameter value.
   *
   * @param sendTime the send time as {@link Date}
   * @return the send time URL parameter value
   */
  protected String createSendTime(final Date sendTime) {
    if (sendTime == null || new Date(System.currentTimeMillis() + 1000L * 60L).after(sendTime)) {
      return null;
    }
    String customSendTimePattern =
        StringUtils.isBlank(this.sendTimePattern) ? DEFAULT_SEND_TIME_PATTERN
            : this.sendTimePattern;
    SimpleDateFormat sdf = new SimpleDateFormat(customSendTimePattern, Locale.GERMANY);
    sdf.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
    return sdf.format(sendTime);
  }

  /**
   * Creates the URL connection.
   *
   * @param url the URL
   * @return the URL connection
   * @throws IOException if creation of the URL connection fails
   */
  protected HttpURLConnection createHttpUrlConnection(final String url) throws IOException {

    URL sendUrl = new URL(url);

    HttpURLConnection con;

    if (StringUtils.isNotBlank(proxyHost) && proxyPort != null) {

      Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
      con = (HttpURLConnection) sendUrl.openConnection(proxy);
      if (StringUtils.isNotBlank(proxyUsername)) {
        String passwd = proxyPassword != null ? proxyPassword : "";
        String authValue = proxyUsername + ":" + passwd;
        String headerValue = Base64.encodeBase64String(authValue.getBytes(
            StandardCharsets.UTF_8));
        con.setRequestProperty("Proxy-Authorization", "Basic " + headerValue);
      }

    } else {

      con = (HttpURLConnection) sendUrl.openConnection();
    }

    try {
      if (url.toLowerCase().startsWith("https")) {
        HttpsURLConnection secCon = (HttpsURLConnection) con;
        secCon.setHostnameVerifier(createAllHostnamesVerifier());
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, createTrustAllManagers(), new SecureRandom());
        secCon.setSSLSocketFactory(sc.getSocketFactory());
      }

    } catch (NoSuchAlgorithmException | KeyManagementException e) {
      throw new IOException(e);
    }

    return con;
  }

  /**
   * Creates a host name verifier that does not verify the host name.
   *
   * @return the hostname verifier
   */
  protected HostnameVerifier createAllHostnamesVerifier() {
    return (hostname, session) -> true;
  }

  /**
   * Creates an array of trust managers which trusts all X509 certificates.
   *
   * @return the trust manager [ ]
   */
  protected TrustManager[] createTrustAllManagers() {
    return new TrustManager[]{

        new X509TrustManager() {

          @Override
          public X509Certificate[] getAcceptedIssuers() {
            return null;
          }

          @Override
          public void checkClientTrusted(X509Certificate[] certs, String authType) {
          }

          @Override
          public void checkServerTrusted(X509Certificate[] certs, String authType) {
          }
        }
    };
  }

  /**
   * Encode query parameter.
   *
   * @param value   the parameter value
   * @param charset the charset
   * @return the encoded parameter value
   */
  private String encode(final String value, final Charset charset) {
    if (StringUtils.isBlank(value)) {
      return "";
    }
    try {
      return URLEncoder.encode(value, charset.name());

    } catch (UnsupportedEncodingException e) {
      return value;
    }
  }

}
