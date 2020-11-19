package co.tdude.soen341.projectb.Lexer.Tokens;

/**
 * Token used to identify an end of line marker.
 */
public class EOLToken extends Token{
    /**
     * Constructor used to create an EOLToken object.
     */
    public EOLToken() {
        super("\n");
        type = TokenType.EOL;
    }

}
