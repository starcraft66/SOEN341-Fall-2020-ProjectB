package co.tdude.soen341.projectb.SymbolTable;

import java.util.HashMap;

public class SymbolTable implements ISymbolTable{
    HashMap<String, Integer> table;

    public SymbolTable() {
        table = new HashMap<>();
        // One byte inherent ops
        table.put("halt", 0x00);
        table.put("pop", 0x01);
        table.put("dup", 0x02);
        table.put("exit", 0x03);
        table.put("ret", 0x04);
        table.put("not", 0x0C);
        table.put("and", 0x0D);
        table.put("or", 0x0E);
        table.put("xor", 0x0F);
        table.put("neg", 0x10);
        table.put("inc", 0x11);
        table.put("dec", 0x12);
        table.put("add", 0x13);
        table.put("sub", 0x14);
        table.put("mul", 0x15);
        table.put("div", 0x16);
        table.put("rem", 0x17);
        table.put("shl", 0x18);
        table.put("shr", 0x19);
        table.put("teq", 0x1A);
        table.put("tne", 0x1B);
        table.put("tlt", 0x1C);
        table.put("tgt", 0x1D);
        table.put("tle", 0x1E);
        table.put("tge", 0x1F);
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
}
