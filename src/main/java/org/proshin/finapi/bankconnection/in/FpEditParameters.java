/*
 * Copyright 2018 Roman Proshin
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
package org.proshin.finapi.bankconnection.in;

import org.json.JSONObject;

public final class FpEditParameters implements EditParameters {

    private final JSONObject origin;

    public FpEditParameters() {
        this(new JSONObject());
    }

    public FpEditParameters(final JSONObject origin) {
        this.origin = origin;
    }

    @Override
    public EditParameters withUserId(final String userId) {
        this.origin.put("bankingUserId", userId);
        return this;
    }

    @Override
    public EditParameters withCustomerId(final String customerId) {
        this.origin.put("bankingCustomerId", customerId);
        return this;
    }

    @Override
    public EditParameters withPin(final String pin) {
        this.origin.put("bankingPin", pin);
        return this;
    }

    @Override
    public EditParameters withDefaultTwoStepProcedure(final String procedureId) {
        this.origin.put("defaultTwoStepProcedureId", procedureId);
        return this;
    }

    @Override
    public EditParameters withName(final String name) {
        this.origin.put("name", name);
        return this;
    }

    @Override
    public String asJson() {
        return this.origin.toString();
    }
}
