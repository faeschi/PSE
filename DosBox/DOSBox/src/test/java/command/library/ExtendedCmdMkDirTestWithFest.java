/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package command.library;

// This import is usually used for fest
import static org.fest.assertions.api.Assertions.*;
// This import is used when adding own assertions as I did for IOutputter. Imports own and usual fest assertions.
import static helpers.DosBoxFestProjectAssertions.*;
import helpers.Path;
import helpers.TestHelper;

import org.junit.Before;
import org.junit.Test;

import command.library.CmdTest;
import filesystem.Directory;

/** Fest Asserts are described in https://github.com/alexruiz/fest-assert-2.x/wiki 
 */
public class ExtendedCmdMkDirTestWithFest extends CmdTest {

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
        assertThat(drive.getRootDirectory()).isSameAs(testDirectory.getParent());
        assertThat(numbersOfDirectoriesBeforeTest+1).isSameAs(drive.getRootDirectory().getNumberOfContainedDirectories());
        assertThat(testOutput).isEmpty();  // Own assertion added
    }

    @Test
    public void CmdMkDir_CreateNewDirectory_NewDirectoryIsAddedToCorrectLocation()
    {
        final String testDirName = "test1";
        executeCommand("mkdir " + testDirName);
        Directory testDirectory = TestHelper.getDirectory(drive, Path.Combine(drive.getDriveLetter(), testDirName),
                                                          testDirName);
        assertThat(drive.getRootDirectory()).isSameAs(testDirectory.getParent());
    }

    @Test
    public void CmdMkDir_SingleLetterDirectory_NewDirectoryIsAdded()
    {
        final String testDirName = "a";
        executeCommand("mkdir " + testDirName);
        Directory testDirectory = TestHelper.getDirectory(drive, Path.Combine(drive.getDriveLetter(), testDirName),
                                                          testDirName);
        assertThat(drive.getRootDirectory()).isSameAs(testDirectory.getParent());
        assertThat(numbersOfDirectoriesBeforeTest+1).isEqualTo(drive.getRootDirectory().getNumberOfContainedDirectories());
        assertThat(testOutput.isEmpty());
    }

    @Test
    public void CmdMkDir_NoParameters_ErrorMessagePrinted()
    {
        executeCommand("mkdir");
        assertThat(numbersOfDirectoriesBeforeTest).isEqualTo(drive.getRootDirectory().getNumberOfContainedDirectories());
        assertThat(testOutput).contains("syntax of the command is incorrect");
    }

    @Test
    public void CmdMkDir_ParameterContainsBacklash_ErrorMessagePrinted()
    {
        executeCommand("mkdir c:\\test1");
        assertThat(testOutput).contains("At least one parameter");
        assertThat(testOutput).contains("path rather than a directory name");
    }

    @Test
    public void CmdMkDir_ParameterContainsBacklash_NoDirectoryCreated()
    {
        executeCommand("mkdir c:\\test1");
        assertThat(numbersOfDirectoriesBeforeTest).isEqualTo(drive.getRootDirectory().getNumberOfContainedDirectories());
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
        assertThat(directory1.getParent()).isSameAs(drive.getRootDirectory());
        assertThat(directory2.getParent()).isSameAs(drive.getRootDirectory());
        assertThat(directory3.getParent()).isSameAs(drive.getRootDirectory());
        assertThat(numbersOfDirectoriesBeforeTest + 3).isEqualTo(drive.getRootDirectory().getNumberOfContainedDirectories());
        assertThat(testOutput).isEmpty();
    }

    @Test
    public void CmdMkDir_AllParametersAreReset()
    {
        final String testDirName = "test1";
        executeCommand("mkdir " + testDirName);
        assertThat(numbersOfDirectoriesBeforeTest + 1).isEqualTo(drive.getRootDirectory().getNumberOfContainedDirectories());

        executeCommand("mkdir");
        assertThat(numbersOfDirectoriesBeforeTest + 1).isEqualTo(drive.getRootDirectory().getNumberOfContainedDirectories());
        assertThat(testOutput).contains("The syntax of the command is incorrect");
    }
}
