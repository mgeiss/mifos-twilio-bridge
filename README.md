# mifos-twilio-bridge #

## Summary ##
mifos-twilio-bridge provides a RESTful interface to send SMS based on
events send by the Mifos X Platform.

Needed additional information is requested via the Mifos X Platform and
a SMS message is created and send using Twilio.

## Execution ##
Simply use the gradle task bootRun or build the project and run the
executable jar file by calling:

    java -jar mifos-twilio-bridge-0.0.1.jar

## API Documentation ##

### Resources ###

<table>
    <tr>
        <th>Path</th>
        <th>Type</th>
        <th>Header</th>
        <th>Parameter</th>
        <th>Body</th>
        <th>Result</th>
    </tr>
    <tr>
        <td>/modules/sms</td>
        <td>POST</td>
        <td>X-Mifos-API-Key<br>X-Mifos-Entity<br>X-Mifos-Action</td>
        <td>none</td>
        <td>Stringified JSON</td>
        <td>200<br>401<br>404</td>
    </tr>
</table>

### Available Entities and Actions ###

<table>
    <tr>
        <th>Entity</th>
        <th>Action</th>
        <th>Body</th>
    </tr>
    <tr>
        <td>client</td>
        <td>create</td>
        <td>{"officeId":1,"clientId":1,"savingsId":1,"resourceId":1}</td>
    </tr>
    <tr>
        <td>loan</td>
        <td>repayment</td>
        <td>{"officeId":1,"clientId":1,"loanId":1,"resourceId":1}</td>
    </tr>
    <tr>
        <td>sms</td>
        <td>send</td>
        <td>{"mobileNo":"1234567890","message":"Custom message"}</td>
    </tr>
</table>

## People ##
Markus Geiss (markus.geiss@live.de).

## License ##
Copyright 2014 Markus Geiss

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
