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

package org.bremersee.sms;

import org.bremersee.sms.model.SmsSendRequestDto;
import org.bremersee.sms.model.SmsSendResponseDto;

/**
 * <p>
 * A dummy implementation that does not send any SMS.
 * </p>
 *
 * @author Christian Bremer
 */
public class DummySmsService extends AbstractSmsService implements SmsService {

    /**
     * Default constructor.
     */
    public DummySmsService() {
        super();
    }
    
    @Override
    public SmsSendResponseDto doSendSms(SmsSendRequestDto smsSendRequest) {
        log.warn("THIS IS ONLY A DUMMY SMS SERVICE - SMS WAS NOT SENT !!!");
        return new SmsSendResponseDto(smsSendRequest, true);
    }

}
