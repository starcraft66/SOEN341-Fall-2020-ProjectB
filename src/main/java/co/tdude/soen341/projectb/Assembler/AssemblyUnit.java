package co.tdude.soen341.projectb.Assembler;

import co.tdude.soen341.projectb.Node.LineStatement;
import co.tdude.soen341.projectb.SymbolTable.SymbolTable;

import java.io.*;
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
     * A writer to output binary bytes to a the .exe file
     */
    private BufferedOutputStream binwriter;

    /**
     * A writer to generate the listing file when requested
     */
    private FileWriter lstwriter;

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
        binwriter= null;
        lstwriter= null;
    }

    /**
     * Generate a listing file in the user's home directory.
     * @param createListing create a listing file and print it to console
     * @throws IOException if an unspecified IO error occurs
     */
    public void Assemble(boolean createListing) throws IOException {
        binwriter = setupBinaryFile();
        if (createListing) {
            lstwriter = setupListingFile(); // For the writer
            PrintHeader(); // For the console
        }

        int lineCount = 0;
        for (LineStatement ls : _assemblyUnit) {
            lineCount++;
            // Process the line statement and feed it to the active writers
            int instVal = getBinaryRepresentation(ls);
            binwriter.write(instVal); // Write raw bytes of the opcode+operand representation to the .exe
            if (createListing) {
                String strRepresentation = getStringRepresentation(lineCount, ls);
                System.out.print(strRepresentation);
                lstwriter.write(strRepresentation);
            }
        }

        binwriter.close();
        if (lstwriter != null) lstwriter.close();
    }

    /**
     * Convert the linestatement to integer (binary) representation
     * @param ls the linestatement to convert
     * @return an integer value representing the opcode+operand combination
     */
    private int getBinaryRepresentation(LineStatement ls) {
        var instruction = ls.getInst();
        String mnemonic = instruction.get_mnemonic();
        String opcode = instruction.get_operand();

        int instructionValue = SymbolTable.getMnemonic(mnemonic);
        if (opcode != null) {
            instructionValue += Integer.parseInt(opcode);
        }
        return instructionValue;
    }

    /**
     * Returns a string representation of the line+linestatement combination
     * @param lineCount The current line of the instruction
     * @param ls the linestatement to convert
     * @return a 45 character wide representation of ls
     */
    private String getStringRepresentation(int lineCount, LineStatement ls) {
        var hexInstruction = Integer.toHexString(getBinaryRepresentation(ls));
        return String.format("%-15s%-15s%-15s\n", lineCount, hexInstruction, ls.toString());
    }

    /**
     * Opens but _does not close_ a binary file writer
     * @return A FileWriter opened with a new binary file
     * @throws IOException if an IO error occurs
     */
    private BufferedOutputStream setupBinaryFile() throws IOException {
        String fileName = _binaryFilePath + ".exe";
        File dstFile = new File(fileName);

        return new BufferedOutputStream(new FileOutputStream(dstFile));
    }

    /**
     * Opens but _does not close_ a listing file writer
     * @return A FileWriter opened with a new listing file
     * @throws IOException if an IO error occurs
     */
    private FileWriter setupListingFile() throws IOException {
        String fileName = _listingFilePath + ".lst";
        File dstFile = new File(fileName);

        FileWriter writer = new FileWriter(dstFile);

        WriteHeader(writer);

        return writer;
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
        writer.write(String.format("%-15s%-15s%-15s\n","Line", "Hex Code", "Assembly Code"));
    }

    /**
     * Print a header to console
     */
    private void PrintHeader() {
        System.out.printf("%-15s%-15s%-15s\n%n","Line", "Hex Code", "Assembly Code");
    }
}
