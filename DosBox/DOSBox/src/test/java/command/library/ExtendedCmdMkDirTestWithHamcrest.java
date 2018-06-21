/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package command.library;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import helpers.Path;
import helpers.TestHelper;

import org.junit.Before;
import org.junit.Test;

import filesystem.Directory;

public class ExtendedCmdMkDirTestWithHamcrest extends CmdTest {

	@Before
	public void setUp() {
        // Check this file structure in base class: crucial to understand the tests.
        this.createTestFileStructure();

		// Add all commands which are necessary to execute this unit test
		// Important: Other commands are not available unless added here.
		this.commandInvoker.addCommand(new CmdMkDir("mkdir", this.drive));
	}

    @Test
    public void CmdMkDir_CreateNewDirectory_NewDirectoryIsAdded()
    {
        final String testDirName = "test1";
        executeCommand("mkdir " + testDirName);
        Directory testDirectory = TestHelper.getDirectory(drive, Path.Combine(drive.getDriveLetter(), testDirName),
                                                          testDirName);
        assertThat(drive.getRootDirectory(), is(testDirectory.getParent()));
        assertThat(drive.getRootDirectory().getNumberOfContainedDirectories(), equalTo(numbersOfDirectoriesBeforeTest+1));
        TestHelper.assertOutputIsEmpty(testOutput);
    }

    @Test
    public void CmdMkDir_CreateNewDirectory_NewDirectoryIsAddedToCorrectLocation()
    {
        final String testDirName = "test1";
        executeCommand("mkdir " + testDirName);
        Directory testDirectory = TestHelper.getDirectory(drive, Path.Combine(drive.getDriveLetter(), testDirName),
                                                          testDirName);
        assertThat(drive.getRootDirectory(), is(testDirectory.getParent()));
    }

    @Test
    public void CmdMkDir_SingleLetterDirectory_NewDirectoryIsAdded()
    {
        final String testDirName = "a";
        executeCommand("mkdir " + testDirName);
        Directory testDirectory = TestHelper.getDirectory(drive, Path.Combine(drive.getDriveLetter(), testDirName),
                                                          testDirName);
        
        assertThat(drive.getRootDirectory(), is(testDirectory.getParent()));
        assertThat(numbersOfDirectoriesBeforeTest+1, equalTo(drive.getRootDirectory().getNumberOfContainedDirectories()));
        TestHelper.assertOutputIsEmpty(testOutput);
    }

    @Test
    public void CmdMkDir_NoParameters_ErrorMessagePrinted()
    {
        executeCommand("mkdir");
        assertThat(numbersOfDirectoriesBeforeTest, equalTo(drive.getRootDirectory().getNumberOfContainedDirectories()));
        TestHelper.assertContains("syntax of the command is incorrect", testOutput);
    }

    @Test
    public void CmdMkDir_ParameterContainsBacklash_ErrorMessagePrinted()
    {
        executeCommand("mkdir c:\\test1");
        TestHelper.assertContains("At least one parameter", this.testOutput);
        TestHelper.assertContains("path rather than a directory name", this.testOutput);
    }

    @Test
    public void CmdMkDir_ParameterContainsBacklash_NoDirectoryCreated()
    {
        executeCommand("mkdir c:\\test1");
        assertThat(numbersOfDirectoriesBeforeTest, equalTo(drive.getRootDirectory().getNumberOfContainedDirectories()));
    }

    @Test
    public void CmdMkDir_SeveralParameters_SeveralNewDirectoriesCreated()
    {
        // given
        final String testDirName1 = "test1";
        final String testDirName2 = "test2";
        final String testDirName3 = "test3";

        // when
        executeCommand("mkdir " + testDirName1 + " " + testDirName2 + " " + testDirName3);

        // then
        Directory directory1 = TestHelper.getDirectory(drive, Path.Combine(drive.getDriveLetter(), testDirName1),
                                                       testDirName1);
        Directory directory2 = TestHelper.getDirectory(drive, Path.Combine(drive.getDriveLetter(), testDirName2),
                                                       testDirName2);
        Directory directory3 = TestHelper.getDirectory(drive, Path.Combine(drive.getDriveLetter(), testDirName3),
                                                       testDirName3);
        assertThat(directory1.getParent(), is(drive.getRootDirectory()));
        assertThat(directory2.getParent(), is(drive.getRootDirectory()));
        assertThat(directory3.getParent(), is(drive.getRootDirectory()));
        assertThat(numbersOfDirectoriesBeforeTest+3, equalTo(drive.getRootDirectory().getNumberOfContainedDirectories()));
        TestHelper.assertOutputIsEmpty(testOutput);
    }

    @Test
    public void CmdMkDir_AllParametersAreReset()
    {
        final String testDirName = "test1";
        executeCommand("mkdir " + testDirName);
        assertThat(numbersOfDirectoriesBeforeTest+1, equalTo(drive.getRootDirectory().getNumberOfContainedDirectories()));

        executeCommand("mkdir");
        assertThat(numbersOfDirectoriesBeforeTest+1, equalTo(drive.getRootDirectory().getNumberOfContainedDirectories()));
        TestHelper.assertContains("The syntax of the command is incorrect", this.testOutput);
    }
}
