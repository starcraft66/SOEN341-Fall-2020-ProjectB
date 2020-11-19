package co.tdude.soen341.projectb.Lexer.Tokens;

/**
 * Token used to store an identifier/label within an assembly file.
 */
public class IdentifierToken extends Token {
    /**
     * Constructor used to create an IdentifierToken object.
     */
    public IdentifierToken(String lexeme) {
        super(lexeme);
        type = TokenType.IDENT;
    }
}
