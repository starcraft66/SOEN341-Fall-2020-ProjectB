package co.tdude.soen341.projectb.Assembler;

import co.tdude.soen341.projectb.Node.Instruction;
import co.tdude.soen341.projectb.Node.LineStatement;
import co.tdude.soen341.projectb.SymbolTable.SymbolTable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AssemblyUnit {

    private ArrayList<LineStatement> _assemblyUnit;
    private String _outputFilePath;

    /**
     * @param outputFilePath The path for the output file name  
     */
    public AssemblyUnit(ArrayList<LineStatement> assemblyUnit, String outputFilePath) {
        _assemblyUnit = assemblyUnit;
        _outputFilePath = outputFilePath;
    }

    // TODO: FOR NOW THIS IS ONLY COPYING THE FILE. THIS WILL CHANGE
    public void GenerateListing() throws IOException {

        int lineCount = 1;
        String fileName = _outputFilePath + ".lst";
        File dstFile = new File(fileName);

        FileWriter writer = new FileWriter(dstFile);

        WriteHeader(writer);

        for(LineStatement lineStatement: _assemblyUnit) {
            Instruction mnemonic = lineStatement.getInst();

            if (mnemonic == null) {
                continue;
            }
            else {
                int value = SymbolTable.getMnemonic(mnemonic.toString());
                String hex = Integer.toHexString(value);

                if (hex.length() == 1 && String.valueOf(lineCount).length() == 1) {
                    writer.write( hex + "          " + lineCount + "      " + lineStatement.getInst() + "\n");
                }
                else if (hex.length() == 1 && String.valueOf(lineCount).length() == 2) {
                    writer.write( hex + "          " + lineCount + "     " + lineStatement.getInst() + "\n");
                }
                else {
                    writer.write(hex + "         " + lineCount + "     " + lineStatement.getInst() + "\n");
                }
                ++lineCount;
            }
        }

        writer.close();
    }

    public ArrayList<LineStatement> getAssemblyUnit() {
        return _assemblyUnit;
    }

    private void WriteHeader(FileWriter writer) throws IOException {
        writer.write("OBJ       " + "LINE   " + "SOURCE\n");
    }
}
