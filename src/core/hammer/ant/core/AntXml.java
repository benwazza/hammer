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

import hammer.xml.Attribute;
import hammer.xml.Element;
import hammer.xml.Xml;

import java.io.File;

public class AntXml {

    public static Element e(String name, Object... content) {
        return Xml.n(name, content);
    }

    public static Attribute a(String name, File location) {
        return a(name, location.getAbsolutePath());
    }

    public static Attribute a(String name, Object value) {
        return Xml.a(name, value);
    }

    public static Element fileSet(String type, File dir, Element... children) {
        return e(type, a("dir", dir)).withElems(children);
    }

    public static Element fileSet(File dir, Element... children) {
        return fileSet("fileset", dir, children);
    }

    public static Element zipFileSet(File dir, Element... children) {
        return fileSet("zipfileset", dir, children);
    }

    public static Element classPath(Element... children) {
        return e("classpath").withElems(children);
    }

    public static Element include(String name) {
        return e("include", a("name", name));
    }

    public static Element exclude(String name) {
        return e("exclude", a("name", name));
    }

    public static Element pathElement(File location) {
        return e("pathelement", a("location", location));
    }

    public static Element manifestAttr(String name, String value) {
        return e("attribute", a("name", name), a("value", value));
    }

    public static Element filterSet(Element... filters) {
        return e("filterset").withElems(filters);
    }

    public static Element filter(String name, String value) {
        return e("filter", a("token", name), a("value", value));
    }
}