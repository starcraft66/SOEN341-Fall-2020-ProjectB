package co.tdude.soen341.projectb.Lexer;

import co.tdude.soen341.projectb.ErrorReporter.ErrorReporter;
import co.tdude.soen341.projectb.Lexer.Tokens.*;
import co.tdude.soen341.projectb.SymbolTable.*;
import co.tdude.soen341.projectb.Reader.*;
import co.tdude.soen341.projectb.ErrorReporter.Error;


import java.io.IOException;

/**
 * Lexical Analyzer (Lexer) used to extract tokens from an assembly file.
 */
public class Lexer implements ILexer {
    /**
     * SymbolTable that holds opcodes and their hex equivalences.
     */
    //private ISymbolTable _keywordTable;

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

    private ErrorReporter ereport;

    /**
     * The purpose of the _lexeme is to store the contents of the token, while the getToken function returns
     * the "Type" of the _lexeme.
     */
    private String _lexeme;

    /**
     * Constructor for a Lexer object.
     * @param reader Used to read individual characters in the assembly file.
     * @param //keywordTable Symbol table of key-value pairs that represent opcodes and their hex representations, respectively.
     */
    public Lexer(IReader reader/*, ISymbolTable keywordTable*/) {

        //_keywordTable = keywordTable;
        _reader = reader;
        _curLinePos = 1;
        _curColPos = 0;
        read(); // Prime the first character;
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
        try {
            _ch = _reader.read();
        } catch (IOException e) {
            // IO EXCEPTION!
            System.exit(1);
        }
        return _ch;
    }

    /**
     *Creates a new error and adds it to ErrorReporter
     * @param msg: error message which is to be added to ErrorReporter
     */
    private void LexerError(String msg) {
        Error e1=new Error(msg);
        ereport.record(e1);
//         Shouldn't this exit in some way?
    }


    /*
     * The scanX() family of functions will progress along the file using read() searching for whitespace to
     * terminate the _lexeme. It builds up the _lexeme in the `_lexeme` member variable with string concatenation
     * because I'm lazy. It finally performs some final validation to ensure that the token is in the expected format
     * (e.g. a number does not have any non-number elements in it)
     */

    /**
     * Scans and returns an integer (No negative numbers allowed)
     * @return
     */
    private Token scanNumber() {
        while (!Character.isWhitespace(_ch) && _ch != '\0') {
            _lexeme += _ch;
            _ch = read();
        }
        try {
            Integer.parseInt(_lexeme);
            return new NumberToken(_lexeme);
        } catch (NumberFormatException e) {
            LexerError(e.getMessage());
            return new IllegalToken();
        }
    }

    /**
     * Iterates through the read alphabetical characters and tries to identify whether it is a mnemonic token or identifier token.
     * @return A mnemonic token or identifier token, depending on the the string of characters read.
     */
    private Token scanIdentifier() {
        while (!Character.isWhitespace(_ch) && _ch != '\0') {
            _curColPos++;
            _lexeme += _ch;
            if (!(Character.isAlphabetic(_ch) || Character.isDigit(_ch) || _ch == '.')) {
                LexerError("Position: "+ getPosition()+" The Identifier had a non-ident character in it");
            }
            else {
                _ch = read();
            }
        }

        if (SymbolTable.isMnemonicRegistered(_lexeme)) {
            return new MnemonicToken(_lexeme);
        } else {
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

        //_ch = read(); This was removed because it advanced past the End of Word. It is the responsibility of the
        // various scanX() functions to read() until their word is done

        // Mark position (after skipping blanks)
        //curlinePos = linePos;
        //curcolPos = colPos;

        // skip whitespaces
        while (_ch == ' ' || _ch == '\t') {
            _ch = read();
        }

        switch ( _ch ) {
            // As his usage of '-1' doesn't work in java chars, we'll send a null byte from _reader.read if the EOF
            // was reached.
            case '\0':
                return new EOFToken();

            case '\r':
                if (read() != '\n') {
                    LexerError("Position: "+ getPosition()+" A \\r character must be followed by a \\n on dos architectures");
                }
            case '\n':
                // I think this works to carriage return on to the next line of source
                _curColPos = 1;
                _curLinePos++;
                read(); // Prime the next line
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
                LexerError("Position:"+getPosition()+" Illegal Token Detected");
                read();
                return new IllegalToken();
        }
    }

    // No setter as Lexeme is only mutated inside Lexer
    public String getLexeme() {
        return _lexeme;
    }
}
