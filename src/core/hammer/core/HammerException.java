/*
 * Copyright 2008-2009 Ben Warren
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

package hammer.core;

import au.net.netstorm.boost.bullet.primordial.PrimordialException;

public abstract class HammerException extends PrimordialException {
    private static final long serialVersionUID = -5496639098429170604L;

    public HammerException(String msg) {
        super(msg);
    }

    public HammerException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
