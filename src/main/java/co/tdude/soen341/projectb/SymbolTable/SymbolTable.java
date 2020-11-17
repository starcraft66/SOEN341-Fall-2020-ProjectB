package co.tdude.soen341.projectb.SymbolTable;

import java.util.HashMap;

public class SymbolTable implements ISymbolTable{
    HashMap<String, Integer> table;

    public SymbolTable() {
        table = new HashMap<>();
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
