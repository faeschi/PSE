/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package command.library;

import org.junit.Before;
import org.junit.Test;

public class ExtendedCmdMkDirTestWithGherkin extends CmdTest {

	@Before
	public void setUp() {
		// Add all commands which are necessary to execute this unit test
		// Important: Other commands are not available unless added here.
		this.commandInvoker.addCommand(new CmdMkDir("mkdir", this.drive));
	}

    @Test
    public void CmdMkDir_CreateNewDirectory_NewDirectoryIsAdded()
    {
    	givenStandardDirectoryStructure();
    	andCurrentDirectoryIsRoot();
    	whenExecutingCommand("mkdir test1");
    	thenDirectoryIsCreated("C:\\test1");
    }

	@Test
    public void CmdMkDir_CreateNewDirectory_NewDirectoryIsAddedToCorrectLocation()
    {
		givenStandardDirectoryStructure();
		andCurrentDirectoryIs(this.subDir1);
		whenExecutingCommand("mkdir test1");
		thenDirectoryIsCreated("C:\\subDir1\\test1");
    }

	@Test
    public void CmdMkDir_SingleLetterDirectory_NewDirectoryIsAdded()
    {
		givenStandardDirectoryStructure();
		andCurrentDirectoryIsRoot();
		whenExecutingCommand("mkdir a");
		thenDirectoryIsCreated("C:\\a");
		thenNoErrorMessageIsPrinted();
    }

	@Test
    public void CmdMkDir_NoParameters_ErrorMessagePrinted()
    {
		givenEmptyDirectoryStructure();
		whenExecutingCommand("mkdir");
		thenNoDirectoryIsCreated();
		thenOutputContains("syntax of the command is incorrect");
    }

	@Test
    public void CmdMkDir_ParameterContainsBacklash_ErrorMessagePrinted()
    {
		givenEmptyDirectoryStructure();
		whenExecutingCommand("mkdir c:\\test1");
		thenOutputContains("At least one parameter");
		thenOutputContains("path rather than a directory name");
    }

    @Test
    public void CmdMkDir_ParameterContainsBacklash_NoDirectoryCreated()
    {
		givenEmptyDirectoryStructure();
    	whenExecutingCommand("mkdir c:\\test1");
    	thenNoDirectoryIsCreated();
    }

    @Test
    public void CmdMkDir_SeveralParameters_SeveralNewDirectoriesCreated()
    {
    	givenStandardDirectoryStructure();
    	andCurrentDirectoryIsRoot();
        whenExecutingCommand("mkdir test1 test2 test3");
        thenDirectoryIsCreated("c:\\test1");
        thenDirectoryIsCreated("c:\\test2");
        thenDirectoryIsCreated("c:\\test3");
        thenNumberOfDirectoriesCreatedIs(3);
    }

    @Test
    public void CmdMkDir_AllParametersAreReset()
    {
    	givenStandardDirectoryStructure();
    	andCurrentDirectoryIsRoot();
        whenExecutingCommand("mkdir test1");
        whenExecutingCommand("mkdir");  // Bad programming may lead to storing test1 as parameter and creating a new test1 directory with the empty mkdir command.
        thenNumberOfDirectoriesCreatedIs(1);
        thenOutputContains("The syntax of the command is incorrect");
    }
}
