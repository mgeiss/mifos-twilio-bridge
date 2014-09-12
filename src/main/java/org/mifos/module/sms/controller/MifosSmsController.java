/**
 * Copyright 2014 Markus Geiss
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mifos.module.sms.controller;

import org.mifos.module.sms.exception.InvalidApiKeyException;
import org.mifos.module.sms.exception.UnknownEventTypeException;
import org.mifos.module.sms.service.SecurityService;
import org.mifos.module.sms.service.MifosSMSBridgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/modules")
public class MifosSmsController {

    private final SecurityService securityService;
    private final MifosSMSBridgeService mifosSMSBridgeService;

    @Autowired
    public MifosSmsController(final SecurityService securityService,
                              final MifosSMSBridgeService mifosSMSBridgeService) {
        super();
        this.securityService = securityService;
        this.mifosSMSBridgeService = mifosSMSBridgeService;
    }

    @RequestMapping(value = "/sms", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public void sendShortMessage(@RequestHeader("X-Mifos-API-Key") final String apiKey,
                                 @RequestHeader("X-Mifos-Entity") final String entity,
                                 @RequestHeader("X-Mifos-Action") final String action,
                                 @RequestBody final String payload) {
        this.securityService.verifyApiKey(apiKey);
        this.mifosSMSBridgeService.sendShortMessage(entity, action, payload);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleInvalidApiKeyException(final InvalidApiKeyException ex) {

    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUnknownEventException(final UnknownEventTypeException ex) {
        return ex.getMessage();
    }
}
