/*
 * Copyright 2019 Roman Proshin
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
package org.proshin.finapi.exception;

import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.json.JSONObject;
import org.junit.Test;
import org.proshin.finapi.bankconnection.out.TwoStepProcedure;
import org.proshin.finapi.primitives.OffsetDateTimeOf;

public class FinapiExceptionTest {

    @Test
    public void test() {
        final FinapiException exception = new FinapiException(
            "Test error message",
            new JSONObject('{' +
                "  \"errors\": [" +
                "    {" +
                "      \"message\": \"An unexpected error occurred\"," +
                "      \"code\": \"UNEXPECTED_ERROR\"," +
                "      \"type\": \"TECHNICAL\"," +
                "      \"multiStepAuthentication\": {" +
                "        \"hash\": \"c7af602c031117458affd825305fb56d\"," +
                "        \"status\": \"CHALLENGE_RESPONSE_REQUIRED\"," +
                "        \"challengeMessage\": \"Fake challenge message\"," +
                "        \"answerFieldLabel\": \"TAN-Nummer\"," +
                "        \"redirectUrl\": \"https://user-login.bank.de/\"," +
                "        \"redirectContext\": \"12345\"," +
                "        \"redirectContextField\": \"state\"," +
                "        \"twoStepProcedures\": [" +
                "          {" +
                "            \"procedureId\": \"955\"," +
                "            \"procedureName\": \"mobileTAN\"," +
                "            \"procedureChallengeType\": \"TEXT\"," +
                "            \"implicitExecute\": true" +
                "          }" +
                "        ]," +
                "        \"opticalData\": \"11048813833205002812775114302C30315D\"," +
                "        \"photoTanMimeType\": \"image/svg+xml\"," +
                "        \"photoTanData\": \"fake photo tan data\"" +
                "      }" +
                "    }" +
                "  ]," +
                "  \"date\": \"2018-01-01 00:00:00.000\"," +
                "  \"requestId\": \"request-id-01234567890123456789\"," +
                "  \"endpoint\": \"https://finapi.localhost\"," +
                "  \"authContext\": \"1/2\"," +
                "  \"bank\": \"00000000\"" +
                '}'),
            "fake location"
        );
        assertThat(exception.getMessage(), is("Test error message"));
        assertThat(exception.date(), is(new OffsetDateTimeOf("2018-01-01 00:00:00.000").get()));
        assertThat(exception.requestId(), is("request-id-01234567890123456789"));
        assertThat(exception.endpoint(), is("https://finapi.localhost"));
        assertThat(exception.authContext(), is("1/2"));
        assertThat(exception.bank(), is("00000000"));
        assertThat(exception.location(), is(Optional.of("fake location")));

        exception.errors().forEach(error -> {
            assertThat(error.message(), is(Optional.of("An unexpected error occurred")));
            assertThat(error.errorCode(), is(Optional.of(ErrorCode.UNEXPECTED_ERROR)));
            assertThat(error.errorType(), is(Optional.of(ErrorType.TECHNICAL)));
            final MultiStepAuthenticationChallenge msa = error.multiStepAuthentication();
            assertThat(msa.hash(), is("c7af602c031117458affd825305fb56d"));
            assertThat(msa.status(), is(MultiStepAuthenticationChallenge.Status.CHALLENGE_RESPONSE_REQUIRED));
            assertThat(msa.challengeMessage(), is(Optional.of("Fake challenge message")));
            assertThat(msa.answerFieldLabel(), is(Optional.of("TAN-Nummer")));
            assertThat(msa.redirectUrl(), is(Optional.of("https://user-login.bank.de/")));
            assertThat(msa.redirectContext(), is(Optional.of("12345")));
            assertThat(msa.redirectContextField(), is(Optional.of("state")));
            assertThat(msa.opticalData(), is(Optional.of("11048813833205002812775114302C30315D")));
            assertThat(msa.photoTanMimeType(), is(Optional.of("image/svg+xml")));
            assertThat(msa.photoTanData(), is(Optional.of("fake photo tan data")));
            msa.twoStepProcedures().forEach(tsp -> {
                assertThat(tsp.id(), is("955"));
                assertThat(tsp.name(), is("mobileTAN"));
                assertThat(tsp.type(), is(Optional.of(TwoStepProcedure.Type.TEXT)));
                assertThat(tsp.implicitExecute(), is(true));
            });
        });
    }
}
