package co.tdude.soen341.projectb.SymbolTable;

import java.util.HashMap;

public class SymbolTable implements ISymbolTable{
    HashMap<String, Integer> table;

    public SymbolTable() {
        table = new HashMap<>();
        InitializeSymbolTable();
    }

    private void InitializeSymbolTable() {
        // Enter all mnemonics as keywords in the symbol table...

        // One byte inherent ops
        // It feels weird to handle them here, but that's what the prof says
        registerSymbol("halt", 0x00);
        registerSymbol("pop", 0x01);
        registerSymbol("dup", 0x02);
        registerSymbol("exit", 0x03);
        registerSymbol("ret", 0x04);
        registerSymbol("not", 0x0C);
        registerSymbol("and", 0x0D);
        registerSymbol("or", 0x0E);
        registerSymbol("xor", 0x0F);
        registerSymbol("neg", 0x10);
        registerSymbol("inc", 0x11);
        registerSymbol("dec", 0x12);
        registerSymbol("add", 0x13);
        registerSymbol("sub", 0x14);
        registerSymbol("mul", 0x15);
        registerSymbol("div", 0x16);
        registerSymbol("rem", 0x17);
        registerSymbol("shl", 0x18);
        registerSymbol("shr", 0x19);
        registerSymbol("teq", 0x1A);
        registerSymbol("tne", 0x1B);
        registerSymbol("tlt", 0x1C);
        registerSymbol("tgt", 0x1D);
        registerSymbol("tle", 0x1E);
        registerSymbol("tge", 0x1F);
    }

    @Override
    public void registerSymbol(String symbol, int value) {
        table.put(symbol, value);
    }

    @Override
    public int get(String symbol) throws ValueNotExist {
        Integer ret = table.get(symbol);
        if (ret == null) {
            throw new ValueNotExist();
        } else {
            return ret;
        }
    }

    @Override
    public boolean isRegistered(String symbol) {
        Integer test = table.get(symbol);
        return test != null;
    }

}
