package co.tdude.soen341.projectb.Lexer.Tokens;

/**
 * Token used to identify an illegal entry in an assembly file.
 */
public class IllegalToken extends Token {
    /**
     * Constructor used to create an IllegalToken object.
     */
    public IllegalToken() {
        super("ILLEGAL");
        type = TokenType.ILLEGAL_CHAR;
    }
}
