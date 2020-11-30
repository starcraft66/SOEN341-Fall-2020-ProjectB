package co.tdude.soen341.projectb.Assembler;

import co.tdude.soen341.projectb.Lexer.Tokens.LabelToken;
import co.tdude.soen341.projectb.Node.Instruction;
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
     * Hold all label addresses
     */
    private SymbolTable<Integer> labelTable;

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

    int currentAddr;

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

        /*
        First pass. Responsibilities:
        Register all encountered labels into the label table.
        Resolve offsets when possible.
         */
        int currentAddr = 0;
        ArrayList<LabelToken> labelsToBeApplied = new ArrayList<>();
        for (LineStatement ls : _assemblyUnit) {
            if (ls.getLabel() != null) labelsToBeApplied.add(ls.getLabel());
            if (ls.getInst() != null) {
                // Bind all labels that were prior to this instruction
                for (LabelToken lt : labelsToBeApplied) {
                    labelTable.put(lt.getValue(), currentAddr);
                }
                // If the instruction requires resolving, attempt to resolve it
                if (!ls.getInst().isResolved()) {
                    ls.getInst().resolve(currentAddr, labelTable);
                }
                currentAddr++;
            }
        }

        /*
        Second pass. Responsibilities:
        If an offset is unresolved, resolve it. If it still can't be resolved,
        throw an error as you have an undefined label.
         */

        currentAddr = 0;
        int lineCount = 0;
        for (LineStatement ls : _assemblyUnit) {
            lineCount++;
            // Process the line statement and feed it to the active writers
            if (ls.getInst() != null) {
                if (!ls.getInst().isResolved()) {
                    if (!ls.getInst().resolve(currentAddr, labelTable)) {
                        // If it was unable to be resolved, fatal error, unresolvable offset
                        // Todo implement error reporter here?
                        throw new RuntimeException("Unresolvable offset");
                    }
                }
                int instVal = getBinaryRepresentation(ls.getInst());
                binwriter.write(instVal); // Write raw bytes of the opcode+operand representation to the .exe
            }
            if (createListing) {
                String strRepresentation = getStringRepresentation(lineCount, currentAddr, ls);
                System.out.print(strRepresentation);
                lstwriter.write(strRepresentation);
            }
            // Increment addr if there was an instruction
            if (ls.getInst() != null) currentAddr++;
        }

        binwriter.close();
        if (lstwriter != null) lstwriter.close();
    }

    /**
     * Convert an instruction to binary. The instruction MUST BE RESOLVED
     * @param inst the instruction to convert
     * @return an integer value representing the opcode+operand combination
     */
    private int getBinaryRepresentation(Instruction inst) {
        int binrep = inst.getMnemonic().getOpcode();
        if (inst.getOperand() != null) {
            binrep += inst.getOperand().getResolvedValue();
        }
        return binrep;
    }

    /**
     * Returns a string representation of the line+linestatement combination
     * @param lineCount The current line of the instruction
     * @param ls the linestatement to convert
     * @return a 45 character wide representation of ls
     */
    private String getStringRepresentation(int lineCount, int currentAddr, LineStatement ls) {
        var hexInstruction = Integer.toHexString(getBinaryRepresentation(ls.getInst()));
        return String.format("%-15s%-15s%-15s%-15s\n", lineCount, currentAddr, hexInstruction, ls.toString());
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
        writer.write(String.format("%-15s%-15s%-15s%-15s\n%n","Line", "Addr", "Hex Code", "Assembly Code"));
    }

    /**
     * Print a header to console
     */
    private void PrintHeader() {
        System.out.printf("%-15s%-15s%-15s%-15s\n%n","Line", "Addr", "Hex Code", "Assembly Code");
    }
}
