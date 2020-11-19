package co.tdude.soen341.projectb.Lexer.Tokens;

/**
 * Token used to identify a mnemonic within an assembly file.
 */
public class MnemonicToken extends Token {
    /**
     * Constructor used to create a MnemonicToken object.
     */
    public MnemonicToken(String lexeme) {
        super(lexeme);
        type = TokenType.MNEMONIC;
    }
}
