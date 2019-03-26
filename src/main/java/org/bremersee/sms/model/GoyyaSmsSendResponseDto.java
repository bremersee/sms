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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Objects;
import java.util.regex.Pattern;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.bremersee.sms.GoyyaSmsService;

/**
 * A SMS service response extension from the {@link GoyyaSmsService}.
 *
 * @author Christian Bremer
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "goyyaSmsSendResponse")
@XmlType(name = "goyyaSmsSendResponseType", propOrder = {
    "response",
    "id",
    "count",
    "responseParsingException"
})

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude
@JsonAutoDetect(fieldVisibility = Visibility.NONE,
    getterVisibility = Visibility.PROTECTED_AND_PUBLIC,
    creatorVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.PROTECTED_AND_PUBLIC,
    setterVisibility = Visibility.PROTECTED_AND_PUBLIC
)
@JsonPropertyOrder(value = {
    "response",
    "id",
    "count",
    "responseParsingException"
})
@SuppressWarnings({"WeakerAccess", "unused"})
public class GoyyaSmsSendResponseDto implements Serializable {

  private static final long serialVersionUID = -5269124339502557889L;

  private static final String SUCCESS_RESPONSE = "OK";

  private String response;

  private String id;

  private Integer count;

  private ResponseParsingExceptionDto responseParsingException = null;

  /**
   * Default constructor.
   */
  public GoyyaSmsSendResponseDto() {
    super();
  }

  /**
   * Constructs a SMS service response from the real response of the Goyya SMS service.
   *
   * @param response the real response from the Goyya SMS service
   */
  public GoyyaSmsSendResponseDto(String response) {
    this.response = response;
    try {
      if (response != null && response.startsWith(SUCCESS_RESPONSE)) {

        int i1 = response.indexOf('(');
        int i2 = response.indexOf(')');

        if (0 < i1 && i1 < i2) {
          String tmp = response.substring(i1 + 1, i2);
          String[] a = tmp.split(Pattern.quote(","));

          if (a.length > 0) {
            this.id = a[0].trim();
          }

          if (a.length > 1) {
            a[1] = a[1].trim();
            int i3 = a[1].indexOf(' ');
            if (i3 > 0) {
              this.count = Integer.parseInt(a[1].substring(0, i3));
            }
          }
        }
      }
    } catch (Exception e) {
      this.responseParsingException = new ResponseParsingExceptionDto(e);
    }
  }

  /**
   * Returns {@code true} if everything seems to be good otherwise {@code false}.
   *
   * @return {@code true} if everything seems to be good otherwise {@code false}
   */
  @XmlTransient
  @JsonIgnore
  public boolean isOk() {
    return response != null && response.startsWith(SUCCESS_RESPONSE);
  }

  /**
   * Returns the real response from the Goyya SMS service.
   *
   * @return the response
   */
  @XmlElement(name = "response")
  @JsonProperty(value = "response")
  public String getResponse() {
    return response;
  }

  /**
   * Sets the real response from the Goyya SMS service.
   *
   * @param response the response
   */
  @JsonProperty(value = "response")
  protected void setResponse(String response) {
    this.response = response;
  }

  /**
   * Returns the id from the Goyya SMS service.
   *
   * @return the id
   */
  @XmlElement(name = "ID")
  @JsonProperty(value = "id")
  public String getId() {
    return id;
  }

  /**
   * Sets the id from the Goyya SMS service.
   *
   * @param iD the d
   */
  @JsonProperty(value = "id")
  protected void setId(String iD) {
    id = iD;
  }

  /**
   * Returns the size of sent messages.
   *
   * @return the count
   */
  @XmlElement(name = "count")
  @JsonProperty(value = "count")
  public Integer getCount() {
    return count;
  }

  /**
   * Sets the size of sent messages.
   *
   * @param count the count
   */
  @JsonProperty(value = "count")
  protected void setCount(Integer count) {
    this.count = count;
  }

  /**
   * Returns the exception that occurred by parsing the real response from the Goyya service or
   * {@code null} if there is no such exception.
   *
   * @return the response parsing exception
   */
  @XmlElement(name = "responseParsingException")
  @JsonProperty(value = "responseParsingException")
  public ResponseParsingExceptionDto getResponseParsingException() {
    return responseParsingException;
  }

  /**
   * Sets the exception that occurred by parsing the real response from the Goyya service or {@code
   * null} if there is no such exception.
   *
   * @param responseParsingException the response parsing exception
   */
  @JsonProperty(value = "responseParsingException")
  protected void setResponseParsingException(ResponseParsingExceptionDto responseParsingException) {
    this.responseParsingException = responseParsingException;
  }

  @Override
  public String toString() {
    return "GoyyaSmsSendResponseDto {"
        + "response='" + response + '\''
        + ", id='" + id + '\''
        + ", count=" + count
        + ", responseParsingException=" + responseParsingException
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GoyyaSmsSendResponseDto)) {
      return false;
    }
    GoyyaSmsSendResponseDto that = (GoyyaSmsSendResponseDto) o;
    return Objects.equals(response, that.response)
        && Objects.equals(id, that.id)
        && Objects.equals(count, that.count)
        && Objects.equals(responseParsingException, that.responseParsingException);
  }

  @Override
  public int hashCode() {
    return Objects.hash(response, id, count, responseParsingException);
  }

  /**
   * A data transfer object for an exception that might occur by parsing the response from the Goyya
   * SMS service.
   *
   * @author Christian Bremer
   */
  @XmlAccessorType(XmlAccessType.PROPERTY)
  @XmlType(name = "responseParsingExceptionType", propOrder = {
      "message",
      "stackTrace"
  })
  @JsonIgnoreProperties(ignoreUnknown = true)
  @JsonInclude
  @JsonAutoDetect(fieldVisibility = Visibility.NONE,
      getterVisibility = Visibility.PROTECTED_AND_PUBLIC,
      creatorVisibility = Visibility.NONE,
      isGetterVisibility = Visibility.PROTECTED_AND_PUBLIC,
      setterVisibility = Visibility.PROTECTED_AND_PUBLIC
  )
  @JsonPropertyOrder(value = {
      "message",
      "stackTrace"
  })
  public static class ResponseParsingExceptionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;

    private String stackTrace;

    /**
     * Default constructor.
     */
    public ResponseParsingExceptionDto() {
      super();
    }

    /**
     * Creates a response parsing exception with the specified exception.
     *
     * @param exception the exception
     */
    public ResponseParsingExceptionDto(Exception exception) {
      if (exception != null) {
        this.message = exception.getMessage();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        pw.flush();
        pw.close();
        this.stackTrace = sw.toString();
      }
    }

    /**
     * Returns the message of the exception (can be {@code null}).
     *
     * @return the message
     */
    @XmlElement(name = "message")
    @JsonProperty(value = "message")
    public String getMessage() {
      return message;
    }

    /**
     * Sets the message of the exception (can be {@code null}).
     *
     * @param message the message
     */
    @JsonProperty(value = "message")
    protected void setMessage(String message) {
      this.message = message;
    }

    /**
     * Returns the steck trace of the exception (can be {@code null}).
     *
     * @return the stack trace
     */
    @XmlElement(name = "stackTrace")
    @JsonProperty(value = "stackTrace")
    public String getStackTrace() {
      return stackTrace;
    }

    /**
     * Sets the steck trace of the exception (can be {@code null}).
     *
     * @param stackTrace the stack trace
     */
    @JsonProperty(value = "stackTrace")
    protected void setStackTrace(String stackTrace) {
      this.stackTrace = stackTrace;
    }

    @Override
    public String toString() {
      return "ResponseParsingExceptionDto {"
          + "message='" + message + '\''
          + ", stackTrace='" + stackTrace + '\''
          + '}';
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof ResponseParsingExceptionDto)) {
        return false;
      }
      ResponseParsingExceptionDto that = (ResponseParsingExceptionDto) o;
      return Objects.equals(message, that.message) &&
          Objects.equals(stackTrace, that.stackTrace);
    }

    @Override
    public int hashCode() {
      return Objects.hash(message, stackTrace);
    }
  }

}
