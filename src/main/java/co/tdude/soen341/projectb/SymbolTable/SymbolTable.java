package co.tdude.soen341.projectb.SymbolTable;

import java.util.HashMap;

/**
 *  Holds symbol tables for mnemonics and labels.
 */
public class SymbolTable<T> {
    /**
     * Hash map that holds the mnemonic strings as keys and their hex representations as values.
     */
    private HashMap<String, T> _table;

    /**
     * Constructor called when a new SymbolTable object is created.
     */
    public SymbolTable() {
        _table = new HashMap<>();
    }

    /**
     * Gets the hash map with all of the symbols.
     * @return The symbol table.
     */
    public HashMap<String, T> getMap() {
        return _table;
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
