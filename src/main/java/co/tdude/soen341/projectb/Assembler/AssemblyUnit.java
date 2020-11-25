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

    /**
     * The path to the output listing file
     */
    private String _listingFilePath;

    /**
     * The path to the output binary file
     */
    private String _binaryFilePath;

    /**
     * Constructor used to instantiate an AssemblyUnit object.
     * @param assemblyUnit The list of LineStatement objects that models the Assembly Unit.
     * @param listingFilePath The path for the listing file
     * @param binaryFilePath The path for the binary file
     */
    public AssemblyUnit(ArrayList<LineStatement> assemblyUnit, String listingFilePath, String binaryFilePath) {
        _assemblyUnit = assemblyUnit;
        _listingFilePath = listingFilePath;
        _binaryFilePath = binaryFilePath;
    }

    /**
     * Generate a listing file in the user's home directory.
     * @throws IOException
     */
    public void Assemble() throws IOException {
        GenerateListingFile();
        GenerateBinaryFile();
    }

    private void GenerateListingFile() throws IOException {
        int lineCount = 1;
        String fileName = _listingFilePath + ".lst";
        File dstFile = new File(fileName);

        FileWriter writer = new FileWriter(dstFile);

        WriteHeader(writer);

        for(LineStatement lineStatement: _assemblyUnit) {
            Instruction mnemonic = lineStatement.getInst();

            if (mnemonic == null) {
                //TODO: eventually going to have to fix this and delete all the empty line statements from the assembly unit object
                continue;
            }
            else {
                int value = SymbolTable.getMnemonic(mnemonic.toString());
                String hex = Integer.toHexString(value);

                writer.write(String.format("%-15s%-15s%-15s\n", lineCount, hex, lineStatement.getInst()));;

                ++lineCount;
            }
        }

        writer.close();
    }

    private void GenerateBinaryFile() throws IOException {
        String fileName = _binaryFilePath + ".exe";
        File dstFile = new File(fileName);

        FileWriter writer = new FileWriter(dstFile);

        for(LineStatement lineStatement: _assemblyUnit) {
            Instruction mnemonic = lineStatement.getInst();

            if (mnemonic == null) {
                //TODO: eventually going to have to fix this and delete all the empty line statements from the assembly unit object
                continue;
            }
            else {
                int value = SymbolTable.getMnemonic(mnemonic.toString());

                writer.write(String.format("%8s", Integer.toBinaryString(value) + '\n').replace(' ', '0'));
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
        writer.write(String.format("%-15s%-15s%-15s\n","Line", "Obj", "Source"));
    }


}
