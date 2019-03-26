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

package org.bremersee.sms.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import junit.framework.TestCase;
import org.bremersee.sms.ExtensionUtils;
import org.bremersee.sms.model.GoyyaSmsSendResponseDto;
import org.bremersee.sms.model.SmsSendRequestDto;
import org.bremersee.sms.model.SmsSendResponseDto;
import org.junit.Before;
import org.junit.Test;

/**
 * The model tests.
 *
 * @author Christian Bremer
 */
public class ModelTests {

  private JAXBContext jaxbContext;

  /**
   * Create jaxb context.
   *
   * @throws JAXBException the jaxb exception
   */
  @Before
  public void createJAXBContext() throws JAXBException {
    this.jaxbContext = JAXBContext
        .newInstance(org.bremersee.sms.model.ObjectFactory.class.getPackage().getName());
  }

  /**
   * Test xml sms send request dto.
   *
   * @throws Exception the exception
   */
  @Test
  public void testXmlSmsSendRequestDto() throws Exception {

    System.out.println("Testing XML SmsSendRequestDto ...");

    SmsSendRequestDto request = new SmsSendRequestDto(
        "bremersee",
        "0123456789",
        "Hello",
        new Date(System.currentTimeMillis() + 30000L));

    ByteArrayOutputStream out = new ByteArrayOutputStream();

    Marshaller m = jaxbContext.createMarshaller();
    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

    m.marshal(request, out);

    String xmlStr = new String(out.toByteArray(), StandardCharsets.UTF_8);

    System.out.println(xmlStr);

    SmsSendRequestDto readRequest = (SmsSendRequestDto) jaxbContext.createUnmarshaller()
        .unmarshal(new ByteArrayInputStream(xmlStr.getBytes(StandardCharsets.UTF_8)));

    m.marshal(readRequest, System.out);

    TestCase.assertEquals(request, readRequest);

    System.out.println("OK\n");
  }

  /**
   * Test json sms send request dto.
   *
   * @throws Exception the exception
   */
  @Test
  public void testJsonSmsSendRequestDto() throws Exception {

    System.out.println("Testing JSON SmsSendRequestDto ...");

    SmsSendRequestDto request = new SmsSendRequestDto(
        "bremersee",
        "0123456789",
        "Hello",
        new Date(System.currentTimeMillis() + 30000L));

    ObjectMapper om = new ObjectMapper();

    String jsonStr = om.writerWithDefaultPrettyPrinter().writeValueAsString(request);

    System.out.println(jsonStr);

    SmsSendRequestDto readRequest = om.readValue(jsonStr, SmsSendRequestDto.class);

    System.out.println(readRequest);

    String newJsonStr = om.writerWithDefaultPrettyPrinter().writeValueAsString(readRequest);
    System.out.println(newJsonStr);

    TestCase.assertEquals(jsonStr, newJsonStr);

    System.out.println("OK\n");
  }

  /**
   * Test xml sms send response dto.
   *
   * @throws Exception the exception
   */
  @Test
  public void testXmlSmsSendResponseDto() throws Exception {

    System.out.println("Testing XML SmsSendResponseDto ...");

    GoyyaSmsSendResponseDto goyyaResponse = new GoyyaSmsSendResponseDto("OK");

    SmsSendResponseDto response = new SmsSendResponseDto(
        new SmsSendRequestDto("bremersee", "0123456789", "Hello",
            new Date(System.currentTimeMillis() + 30000L)),
        goyyaResponse.isOk(),
        goyyaResponse);

    ByteArrayOutputStream out = new ByteArrayOutputStream();

    Marshaller m = jaxbContext.createMarshaller();
    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

    m.marshal(response, out);

    String xmlStr = new String(out.toByteArray(), StandardCharsets.UTF_8);

    System.out.println(xmlStr);

    SmsSendResponseDto readResponse = (SmsSendResponseDto) jaxbContext.createUnmarshaller()
        .unmarshal(new ByteArrayInputStream(xmlStr.getBytes(StandardCharsets.UTF_8)));

    m.marshal(readResponse, System.out);

    GoyyaSmsSendResponseDto tmp = ExtensionUtils
        .transform(readResponse.getExtension(), GoyyaSmsSendResponseDto.class, jaxbContext,
            new ObjectMapper());
    readResponse.setExtension(tmp);

    TestCase.assertEquals(response, readResponse);

    System.out.println("OK\n");
  }

  /**
   * Test json sms send response dto.
   *
   * @throws Exception the exception
   */
  @Test
  public void testJsonSmsSendResponseDto() throws Exception {

    System.out.println("Testing JSON SmsSendResponseDto ...");

    GoyyaSmsSendResponseDto goyyaResponse = new GoyyaSmsSendResponseDto("OK");

    SmsSendResponseDto response = new SmsSendResponseDto(
        new SmsSendRequestDto("bremersee", "0123456789", "Hello",
            new Date(System.currentTimeMillis() + 30000L)),
        goyyaResponse.isOk(),
        goyyaResponse);

    ObjectMapper om = new ObjectMapper();

    String jsonStr = om.writerWithDefaultPrettyPrinter().writeValueAsString(response);

    System.out.println(jsonStr);

    SmsSendResponseDto readResponse = om.readValue(jsonStr, SmsSendResponseDto.class);

    System.out.println(readResponse);

    String newJsonStr = om.writerWithDefaultPrettyPrinter().writeValueAsString(readResponse);
    System.out.println(newJsonStr);

    GoyyaSmsSendResponseDto tmp = ExtensionUtils
        .transform(readResponse.getExtension(), GoyyaSmsSendResponseDto.class, null, om);
    readResponse.setExtension(tmp);

    TestCase.assertEquals(response, readResponse);
    //TestCase.assertEquals(jsonStr, newJsonStr);

    System.out.println("OK\n");
  }

}
