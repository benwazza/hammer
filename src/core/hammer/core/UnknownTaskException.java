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

public final class UnknownTaskException extends HammerException {
    private static final long serialVersionUID = 3210300846951381213L;

    public UnknownTaskException(String taskName) {
        super("Unknown task: " + taskName);
    }

    public UnknownTaskException() {
        super("No task specified via call or BuildInfo annotation.");
    }
}
