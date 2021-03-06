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

package org.bremersee.sms;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import org.apache.commons.lang3.Validate;
import org.w3c.dom.Node;

/**
 * Utility methods to transform an extension that was serialized by a {@link JAXBContext} or an
 * {@link ObjectMapper} into a POJO.
 *
 * @author Christian Bremer
 */
@SuppressWarnings("WeakerAccess")
public abstract class ExtensionUtils {

  private static final ObjectMapper DEFAULT_OBJECT_MAPPER = new ObjectMapper();

  private static final Map<java.lang.reflect.AnnotatedElement, JAXBContext> JAXB_CONTEXTS
      = new ConcurrentHashMap<>();

  /**
   * Never construct.
   */
  private ExtensionUtils() {
    super();
  }

  private static JAXBContext getJaxbContext(final Class<?> valueType) throws JAXBException {
    JAXBContext jaxbContext = JAXB_CONTEXTS.get(valueType.getPackage());
    if (jaxbContext == null) {
      jaxbContext = JAXB_CONTEXTS.get(valueType);
    }
    if (jaxbContext == null) {
      try {
        jaxbContext = JAXBContext.newInstance(valueType.getPackage().getName());
      } catch (JAXBException e) {
        jaxbContext = JAXBContext.newInstance(valueType);
        JAXB_CONTEXTS.put(valueType, jaxbContext);
      }
    }
    return jaxbContext;
  }

  /**
   * Transforms a XML node or a JSON map into an object.
   *
   * @param <T>              the type parameter
   * @param xmlNodeOrJsonMap the XML node or JSON map
   * @param valueType        the class of the target object
   * @param jaxbContext      the {@link JAXBContext} (can be null)
   * @param objectMapper     the JSON object mapper (optional)
   * @return the target object
   * @throws Exception if transformation fails
   */
  @SuppressWarnings("unchecked")
  public static <T> T transform(final Object xmlNodeOrJsonMap, final Class<T> valueType,
      final JAXBContext jaxbContext, final ObjectMapper objectMapper)
      throws Exception {

    if (xmlNodeOrJsonMap == null) {
      return null;
    }
    Validate.notNull(valueType, "valueType must not be null");
    if (valueType.isAssignableFrom(xmlNodeOrJsonMap.getClass())) {
      return valueType.cast(xmlNodeOrJsonMap);
    }
    if (xmlNodeOrJsonMap instanceof Node) {
      return xmlNodeToObject((Node) xmlNodeOrJsonMap, valueType, jaxbContext);
    }
    if (xmlNodeOrJsonMap instanceof Map) {
      return jsonMapToObject((Map<String, Object>) xmlNodeOrJsonMap, valueType, objectMapper);
    }
    throw new IllegalArgumentException(
        "xmlNodeOrJsonMap must be of type " + valueType + ", " + Node.class.getName()
            + " or of type " + Map.class.getName());
  }

  /**
   * Transforms a XML node into an object.
   *
   * @param <T>         the type parameter
   * @param node        the XML node
   * @param valueType   the class of the target object
   * @param jaxbContext the {@link JAXBContext} (can be null)
   * @return the target object
   * @throws JAXBException if transformation fails
   */
  public static <T> T xmlNodeToObject(final Node node, final Class<T> valueType,
      final JAXBContext jaxbContext)
      throws JAXBException {

    if (node == null) {
      return null;
    }
    Validate.notNull(valueType, "valueType must not be null");
    if (jaxbContext == null) {
      return valueType.cast(getJaxbContext(valueType).createUnmarshaller().unmarshal(node));
    }
    return valueType.cast(jaxbContext.createUnmarshaller().unmarshal(node));
  }

  /**
   * Transforms a JSON map into an object.
   *
   * @param <T>          the type parameter
   * @param map          the JSON map
   * @param valueType    the class of the target object
   * @param objectMapper the JSON object mapper (can be null)
   * @return the target object
   * @throws IOException if transformation fails
   */
  public static <T> T jsonMapToObject(final Map<String, Object> map, final Class<T> valueType,
      final ObjectMapper objectMapper) throws IOException {
    if (map == null) {
      return null;
    }
    Validate.notNull(valueType, "valueType must not be null");
    if (objectMapper == null) {
      return DEFAULT_OBJECT_MAPPER
          .readValue(DEFAULT_OBJECT_MAPPER.writeValueAsBytes(map), valueType);
    }
    return objectMapper.readValue(objectMapper.writeValueAsBytes(map), valueType);
  }

}
