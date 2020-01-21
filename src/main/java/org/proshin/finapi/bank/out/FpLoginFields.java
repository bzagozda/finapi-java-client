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
package org.proshin.finapi.bank.out;

import java.util.Optional;
import org.json.JSONObject;
import org.proshin.finapi.primitives.optional.OptionalOf;

/**
 * @deprecated since v0.1.92 due to PSD2-related changes
 */
@Deprecated
public final class FpLoginFields implements LoginFields {

    private final JSONObject origin;

    public FpLoginFields(final JSONObject origin) {
        this.origin = origin;
    }

    /**
     * @deprecated since v0.1.92 due to PSD2-related changes
     */
    @Override
    @Deprecated
    public Optional<String> userId() {
        return new OptionalOf<>(this.origin, "loginFieldUserId", JSONObject::getString).get();
    }

    /**
     * @deprecated since v0.1.92 due to PSD2-related changes
     */
    @Override
    @Deprecated
    public Optional<String> customerId() {
        return new OptionalOf<>(this.origin, "loginFieldCustomerId", JSONObject::getString).get();
    }

    /**
     * @deprecated since v0.1.92 due to PSD2-related changes
     */
    @Override
    @Deprecated
    public Optional<String> pin() {
        return new OptionalOf<>(this.origin, "loginFieldPin", JSONObject::getString).get();
    }
}
