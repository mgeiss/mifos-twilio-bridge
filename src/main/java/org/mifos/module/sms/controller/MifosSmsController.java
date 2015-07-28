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

import org.mifos.module.sms.domain.EventSource;
import org.mifos.module.sms.domain.SMSBridgeConfig;
import org.mifos.module.sms.exception.InvalidApiKeyException;
import org.mifos.module.sms.exception.UnknownEventTypeException;
import org.mifos.module.sms.listener.BulkSmsListener;
import org.mifos.module.sms.service.SMSBridgeService;
import org.mifos.module.sms.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modules")
public class MifosSmsController {

    private final SecurityService securityService;
    private final SMSBridgeService smsBridgeService;

    @Autowired
    public MifosSmsController(final SecurityService securityService,
                              final SMSBridgeService smsBridgeService)
                               {
        super();
        this.securityService = securityService;
        this.smsBridgeService = smsBridgeService;
    }

    @RequestMapping(value = "/sms", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Void> sendShortMessage(@RequestHeader("X-Mifos-API-Key") final String apiKey,
                                                 @RequestHeader("X-Mifos-Platform-TenantId") final String tenantId,
                                                 @RequestHeader("X-Mifos-Entity") final String entity,
                                                 @RequestHeader("X-Mifos-Action") final String action,
                                                 @RequestBody final String payload) {
        this.securityService.verifyApiKey(apiKey, tenantId);
        this.smsBridgeService.sendShortMessage(entity, action, tenantId, payload);  
        
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    @RequestMapping(value = "/sms/configuration", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<String> createSMSBridgeConfig(@RequestBody final SMSBridgeConfig smsBridgeConfig) {
        if (this.smsBridgeService.findSmsBridgeConfigByTenantId(smsBridgeConfig.getTenantId()) != null) {
            return new ResponseEntity<String>("Tenant " + smsBridgeConfig.getTenantId() + " already exists!", HttpStatus.BAD_REQUEST);
        }
        final String newApiKey = this.securityService.generateApiKey(smsBridgeConfig.getTenantId(), smsBridgeConfig.getMifosToken(), smsBridgeConfig.getSmsProviderAccountId(), smsBridgeConfig.getSmsProviderToken());
        smsBridgeConfig.setApiKey(newApiKey);
        this.smsBridgeService.createSmsBridgeConfig(smsBridgeConfig);

        return new ResponseEntity<String>(newApiKey, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/sms/configuration/{tenantId}", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<SMSBridgeConfig> getSmsBridgeConfig(@RequestHeader("X-Mifos-API-Key") final String apiKey,
                                                              @PathVariable("tenantId") final String tenantId) {
        this.securityService.verifyApiKey(apiKey, tenantId);
        final SMSBridgeConfig smsBridgeConfig = this.smsBridgeService.findSmsBridgeConfigByTenantId(tenantId);
        return new ResponseEntity<SMSBridgeConfig>(smsBridgeConfig, HttpStatus.OK);
    }

    @RequestMapping(value = "/sms/configuration/{tenantId}", method = RequestMethod.DELETE, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Void> deleteSmsBridgeConfig(@RequestHeader("X-Mifos-API-Key") final String apiKey,
                                                      @PathVariable("tenantId") final String tenantId) {
        this.securityService.verifyApiKey(apiKey, tenantId);
        final SMSBridgeConfig smsBridgeConfig = this.smsBridgeService.findSmsBridgeConfigByTenantId(tenantId);
        if (smsBridgeConfig != null) {
            this.smsBridgeService.deleteSmsBridgeConfig(smsBridgeConfig.getId());
            return new ResponseEntity<Void>(HttpStatus.OK);
        }

        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/sms/events/{tenantId}", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<List<EventSource>> findEventSources(@RequestHeader("X-Mifos-API-Key") final String apiKey,
                                                              @PathVariable("tenantId") final String tenantId) {
        this.securityService.verifyApiKey(apiKey, tenantId);
        final List<EventSource> eventSources = this.smsBridgeService.findEventsSourcesByTenantId(tenantId);

        return new ResponseEntity<List<EventSource>>(eventSources, HttpStatus.OK);
    }

    @RequestMapping(value = "/sms/events/{tenantId}/_resend", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Void> resendEventSources(@RequestHeader("X-Mifos-API-Key") final String apiKey,
                                                   @PathVariable("tenantId") final String tenantId) {
        this.securityService.verifyApiKey(apiKey, tenantId);
        this.smsBridgeService.resendEventsSourcesByTenantId(tenantId);

        return new ResponseEntity<Void>(HttpStatus.OK);
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
