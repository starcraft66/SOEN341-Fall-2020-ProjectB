package co.tdude.soen341.projectb.Assembler;

import co.tdude.soen341.projectb.Node.Instruction;
import co.tdude.soen341.projectb.Node.LineStatement;
import co.tdude.soen341.projectb.SymbolTable.SymbolTable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Holds the list of LineStatement objects that represents the entire .asm file.
 */
public class AssemblyUnit {

    /**
     * List of LineStatement objects.
     */
    private ArrayList<LineStatement> _assemblyUnit;
    private String _outputFilePath;

    /**
     * Constructor used to instantiate an AssemblyUnit object.
     * @param assemblyUnit The list of LineStatement objects that models the Assembly Unit.
     * @param outputFilePath The path for the output file name  
     */
    public AssemblyUnit(ArrayList<LineStatement> assemblyUnit, String outputFilePath) {
        _assemblyUnit = assemblyUnit;
        _outputFilePath = outputFilePath;
    }

    /**
     * Generate a listing file in the user's home directory.
     * @throws IOException
     */
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

    /**
     * Gets the list of LineStatement objects.
     * @return ArrayList of LineStatements that make up the AssemblyUnit.
     */
    public ArrayList<LineStatement> getAssemblyUnit() {
        return _assemblyUnit;
    }

    /**
     * Writes a header to the listing file.
     * @param writer FileWriter used to write to the listing file.
     * @throws IOException
     */
    private void WriteHeader(FileWriter writer) throws IOException {
        writer.write("OBJ       " + "LINE   " + "SOURCE\n");
    }


}
