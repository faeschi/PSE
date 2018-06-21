/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package filesystem;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FileTest {

	private File testfile;

	@Before
	public void setUp() throws Exception {
		testfile = new File("test", "test content");
	}
	
	@Test
	public void testConstructor() {
		String name = "hello.txt";
		String content = "This is the content";
		
		testfile = new File(name, content);
		assertTrue(testfile.getName().compareTo(name) == 0);
		assertTrue(testfile.getFileContent().compareTo(content) == 0);
	}
	
	@Test
	public void testForDirectory() {
		assertTrue(testfile.isDirectory() == false);
	}

	@Test
	public void testRename() {
		this.testfile.setName("NewName");
		assertTrue(this.testfile.getName().compareTo("NewName") == 0);
	}
	
	@Test
	public void testRenameWithIllegalNames() {
		String defaultName = new String("default");
		this.testfile.setName(defaultName);
		assertTrue(this.testfile.getName().compareTo(defaultName) == 0);
		
		try {
			this.testfile.setName("Illegal\\Name");
			fail();  // must throw an exception
		}
		catch(IllegalArgumentException e) {
			assertTrue(this.testfile.getName().compareTo(defaultName) == 0);
		}
	}
	
	@Test
	public void testFileSize() {
		//TODO
	}
}
