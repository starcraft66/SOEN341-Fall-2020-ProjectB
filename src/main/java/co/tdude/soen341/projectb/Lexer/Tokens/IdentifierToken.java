package co.tdude.soen341.projectb.Lexer.Tokens;

public class IdentifierToken extends Token {

    public IdentifierToken(String lexeme) {
        super(lexeme);
        type = TokenType.IDENT;
    }
}
