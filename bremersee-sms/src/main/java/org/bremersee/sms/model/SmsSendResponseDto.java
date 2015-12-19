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

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Christian Bremer
 */
//@formatter:off
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "smsSendResponse")
@XmlType(name = "smsSendResponseType", propOrder = { 
        "request",
        "successfullySent", 
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
    "request",
    "successfullySent", 
    "extension" 
})
public class SmsSendResponseDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private SmsSendRequestDto request;
    
    private boolean successfullySent;
    
    private Object extension;

    /**
     * Default constructor.
     */
    public SmsSendResponseDto() {
    }

    public SmsSendResponseDto(boolean successfullySent) {
        this.successfullySent = successfullySent;
    }

    public SmsSendResponseDto(SmsSendRequestDto request, boolean successfullySent) {
        this.request = request;
        this.successfullySent = successfullySent;
    }

    public SmsSendResponseDto(SmsSendRequestDto request, boolean successfullySent, Object extension) {
        this.request = request;
        this.successfullySent = successfullySent;
        this.extension = extension;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SmsSendResponseDto [request=" + request + ", successfullySent=" + successfullySent + ", extension="
                + extension + "]";
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((extension == null) ? 0 : extension.hashCode());
        result = prime * result + ((request == null) ? 0 : request.hashCode());
        result = prime * result + (successfullySent ? 1231 : 1237);
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SmsSendResponseDto other = (SmsSendResponseDto) obj;
        if (extension == null) {
            if (other.extension != null)
                return false;
        } else if (!extension.equals(other.extension))
            return false;
        if (request == null) {
            if (other.request != null)
                return false;
        } else if (!request.equals(other.request))
            return false;
        if (successfullySent != other.successfullySent)
            return false;
        return true;
    }

    @XmlElement(name = "request", required = false)
    @JsonProperty(value = "request", required = false)
    public SmsSendRequestDto getRequest() {
        return request;
    }

    @JsonProperty(value = "request", required = false)
    public void setRequest(SmsSendRequestDto request) {
        this.request = request;
    }

    @XmlElement(name = "successfullySent", required = true)
    @JsonProperty(value = "successfullySent", required = true)
    public boolean isSuccessfullySent() {
        return successfullySent;
    }

    @JsonProperty(value = "successfullySent", required = true)
    public void setSuccessfullySent(boolean successfullySent) {
        this.successfullySent = successfullySent;
    }

    @XmlAnyElement(lax = false)
    @JsonProperty(value = "extension", required = false)
    public Object getExtension() {
        return extension;
    }

    @JsonProperty(value = "extension", required = false)
    public void setExtension(Object extension) {
        this.extension = extension;
    }

}
