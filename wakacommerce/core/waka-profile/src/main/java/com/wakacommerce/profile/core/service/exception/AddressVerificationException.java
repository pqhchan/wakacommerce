/*
 * #%L
 * BroadleafCommerce Profile
 * %%
 * Copyright (C) 2009 - 2013 Broadleaf Commerce
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.wakacommerce.profile.core.service.exception;


public class AddressVerificationException extends Exception {

    private static final long serialVersionUID = 1L;

    public AddressVerificationException() {
        super();
    }

    public AddressVerificationException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public AddressVerificationException(String arg0) {
        super(arg0);
    }

    public AddressVerificationException(Throwable arg0) {
        super(arg0);
    }

}
