package co.tdude.soen341.projectb.Lexer.Tokens;

/**
 * Token used to identify a mnemonic within an assembly file.
 */
public class MnemonicToken extends Token {
    /**
     * The opcode associated with the mnemonic.
     */
    int opcode;

    /**
     * The boolean value that states whether the mnemonic is signed or unsigned.
     */
    boolean signed;

    /**
     * The size of the operand.
     */
    int opsize;

    /**
     * Constructor used to create a MnemonicToken object.
     */
    public MnemonicToken(String lexeme, int opcode, boolean signed, int opsize) {
        super(lexeme);
        type = TokenType.MNEMONIC;
        this.opcode=opcode;
        this.signed=signed;
        this.opsize=opsize;
    }

    /**
     * Gets the opcode associated with the mnemonic.
     * @return The integer representation of the opcode.
     */
    public int getOpcode() {
        return opcode;
    }

    /**
     * Checks whether the mnemonic is signed or unsigned.
     * @return True if signed. False if unsigned.
     */
    public boolean isSigned() {
        return signed;
    }

    /**
     * Gets the size of the opcode.
     * @return The opcode size.
     */
    public int getOpsize() {
        return opsize;
    }
}
