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
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The type Sms send response dto.
 *
 * @author Christian Bremer
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "smsSendResponse")
@XmlType(name = "smsSendResponseType", propOrder = {
    "request",
    "successfullySent",
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
    "request",
    "successfullySent",
    "extension"
})
@SuppressWarnings({"WeakerAccess", "unused"})
public class SmsSendResponseDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private SmsSendRequestDto request;

  private boolean successfullySent;

  private Object extension;

  /**
   * Default constructor.
   */
  public SmsSendResponseDto() {
    super();
  }

  /**
   * Instantiates a new Sms send response dto.
   *
   * @param successfullySent the successfully sent
   */
  public SmsSendResponseDto(boolean successfullySent) {
    this.successfullySent = successfullySent;
  }

  /**
   * Instantiates a new Sms send response dto.
   *
   * @param request          the request
   * @param successfullySent the successfully sent
   */
  public SmsSendResponseDto(SmsSendRequestDto request, boolean successfullySent) {
    this.request = request;
    this.successfullySent = successfullySent;
  }

  /**
   * Instantiates a new Sms send response dto.
   *
   * @param request          the request
   * @param successfullySent the successfully sent
   * @param extension        the extension
   */
  public SmsSendResponseDto(SmsSendRequestDto request, boolean successfullySent, Object extension) {
    this.request = request;
    this.successfullySent = successfullySent;
    this.extension = extension;
  }

  /**
   * Gets request.
   *
   * @return the request
   */
  @XmlElement(name = "request")
  @JsonProperty(value = "request")
  public SmsSendRequestDto getRequest() {
    return request;
  }

  /**
   * Sets request.
   *
   * @param request the request
   */
  @JsonProperty(value = "request")
  public void setRequest(SmsSendRequestDto request) {
    this.request = request;
  }

  /**
   * Is successfully sent boolean.
   *
   * @return the boolean
   */
  @XmlElement(name = "successfullySent", required = true)
  @JsonProperty(value = "successfullySent", required = true)
  public boolean isSuccessfullySent() {
    return successfullySent;
  }

  /**
   * Sets successfully sent.
   *
   * @param successfullySent the successfully sent
   */
  @JsonProperty(value = "successfullySent", required = true)
  public void setSuccessfullySent(boolean successfullySent) {
    this.successfullySent = successfullySent;
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
    return "SmsSendResponseDto {"
        + "request=" + request
        + ", successfullySent=" + successfullySent
        + ", extension=" + extension
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SmsSendResponseDto)) {
      return false;
    }
    SmsSendResponseDto that = (SmsSendResponseDto) o;
    return successfullySent == that.successfullySent
        && Objects.equals(request, that.request)
        && Objects.equals(extension, that.extension);
  }

  @Override
  public int hashCode() {
    return Objects.hash(request, successfullySent, extension);
  }
}
