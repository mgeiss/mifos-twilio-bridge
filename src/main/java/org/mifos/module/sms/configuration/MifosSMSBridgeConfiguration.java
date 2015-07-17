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
package org.mifos.module.sms.configuration;

import org.mifos.module.sms.domain.EventSource;
import org.mifos.module.sms.domain.EventSourceDetail;
import org.mifos.module.sms.domain.SMSBridgeConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {
        "org.mifos.module.sms.repository"
})
@EntityScan(basePackageClasses = {
        SMSBridgeConfig.class,
        EventSource.class,
        EventSourceDetail.class
})
@ComponentScan(basePackages = {
        "org.mifos.module.sms.controller",
        "org.mifos.module.sms.service",
        "org.mifos.module.sms.provider",
        "org.mifos.module.sms.listener",
        "org.mifos.module.sms.parser"
})
public class MifosSMSBridgeConfiguration {

    public MifosSMSBridgeConfiguration() {
        super();
    }

    @Bean
    public SimpleApplicationEventMulticaster applicationEventMulticaster() {
        final SimpleApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();
        multicaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return multicaster;
    }
}
