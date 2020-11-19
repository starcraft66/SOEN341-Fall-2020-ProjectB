package co.tdude.soen341.projectb.Lexer.Tokens;

/**
 * Token used to identify a comment within the assembly file.
 */
public class CommentToken extends Token{
    /**
     * Constructor used to create a CommentToken object.
     */
    public CommentToken(String lexeme) {
        super(lexeme);
        type = TokenType.COMMENT;
    }
}
