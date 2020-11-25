package co.tdude.soen341.projectb.SymbolTable;

import java.util.HashMap;

/**
 * Static class that holds symbol tables for mnemonics and labels.
 */
public final class SymbolTable {

    /**
     * Hash map that holds the mnemonic strings as keys and their hex representations as values.
     */
    private static HashMap<String, Integer> _inherentTable = new HashMap<>();
    private static HashMap<String, Integer> _immediateTable = new HashMap<>();

    /**
     * Static constructor that initializes the mnemonic symbol table.
     */
    static {
        InitializeInherentTable();
        InitializeImmediateTable();
    }

    /**
     * Initializes the mnemonic symbol table.
     */
    private static void InitializeInherentTable() {
        // One byte inherent ops
        registerInherent("halt", 0x00);
        registerInherent("pop", 0x01);
        registerInherent("dup", 0x02);
        registerInherent("exit", 0x03);
        registerInherent("ret", 0x04);
        registerInherent("not", 0x0C);
        registerInherent("and", 0x0D);
        registerInherent("or", 0x0E);
        registerInherent("xor", 0x0F);
        registerInherent("neg", 0x10);
        registerInherent("inc", 0x11);
        registerInherent("dec", 0x12);
        registerInherent("add", 0x13);
        registerInherent("sub", 0x14);
        registerInherent("mul", 0x15);
        registerInherent("div", 0x16);
        registerInherent("rem", 0x17);
        registerInherent("shl", 0x18);
        registerInherent("shr", 0x19);
        registerInherent("teq", 0x1A);
        registerInherent("tne", 0x1B);
        registerInherent("tlt", 0x1C);
        registerInherent("tgt", 0x1D);
        registerInherent("tle", 0x1E);
        registerInherent("tge", 0x1F);
    }

    /**
     * Initializes the Immediate Opcode table
     */
    private static void InitializeImmediateTable() {
        registerImmediate("br.i5", 0x30);
        registerImmediate("brf.i5", 0x50);
        registerImmediate("enter.u5", 0x70);
        registerImmediate("ldc.i3", 0x90);
        registerImmediate("addv.u3", 0x98);
        registerImmediate("ldv.u3", 0xA0);
        registerImmediate("stv.u3", 0xA8);
    }

    /**
     * Registers a new inherent mnemonic key-value pair to the inherent mnemonic symbol table.
     * @param symbol
     * @param value
     */
    private static void registerInherent(String symbol, int value) {
        _inherentTable.put(symbol, value);
    }

    /**
     * Registers a new immediate mnemonic key-value pair to the immediate mnemonic symbol table.
     * @param symbol
     * @param value
     */
    private static void registerImmediate(String symbol, int value) {
        _immediateTable.put(symbol, value);
    }

    /**
     * Gets a mnemonic's value from the mnemonic symbol table.
     * @param symbol The string representation of the mnemonic.
     * @return The int representation of the mnemonic.
     * @throws ValueNotExist
     */
    public static int getMnemonic(String symbol) throws ValueNotExist {
        Integer ret = _inherentTable.get(symbol);
        if (ret == null) {
            ret = _immediateTable.get(symbol);
            if (ret == null) {
                throw new ValueNotExist();
            }
        }
        return ret;
    }

    /**
     * Verifies whether a specified mnemonic exists within the mnemonic symbol table.
     * @param symbol The mnemonic to be verified.
     * @return True if the mnemonic is found in the mnemonic symbol table.
     */
    public static boolean isMnemonicRegistered(String symbol) {
        return _inherentTable.containsKey(symbol) || _immediateTable.containsKey(symbol);
    }

    /**
     * Checks if symbol is an inherent mnemonic
     * @param symbol the mnemonic to be checked
     * @return True if symbol is inherent mnemonic
     */
    public static boolean isInherent(String symbol) {
        return _inherentTable.containsKey(symbol);
    }

    /**
     * Checks if symbol is an immediate mnemonic
     * @param symbol the mnemonic to be checked
     * @return True if symbol is immediate mnemonic
     */
    public static boolean isImmediate(String symbol) {
        return _immediateTable.containsKey(symbol);
    }
}
