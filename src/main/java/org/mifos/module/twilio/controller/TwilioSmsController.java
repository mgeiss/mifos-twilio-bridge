package org.mifos.module.twilio.controller;

import org.mifos.module.twilio.service.TwilioBridgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/twilio")
public class TwilioSmsController {

    private final TwilioBridgeService twilioBridgeService;

    @Autowired
    public TwilioSmsController(final TwilioBridgeService twilioBridgeService) {
        super();
        this.twilioBridgeService = twilioBridgeService;
    }

    @RequestMapping(value = "/sms", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public void sendShortMessage(@RequestHeader("X-Mifos-Entity") final String entity,
                                 @RequestHeader("X-Mifos-Action") final String action,
                                 @RequestBody final String payload) {
        this.twilioBridgeService.sendShortMessage(entity, action, payload);
    }
}
