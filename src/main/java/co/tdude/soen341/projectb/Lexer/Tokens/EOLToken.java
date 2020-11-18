package co.tdude.soen341.projectb.Lexer.Tokens;

public class EOLToken extends Token{
    public EOLToken() {
        super("\n");
        type = TokenType.EOL;
    }

}
