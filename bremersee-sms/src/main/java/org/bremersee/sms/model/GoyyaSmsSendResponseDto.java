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

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.bremersee.sms.GoyyaSmsService;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * <p>
 * A SMS service response extension from the {@link GoyyaSmsService}.
 * </p>
 * 
 * @author Christian Bremer
 */
//@formatter:off
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "goyyaSmsSendResponse")
@XmlType(name = "goyyaSmsSendResponseType", propOrder = { 
        "response", 
        "ID", 
        "count", 
        "reponseParsingException" 
})

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.ALWAYS)
@JsonAutoDetect(fieldVisibility = Visibility.NONE, 
    getterVisibility = Visibility.PROTECTED_AND_PUBLIC, 
    creatorVisibility = Visibility.NONE, 
    isGetterVisibility = Visibility.PROTECTED_AND_PUBLIC, 
    setterVisibility = Visibility.PROTECTED_AND_PUBLIC
)
@JsonPropertyOrder(value = { 
        "response", 
        "ID", 
        "count", 
        "reponseParsingException" 
})
//@formatter:on
public class GoyyaSmsSendResponseDto implements Serializable {

    private static final long serialVersionUID = -5269124339502557889L;

    private static final String SUCCESS_RESPONSE = "OK";

    private String response = null;

    private String ID = null;

    private Integer count = null;

    private ResponseParsingExceptionDto reponseParsingException = null;

    /**
     * Default constructor.
     */
    public GoyyaSmsSendResponseDto() {
    }

