package co.tdude.soen341.projectb.Lexer.Tokens;

/**
 * Token used to identify a String within an assembly file
 */
public class StringToken extends Token{
    /**
     * Constructor used to create a StringToken object.
     * @param lexeme: the lexeme
     */
    public StringToken (String lexeme){
        super (lexeme);
        type = TokenType.STRING;
    }
}
