package co.tdude.soen341.projectb.test;

import co.tdude.soen341.projectb.Assembler.AssemblyUnit;
import co.tdude.soen341.projectb.ErrorReporter.Error;
import co.tdude.soen341.projectb.ErrorReporter.ErrorReporter;
import co.tdude.soen341.projectb.ErrorReporter.Position;
import co.tdude.soen341.projectb.Lexer.Lexer;
import co.tdude.soen341.projectb.Lexer.Tokens.*;
import co.tdude.soen341.projectb.Node.Instruction;
import co.tdude.soen341.projectb.Node.LineStatement;
import co.tdude.soen341.projectb.Reader.Reader;
import co.tdude.soen341.projectb.SymbolTable.SymbolTable;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssemblerTest {

    static File testfile;
    static FileWriter fw;

    @BeforeAll
    static void SetupTestFile() throws IOException {
        testfile = File.createTempFile("testfile", ".txt");
        testfile.deleteOnExit();
    }

    @BeforeEach
    void OverwriteTestFile() throws IOException {
        fw = new FileWriter(testfile); // Wipes contents of testfile every time
    }

    @AfterEach
    void CloseTestFile() throws IOException {
        fw.close();
    }

    @Test
    void AssemblyUnitTest() {
        ArrayList<LineStatement> statements = new ArrayList<>();
        LineStatement l1 = new LineStatement(new LabelToken(""), new Instruction("A", null), new CommentToken("some comment"));
        LineStatement l2 = new LineStatement(new LabelToken(""), new Instruction("B", null), new CommentToken("some comment"));
        LineStatement l3 = new LineStatement(new LabelToken(""), new Instruction("C", null), new CommentToken("some comment"));
        statements.add(l1);
        statements.add(l2);
        statements.add(l3);
        AssemblyUnit intermediateRepresentation = new AssemblyUnit(statements, "", "");

        assertEquals(l1, intermediateRepresentation.getAssemblyUnit().get(0));
        assertEquals(l2, intermediateRepresentation.getAssemblyUnit().get(1));
        assertEquals(l3, intermediateRepresentation.getAssemblyUnit().get(2));
    }

    @Test
    void InstructionTest() {
        assertEquals(0x00, SymbolTable.getMnemonic("halt"));
        assertEquals(0x01, SymbolTable.getMnemonic("pop"));
        assertEquals(0x02, SymbolTable.getMnemonic("dup"));
        assertEquals(0x03, SymbolTable.getMnemonic("exit"));
        assertEquals(0x04, SymbolTable.getMnemonic("ret"));
        assertEquals(0x0C, SymbolTable.getMnemonic("not"));
        assertEquals(0x0D, SymbolTable.getMnemonic("and"));
        assertEquals(0x0E, SymbolTable.getMnemonic("or"));
        assertEquals(0x0F, SymbolTable.getMnemonic("xor"));
        assertEquals(0x10, SymbolTable.getMnemonic("neg"));
        assertEquals(0x11, SymbolTable.getMnemonic("inc"));
        assertEquals(0x12, SymbolTable.getMnemonic("dec"));
        assertEquals(0x13, SymbolTable.getMnemonic("add"));
        assertEquals(0x14, SymbolTable.getMnemonic("sub"));
        assertEquals(0x15, SymbolTable.getMnemonic("mul"));
        assertEquals(0x16, SymbolTable.getMnemonic("div"));
        assertEquals(0x17, SymbolTable.getMnemonic("rem"));
        assertEquals(0x18, SymbolTable.getMnemonic("shl"));
        assertEquals(0x19, SymbolTable.getMnemonic("shr"));
        assertEquals(0x1A, SymbolTable.getMnemonic("teq"));
        assertEquals(0x1B, SymbolTable.getMnemonic("tne"));
        assertEquals(0x1C, SymbolTable.getMnemonic("tlt"));
        assertEquals(0x1D, SymbolTable.getMnemonic("tgt"));
        assertEquals(0x1E, SymbolTable.getMnemonic("tle"));
        assertEquals(0x1F, SymbolTable.getMnemonic("tge"));
    }

    @Test
    void PositionTest() {
        Position p1 = new Position(2, 1);
        Position p2 = new Position(4, 2);
        Position p3 = new Position(6, 3);
        Position p4 = new Position(255, 65535);

        assertEquals(1, p1.getLine());
        assertEquals(2, p1.getColumn());
        assertEquals(2, p2.getLine());
        assertEquals(4, p2.getColumn());
        assertEquals(3, p3.getLine());
        assertEquals(6, p3.getColumn());
        assertEquals(65535, p4.getLine());
        assertEquals(255, p4.getColumn());
    }
@Test
    void ErrorTest(){
        Error e1=new Error("Illegal Token");
        ErrorReporter er1=new ErrorReporter();
        er1.record(e1);

        assertEquals("Illegal Token",er1.getErrorLst().get(0).getmsg());
    }


    @Test
    void NumberParsedCorrectly() throws IOException {
        fw.write("1234");
        fw.flush();
        Reader r = new Reader(testfile);
        Lexer l = new Lexer(r);
        Token t = l.getToken();
        assertEquals(t.getClass(), NumberToken.class);
        NumberToken nt = (NumberToken) t;
        assertEquals(nt.getType(), TokenType.NUMBER);
        assertEquals(nt.getNumber(), 1234);
        assertEquals(l.getToken().getType(), TokenType.EOF);
    }

    @Test
    void MnemonicParsedCorrectly() throws IOException {
        fw.write("add");
        fw.flush();
        Reader r = new Reader(testfile);
        Lexer l = new Lexer(r);
        Token t = l.getToken();
        assertEquals(t.getClass(), MnemonicToken.class);
        MnemonicToken nt = (MnemonicToken) t;
        assertEquals(nt.getType(), TokenType.MNEMONIC);
        assertEquals(nt.getValue(), "add");
        assertEquals(l.getToken().getType(), TokenType.EOF);
    }

    @Test
    void MultiLineMnemonics() throws IOException {
        fw.write("add\nsub");
        fw.flush();
        Reader r = new Reader(testfile);
        Lexer l = new Lexer(r);
        assertEquals(l.getToken().getType(), TokenType.MNEMONIC);
        assertEquals(l.getToken().getType(), TokenType.EOL);
        assertEquals(l.getToken().getType(), TokenType.MNEMONIC);
        assertEquals(l.getToken().getType(), TokenType.EOF);
    }
}