    /**
     * Constructs a SMS service response from the real response of the Goyya SMS
     * service.
     * 
     * @param response
     *            the real response from the Goyya SMS service
     */
    public GoyyaSmsSendResponseDto(String response) {
        this.response = response;
        try {
            if (response != null && response.startsWith(SUCCESS_RESPONSE)) {

                int i1 = response.indexOf('(');
                int i2 = response.indexOf(')');

                if (0 < i1 && i1 < i2) {
                    String tmp = response.substring(i1 + 1, i2);
                    String[] a = tmp.split(",");

                    if (a.length > 0) {
                        this.ID = a[0].trim();
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
            this.reponseParsingException = new ResponseParsingExceptionDto(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("%s [response = %s, ID = %s, count = %s, reponseParsingException = %s]",
                getClass().getName(), response, ID, count,
                (reponseParsingException == null ? "null" : reponseParsingException.getMessage()));
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ID == null) ? 0 : ID.hashCode());
        result = prime * result + ((count == null) ? 0 : count.hashCode());
        result = prime * result + ((reponseParsingException == null) ? 0 : reponseParsingException.hashCode());
        result = prime * result + ((response == null) ? 0 : response.hashCode());
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
        GoyyaSmsSendResponseDto other = (GoyyaSmsSendResponseDto) obj;
        if (ID == null) {
            if (other.ID != null)
                return false;
        } else if (!ID.equals(other.ID))
            return false;
        if (count == null) {
            if (other.count != null)
                return false;
        } else if (!count.equals(other.count))
            return false;
        if (reponseParsingException == null) {
            if (other.reponseParsingException != null)
                return false;
        } else if (!reponseParsingException.equals(other.reponseParsingException))
            return false;
        if (response == null) {
            if (other.response != null)
                return false;
        } else if (!response.equals(other.response))
            return false;
        return true;
    }

    /**
     * Returns {@code true} if everything seems to be good otherwise
     * {@code false}.
     */
    @XmlTransient
    @JsonIgnore
    public boolean isOk() {
        return response != null && response.startsWith(SUCCESS_RESPONSE);
    }

    /**
     * Returns the real response from the Goyya SMS service.
     */
    @XmlElement(name = "response", required = false)
    @JsonProperty(value = "response", required = false)
    public String getResponse() {
        return response;
    }

    /**
     * Sets the real response from the Goyya SMS service.
     */
    @JsonProperty(value = "response", required = false)
    protected void setResponse(String response) {
        this.response = response;
    }

    /**
     * Returns the ID from the Goyya SMS service.
     */
    @XmlElement(name = "ID", required = false)
    @JsonProperty(value = "ID", required = false)
    public String getID() {
        return ID;
    }

    /**
     * Sets the ID from the Goyya SMS service.
     */
    @JsonProperty(value = "ID", required = false)
    protected void setID(String iD) {
        ID = iD;
    }

    /**
     * Returns the size of sent messages.
     */
    @XmlElement(name = "count", required = false)
    @JsonProperty(value = "count", required = false)
    public Integer getCount() {
        return count;
    }

    /**
     * Sets the size of sent messages.
     */
    @JsonProperty(value = "count", required = false)
    protected void setCount(Integer count) {
        this.count = count;
    }

    /**
     * Returns the exception that occurred by parsing the real response from the
     * Goyya service or {@code null} if there is no such exception.
     */
    @XmlElement(name = "reponseParsingException", required = false)
    @JsonProperty(value = "reponseParsingException", required = false)
    public ResponseParsingExceptionDto getReponseParsingException() {
        return reponseParsingException;
    }

    /**
     * Sets the exception that occurred by parsing the real response from the
     * Goyya service or {@code null} if there is no such exception.
     */
    @JsonProperty(value = "reponseParsingException", required = false)
    protected void setReponseParsingException(ResponseParsingExceptionDto reponseParsingException) {
        this.reponseParsingException = reponseParsingException;
    }

    /**
     * <p>
     * A data transfer object for an exception that might occur by parsing the
     * response from the Goyya SMS service.
     * </p>
     * 
     * @author Christian Bremer
     */
    //@formatter:off
    @XmlAccessorType(XmlAccessType.PROPERTY)
    @XmlType(name = "responseParsingExceptionType", propOrder = { 
            "message", 
            "stackTrace" 
    })
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(Include.ALWAYS)
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
    //@formatter:on
    public static class ResponseParsingExceptionDto implements Serializable {

        private static final long serialVersionUID = 1L;

        private String message;

        private String stackTrace;

        /**
         * Default constructor.
         */
        public ResponseParsingExceptionDto() {
        }

        /**
         * Creates a response parsing exception with the specified exception.
         * 
         * @param exception
         *            the exception
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

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ResponseParsingExceptionDto [message=" + message + ", stackTrace=" + stackTrace + "]";
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((message == null) ? 0 : message.hashCode());
            result = prime * result + ((stackTrace == null) ? 0 : stackTrace.hashCode());
            return result;
        }

        /*
         * (non-Javadoc)
         * 
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
            ResponseParsingExceptionDto other = (ResponseParsingExceptionDto) obj;
            if (message == null) {
                if (other.message != null)
                    return false;
            } else if (!message.equals(other.message))
                return false;
            if (stackTrace == null) {
                if (other.stackTrace != null)
                    return false;
            } else if (!stackTrace.equals(other.stackTrace))
                return false;
            return true;
        }

        /**
         * Returns the message of the exception (can be {@code null}).
         */
        @XmlElement(name = "message", required = false)
        @JsonProperty(value = "message", required = false)
        public String getMessage() {
            return message;
        }

        /**
         * Sets the message of the exception (can be {@code null}).
         */
        @JsonProperty(value = "message", required = false)
        protected void setMessage(String message) {
            this.message = message;
        }

        /**
         * Returns the steck trace of the exception (can be {@code null}).
         */
        @XmlElement(name = "stackTrace", required = false)
        @JsonProperty(value = "stackTrace", required = false)
        public String getStackTrace() {
            return stackTrace;
        }

        /**
         * Sets the steck trace of the exception (can be {@code null}).
         */
        @JsonProperty(value = "stackTrace", required = false)
        protected void setStackTrace(String stackTrace) {
            this.stackTrace = stackTrace;
        }
    }

}
