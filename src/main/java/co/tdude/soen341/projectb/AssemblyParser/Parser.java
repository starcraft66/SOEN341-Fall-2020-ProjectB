package co.tdude.soen341.projectb.AssemblyParser;

import co.tdude.soen341.projectb.Lexer.*;
import co.tdude.soen341.projectb.Lexer.Tokens.*;
import co.tdude.soen341.projectb.Node.*;
import co.tdude.soen341.projectb.SymbolTable.*;
import co.tdude.soen341.projectb.Environment.*;

import java.io.File;
import java.util.ArrayList;

public class Parser implements IParser {
    public Parser(Environment env) {
        this.lexer = env.getLexer();
        this.sourceFile = env.getSourceFile();
        //this.errorReporter = env.getErrorReporter(); TODO ONCE ERREPORTER IS IN
        this.labelTable = env.getSymbolTable();
        nextToken(); // prime
        // address = 0; Don't know what this is for
    }
    protected void expect(TokenType expected, TokenType test) {
        if (expected != test) {
            error("The Token Type " + expected.name() + " was expected, but we got a " + test.name());
        }
    }
    // Record the error: <t> expected, found <token> at <token>.position
    protected void expect(String t) {
        errorReporter.record( _Error.create(t+" expected", Lexer.getPosition()) );
    }
    protected void error(String t) {
        errorReporter.record( _Error.create(t, Lexer.getPosition()) );
    }

    private class SyntaxError extends Exception {}

    // -------------------------------------------------------------------
    // An assembly unit is zero or more line statement(s).
    //
    // AssemblyUnit = { LineStmt } EOF .
    // -------------------------------------------------------------------
    public AssemblyUnit parse() {
        System.out.println("Parsing a AssemblyUnit...");

        ArrayList<LineStatement> seq = new ArrayList<>();

        while ( token.getType() != TokenType.EOF ) {
            seq.add( parseLineStmt() );
        }
        return new AssemblyUnit(seq);
    }
    //---------------------------------------------------------------------------------
    // Parse a 1 Byte no Operand Mnemonic
    private Instruction parseInherent() {
        return new Instruction(token.getValue(), null);
    }
    //---------------------------------------------------------------------------------
//    private Instruction parseImmediate() {
//        // your code...
//    } TODO: Sprint 2
    //---------------------------------------------------------------------------------
//    private Instruction parseRelative() {
//        // your code...
//    } TODO: Sprint 2
    // -------------------------------------------------------------------
    // A line statement:
    //   - could be empty (only a EOL);
    //   - could have a single comment start at BOL or after a label, label/inst, or label/dir;
    //   - could have a label only, etc.
    //
    // LineStatement = [Label] [Instruction | Directive ] [Comment] EOL .
    //
    public LineStatement parseLineStmt() {
        LabelToken   label = null;
        Instruction  inst = null;
        CommentToken comment = null;

        System.out.println("Parsing a Line Statement...");

        // Test if EOL first
        if (token.getType() == TokenType.EOL) {
            return new LineStatement(null, null, null);
        }
        if (token.getType() == TokenType.COMMENT) {
            return new LineStatement(null, null, (CommentToken) token);
        }
        if (keywordTable.isRegistered(token.getValue())) {
            // If registered, then mnemonic
            // Right now, we can assume that no operand.
            lexer.getToken();
            inst = parseInherent();
            return new LineStatement(null, inst, null);
        } else {
            // If not registered, then label
            // TODO Label processing
        }
        return null;
    }

    /**
     * Loads the next token to parse into the token member
     */
    protected void nextToken() {
        token = lexer.getToken();
    }

    private Token         token;
    private ILexer        lexer;
    private File sourceFile;
    //private IReportable   errorReporter;
    private ISymbolTable  labelTable;
    private ISymbolTable  keywordTable;
}
