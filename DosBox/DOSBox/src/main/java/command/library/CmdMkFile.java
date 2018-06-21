/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package command.library;

import interfaces.IDrive;
import interfaces.IOutputter;
import command.framework.Command;
import filesystem.File;

class CmdMkFile extends Command {
    private static final String PARAMETER_CONTAINS_BACKLASH = "At least one parameter denotes a path rather than a directory name.";

	public CmdMkFile(String cmdName, IDrive drive) {
		super(cmdName, drive);
	}

	@Override
	public void execute(IOutputter outputter) {
		String fileName = this.getParameterAt(0);
		String fileContent = this.getParameterAt(1);
		File newFile = new File(fileName, fileContent);
		this.getDrive().getCurrentDirectory().add(newFile);
	}

	@Override
	protected boolean checkNumberOfParameters(int numberOfParametersEntered) {
		return true;
	}

    @Override
    protected boolean checkParameterValues(IOutputter outputter) {
        for(int i=0 ; i<getParameterCount() ; i++)
        {
            // Do not allow "mkfile c:\temp\text.txt" to keep the command simple
            if (parameterContainsBacklashes(getParameterAt(i), outputter))
                return false;
        }

        return true;
    }

    private static boolean parameterContainsBacklashes(String parameter, IOutputter outputter) {
        if (parameter.contains("\\") || parameter.contains("/"))
        {
            outputter.printLine(PARAMETER_CONTAINS_BACKLASH);
            return true;
        }
        return false;
    }
}
