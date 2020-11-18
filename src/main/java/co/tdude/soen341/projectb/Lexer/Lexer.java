package co.tdude.soen341.projectb.Lexer;

import co.tdude.soen341.projectb.Lexer.Tokens.*;
import co.tdude.soen341.projectb.SymbolTable.*;
import co.tdude.soen341.projectb.Reader.*;

import java.io.IOException;

/**
 * Lexical Analyzer (Lexer) used to extract tokens from an assembly file.
 */
public class Lexer implements ILexer {
    /**
     * SymbolTable that holds opcodes and their hex equivalences.
     */
    private ISymbolTable _keywordTable;

    /**
     * Current line position within the lexer
     */
    private int _curLinePos;

    /**
     * Current column position within the lexer
     */
    private int _curColPos;

    /**
     * Current character being read from the assembly file.
     */
    private char _ch;

    /**
     * Used to read characters
     */
    private IReader _reader;

    /**
     * The purpose of the _lexeme is to store the contents of the token, while the getToken function returns
     * the "Type" of the _lexeme.
     */
    private String _lexeme;

    /**
     * Constructor for a Lexer object.
     * @param reader Used to read individual characters in the assembly file.
     * @param keywordTable Symbol table of key-value pairs that represent opcodes and their hex representations, respectively.
     */
    public Lexer(IReader reader, ISymbolTable keywordTable) {

        _keywordTable = keywordTable;
        _reader = reader;
        _curLinePos = 1;
        _curColPos = 0;

//        // Enter all mnemonics as keywords in the symbol table...
//
//        // One byte inherent ops
//        // It feels weird to handle them here, but that's what the prof says
//        _keywordTable.registerSymbol("halt", 0x00);
//        _keywordTable.registerSymbol("pop", 0x01);
//        _keywordTable.registerSymbol("dup", 0x02);
//        _keywordTable.registerSymbol("exit", 0x03);
//        _keywordTable.registerSymbol("ret", 0x04);
//        _keywordTable.registerSymbol("not", 0x0C);
//        _keywordTable.registerSymbol("and", 0x0D);
//        _keywordTable.registerSymbol("or", 0x0E);
//        _keywordTable.registerSymbol("xor", 0x0F);
//        _keywordTable.registerSymbol("neg", 0x10);
//        _keywordTable.registerSymbol("inc", 0x11);
//        _keywordTable.registerSymbol("dec", 0x12);
//        _keywordTable.registerSymbol("add", 0x13);
//        _keywordTable.registerSymbol("sub", 0x14);
//        _keywordTable.registerSymbol("mul", 0x15);
//        _keywordTable.registerSymbol("div", 0x16);
//        _keywordTable.registerSymbol("rem", 0x17);
//        _keywordTable.registerSymbol("shl", 0x18);
//        _keywordTable.registerSymbol("shr", 0x19);
//        _keywordTable.registerSymbol("teq", 0x1A);
//        _keywordTable.registerSymbol("tne", 0x1B);
//        _keywordTable.registerSymbol("tlt", 0x1C);
//        _keywordTable.registerSymbol("tgt", 0x1D);
//        _keywordTable.registerSymbol("tle", 0x1E);
//        _keywordTable.registerSymbol("tge", 0x1F);
    }

    /**
     * Gets the current column and line position being parsed in the assembly file.
     * @return A String with the current column and line position.
     */
    public String getPosition() {
        return _curLinePos + ":" + _curColPos;
    }

    /**
     * Read the next character from the assembly file.
     */
    private char read() {
        _curColPos++;
        try {
            _ch = _reader.read();
        } catch (IOException e) {
            // IO EXCEPTION!
            System.exit(1);
        }
        return _ch;
    }

    /**
     *
     * @param t
     */
    private void error(String t) {
//         errorReporter.record( _Error.create(t, getPosition()) ); TODO: Bring in the Error Reporter
//         Shouldn't this exit in some way?
    }


