package co.tdude.soen341.projectb.Lexer.Tokens;

import co.tdude.soen341.projectb.SymbolTable.SymbolTable;

/**
 * Token used to identify a number/operand within an assembly file.
 */
public class NumberToken extends OperandToken {

    /**
     * The value of the number/operand.
     */
    private int number;

    /**
     * Constructor used to create a NumberToken object.
     */
    public NumberToken(String lexeme) {
        super(lexeme);
        type = TokenType.NUMBER;
        // Should be guaranteed that getValue will return a valid int in the number subclass
        number = Integer.parseInt(getValue());
    }

    /**
     * Resolving a number always succeeds
     */
    @Override
    public boolean resolve(int currentAddr, SymbolTable<Integer> labelTable) {
        resolvedValue = number;
        resolved = true;
        return true;
    }

    /**
     * Accessor for the number field
     * @return the integer held in the number field
     */
    public int getNumber() {
        return number;
    }
}
