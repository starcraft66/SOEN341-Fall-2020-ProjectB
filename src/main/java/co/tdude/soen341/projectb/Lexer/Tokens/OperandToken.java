package co.tdude.soen341.projectb.Lexer.Tokens;

import co.tdude.soen341.projectb.SymbolTable.SymbolTable;

abstract public class OperandToken extends Token{

    protected boolean resolved;
    protected int resolvedValue;

    /**
     * Constructor used to create a Token object.
     *
     * @param lexeme
     */
    public OperandToken(String lexeme) {
        super(lexeme);
        resolved = false;
    }
    //TODO: Javadoc
    public boolean isResolved() {
        return resolved;
    }

    public int getResolvedValue() {
        return resolvedValue;
    }

    public abstract boolean resolve(int currentAddr, SymbolTable<Integer> labelTable);
}
