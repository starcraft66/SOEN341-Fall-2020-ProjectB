package co.tdude.soen341.projectb.SymbolTable;

import java.util.HashMap;

public final class SymbolTable {

    private static HashMap<String, Integer> _mnemonicTable = new HashMap<>();

    static {
        InitializeMnemonicTable();
    }

    private static void InitializeMnemonicTable() {
        // One byte inherent ops
        registerMnemonic("halt", 0x00);
        registerMnemonic("pop", 0x01);
        registerMnemonic("dup", 0x02);
        registerMnemonic("exit", 0x03);
        registerMnemonic("ret", 0x04);
        registerMnemonic("not", 0x0C);
        registerMnemonic("and", 0x0D);
        registerMnemonic("or", 0x0E);
        registerMnemonic("xor", 0x0F);
        registerMnemonic("neg", 0x10);
        registerMnemonic("inc", 0x11);
        registerMnemonic("dec", 0x12);
        registerMnemonic("add", 0x13);
        registerMnemonic("sub", 0x14);
        registerMnemonic("mul", 0x15);
        registerMnemonic("div", 0x16);
        registerMnemonic("rem", 0x17);
        registerMnemonic("shl", 0x18);
        registerMnemonic("shr", 0x19);
        registerMnemonic("teq", 0x1A);
        registerMnemonic("tne", 0x1B);
        registerMnemonic("tlt", 0x1C);
        registerMnemonic("tgt", 0x1D);
        registerMnemonic("tle", 0x1E);
        registerMnemonic("tge", 0x1F);
    }

    public static void registerMnemonic(String symbol, int value) {
        _mnemonicTable.put(symbol, value);
    }

    public static int getMnemonic(String symbol) throws ValueNotExist {
        Integer ret = _mnemonicTable.get(symbol);
        if (ret == null) {
            throw new ValueNotExist();
        } else {
            return ret;
        }
    }

    public static boolean isMnemonicRegistered(String symbol) {
        Integer test = _mnemonicTable.get(symbol);
        return test != null;
    }
}

//public class SymbolTable implements ISymbolTable{
//    HashMap<String, Integer> table;
//
//    public SymbolTable() {
//        table = new HashMap<>();
//        InitializeSymbolTable();
//    }
//
//    private void InitializeSymbolTable() {
//        // One byte inherent ops
//        registerMnemonic("halt", 0x00);
//        registerMnemonic("pop", 0x01);
//        registerMnemonic("dup", 0x02);
//        registerMnemonic("exit", 0x03);
//        registerMnemonic("ret", 0x04);
//        registerMnemonic("not", 0x0C);
//        registerMnemonic("and", 0x0D);
//        registerMnemonic("or", 0x0E);
//        registerMnemonic("xor", 0x0F);
//        registerMnemonic("neg", 0x10);
//        registerMnemonic("inc", 0x11);
//        registerMnemonic("dec", 0x12);
//        registerMnemonic("add", 0x13);
//        registerMnemonic("sub", 0x14);
//        registerMnemonic("mul", 0x15);
//        registerMnemonic("div", 0x16);
//        registerMnemonic("rem", 0x17);
//        registerMnemonic("shl", 0x18);
//        registerMnemonic("shr", 0x19);
//        registerMnemonic("teq", 0x1A);
//        registerMnemonic("tne", 0x1B);
//        registerMnemonic("tlt", 0x1C);
//        registerMnemonic("tgt", 0x1D);
//        registerMnemonic("tle", 0x1E);
//        registerMnemonic("tge", 0x1F);
//    }
//
//    @Override
//    public void registerMnemonic(String symbol, int value) {
//        table.put(symbol, value);
//    }
//
//    @Override
//    public int get(String symbol) throws ValueNotExist {
//        Integer ret = table.get(symbol);
//        if (ret == null) {
//            throw new ValueNotExist();
//        } else {
//            return ret;
//        }
//    }
//
//    @Override
//    public boolean isRegistered(String symbol) {
//        Integer test = table.get(symbol);
//        return test != null;
//    }
//
//}
