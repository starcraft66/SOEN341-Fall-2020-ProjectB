package co.tdude.soen341.projectb.Lexer.Tokens;

import co.tdude.soen341.projectb.SymbolTable.SymbolTable;

/**
 * Token used to identify a label within an assembly file.
 */
public class LabelToken extends OperandToken {
    /**
     * Constructor used to create a LabelToken object.
     */
    public LabelToken(String lexeme) {
        super(lexeme);
        type = TokenType.IDENT;
    }

    /**
     * Attempts to resolve the offset against the SymbolTable
     * @param labelTable the symboltable to check if the  can be calculated already
     * @return true if the offset was resolved
     */
    @Override
    public boolean resolve(int currentAddr, SymbolTable<Integer> labelTable) {
        // Integer to allow nulls
        Integer lookupResult = labelTable.get(this.getValue());
        if (lookupResult != null) {
            resolvedValue = lookupResult - currentAddr;
            resolved=true;
            return true;
        } else {
            return false;
        }
    }
}
