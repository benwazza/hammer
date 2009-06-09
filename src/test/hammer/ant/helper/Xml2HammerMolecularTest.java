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

package hammer.ant.helper;

import au.net.netstorm.boost.sniper.core.LifecycleTestCase;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Result;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.ByteArrayOutputStream;

public final class Xml2HammerMolecularTest extends LifecycleTestCase{

    // FIX I want to make this final - fix Boost
    private String expected = "e(\"grandparent\", \n" +
        "    e(\"parent\"), \n" +
        "    e(\"parent\", a(\"greeting\", \"I'm a Parent!\")), \n" +
        "    e(\"parent\", a(\"greeting\", \"I'm also a Parent!\"), \n" +
        "        e(\"child\"), \"I'm not a parent.\", e(\"child\"), " +
        "\"<I'm not a parent either.> Neither am I.\", e(\"child\")\n" +
        "    )\n" +
        ");";

    public void testTransform() throws Exception {
        // FIX Use build properties here???
        Source xsl = new StreamSource(new File("src/core/hammer/ant/helper/xml2hammer.xsl"));
        Source data = new StreamSource(new File("test/data/xml2hammer.xml"));
        checkTransform(xsl, data, expected);
    }

    private void checkTransform(Source xsl, Source data, String expected) throws TransformerException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Result result = new StreamResult(bytes);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(xsl);
        transformer.transform(data, result);
        assertEquals(expected, bytes.toString());
    }
}