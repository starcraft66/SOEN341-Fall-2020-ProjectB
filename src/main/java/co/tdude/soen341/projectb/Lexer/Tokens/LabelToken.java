package co.tdude.soen341.projectb.Lexer.Tokens;

public class LabelToken extends Token {

    public LabelToken(String lexeme) {
        super(lexeme);
        type = TokenType.IDENT;
    }
}
