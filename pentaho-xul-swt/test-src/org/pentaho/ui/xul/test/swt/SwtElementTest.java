package org.pentaho.ui.xul.test.swt;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;

import javax.swing.JComponent;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.swt.SwtXulLoader;
import org.pentaho.ui.xul.swt.SwtXulRunner;
import org.pentaho.ui.xul.util.Orient;

import static org.junit.Assert.*;

public class SwtElementTest {

  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;
  SwtElement element;
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {

    InputStream in = SwtXulRunner.class.getClassLoader().getResourceAsStream("resource/documents/splitterTest.xul");
    assertNotNull("XUL input not found.", in);
    SAXReader rdr = new SAXReader();
    final Document doc = rdr.read(in);
    
    container = new SwtXulLoader().loadXul(doc);

    runner = new SwtXulRunner();
    runner.addContainer(container);
    
    element = (SwtElement) container.getDocumentRoot().getElementById("testContainerElement");
  }

  @After
  public void tearDown() throws Exception {
  	try{
  		runner.stop();
  	} catch(Exception e){}
  }

  @Test
  public final void testOrient() {
  	element.setOrient("vertical");
  	String getVal = element.getOrient();
  	assertTrue(getVal.equalsIgnoreCase("vertical"));
  }

  @Test
  public final void testVerticalOrientation() {
  	element.setOrient("vertical");
  	Orient val = element.getOrientation();
  	assertEquals(val, Orient.VERTICAL);
  }

  @Test
  public final void testHorizontalOrientation() {
  	element.setOrient("horizontal");
  	Orient val = element.getOrientation();
  	assertEquals(val, Orient.HORIZONTAL);
  }
  
  @Test
  @Ignore
  public final void testSetOnBlur() {
  	element.setOnblur("test.foo()");
  }

  @Test
  public final void testAddChangeListener() {
  	element.addPropertyChangeListener(new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent arg0) {
			}
  	}); 
  }

  @Test
  public final void testRemoveChangeListener() {
  	PropertyChangeListener changeListener = new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent arg0) {
			}
  	};
  	element.addPropertyChangeListener(changeListener);
  	element.removePropertyChangeListener(changeListener);
  }
  
  @Test 
  @Ignore
  public final void testSetDisabled() {
  	element.setDisabled(true);
  }

  @Test
  @Ignore
  public final void testGetDisabled() {
  	element.setDisabled(true);
  	boolean b1 = element.isDisabled();
  	
  	element.setDisabled(false);
  	boolean b2 = element.isDisabled();
  	
  	assertTrue(b1 && !b2);
  	
  }

  @Test
  public final void testReplaceChild() throws Exception{
		XulComponent firstChild = element.getFirstChild();
  	
  	XulComponent newComp = element.getDocument().createElement("button");
  	element.replaceChild(firstChild, newComp);
	
  }

  @Test
  public final void testReplaceChildFailure() throws Exception{
//  	assertThrows(XulDomException.class){
//  		public void test() throws Exception { 
//  			XulComponent firstChild = element.getFirstChild();
//  	  	
//  	  	XulComponent newComp = element.getDocument().createElement("button");
//  	  	XulComponent badComp = element.getDocument().createElement("button");
//  	  	element.replaceChild(badComp, newComp); 
//  		} 
//  	}.runTest();

  	try{
	  	XulComponent firstChild = element.getFirstChild();
	  	  	
	  	XulComponent newComp = element.getDocument().createElement("button");
	  	XulComponent badComp = element.getDocument().createElement("button");
	  	element.replaceChild(badComp, newComp); 
	  	fail("You should have thrown a XulDomException");
  	} catch(Exception e){}
	
  }
  
}
