/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package command.library;

import java.util.ArrayList;
import interfaces.IDrive;
import interfaces.IOutputter;
import command.framework.Command;
import filesystem.Directory;
import filesystem.FileSystemItem;

class CmdDir extends Command {
    private static final String SYSTEM_CANNOT_FIND_THE_PATH_SPECIFIED = "File Not Found"; 
    private Directory directoryToPrint;

    public CmdDir(String name, IDrive drive) {
        super(name, drive);
    }

    @Override
    protected boolean checkNumberOfParameters(int numberOfParameters) {
        return numberOfParameters == 0 || numberOfParameters == 1;
    }

    @Override
    protected boolean checkParameterValues(IOutputter outputter) {
        if (getParameterCount() == 0)
        {
            directoryToPrint = getDrive().getCurrentDirectory();
        }
        else
        {
            this.directoryToPrint = checkAndPreparePathParameter(getParameterAt(0), outputter);
        }
        return this.directoryToPrint != null;
    }

    private Directory checkAndPreparePathParameter(String pathName, IOutputter outputter) {
        FileSystemItem fsi = getDrive().getItemFromPath(pathName);
        if (fsi == null)
        {
            outputter.printLine(SYSTEM_CANNOT_FIND_THE_PATH_SPECIFIED);
            return null;
        }
        if (!fsi.isDirectory())
        {
            return fsi.getParent();
        }
        return (Directory)fsi;
    }

    @Override
    public void execute(IOutputter outputter) {
        printHeader(this.directoryToPrint, outputter);
        printContent(this.directoryToPrint.getContent(), outputter);
        printFooter(this.directoryToPrint, outputter);
    }

    private static void printHeader(Directory directoryToPrint, IOutputter outputter) {
        outputter.printLine("Directory of " + directoryToPrint.getPath());
        outputter.newLine();
    }

    private static void printContent(ArrayList<FileSystemItem> directoryContent, IOutputter outputter) {
        for (FileSystemItem item : directoryContent)
        {
            if (item.isDirectory())
            {
                outputter.print("<DIR>");
            }
            else
            {
                outputter.print("" + item.getSize());
            }

            outputter.print("\t" + item.getName());
            outputter.newLine();
        }
    }

    private static void printFooter(Directory directoryToPrint, IOutputter outputter) {
        outputter.printLine("\t" + directoryToPrint.getNumberOfContainedFiles() + " File(s)");
        outputter.printLine("\t" + directoryToPrint.getNumberOfContainedDirectories() + " Dir(s)");
    }
}
