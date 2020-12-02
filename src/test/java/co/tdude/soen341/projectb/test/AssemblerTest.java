package co.tdude.soen341.projectb.test;

import co.tdude.soen341.projectb.Assembler.AssemblyParser.Parser;
import co.tdude.soen341.projectb.Assembler.AssemblyUnit;
import co.tdude.soen341.projectb.Assembler.SourceFile;
import co.tdude.soen341.projectb.Environment.Environment;
import co.tdude.soen341.projectb.ErrorReporter.Error;
import co.tdude.soen341.projectb.ErrorReporter.ErrorReporter;
import co.tdude.soen341.projectb.ErrorReporter.Position;
import co.tdude.soen341.projectb.Lexer.Lexer;
import co.tdude.soen341.projectb.Lexer.Tokens.*;
import co.tdude.soen341.projectb.Node.Instruction;
import co.tdude.soen341.projectb.Node.LineStatement;
import co.tdude.soen341.projectb.Reader.Reader;
import org.junit.jupiter.api.*;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @AfterAll
    static void DeleteArtifacts() {
        try {
            new File("build/resources/test/asm_input.lst").delete();
            new File("build/resources/test/asm_input.exe").delete();
        } catch (Exception e) {
            //swallow
        }

    }

    @Test
    void AssemblyUnitTest() {
        ArrayList<LineStatement> statements = new ArrayList<>();
        LineStatement l1 = new LineStatement(new LabelToken(""), new Instruction(new MnemonicToken("A", 1, false, 0), null), new CommentToken("some comment"));
        LineStatement l2 = new LineStatement(new LabelToken(""), new Instruction(new MnemonicToken("B", 2, false, 0), null), new CommentToken("some comment"));
        LineStatement l3 = new LineStatement(new LabelToken(""), new Instruction(new MnemonicToken("C", 3, false, 0), null), new CommentToken("some comment"));
        statements.add(l1);
        statements.add(l2);
        statements.add(l3);
        AssemblyUnit intermediateRepresentation = new AssemblyUnit(statements, "", "");

        assertEquals(l1, intermediateRepresentation.getAssemblyUnit().get(0));
        assertEquals(l2, intermediateRepresentation.getAssemblyUnit().get(1));
        assertEquals(l3, intermediateRepresentation.getAssemblyUnit().get(2));
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

    @Test
    void EndToEndListing() throws IOException, URISyntaxException {
        File asmFile = Paths.get(getClass().getClassLoader().getResource("asm_input.asm").toURI()).toFile();
        SourceFile.StoreAssemblyFile(asmFile);
        Environment environment = new Environment(asmFile);
        Parser assemblyParser = new Parser(environment);
        AssemblyUnit assemblyUnit = assemblyParser.parse();
        assemblyUnit.Assemble(true);
        // probably terrible performance for large files but should be ok for something tiny
        InputStream resultInputStream = new FileInputStream("build/resources/test/asm_input.lst");
        InputStream targetInputStream = getClass().getClassLoader().getResourceAsStream("asm_output.lst");
        byte[] bytes1 = resultInputStream.readAllBytes();
        byte[] bytes2 = targetInputStream.readAllBytes();
        for (int i=0; i < bytes1.length; i++) {
            assertEquals(bytes1[i], bytes2[i]);
        }
    }

    @Test
    void EndToEndBinary() throws IOException, URISyntaxException {
        File asmFile = Paths.get(getClass().getClassLoader().getResource("asm_input.asm").toURI()).toFile();
        SourceFile.StoreAssemblyFile(asmFile);
        Environment environment = new Environment(asmFile);
        Parser assemblyParser = new Parser(environment);
        AssemblyUnit assemblyUnit = assemblyParser.parse();
        assemblyUnit.Assemble(false);
        // probably terrible performance for large files but should be ok for something tiny
        InputStream resultInputStream = new FileInputStream("build/resources/test/asm_input.exe");
        InputStream targetInputStream = getClass().getClassLoader().getResourceAsStream("asm_binary");
        assertTrue(Arrays.equals(resultInputStream.readAllBytes(), targetInputStream.readAllBytes()));
    }

    @Test
    void LabelTest() throws IOException {
        fw.write("Loop add\nadd\nbr.i5 Loop\n");
        fw.flush();
        Parser p = new Parser(new Environment(testfile));
        AssemblyUnit au = p.parse();
        au.Assemble(true);
    }
    @Test
    void NewErrorTest() throws IOException {
        fw.write("adx");
        fw.flush();
        Parser p = new Parser(new Environment(testfile));
        AssemblyUnit au = p.parse();
        au.Assemble(true);
    }
    @Test
    void DirectiveTest() throws IOException {
        fw.write(".cstring \"A2\"\n.cstring \"B3\"");
        fw.flush();
        Parser p = new Parser(new Environment(testfile));
        AssemblyUnit au = p.parse();
        au.Assemble(true);
    }

    @Test
    void RelativeInstructionTest() throws IOException {
        fw.write("lda.i16 60000\ncall.i16 20000\ntrap 200");
        fw.flush();
        Parser p = new Parser(new Environment(testfile));
        AssemblyUnit au = p.parse();
        au.Assemble(true);
    }

    @Test
    void EmptyLineTest() throws IOException {
        fw.write("add\n;Comment\nsub\n");
        fw.flush();
        Parser p = new Parser(new Environment(testfile));
        AssemblyUnit au = p.parse();
        au.Assemble(true);
    }
}
