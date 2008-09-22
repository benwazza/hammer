/*
 * Copyright 2008 Ben Warren
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

import java.util.ArrayList;
import java.util.List;

public final class CommandLineImpl implements Constants, CommandLine {
    private final List<String> tasks = new ArrayList<String>();
    private boolean publish = false;
    private boolean trace = false;

    public BuildConfigImpl getConfig(String... args) {
        for (String arg : args) {
            processArg(arg);
        }
        return new BuildConfigImpl(publish, trace);
    }

    public String[] getTasks() {
        return tasks.toArray(new String[tasks.size()]);
    }

    // OK NCSS {
    private void processArg(String arg) {
        if (arg.equals(PUBLISH)) publish = true;
        else if (arg.equals(TRACE)) trace = true;
        else if (arg.equals(HELP)) help();
        else tasks.add(arg);
    }
    // } OK NCSS

    // OK GenericIllegalRegexp {

    private void help() {
        System.out.println("");
        System.out.println("Hammer usage: hammer [-h] | [-p] | [-t] | task1 task2 ...");
        System.out.println("");
        System.out.println("  -h Print out the usage info.");
        System.out.println("  -p Publish documented tasks.");
        System.out.println("  -t Output trace logging.");
        System.out.println("");
        System.exit(OK);
    }
    // } OK GenericIllegalRegexp
}
