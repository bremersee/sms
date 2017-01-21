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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.bremersee.sms.model.SmsSendRequestDto;
import org.bremersee.sms.model.SmsSendResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * <p>
 * Abstract SMS service implementation.
 * </p>
 *
 * @author Christian Bremer
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class AbstractSmsService implements SmsService {

    /**
     * The default charset.
     */
    public static final Charset DEFAULT_CHARSET = Charset.forName("ISO-8859-1");

    /**
     * The maximum length of one SMS.
     */
    public static final int DEFAULT_MAX_LENGTH_OF_ONE_SMS = 153;

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private String defaultSender;

    private String defaultReceiver;

    private String defaultMessage;

    private String charset = DEFAULT_CHARSET.name();

    private int maxLengthOfOneSms = DEFAULT_MAX_LENGTH_OF_ONE_SMS;

    /**
     * Default constructor.
     */
    public AbstractSmsService() {
        super();
    }

    /**
     * Gets the default name or number of the sender.
     *
     * @return the default name or number of the sender
     */
    public String getDefaultSender() {
        return defaultSender;
    }

    /**
     * Sets the default name or number of the sender.
     *
     * @param defaultSender the default name or number of the sender (legal characters are
     *                      a-z, A-Z and 0-9)
     */
    public void setDefaultSender(String defaultSender) {
        this.defaultSender = defaultSender;
    }

    /**
     * Gets the default name or number of the receiver.
     *
     * @return the default name or number of the receiver
     */
    public String getDefaultReceiver() {
        return defaultReceiver;
    }

    /**
     * Sets the default name or number of the receiver.
     *
     * @param defaultReceiver the default name or number of the receiver (legal characters
     *                        are a-z, A-Z and 0-9)
     */
    public void setDefaultReceiver(String defaultReceiver) {
        this.defaultReceiver = defaultReceiver;
    }

    /**
     * Gets the default message.
     *
     * @return the default message
     */
    public String getDefaultMessage() {
        return defaultMessage;
    }

    /**
     * Sets the default message.
     *
     * @param defaultMessage the default message
     */
    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    /**
     * Gets the used charset.
     *
     * @return the used charset
     */
    public String getCharset() {
        return charset;
    }

    /**
     * Sets the charset to use.
     *
     * @param charset the charset to use
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

    /**
     * Gets the maximum length of one SMS.
     *
     * @return the maximum length of one SMS
     */
    public int getMaxLengthOfOneSms() {
        return maxLengthOfOneSms;
    }

    /**
     * Sets the maximum length of one SMS.
     *
     * @param maxLengthOfOneSms the maximum length of one SMS
     */
    public void setMaxLengthOfOneSms(int maxLengthOfOneSms) {
        this.maxLengthOfOneSms = maxLengthOfOneSms;
    }

    @Override
    public SmsSendResponseDto sendSms() {
        return sendSms(null, null, null, null);
    }

    @Override
    public SmsSendResponseDto sendSms(final String message) {
        return sendSms(null, null, message, null);
    }

    @Override
    public SmsSendResponseDto sendSms(final String receiver, final String message) {
        return sendSms(null, receiver, message, null);
    }

    @Override
    public SmsSendResponseDto sendSms(final String receiver, final String message, final Date sendTime) {

        return sendSms(null, receiver, message, sendTime);
    }

    @Override
    public SmsSendResponseDto sendSms(final String sender, final String receiver, final String message) {

        return sendSms(sender, receiver, message, null);
    }

    @Override
    public SmsSendResponseDto sendSms(final String sender, final String receiver, final String message,
                                      final Date sendTime) {
        return sendSms(new SmsSendRequestDto(sender, receiver, message, sendTime));
    }

    @Override
    public SmsSendResponseDto sendSms(final SmsSendRequestDto smsSendRequest) {
        Validate.notNull(smsSendRequest, "smsSendRequest must not be null");
        log.info("Sending SMS specified by " + smsSendRequest);
        SmsSendResponseDto response = doSendSms(smsSendRequest);
        if (response.isSuccessfullySent()) {
            log.info("SMS specified by " + smsSendRequest + " was successfully sent:\n" + response);
        } else {
            log.warn("SMS specified by " + smsSendRequest + " was NOT successfully sent:\n" + response);
        }
        return response;
    }

    /**
     * Sends a SMS specified by the request.
     *
     * @param smsSendRequest the request
     * @return response information of the implementation
     * @throws SmsException if sending of the message fails
     */
    protected abstract SmsSendResponseDto doSendSms(final SmsSendRequestDto smsSendRequest);

    /**
     * Returns the sender of the request. If no sender is specified the default
     * sender will be returned.
     *
     * @param smsSendRequestDto the sms request
     * @return the sender
     * @throws IllegalArgumentException if no sender is specified at all
     */
    protected String getSender(final SmsSendRequestDto smsSendRequestDto) {
        if (StringUtils.isNotBlank(smsSendRequestDto.getSender())) {
            return smsSendRequestDto.getSender();
        }
        Validate.notEmpty(defaultSender, "defaultSender must not be null or blank");
        return defaultSender;
    }

    /**
     * Returns the receiver of the request. If no receiver is specified the
     * default receiver will be returned.
     *
     * @param smsSendRequestDto the sms request
     * @return the receiver
     * @throws IllegalArgumentException if no receiver is specified at all
     */
    protected String getReceiver(final SmsSendRequestDto smsSendRequestDto) {
        if (StringUtils.isNotBlank(smsSendRequestDto.getReceiver())) {
            return smsSendRequestDto.getReceiver();
        }
        Validate.notEmpty(defaultReceiver, "defaultReceiver must not be null or blank");
        return defaultReceiver;
    }

    /**
     * Returns the message of the request. If no message is specified the
     * default message will be returned.
     *
     * @param smsSendRequestDto the sms request
     * @return the message
     * @throws IllegalArgumentException if no message is specified at all
     */
    protected String getMessage(final SmsSendRequestDto smsSendRequestDto) {
        if (StringUtils.isNotBlank(smsSendRequestDto.getMessage())) {
            return smsSendRequestDto.getMessage();
        }
        Validate.notEmpty(defaultMessage, "defaultMessage must not be null or blank");
        return defaultMessage;
    }

    /**
     * Returns the {@link Charset}.
     */
    protected Charset createCharset() {
        if (StringUtils.isNotBlank(charset)) {
            return Charset.forName(charset);
        }
        return DEFAULT_CHARSET;
    }

}
