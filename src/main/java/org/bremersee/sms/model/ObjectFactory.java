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

import javax.xml.bind.annotation.XmlRegistry;

/**
 * The xml object factory.
 *
 * @author Christian Bremer
 */
@XmlRegistry
@SuppressWarnings("unused")
public class ObjectFactory {

  /**
   * Create goyya sms send response dto goyya sms send response dto.
   *
   * @return the goyya sms send response dto
   */
  public GoyyaSmsSendResponseDto createGoyyaSmsSendResponseDto() {
    return new GoyyaSmsSendResponseDto();
  }

  /**
   * Create sms send request dto sms send request dto.
   *
   * @return the sms send request dto
   */
  public SmsSendRequestDto createSmsSendRequestDto() {
    return new SmsSendRequestDto();
  }

  /**
   * Create sms send response dto sms send response dto.
   *
   * @return the sms send response dto
   */
  public SmsSendResponseDto createSmsSendResponseDto() {
    return new SmsSendResponseDto();
  }

}
