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

package org.bremersee.sms.test;

import org.bremersee.sms.DummySmsService;
import org.bremersee.sms.GoyyaSmsService;
import org.bremersee.sms.SmsService;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Christian Bremer
 */
public class SmsServiceTests {

    private static final String username = null;
    private static final String password = null;
    private static final String receiver = "0123456789";

    private SmsService smsService;

    @SuppressWarnings("ConstantConditions")
    @Before
    public void createSmsService() {
        if (username != null && password != null) {
            smsService = new GoyyaSmsService(username, password);
        } else {
            smsService = new DummySmsService();
        }
    }

    @Test
    public void testSendSms() throws Exception {
        smsService.sendSms("bremersee", receiver, "Hello sms service user");
    }

}
