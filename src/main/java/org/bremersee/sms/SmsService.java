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

import java.util.Date;
import org.bremersee.sms.model.SmsSendRequestDto;
import org.bremersee.sms.model.SmsSendResponseDto;

/**
 * SMS service interface.
 *
 * @author Christian Bremer
 */
@SuppressWarnings({"unused", "SameParameterValue", "UnusedReturnValue"})
public interface SmsService {

  /**
   * Sends a the default message from the default sender to the default receiver.
   *
   * @return response information of the implementation
   * @throws SmsException if sending of the message fails
   */
  SmsSendResponseDto sendSms();

  /**
   * Sends a SMS message from the default sender to the default receiver.
   *
   * @param message the message
   * @return response information of the implementation
   * @throws SmsException if sending of the message fails
   */
  SmsSendResponseDto sendSms(String message);

  /**
   * Sends a SMS message from the default sender.
   *
   * @param receiver the receiver of the message
   * @param message  the message
   * @return response information of the implementation
   * @throws SmsException if sending of the message fails
   */
  SmsSendResponseDto sendSms(String receiver, String message);

  /**
   * Sends a SMS message from the default sender at the specified time.
   *
   * @param receiver the receiver of the message
   * @param message  the message
   * @param sendTime the triggered send time
   * @return response information of the implementation
   * @throws SmsException if sending of the message fails
   */
  SmsSendResponseDto sendSms(String receiver, String message, Date sendTime);

  /**
   * Sends a SMS message.
   *
   * @param sender   the sender of the message
   * @param receiver the receiver of the message
   * @param message  the message
   * @return response information of the implementation
   * @throws SmsException if sending of the message fails
   */
  SmsSendResponseDto sendSms(String sender, String receiver, String message);

  /**
   * Sends a SMS message at the specified time.
   *
   * @param sender   the sender of the message
   * @param receiver the receiver of the message
   * @param message  the message
   * @param sendTime the triggered send time
   * @return response information of the implementation
   * @throws SmsException if sending of the message fails
   */
  SmsSendResponseDto sendSms(String sender, String receiver, String message, Date sendTime);

  /**
   * Sends a SMS specified by the request.
   *
   * @param smsSendRequest the request
   * @return response information of the implementation
   * @throws SmsException if sending of the message fails
   */
  SmsSendResponseDto sendSms(SmsSendRequestDto smsSendRequest);

}
