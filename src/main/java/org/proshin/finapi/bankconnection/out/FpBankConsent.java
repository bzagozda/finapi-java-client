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
package org.proshin.finapi.bankconnection.out;

import java.time.OffsetDateTime;
import org.json.JSONObject;
import org.proshin.finapi.primitives.OffsetDateTimeOf;

public class FpBankConsent implements BankConsent {

    private final JSONObject origin;

    public FpBankConsent(final JSONObject origin) {
        this.origin = origin;
    }

    @Override
    public Status status() {
        return Status.valueOf(this.origin.getString("status"));
    }

    @Override
    public OffsetDateTime expiresAt() {
        return new OffsetDateTimeOf(this.origin.getString("expiresAt")).get();
    }
}
