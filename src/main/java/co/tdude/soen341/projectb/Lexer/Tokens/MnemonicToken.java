package co.tdude.soen341.projectb.Lexer.Tokens;

/**
 * Token used to identify a mnemonic within an assembly file.
 */
public class MnemonicToken extends Token {

    int opcode;
    boolean signed;
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

    public int getOpcode() {
        return opcode;
    }

    public boolean isSigned() {
        return signed;
    }

    public int getOpsize() {
        return opsize;
    }
}
