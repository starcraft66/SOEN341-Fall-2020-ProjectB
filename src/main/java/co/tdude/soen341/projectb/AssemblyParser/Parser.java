package co.tdude.soen341.projectb.AssemblyParser;

import co.tdude.soen341.projectb.Lexer.*;
import co.tdude.soen341.projectb.Lexer.Tokens.LabelToken;
import co.tdude.soen341.projectb.Lexer.Tokens.TokenType;
import co.tdude.soen341.projectb.Node.*;
public class Parser implements IParser {
    public Parser(Environment env) {
        this.lexer = env.getLexer();
        this.sourceFile = env.getSourceFile();
        this.errorReporter = env.getErrorReporter();
        this.table = env.getSymbolTable();
        nextToken(); // prime
        address = 0;
    }
    // Record the error: <t> expected, found <token> at <token>.position
    protected void expect(int t) {
        if (t != token) {
            String expected = Lexer.getTokenName(t);
            errorReporter.record( _Error.create( expected+" expected", Lexer.getPosition()) );
        }
        nextToken();
    }
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
    public Link parse() {
        System.out.println("Parsing a AssemblyUnit...");

        LineStmtSeq seq = new LineStmtSeq();

        while ( token != TokenType.EOF ) {
            seq.add( parseLineStmt() );
        }
        return new TranslationUnit(seq);
    }
    //---------------------------------------------------------------------------------
    // Parse a 1 Byte no Operand Mnemonic
    private Instruction parseInherent() {
        // your code...
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
        LabelToken label = null;
        Instruction  inst = null;
        Comment      comment = null;

        System.out.println("Parsing a Line Statement...");

        // your code...

        return new LineStatement(label, inst, comment);
    }

    protected void nextToken() {
        token = lexer.getToken();
    }

    private TokenType token;
    private ILexer        lexer;
    private ISourceFile   sourceFile;
    private IReportable   errorReporter;
    private ISymbolTable  table;
    private ISymbolTable  keywordTable;
}
