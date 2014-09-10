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
package org.mifos.module.twilio.provider;

import com.squareup.okhttp.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

@Component
public class RestAdapterProvider {

    @Value("${mifos.endpoint}")
    private String endPoint;

    public RestAdapterProvider() {
        super();
    }

    public RestAdapter get() {

        final OkHttpClient okHttpClient = new OkHttpClient();

        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(this.endPoint)
                .setClient(new OkClient(okHttpClient))
                .build();
        return restAdapter;
    }
}
