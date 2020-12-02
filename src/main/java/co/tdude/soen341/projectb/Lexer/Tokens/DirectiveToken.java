package co.tdude.soen341.projectb.Lexer.Tokens;

/**
 * Token used to identify a Directive within an assembly file.
 */
public class DirectiveToken extends MnemonicToken {
    /**
     * Constructor used to create a DirectiveToken object.
     * @param lexeme: the lexeme
     */
    public DirectiveToken(String lexeme) {
        super (lexeme,0,false,0);
        type = TokenType.DIRECTIVE;
    }
}
