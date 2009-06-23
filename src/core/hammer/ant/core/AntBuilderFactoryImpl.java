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

package hammer.ant.core;

import hammer.ioc.Ioc;
import hammer.ioc.IocImpl;
import org.apache.tools.ant.BuildLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.input.DefaultInputHandler;
import org.apache.tools.ant.input.InputHandler;

public final class AntBuilderFactoryImpl implements AntBuilderFactory {
    Ioc ioc;

    public AntBuilder nu() {
        initIoc();
        Project project = new Project();
        configureLogging(project);
        setupInputHandler(project);
        project.init();
        return ioc.nu(AntBuilder.class, project);
    }

    // Convenience initialisation to enable simple use of this factory from arbitrary
    // (Non-Hammer) Java code that does not want to know about or use our IOC.
    private synchronized void initIoc() {
        if (ioc == null) {
            ioc = new IocImpl();
        }
    }

    private void setupInputHandler(Project project) {
        InputHandler inputHandler = new DefaultInputHandler();
        project.setInputHandler(inputHandler);
    }

    private void configureLogging(Project project) {
        ioc.single(BuildLogger.class, HammerAntLogger.class);
        BuildLogger logger = ioc.resolve(BuildLogger.class);
        project.addBuildListener(logger);
    }
}