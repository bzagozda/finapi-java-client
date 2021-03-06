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
package org.proshin.finapi.account;

import java.math.BigDecimal;
import java.util.Optional;
import org.hamcrest.BaseMatcher;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import org.hamcrest.Description;
import static org.hamcrest.MatcherAssert.assertThat;
import org.json.JSONObject;
import org.junit.Test;
import org.proshin.finapi.TestWithMockedEndpoint;
import org.proshin.finapi.account.out.Capability;
import org.proshin.finapi.account.out.ClearingAccount;
import org.proshin.finapi.account.out.Order;
import org.proshin.finapi.account.out.Status;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.primitives.BankingInterface;
import org.proshin.finapi.primitives.OffsetDateTimeOf;

public class FpAccountTest extends TestWithMockedEndpoint {

    @Test
    public void testFields() {
        final Account account = new FpAccount(
            this.endpoint(),
            new FakeAccessToken("secure user token"),
            new JSONObject('{' +
                "  \"id\": 1," +
                "  \"bankConnectionId\": 2," +
                "  \"accountName\": \"Testaccount\"," +
                "  \"iban\": \"DE89370400440532013000\"," +
                "  \"accountNumber\": \"12345678\"," +
                "  \"subAccountNumber\": \"1234\"," +
                "  \"accountHolderName\": \"Herr Max Mustermann\"," +
                "  \"accountHolderId\": \"XXXXX\"," +
                "  \"accountCurrency\": \"EUR\"," +
                "  \"accountTypeId\": 1," +
                "  \"accountTypeName\": \"Checking\"," +
                "  \"accountType\": \"Checking\"," +
                "  \"balance\": 91.99," +
                "  \"overdraft\": 92.99," +
                "  \"overdraftLimit\": 93.99," +
                "  \"availableFunds\": 94.99," +
                "  \"lastSuccessfulUpdate\": \"2018-01-01 02:03:04.555\"," +
                "  \"lastUpdateAttempt\": \"2018-01-02 03:04:05.666\"," +
                "  \"isNew\": true," +
                "  \"status\": \"UPDATED\"," +
                "  \"supportedOrders\": [" +
                "    \"SEPA_MONEY_TRANSFER\"," +
                "    \"SEPA_COLLECTIVE_MONEY_TRANSFER\"," +
                "    \"SEPA_BASIC_DIRECT_DEBIT\"," +
                "    \"SEPA_BASIC_COLLECTIVE_DIRECT_DEBIT\"," +
                "    \"SEPA_B2B_DIRECT_DEBIT\"," +
                "    \"SEPA_B2B_COLLECTIVE_DIRECT_DEBIT\"" +
                "  ]," +
                "  \"interfaces\": [" +
                "    {" +
                "      \"interface\": \"FINTS_SERVER\"," +
                "      \"status\": \"UPDATED\"," +
                "      \"capabilities\": [" +
                "        \"DATA_DOWNLOAD\"," +
                "        \"IBAN_ONLY_SEPA_MONEY_TRANSFER\"," +
                "        \"IBAN_ONLY_SEPA_DIRECT_DEBIT\"," +
                "        \"SEPA_MONEY_TRANSFER\"," +
                "        \"SEPA_COLLECTIVE_MONEY_TRANSFER\"," +
                "        \"SEPA_BASIC_DIRECT_DEBIT\"," +
                "        \"SEPA_BASIC_COLLECTIVE_DIRECT_DEBIT\"," +
                "        \"SEPA_B2B_DIRECT_DEBIT\"," +
                "        \"SEPA_B2B_COLLECTIVE_DIRECT_DEBIT\"" +
                "      ]," +
                "      \"lastSuccessfulUpdate\": \"2018-01-01 00:00:00.000\"," +
                "      \"lastUpdateAttempt\": \"2018-01-02 00:00:00.000\"" +
                "    }" +
                "  ]," +
                "  \"clearingAccounts\": [" +
                "    {" +
                "      \"clearingAccountId\": \"Clearing account ID\"," +
                "      \"clearingAccountName\": \"Clearing account name\"" +
                "    }" +
                "  ]" +
                '}'),
            ""
        );
        assertThat(account.id(), is(1L));
        assertThat(account.name(), is(Optional.of("Testaccount")));
        assertThat(account.number(), is("12345678"));
        assertThat(account.subNumber(), is(Optional.of("1234")));
        assertThat(account.iban(), is(Optional.of("DE89370400440532013000")));
        assertThat(account.holder().name(), is(Optional.of("Herr Max Mustermann")));
        assertThat(account.holder().id(), is(Optional.of("XXXXX")));
        assertThat(account.currency(), is(Optional.of("EUR")));
        assertThat(account.type(), is(Type.Checking));
        assertThat(account.balance(), is(Optional.of(new BigDecimal("91.99"))));
        assertThat(account.overdraft(), is(Optional.of(new BigDecimal("92.99"))));
        assertThat(account.overdraftLimit(), is(Optional.of(new BigDecimal("93.99"))));
        assertThat(account.availableFunds(), is(Optional.of(new BigDecimal("94.99"))));
        assertThat(account.lastSuccessfulUpdate(),
            is(Optional.of(new OffsetDateTimeOf("2018-01-01 02:03:04.555").get()))
        );
        assertThat(account.lastUpdateAttempt(), is(Optional.of(new OffsetDateTimeOf("2018-01-02 03:04:05.666").get())));
        assertThat(account.isNew(), is(true));
        assertThat(account.status(), is(Status.UPDATED));
        assertThat(account.supportedOrders(),
            hasItems(
                Order.SEPA_MONEY_TRANSFER,
                Order.SEPA_COLLECTIVE_MONEY_TRANSFER,
                Order.SEPA_BASIC_DIRECT_DEBIT,
                Order.SEPA_BASIC_COLLECTIVE_DIRECT_DEBIT,
                Order.SEPA_B2B_DIRECT_DEBIT,
                Order.SEPA_B2B_COLLECTIVE_DIRECT_DEBIT
            )
        );
        assertThat(
            account.clearingAccounts(),
            CoreMatchers.everyItem(new ClearingAccountMatcher("Clearing account ID", "Clearing account name"))
        );
        account.interfaces().forEach(ai -> {
            assertThat(ai.bankingInterface(), is(BankingInterface.FINTS_SERVER));
            assertThat(ai.capabilities(), hasItems(
                Capability.DATA_DOWNLOAD,
                Capability.SEPA_MONEY_TRANSFER,
                Capability.SEPA_COLLECTIVE_MONEY_TRANSFER,
                Capability.SEPA_BASIC_DIRECT_DEBIT,
                Capability.SEPA_BASIC_COLLECTIVE_DIRECT_DEBIT,
                Capability.SEPA_B2B_DIRECT_DEBIT,
                Capability.SEPA_B2B_COLLECTIVE_DIRECT_DEBIT
            ));
            assertThat(ai.status(), is(Status.UPDATED));
            assertThat(
                ai.lastSuccessfulUpdate(),
                is(Optional.of(new OffsetDateTimeOf("2018-01-01 00:00:00.000").get()))
            );
            assertThat(
                ai.lastUpdateAttempt(),
                is(Optional.of(new OffsetDateTimeOf("2018-01-02 00:00:00.000").get()))
            );
        });
    }

    private static final class ClearingAccountMatcher extends BaseMatcher<ClearingAccount> {

        private final String id;
        private final String name;

        private ClearingAccountMatcher(final String id, final String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean matches(final Object item) {
            final ClearingAccount clearingAccount = (ClearingAccount) item;
            return this.id.equals(clearingAccount.id()) &&
                this.name.equals(clearingAccount.name());
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText("id=" + this.id + " and name=" + this.name);
        }
    }
}
