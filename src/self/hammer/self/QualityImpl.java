/*
 *  Copyright 2008-2009 Ben Warren
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package hammer.self;

import hammer.core.BuildFailureException;

import java.util.ArrayList;
import java.util.List;

public final class QualityImpl implements Quality {
    Compile compile;
    Style style;
    Tests tests;
    Dupe dupe;

    public void preCompile() {
        runPreCompile();
        reportPreCompile();
        checkPreCompile();
    }

    public void postCompile() {
        compile.compile();
        runPostCompile();
        reportPostCompile();
        checkPostCompile();
    }

    private void runPreCompile() {
        dupe.run();
        style.run();
    }

    private void reportPreCompile() {
        dupe.reportDupe();
        style.reportStyle();
    }

    private void checkPreCompile() {
        List<String> messages = new ArrayList<String>();
        if (!dupe.checkDupe()) messages.add("The duplication check failed!");
        if (!style.checkStyle()) messages.add("The style check failed!");
        if (!messages.isEmpty()) throw new BuildFailureException(messages);
    }

    private void runPostCompile() {
        tests.runTests();
    }

    private void reportPostCompile() {
        tests.reportTests();
    }

    private void checkPostCompile() {
        if (!tests.checkTests())
            throw new BuildFailureException("Tests failed!");
    }
}