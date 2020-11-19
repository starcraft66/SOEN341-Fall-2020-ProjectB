package co.tdude.soen341.projectb.Lexer.Tokens;

/**
 * Token used to identify an end of file marker.
 */
public class EOFToken extends Token {
    /**
     * Constructor used to create an EOFToken object.
     */
    public EOFToken() {
        super("EOF");
        this.type = TokenType.EOF;
    }
}
