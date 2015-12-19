/*
 * Copyright 2015 the original author or authors.
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
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.Charset;
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
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bremersee.sms.model.GoyyaSmsSendResponseDto;
import org.bremersee.sms.model.SmsSendRequestDto;
import org.bremersee.sms.model.SmsSendResponseDto;
import org.bremersee.utils.WebUtils;

//@formatter:off
/**
 * <p>
 * A SMS service implementation that uses the Goyya SMS Services (
 * <a href="https://www.goyya.com/sms-services">https://www.goyya.com/sms-services</a>
 * ).
 * </p>
 * 
 * @author Christian Bremer
 */
//@formatter:on
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

    private static final String GET_MSG_ID_KEY = "getID";
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
     * t|f|b|c – t=Text-SMS, f=Flash-SMS, b=Blink-SMS*, c=long SMS
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
     * @param username
     *            the user name
     * @param password
     *            the password
     */
    public GoyyaSmsService(String username, String password) {
        this(username, password, null);
    }

    /**
     * Constructs a SMS service the the specified user name, password and URL.
     * 
     * @param username
     *            the user name
     * @param password
     *            the password
     * @param url
     *            the URL to use
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
     * @param username
     *            the user name
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the password.
     * 
     * @param password
     *            the password
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
     * @param url
     *            the URL to use
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
     * @param proxyHost
     *            the host name of the proxy
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
     * @param proxyPort
     *            the port of the proxy
     */
    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
    }

    /**
     * Sets the user name for proxy authentication.
     * 
     * @param proxyUsername
     *            the user name for proxy authentication
     */
    public void setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
    }

    /**
     * Sets the password for proxy authentication.
     * 
     * @param proxyPassword
     *            the password for proxy authentication
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
     * @param sendTimePattern
     *            the pattern to convert the time
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
     * @param defaultMessageType
     *            the default message type
     */
    public void setDefaultMessageType(String defaultMessageType) {
        this.defaultMessageType = defaultMessageType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.bremersee.sms.service.AbstractSmsService#doSendSms(org.bremersee.sms.
     * service.model.SmsSendRequestDto)
     */
    @Override
    public SmsSendResponseDto doSendSms(final SmsSendRequestDto smsSendRequest) throws SmsException {

        final String sender = getSender(smsSendRequest);

        final String receiver = getReceiver(smsSendRequest);

        final String message = getMessage(smsSendRequest);

        final String messageType = getMessageType(message);

        final String time = createSendTime(smsSendRequest.getSendTime());

        final Charset charset = createCharset();

        String url = new String(this.url);

        url = WebUtils.addUrlParameter(url, GATEWAY_USER_ID_KEY, username, charset);
        url = WebUtils.addUrlParameter(url, GATEWAY_USER_PASSWORD_KEY, password, charset);
        url = WebUtils.addUrlParameter(url, SENDER_KEY, sender, charset);
        url = WebUtils.addUrlParameter(url, RECEIVER_KEY, receiver, charset);
        url = WebUtils.addUrlParameter(url, MESSAGE_KEY, message, charset);

        if (StringUtils.isNotBlank(messageType)) {
            url = WebUtils.addUrlParameter(url, MESSAGE_TYPE_KEY, messageType, charset);
        }
        if (time != null) {
            url = WebUtils.addUrlParameter(url, TIME_KEY, time, charset);
        }

        url = WebUtils.addUrlParameter(url, GET_MSG_ID_KEY, GET_MSG_ID_VALUE, charset);
        url = WebUtils.addUrlParameter(url, GET_COUNT_MSG_KEY, GET_COUNT_MSG_VALUE, charset);
        url = WebUtils.addUrlParameter(url, GET_LIMIT_KEY, GET_LIMIT_VALUE, charset);
        url = WebUtils.addUrlParameter(url, GET_STATUS_KEY, GET_STATUS_VALUE, charset);

        final StringBuilder sb = new StringBuilder();
        HttpURLConnection con = null;
        InputStreamReader in = null;
        try {
            con = createHttpURLConnection(url);

            con.connect();

            in = new InputStreamReader(con.getInputStream(), charset);

            int len;
            char[] buf = new char[64];
            while ((len = in.read(buf)) > 0) {
                sb.append(buf, 0, len);
            }

        } catch (IOException e) {
            SmsException se = new SmsException(e);
            log.error("Sending SMS specified by " + smsSendRequest + " failed.", se);
            throw se;

        } finally {
            IOUtils.closeQuietly(in);
            if (con != null) {
                con.disconnect();
            }
        }

        final GoyyaSmsSendResponseDto goyyaSmsSendResponse = new GoyyaSmsSendResponseDto(sb.toString());
        final SmsSendResponseDto smsSendResponse = new SmsSendResponseDto(smsSendRequest, goyyaSmsSendResponse.isOk(),
                goyyaSmsSendResponse);
        return smsSendResponse;
    }

    /**
     * Returns the message type.
     * 
     * @param message
     *            the message
     * @return the message type
     */
    protected String getMessageType(final String message) {
        if (StringUtils.isBlank(defaultMessageType) || MESSAGE_TYPE_TEXT_VALUE.equalsIgnoreCase(defaultMessageType)
                || MESSAGE_TYPE_LONG_TEXT_VALUE.equalsIgnoreCase(defaultMessageType)) {
            return message.length() > getMaxLengthOfOneSms() ? MESSAGE_TYPE_LONG_TEXT_VALUE : MESSAGE_TYPE_TEXT_VALUE;
        }
        return defaultMessageType;
    }

    /**
     * Creates the send time URL parameter value.
     * 
     * @param sendTime
     *            the send time as {@link Date}
     * @return the send time URL parameter value
     */
    protected String createSendTime(final Date sendTime) {
        if (sendTime == null || new Date(System.currentTimeMillis() + 1000L * 60L).after(sendTime)) {
            return null;
        }
        String sendTimePattern = StringUtils.isBlank(this.sendTimePattern) ? DEFAULT_SEND_TIME_PATTERN
                : this.sendTimePattern;
        SimpleDateFormat sdf = new SimpleDateFormat(sendTimePattern, Locale.GERMANY);
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        return sdf.format(sendTime);
    }

    /**
     * Creates the URL connection.
     * 
     * @param url
     *            the URL
     * @return the URL connection
     * @throws IOException
     *             if creation of the URL connection fails
     */
    protected HttpURLConnection createHttpURLConnection(final String url) throws IOException {

        URL sendUrl = new URL(url);

        HttpURLConnection con = null;

        if (StringUtils.isNotBlank(proxyHost) && proxyPort != null) {

            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
            con = (HttpURLConnection) sendUrl.openConnection(proxy);
            if (StringUtils.isNotBlank(proxyUsername)) {
                String passwd = proxyPassword != null ? proxyPassword : "";
                String authValue = proxyUsername + ":" + passwd;
                String headerValue = Base64.encodeBase64String(authValue.getBytes("utf-8"));
                con.setRequestProperty("Proxy-Authorization", "Basic " + headerValue);
            }

        } else {

            con = (HttpURLConnection) sendUrl.openConnection();
        }

        try {
            if (url.toString().toLowerCase().startsWith("https")) {
                HttpsURLConnection secCon = (HttpsURLConnection) con;
                secCon.setHostnameVerifier(createAllHostnamesVerifier());
                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, createTrustAllManagers(), new SecureRandom());
                secCon.setSSLSocketFactory(sc.getSocketFactory());
            }

        } catch (NoSuchAlgorithmException e) {
            IOException ise = new IOException(e);
            // log.error("Creating HttpURLConnection failed.", ise);
            throw ise;

        } catch (KeyManagementException e) {
            IOException ise = new IOException(e);
            // log.error("Creating HttpURLConnection failed.", ise);
            throw ise;
        }

        return con;
    }

    /**
     * Creates a host name verifier that does not verify the host name.
     */
    protected HostnameVerifier createAllHostnamesVerifier() {

        return new HostnameVerifier() {

            /*
             * (non-Javadoc)
             * 
             * @see javax.net.ssl.HostnameVerifier#verify(java.lang.String,
             * javax.net.ssl.SSLSession)
             */
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
    }

    /**
     * Creates an array of trust managers which trusts all X509 certificates.
     */
    protected TrustManager[] createTrustAllManagers() {
        return new TrustManager[] {

                new X509TrustManager() {

                    /*
                     * (non-Javadoc)
                     * 
                     * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
                     */
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    /*
                     * (non-Javadoc)
                     * 
                     * @see
                     * javax.net.ssl.X509TrustManager#checkClientTrusted(java.
                     * security.cert.X509Certificate[], java.lang.String)
                     */
                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    /*
                     * (non-Javadoc)
                     * 
                     * @see
                     * javax.net.ssl.X509TrustManager#checkServerTrusted(java.
                     * security.cert.X509Certificate[], java.lang.String)
                     */
                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                } };
    }

}
