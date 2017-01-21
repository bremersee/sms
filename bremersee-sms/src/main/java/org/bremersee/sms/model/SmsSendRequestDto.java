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

package org.bremersee.sms.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author Christian Bremer
 */
//@formatter:off
@SuppressWarnings({"WeakerAccess", "unused"})
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
@JsonInclude(Include.ALWAYS)
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
public class SmsSendRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String requestId = UUID.randomUUID().toString();

    private String sender;

    private String receiver;

    private String message;

    private Date sendTime;

    private Object extension; // NOSONAR

    /**
     * Default constructor.
     */
    public SmsSendRequestDto() {
        super();
    }

    public SmsSendRequestDto(String receiver, String message) {
        this(null, receiver, message, null, null);
    }

    public SmsSendRequestDto(String receiver, String message, Date sendTime) {
        this(null, receiver, message, sendTime, null);
    }

    public SmsSendRequestDto(String sender, String receiver, String message) {
        this(sender, receiver, message, null, null);
    }

    public SmsSendRequestDto(String sender, String receiver, String message, Date sendTime) {
        this(sender, receiver, message, sendTime, null);
    }

    public SmsSendRequestDto(String sender, String receiver, String message, Date sendTime, Object extension) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.sendTime = sendTime;
        this.extension = extension;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SmsSendRequestDto [requestId=" + requestId + ", sender=" + sender + ", receiver=" + receiver
                + ", message=" + message + ", sendTime=" + sendTime + ", extension=" + extension + "]";
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((extension == null) ? 0 : extension.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((receiver == null) ? 0 : receiver.hashCode());
        result = prime * result + ((requestId == null) ? 0 : requestId.hashCode());
        result = prime * result + ((sendTime == null) ? 0 : sendTime.hashCode());
        result = prime * result + ((sender == null) ? 0 : sender.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) { // NOSONAR
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SmsSendRequestDto other = (SmsSendRequestDto) obj;
        if (extension == null) {
            if (other.extension != null)
                return false;
        } else if (!extension.equals(other.extension))
            return false;
        if (message == null) {
            if (other.message != null)
                return false;
        } else if (!message.equals(other.message))
            return false;
        if (receiver == null) {
            if (other.receiver != null)
                return false;
        } else if (!receiver.equals(other.receiver))
            return false;
        if (requestId == null) {
            if (other.requestId != null)
                return false;
        } else if (!requestId.equals(other.requestId))
            return false;
        if (sendTime == null) {
            if (other.sendTime != null)
                return false;
        } else if (!sendTime.equals(other.sendTime))
            return false;
        if (sender == null) {
            if (other.sender != null)
                return false;
        } else if (!sender.equals(other.sender))
            return false;
        return true;
    }

    @XmlElement(name = "requestId")
    @JsonProperty(value = "requestId")
    public String getRequestId() {
        return requestId;
    }

    @JsonProperty(value = "requestId")
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @XmlElement(name = "sender")
    @JsonProperty(value = "sender")
    public String getSender() {
        return sender;
    }

    @JsonProperty(value = "sender")
    public void setSender(String sender) {
        this.sender = sender;
    }

    @XmlElement(name = "receiver")
    @JsonProperty(value = "receiver")
    public String getReceiver() {
        return receiver;
    }

    @JsonProperty(value = "receiver")
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @XmlElement(name = "message")
    @JsonProperty(value = "message")
    public String getMessage() {
        return message;
    }

    @JsonProperty(value = "message")
    public void setMessage(String message) {
        this.message = message;
    }

    @XmlElement(name = "sendTime")
    @JsonProperty(value = "sendTime")
    public Date getSendTime() {
        return sendTime;
    }

    @JsonProperty(value = "sendTime")
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @XmlAnyElement
    @JsonProperty(value = "extension")
    public Object getExtension() {
        return extension;
    }

    @JsonProperty(value = "extension")
    public void setExtension(Object extension) {
        this.extension = extension;
    }

}
