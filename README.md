mifos-twilio-bridge
===================

Summary
-------
mifos-twilio-bridge provides a RESTful interface to send SMS based on
events send by the Mifos X Platform.

Needed additional information is requested via the Mifos X Platform and
a SMS message is created and send using Twilio.

Execution
---------
Simply use the gradle task bootRun or build the project and run the
executable jar file by calling:

    java -jar mifos-twilio-bridge-0.0.1.jar

Limitation
----------
Only a create client event will be processed. For this you need to specify
two header params:
    
    X-Mifos-Entity: client
    X-Mifos-Action: create

The Body of the request should look similar to something like this:

    {
        "officeId": 1,
        "clientId": 1745,
        "resourceId": 1,
        "savingsId": 10
    }

People
------
Markus Geiss (markus.geiss@live.de).

License
-------
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
