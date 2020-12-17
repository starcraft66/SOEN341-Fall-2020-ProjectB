package co.tdude.soen341.projectb.Lexer.Tokens;

import co.tdude.soen341.projectb.SymbolTable.SymbolTable;

/**
 * Token used to identify a String within an assembly file
 */
public class StringToken extends OperandToken {
    /**
     * Constructor used to create a StringToken object.
     * @param lexeme: the lexeme
     */
    public StringToken (String lexeme){
        super (lexeme);
        type = TokenType.STRING;
    }

    /**
     * Resolves the operand.
     * @param currentAddr The current memory address of the operand.
     * @param labelTable The table of identified labels.
     * @return True if resolved. False if unresolvable.
     */
    @Override
    public boolean resolve(int currentAddr, SymbolTable<Integer> labelTable) {
        resolved=true;
        return resolved;
    }
}
