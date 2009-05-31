/*
 *  Copyright 2008 Ben Warren
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
import org.apache.tools.ant.Project;
import org.apache.tools.ant.RuntimeConfigurable;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.UnknownElement;
import org.apache.tools.ant.helper.AntXMLContext;
import org.apache.tools.ant.helper.ProjectHelper2;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.AttributesImpl;

import java.util.HashMap;
import java.util.List;

// DEBT FanOutComplexity {
public final class AntBuilderImpl implements AntBuilder {
    // TODO Edge these out
    private static final String EMPTY_NAMESPACE = "";
    private final ProjectHelper2.ElementHandler antElementHandler = new ProjectHelper2.ElementHandler();
    private final AntXMLContext antXmlContext;
    private final Project project;

    public AntBuilderImpl(Project project) {
        this.project = project;
        Target collectorTarget = new Target();
        collectorTarget.setProject(project);
        antXmlContext = setupXmlContext(collectorTarget);
    }

    public void execute(Element element) {
        Object antTask = buildElement(element);
        performTask(antTask);
    }

    public boolean hasProperty(String property) {
        return project.getProperty(property) != null;
    }

    // ===========================================================

    private AntXMLContext setupXmlContext(Target collectorTarget) {
        AntXMLContext antXmlContext = new AntXMLContext(project);
        antXmlContext.setCurrentTarget(collectorTarget);
        antXmlContext.setLocator(new DoNothingLocator());
        antXmlContext.setCurrentTargets(new HashMap());
        return antXmlContext;
    }

// ===========================================================

    // SUGGEST Extract a generic tree builder from this stuff
    // TODO Edge to get rid of SAXParseException

    private Object buildElement(Element element) {
        try {
            startElement(element);
            buildChildren(element);
            setText(element);
            return endElement();
        }
        catch (SAXParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void startElement(Element element) throws SAXParseException {
        Attributes attrs = buildAttributes(element.attributes());
        String tagName = element.name();
        antElementHandler.onStartElement(EMPTY_NAMESPACE, tagName, tagName, attrs, antXmlContext);
    }

    private void buildChildren(Element element) {
        List<Element> children = element.children();
        for (Element child : children) {
            buildElement(child);
        }
    }

    protected void setText(Element element) throws SAXParseException {
        if (!element.hasText()) return;
        String text = element.text();
        final char[] characters = text.toCharArray();
        antElementHandler.characters(characters, 0, characters.length, antXmlContext);
    }

    private Object endElement() {
        List stack = antXmlContext.getWrapperStack();
        RuntimeConfigurable wrapper = last(stack);
        Object elem = wrapper.getProxy();
        antElementHandler.onEndElement(null, null, antXmlContext);
        return elem;
    }

    private RuntimeConfigurable last(List stack) {
        if (stack.isEmpty()) return null;
        int last = stack.size() - 1;
        return (RuntimeConfigurable) stack.get(last);
    }

    protected static Attributes buildAttributes(final List<Attribute> attributes) {
        AttributesImpl attr = new AttributesImpl();
        for (Attribute attribute : attributes) {
            String name = attribute.name();
            String value = attribute.value();
            attr.addAttribute(null, name, name, "CDATA", value);
        }
        return attr;
    }

    private void performTask(Object node) {
        Object task = unwrap(node);
        if (!(task instanceof Task)) throw new RuntimeException("Only tasks supported!");
        ((Task) task).perform();
    }

    private Object unwrap(Object node) {
        if (!(node instanceof UnknownElement)) return node;
        UnknownElement unknownElement = (UnknownElement) node;
        unknownElement.maybeConfigure();
        return unknownElement.getRealThing();
    }

    // Hide this away - looks ugly sitting ouside in the package
    private static class DoNothingLocator implements Locator {
        public int getColumnNumber() {
            return 0;
        }

        public int getLineNumber() {
            return 0;
        }

        public String getPublicId() {
            return "";
        }

        public String getSystemId() {
            return "";
        }
    }
}
// } DEBT FanOutComplexity
