package co.tdude.soen341.projectb.SymbolTable;

public interface ISymbolTable {
    void registerSymbol(String symbol, int value);
    int get(String symbol) throws ValueNotExist;
}
