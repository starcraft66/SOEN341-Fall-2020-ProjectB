package co.tdude.soen341.projectb.Lexer.Tokens;

public class MnemonicToken extends Token {

    public MnemonicToken(String lexeme) {
        super(lexeme);
        type = TokenType.MNEMONIC;
    }
}
