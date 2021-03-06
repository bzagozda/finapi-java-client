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
package org.proshin.finapi.mandator.in;

import java.util.function.Supplier;
import org.json.JSONObject;
import org.proshin.finapi.Jsonable;
import org.proshin.finapi.primitives.Direction;

public final class NewIbanRule implements Jsonable {

    private final Supplier<JSONObject> origin;

    public NewIbanRule(final Long category, final Direction direction, final String iban) {
        this(() -> new JSONObject()
            .put("categoryId", category)
            .put("direction", direction.capitalized())
            .put("iban", iban)
        );
    }

    public NewIbanRule(final Supplier<JSONObject> origin) {
        this.origin = origin;
    }

    @Override
    public JSONObject asJson() {
        return this.origin.get();
    }
}
