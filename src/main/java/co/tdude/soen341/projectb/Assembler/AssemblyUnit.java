package co.tdude.soen341.projectb.Assembler;

import co.tdude.soen341.projectb.Node.LineStatement;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AssemblyUnit {

    private ArrayList<LineStatement> _assemblyUnit;

    public AssemblyUnit(ArrayList<LineStatement> assemblyUnit) {
        _assemblyUnit = assemblyUnit;
    }

    // TODO: FOR NOW THIS IS ONLY COPYING THE FILE. THIS WILL CHANGE
    public void GenerateListing() throws IOException {

        String directoryName = "C:\\Users\\karim\\OneDrive\\Desktop";
        String fileName = "assemblyListingFile.lst";

        File directory = new File(directoryName);
        if (!directory.exists()){
            directory.mkdir();
        }

        File dstFile = new File(directoryName + "/" + fileName);

        FileWriter writer = new FileWriter(dstFile);
        for(LineStatement lineStatement: _assemblyUnit) {
            var mnemonic = lineStatement.getInst();
            if (mnemonic == null) {
                continue;
            }
            else {
                writer.write(lineStatement.getInst() + "\n");
            }
        }
        writer.close();

//        int EOF = -1;
//
//        //TODO: make this directory dynamic
//        String directoryName = "C:\\Users\\karim\\OneDrive\\Desktop";
//        String fileName = "assemblyListingFile.lst";
//
//        File directory = new File(directoryName);
//        if (!directory.exists()){
//            directory.mkdir();
//        }
//
//        File dstFile = new File(directoryName + "/" + fileName);
//
//        FileChannel src = new FileInputStream(srcFile).getChannel();
//        FileChannel dest = new FileOutputStream(dstFile).getChannel();
//        dest.transferFrom(src, 0, src.size());
    }
}
