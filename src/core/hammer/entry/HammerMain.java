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

package hammer.entry;

import hammer.config.BuildConfigImpl;
import hammer.config.CommandLine;
import hammer.config.CommandLineImpl;

public final class HammerMain {

    public static void main(String[] args) throws Exception {
        CommandLine commandLine = new CommandLineImpl();
        BuildConfigImpl config = commandLine.getConfig(args);
        HammerRunner runner = new HammerRunnerImpl(config);
        String[] tasks = commandLine.getTasks();
        System.exit(runner.run(tasks));
    }
}