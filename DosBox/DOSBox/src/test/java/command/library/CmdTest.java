/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package command.library;

import static org.fest.assertions.api.Assertions.assertThat;
import command.framework.TestOutput;
import helpers.TestHelper;
import interfaces.IDrive;
import invoker.CommandInvoker;
import filesystem.Directory;
import filesystem.File;
import filesystem.Drive;
import filesystem.FileSystemItem;

public abstract class CmdTest {
	protected CommandInvoker commandInvoker = new CommandInvoker();
	protected TestOutput testOutput = new TestOutput();
	protected IDrive drive = new Drive("C");
	protected Directory rootDir = this.drive.getRootDirectory();
	protected File fileInRoot1;
	protected File fileInRoot2;
	protected Directory subDir1;
	protected File file1InDir1;
	protected File file2InDir1;
	protected Directory subDir2;
	protected int numbersOfDirectoriesBeforeTest;
	protected int numbersOfFilesBeforeTest;

	public CmdTest() {
		super();
	}

	protected void createTestFileStructure() {
		givenStandardDirectoryStructure();		
	}
	
	protected void executeCommand(String commandLine) {
		whenExecutingCommand(commandLine);
	}
	
	protected void givenEmptyDirectoryStructure() {
		// Nothing to do!
	}

    /**Sets up a directory structure as follows:
     * C:\
     * |---FileInRoot1 ("an entry")
     * |---FileInRoot2 ("a long entry in a file")
     * |---subDir1
     * |   |---File1InDir1 (empty)
     * |   |---File2InDir1 (empty)
     * |---subdir2
     */
    protected void givenStandardDirectoryStructure() {
		this.fileInRoot1 = new File("FileInRoot1", "an entry");
		this.rootDir.add(this.fileInRoot1);
		this.fileInRoot2 = new File("FileInRoot2", "a long entry in a file");
		this.rootDir.add(this.fileInRoot2);
		
		this.subDir1 = new Directory("subDir1");
		this.rootDir.add(subDir1);
		this.file1InDir1 = new File("File1InDir1", "");
		this.subDir1.add(this.file1InDir1);
		this.file2InDir1 = new File("File2InDir1", "");
		this.subDir1.add(this.file2InDir1);
		
		this.subDir2 = new Directory("subDir2");
		this.rootDir.add(subDir2);
		
		this.numbersOfDirectoriesBeforeTest = this.drive.getRootDirectory().getNumberOfContainedDirectories();
		this.numbersOfFilesBeforeTest = this.drive.getRootDirectory().getNumberOfContainedFiles();
	}

	protected void andCurrentDirectoryIsRoot() {
		this.drive.changeCurrentDirectory(rootDir);
	}

    protected void andCurrentDirectoryIs(Directory newCurrentDirectory) {
		this.drive.changeCurrentDirectory(newCurrentDirectory);
	}

	protected void whenExecutingCommand(String commandLine) {
		if(this.commandInvoker == null)
			this.commandInvoker = new CommandInvoker();
		this.commandInvoker.executeCommand(commandLine, this.testOutput);
	}

	protected void thenDirectoryIsCreated(String createdDirectory) {
		assertThat(createdDirectory.toLowerCase().startsWith("c:\\"));
		FileSystemItem fsi = drive.getItemFromPath(createdDirectory);
		assertThat(fsi).isNotNull();
		assertThat(fsi.isDirectory()).isTrue();
	}

    protected void thenNoErrorMessageIsPrinted() {
        TestHelper.assertOutputIsEmpty(testOutput);
	}

    protected void thenOutputContains(String expectedOutput) {
    	TestHelper.assertContains(expectedOutput, this.testOutput);
	}

	protected void thenNoDirectoryIsCreated() {
		thenNumberOfDirectoriesCreatedIs(0);
	}
	
	protected void thenNumberOfDirectoriesCreatedIs(int expectedNumberOfCreatedDirectories) {
		int numberOfDirectories = this.rootDir.getNumberOfContainedDirectories();
		assertThat(this.numbersOfDirectoriesBeforeTest+expectedNumberOfCreatedDirectories).isEqualTo(numberOfDirectories);
	}
}