    /*
     * The scanX() family of functions will progress along the file using read() searching for whitespace to
     * terminate the _lexeme. It builds up the _lexeme in the `_lexeme` member variable with string concatenation
     * because I'm lazy. It finally performs some final validation to ensure that the token is in the expected format
     * (e.g. a number does not have any non-number elements in it)
     */

    /**
     *
     * @return
     */
    private Token scanNumber() {
        while (!Character.isWhitespace(_ch) && _ch != '\0') {
            _lexeme += _ch;
            _ch = read();
        }
        try {
            Integer.parseInt(_lexeme);
        } catch (NumberFormatException e) {
            error(e.getMessage());
        }
        return new NumberToken(_lexeme);
    }

    /**
     * Iterates through the read alphabetical characters and tries to identify whether it is a mnemonic token or identifier token.
     * @return A mnemonic token or identifier token, depending on the the string of characters read.
     */
    private Token scanIdentifier() {
        while (!Character.isWhitespace(_ch) && _ch != '\0') {
            _lexeme += _ch;
            if (!(Character.isAlphabetic(_ch) || Character.isDigit(_ch))) {
                error("The Identifier had a non-ident character in it");
            }
            else {
                _ch = read();
            }
        }

        _curColPos++;

        try {
            _keywordTable.get(_lexeme);
            return new MnemonicToken(_lexeme);
        }
        catch (ValueNotExist e) {
            return new IdentifierToken(_lexeme);
        }
    }

//    private Token scanDirective() {
//        // TODO: Sprint 2
//    }

//    private Token scanString() {
//        // TODO: Sprint 2
//    }

    private Token scanComment() {
        while (_ch != '\n' && _ch != '\r' && _ch != '\0') { // Not endline or EOF
            _lexeme += _ch;
            _ch = read();
        }
        return new CommentToken(_lexeme);
    }

    /**
     * Scan the next token. Mark position on entry in case of error.
     * @return The token.
     */
    public Token getToken() {
        _lexeme = "";

        _ch = read();

        // Mark position (after skipping blanks)
        //curlinePos = linePos;
        //curcolPos = colPos;

        // skip whitespaces
        while (Character.isWhitespace(_ch) && _ch != '\n' && _ch != '\r' && _ch != '\0') {
            _curColPos++;
        }

        switch ( _ch ) {
            // As his usage of '-1' doesn't work in java chars, we'll send a null byte from _reader.read if the EOF
            // was reached.
            case '\0':
                return new EOFToken();

            case '\r':
                if (read() != '\n') {
                    error("a \\r character must be followed by a \\n on dos architectures");
                }
            case '\n':
                // I think this works to carriage return on to the next line of source
                _curColPos = 1;
                _curLinePos++;
                return new EOLToken();

            case 'a': case 'b': case 'c': case 'd': case 'e':
            case 'f': case 'g': case 'h': case 'i': case 'j':
            case 'k': case 'l': case 'm': case 'n': case 'o':
            case 'p': case 'q': case 'r': case 's': case 't':
            case 'u': case 'v': case 'w': case 'x': case 'y':
            case 'z':
            case 'A': case 'B': case 'C': case 'D': case 'E':
            case 'F': case 'G': case 'H': case 'I': case 'J':
            case 'K': case 'L': case 'M': case 'N': case 'O':
            case 'P': case 'Q': case 'R': case 'S': case 'T':
            case 'U': case 'V': case 'W': case 'X': case 'Y':
            case 'Z':
                return scanIdentifier();

//                case '.':  /* dot for directives as a first character */
//                    return scanDirective(); TODO: SPRINT 2

            case '-':
            case '0': case '1': case '2': case '3': case '4':
            case '5': case '6': case '7': case '8': case '9':
                return scanNumber();

            case ';':
                return scanComment();

//                case '"':
//                    return scanString(); // TODO: STRING STUFF

            default:
                read();
                error("Illegal Token Detected");
                return new IllegalToken();
        }
    }

    // No setter as Lexeme is only mutated inside Lexer
    public String getLexeme() {
        return _lexeme;
    }
}
