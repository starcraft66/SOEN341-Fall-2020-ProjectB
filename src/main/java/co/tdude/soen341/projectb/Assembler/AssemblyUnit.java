package co.tdude.soen341.projectb.Assembler;

import co.tdude.soen341.projectb.Lexer.Tokens.DirectiveToken;
import co.tdude.soen341.projectb.ErrorReporter.Error;
import co.tdude.soen341.projectb.ErrorReporter.ErrorReporter;
import co.tdude.soen341.projectb.Lexer.Tokens.LabelToken;
import co.tdude.soen341.projectb.Node.Instruction;
import co.tdude.soen341.projectb.Node.LineStatement;
import co.tdude.soen341.projectb.SymbolTable.SymbolTable;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;

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

    /**
    *An ErrorReporter object to store error messages
    */
    private ErrorReporter ereporter;

    int currentAddr;

    /**
     * Constructor used to instantiate an AssemblyUnit object.
     * @param assemblyUnit The list of LineStatement objects that models the Assembly Unit.
     * @param listingFilePath The path for the listing file
     * @param binaryFilePath The path for the binary file
     */
    public AssemblyUnit(ArrayList<LineStatement> assemblyUnit, String listingFilePath, String binaryFilePath) {
        _assemblyUnit = assemblyUnit;
        labelTable = new SymbolTable<>();
        _listingFilePath = listingFilePath;
        _binaryFilePath = binaryFilePath;
        binwriter= null;
        lstwriter= null;
        ereporter=null;
    }

    /**
     * Generate a listing file in the user's home directory.
     * @param createListing create a listing file and print it to console
     * @throws IOException if an unspecified IO error occurs
     */
    public void Assemble(boolean createListing) throws IOException {
        try{binwriter = setupBinaryFile();}
        catch (IOException e)
        {
            Error e1=new Error();
            e1.generatemsg(Error.err_type.IOERROR, null, null);
            ereporter.record(e1);
        }

        if (createListing) {
            try {lstwriter = setupListingFile();} // For the writer
                catch (IOException e){
                    Error e1=new Error();
                    e1.generatemsg(Error.err_type.IOERROR, null, null);
                    ereporter.record(e1);
                }
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

                labelsToBeApplied.clear();

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
                        Error e1=new Error();
                        e1.generatemsg(Error.err_type.UNRESOLVABLE, null, null);
                        ereporter.record(e1);
                        throw new RuntimeException();
                    }
                }

                if (ls.getInst().getMnemonic() instanceof DirectiveToken) {
                    var directiveValue = ls.getInst().getOperand().getValue();
                    directiveValue += "\0";
                    char[] directiveArray = directiveValue.toCharArray();

                    int ASCIIEquivalent;

                    for (var ch : directiveArray) {
                        if (ch != '"') {
                            ASCIIEquivalent = (int) ch;
                            binwriter.write(ASCIIEquivalent);
                        }
                    }
                }
                else {
                    int instVal = getBinaryRepresentation(ls.getInst());
                    binwriter.write(instVal); // Write raw bytes of the opcode+operand representation to the .exe
                }
            }

            if (createListing) {
                String strRepresentation = getStringRepresentation(lineCount, currentAddr, ls);
                Logger.getLogger("").info(strRepresentation);
                lstwriter.write(strRepresentation + "\n");
            }

            // Increment addr if there was an instruction
            if (ls.getInst() != null) {
                currentAddr++;
                int opsz = ls.getInst().getMnemonic().getOpsize();
                if (opsz >= 8) {
                    currentAddr += opsz/8;
                }
            }
        }

        // Print label table to console and to file
        if (createListing) {
            Logger.getLogger("").info("Label Table");
            String header = String.format("%-15s%-15s", "Label", "Addr");
            Logger.getLogger("").info(header);
            lstwriter.write("\n" + header + "\n\n");
            for (Map.Entry<String, Integer> lt : labelTable.getMap().entrySet()) {
                String line = String.format("%-15s%-15s", lt.getKey(), lt.getValue());
                Logger.getLogger("").info(line);
                lstwriter.write(line + "\n");
            }
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
        // TODO: Add checking for negative/positive bounds
        int binrep = inst.getMnemonic().getOpcode();

        if (inst.getOperand() != null) {
            int val = inst.getOperand().getResolvedValue();

            if (val < 0) {
                if (!inst.getMnemonic().isSigned()) {
                    // if it's a negative number and signed operands are disallowed:
                    // fail
                    Error e1=new Error();
                    e1.generatemsg(Error.err_type.NEGATIVE, null, null);
                    ereporter.record(e1);
                    throw new RuntimeException();
                }
                else {
                    // Signed numbers are allowed
                    val = (int)Math.pow(2, inst.getMnemonic().getOpsize()) + val;
                }
            }

            if (inst.getRelative()) {
                if (inst.getMnemonic().getOpsize() == 8) {
                    byte[] data = new byte[2];

                    data[0] = (byte) binrep;
                    data[1] = (byte) val;

                    var byteToInt = ((data[1] & 0xFF) << 0) | ((data[0] & 0xFF) << 8);

                    return byteToInt;
                }
                else if (inst.getMnemonic().getOpsize() == 16) {
                    if (val > 0) {
                        byte[] data = new byte[3];

                        data[0] = (byte) binrep;
                        data[1] = (byte) ((val >>> 8) & 0xFF); // >>> means unsigned right shift
                        data[2] = (byte) (val & 0xFF); //0xFF masks all but the lowest eight bits

                        var byteToInt = ((data[2] & 0xFF) << 0) | ((data[1] & 0xFF) << 8) | ((data[0] & 0xFF) << 16);

                        return byteToInt;
                    }
                    else {
                        byte[] data = new byte[3];

                        data[0] = (byte) binrep;
                        data[1] = (byte) ((val >> 8) & 0xFF); // >> means signed right shift
                        data[2] = (byte) (val & 0xFF); //0xFF masks all but the lowest eight bits

                        var byteToInt = ((data[2] & 0xFF) << 0) | ((data[1] & 0xFF) << 8) | ((data[0] & 0xFF) << 16);

                        return byteToInt;
                    }
                }
            }

            binrep += val;
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
        if (ls.getInst().getMnemonic() instanceof DirectiveToken) {
            var directiveValue = ls.getInst().getOperand().getValue();
            directiveValue += "\0";
            char[] directiveArray = directiveValue.toCharArray();
            String hexInstruction = "";
            int ASCIIEquivalent;

            for (var ch : directiveArray) {
                ASCIIEquivalent = (int) ch;
                if (ASCIIEquivalent == 0) {
                    hexInstruction += "00";
                }
                else {
                    hexInstruction += Integer.toHexString(ASCIIEquivalent);
                }
            }

            var hexAddr = Integer.toHexString(currentAddr);

            return String.format("%-15s%-15s%-15s%-15s", lineCount, hexAddr, hexInstruction, ls.toString());
        }
        else {
            var hexInstruction = Integer.toHexString(getBinaryRepresentation(ls.getInst()));
            var hexAddr = Integer.toHexString(currentAddr);
            return String.format("%-15s%-15s%-15s%-15s", lineCount, hexAddr, hexInstruction, ls.toString());
        }
    }

    /**
     * Opens but _does not close_ a binary file writer
     * @return A FileWriter opened with a new binary file
     * @throws IOException if an IO error occurs
     */
    private BufferedOutputStream setupBinaryFile() throws IOException  {
        String fileName = _binaryFilePath + ".exe";
        File dstFile = new File(fileName);

        return new BufferedOutputStream(new FileOutputStream(dstFile));


    }

    /**
     * Opens but _does not close_ a listing file writer
     * @return A FileWriter opened with a new listing file
     * @throws IOException if an IO error occurs
     */
    private FileWriter setupListingFile() throws IOException{
        String fileName = _listingFilePath + ".lst";
        File dstFile = new File(fileName);

        FileWriter writer = new FileWriter(dstFile);

        try {
            WriteHeader(writer);
        }
        catch (IOException e){
            Error e1=new Error();
            e1.generatemsg(Error.err_type.IOERROR, null, null);
            ereporter.record(e1);
        }
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
        Logger.getLogger("").info(String.format("%-15s%-15s%-15s%-15s","Line", "Addr", "Hex Code", "Assembly Code"));
    }
}
