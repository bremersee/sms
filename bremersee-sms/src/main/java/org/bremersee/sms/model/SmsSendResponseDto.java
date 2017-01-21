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

/**
 * @author Christian Bremer
 */
//@formatter:off
@SuppressWarnings({"WeakerAccess", "unused"})
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

    private Object extension; // NOSONAR

    /**
     * Default constructor.
     */
    public SmsSendResponseDto() {
        super();
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
    @SuppressWarnings("RedundantIfStatement")
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

    @XmlElement(name = "request")
    @JsonProperty(value = "request")
    public SmsSendRequestDto getRequest() {
        return request;
    }

    @JsonProperty(value = "request")
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
