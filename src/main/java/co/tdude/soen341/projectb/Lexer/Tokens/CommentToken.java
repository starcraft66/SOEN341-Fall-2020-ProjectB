package co.tdude.soen341.projectb.Lexer.Tokens;

public class CommentToken extends Token{

    public CommentToken(String lexeme) {
        super(lexeme);
        type = TokenType.COMMENT;
    }
}
