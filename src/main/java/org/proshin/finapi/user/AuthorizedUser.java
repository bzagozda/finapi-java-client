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
package org.proshin.finapi.user;

import org.proshin.finapi.account.Accounts;
import org.proshin.finapi.bankconnection.BankConnections;
import org.proshin.finapi.category.Categories;
import org.proshin.finapi.label.Labels;
import org.proshin.finapi.notificationrule.NotificationRules;
import org.proshin.finapi.security.Securities;
import org.proshin.finapi.transaction.Transactions;
import org.proshin.finapi.webform.WebForms;

public interface AuthorizedUser extends User {

    AuthorizedUser edit(String email, String phone, boolean isAutoUpdateEnabled);

    void delete();

    BankConnections connections();

    Accounts accounts();

    Transactions transactions();

    Securities securities();

    Categories categories();

    Labels labels();

    NotificationRules notificationRules();

    WebForms webForms();
}
