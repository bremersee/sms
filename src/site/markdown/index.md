# Bremersee SMS Service
  
This project contains classes for sending SMS.
    
### Goyya SMS Service
  
Currently there is only one SMS service implementation available that sends the SMS via the 
[Goyya SMS Services](https://www.goyya.com/sms-services).
    
To use the Goyya SMS Services you need an account there.
    
#### Usage
  
```java
SmsService smsService = new GoyyaSmsService("username", "password");
smsService.sendSms("bremersee", "0123456789", "Hello sms service user");
```
