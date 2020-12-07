package co.tdude.soen341.projectb.Lexer.Tokens;

import co.tdude.soen341.projectb.SymbolTable.SymbolTable;

/**
 * Abtract class for an operand token associated with a mnemonic token.
 */
abstract public class OperandToken extends Token {
    /**
     * Checks if the operand has been resolved on either of the two assembler passes.
     */
    protected boolean resolved;

    /**
     * The resolved value from the passes.
     */
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

    /**
     * Verifies whether the operand is resolved.
     * @return True if resolved. False if not resolved.
     */
    public boolean isResolved() {
        return resolved;
    }

    /**
     * Gets the integer representation of the resolved value.
     * @return The resolved integer value.
     */
    public int getResolvedValue() {
        return resolvedValue;
    }

    /**
     * Resolves the operand.
     * @param currentAddr The current memory address of the operand.
     * @param labelTable The table of identified labels.
     * @return True if resolved. False if unresolvable.
     */
    public abstract boolean resolve(int currentAddr, SymbolTable<Integer> labelTable);
}
