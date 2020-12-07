package co.tdude.soen341.projectb.Environment;

import co.tdude.soen341.projectb.ErrorReporter.ErrorReporter;
import co.tdude.soen341.projectb.Lexer.Lexer;
import co.tdude.soen341.projectb.Reader.Reader;

import java.io.File;

/**
 * Holds and instantiates the critical components necessary for program functionality.
 */
public class Environment {

   /**
    * The .asm source file.
    */
   private File asmFile;

   /**
    * The lexer used to get tokens.
    */
   private Lexer lexer;

   /**
    * Used to read characters from the .asm file.
    */
   private Reader reader;

   /**
    * Used to log error messages.
    */
   private ErrorReporter eReporter;

   /**
    * Constructor used to create an Environment object
    * @param asmFile Assembly source file
    */
   public Environment(File asmFile){
      toolsAndFiles_init(asmFile);
   }

   /**
    * Initials the tools and files needed for the assembler environment.
    * @param asmFile The assembly file.
    */
   private void toolsAndFiles_init(File asmFile) {
      this.asmFile = asmFile;
      this.reader = new Reader(this.asmFile);
      this.eReporter=new ErrorReporter();
      this.lexer = new Lexer(reader);
   }

   /**
    * Gets the Lexer object to fetch tokens.
    * @return Lexer.
    */
   public Lexer getLexer() {
      return lexer;
   }

   /**
    * Gets the .asm source file.
    * @return Assembly source file.
    */
   public File getSourceFile (){
      return asmFile;
   }

   /**
    * Gets the error reporter object.
    * @return Error reporter object.
    */
   public ErrorReporter getErrorReporter (){
      return eReporter;
   }
}
