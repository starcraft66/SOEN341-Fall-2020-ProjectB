package co.tdude.soen341.projectb.SymbolTable;

import co.tdude.soen341.projectb.Lexer.Tokens.Token;

import java.util.HashMap;

/**
 * Static class that holds symbol tables for mnemonics and labels.
 */
public class SymbolTable<T> {

    public HashMap<String, T> getMap() {
        return _table;
    }

    /**
     * Hash map that holds the mnemonic strings as keys and their hex representations as values.
     */
    private HashMap<String, T> _table;

    public SymbolTable() {
        _table = new HashMap<>();
    }

    /**
     * Put a string-T combination in the symboltable
     * @param key
     * @param val
     */
    public void put(String key, T val) {
        _table.put(key, val);
    }

    /**
     * Return the value stored at the specified key in the symbol table
     * @param key
     * @return
     */
    public T get(String key) {
        return _table.get(key);
    }
}
