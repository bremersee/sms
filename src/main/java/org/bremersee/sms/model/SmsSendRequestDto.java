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

package org.bremersee.sms.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The sms send request dto.
 *
 * @author Christian Bremer
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "smsSendRequest")
@XmlType(name = "smsSendRequestType", propOrder = {
    "requestId",
    "sender",
    "receiver",
    "message",
    "sendTime",
    "extension"
})

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude
@JsonAutoDetect(fieldVisibility = Visibility.NONE,
    getterVisibility = Visibility.PROTECTED_AND_PUBLIC,
    creatorVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.PROTECTED_AND_PUBLIC,
    setterVisibility = Visibility.PROTECTED_AND_PUBLIC)
@JsonPropertyOrder(value = {
    "requestId",
    "sender",
    "receiver",
    "message",
    "sendTime",
    "extension"
})
@SuppressWarnings({"WeakerAccess", "unused"})
public class SmsSendRequestDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private String requestId = UUID.randomUUID().toString();

  private String sender;

  private String receiver;

  private String message;

  private Date sendTime;

  private Object extension;

  /**
   * Instantiates a new sms send request dto.
   */
  public SmsSendRequestDto() {
    super();
  }

  /**
   * Instantiates a new sms send request dto.
   *
   * @param receiver the receiver
   * @param message  the message
   */
  public SmsSendRequestDto(String receiver, String message) {
    this(null, receiver, message, null, null);
  }

  /**
   * Instantiates a new sms send request dto.
   *
   * @param receiver the receiver
   * @param message  the message
   * @param sendTime the send time
   */
  public SmsSendRequestDto(String receiver, String message, Date sendTime) {
    this(null, receiver, message, sendTime, null);
  }

  /**
   * Instantiates a new sms send request dto.
   *
   * @param sender   the sender
   * @param receiver the receiver
   * @param message  the message
   */
  public SmsSendRequestDto(String sender, String receiver, String message) {
    this(sender, receiver, message, null, null);
  }

  /**
   * Instantiates a new sms send request dto.
   *
   * @param sender   the sender
   * @param receiver the receiver
   * @param message  the message
   * @param sendTime the send time
   */
  public SmsSendRequestDto(String sender, String receiver, String message, Date sendTime) {
    this(sender, receiver, message, sendTime, null);
  }

  /**
   * Instantiates a new sms send request dto.
   *
   * @param sender    the sender
   * @param receiver  the receiver
   * @param message   the message
   * @param sendTime  the send time
   * @param extension the extension
   */
  public SmsSendRequestDto(String sender, String receiver, String message, Date sendTime,
      Object extension) {
    this.sender = sender;
    this.receiver = receiver;
    this.message = message;
    this.sendTime = sendTime;
    this.extension = extension;
  }

  /**
   * Gets request id.
   *
   * @return the request id
   */
  @XmlElement(name = "requestId")
  @JsonProperty(value = "requestId")
  public String getRequestId() {
    return requestId;
  }

  /**
   * Sets request id.
   *
   * @param requestId the request id
   */
  @JsonProperty(value = "requestId")
  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  /**
   * Gets sender.
   *
   * @return the sender
   */
  @XmlElement(name = "sender")
  @JsonProperty(value = "sender")
  public String getSender() {
    return sender;
  }

  /**
   * Sets sender.
   *
   * @param sender the sender
   */
  @JsonProperty(value = "sender")
  public void setSender(String sender) {
    this.sender = sender;
  }

  /**
   * Gets receiver.
   *
   * @return the receiver
   */
  @XmlElement(name = "receiver")
  @JsonProperty(value = "receiver")
  public String getReceiver() {
    return receiver;
  }

  /**
   * Sets receiver.
   *
   * @param receiver the receiver
   */
  @JsonProperty(value = "receiver")
  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  /**
   * Gets message.
   *
   * @return the message
   */
  @XmlElement(name = "message")
  @JsonProperty(value = "message")
  public String getMessage() {
    return message;
  }

  /**
   * Sets message.
   *
   * @param message the message
   */
  @JsonProperty(value = "message")
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Gets send time.
   *
   * @return the send time
   */
  @XmlElement(name = "sendTime")
  @JsonProperty(value = "sendTime")
  public Date getSendTime() {
    return sendTime;
  }

  /**
   * Sets send time.
   *
   * @param sendTime the send time
   */
  @JsonProperty(value = "sendTime")
  public void setSendTime(Date sendTime) {
    this.sendTime = sendTime;
  }

  /**
   * Gets extension.
   *
   * @return the extension
   */
  @XmlAnyElement
  @JsonProperty(value = "extension")
  public Object getExtension() {
    return extension;
  }

  /**
   * Sets extension.
   *
   * @param extension the extension
   */
  @JsonProperty(value = "extension")
  public void setExtension(Object extension) {
    this.extension = extension;
  }

  @Override
  public String toString() {
    return "SmsSendRequestDto {"
        + "requestId='" + requestId + '\''
        + ", sender='" + sender + '\''
        + ", receiver='" + receiver + '\''
        + ", message='" + message + '\''
        + ", sendTime=" + sendTime
        + ", extension=" + extension
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SmsSendRequestDto)) {
      return false;
    }
    SmsSendRequestDto that = (SmsSendRequestDto) o;
    return Objects.equals(requestId, that.requestId)
        && Objects.equals(sender, that.sender)
        && Objects.equals(receiver, that.receiver)
        && Objects.equals(message, that.message)
        && Objects.equals(sendTime, that.sendTime)
        && Objects.equals(extension, that.extension);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestId, sender, receiver, message, sendTime, extension);
  }
}
