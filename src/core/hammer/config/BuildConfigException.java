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

package hammer.config;

import hammer.core.Constants;
import hammer.core.HammerException;

import java.io.File;

public final class BuildConfigException extends HammerException implements Constants {
    private static final long serialVersionUID = 6262891861459051113L;

    public BuildConfigException(String name) {
        super("Value for '" + name + "' not specified.");
    }

    public BuildConfigException(File file) {
        super("Could not read config file '" + file + "'.");
    }
}
