package co.tdude.soen341.projectb.Lexer.Tokens;

/**
 * Token used to identify a label within an assembly file.
 */
public class LabelToken extends Token {
    /**
     * Constructor used to create a LabelToken object.
     */
    public LabelToken(String lexeme) {
        super(lexeme);
        type = TokenType.IDENT;
    }
}
