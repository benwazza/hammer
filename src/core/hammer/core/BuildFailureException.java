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

import java.util.List;

public final class BuildFailureException extends HammerException implements Constants {
    private static final long serialVersionUID = 6565658345369456224L;

    public BuildFailureException(String msg) {
        super(msg);
    }

    public BuildFailureException(List<String> msgs) {
        this(buildMessage(msgs));
    }

    public BuildFailureException(String msg, Throwable cause) {
        super(msg, cause);
    }

    private static String buildMessage(List<String> msgs) {
        StringBuffer msg = new StringBuffer();
        for (String message : msgs) {
            msg.append(message).append(LINE_SEPARATOR);
        }
        return msg.toString();
    }
}
