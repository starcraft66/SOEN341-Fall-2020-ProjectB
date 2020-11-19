package co.tdude.soen341.projectb.Environment;

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

   //TODO : Add field for ErrorReporter

   /**
    * Constructor used to create an Environment object
    * @param asmFile Assembly source file
    */
   public Environment(File asmFile){
      this.asmFile = asmFile;
      this.reader = new Reader(this.asmFile);
      this.lexer = new Lexer(reader);

      //TODO : Add instantiation of ErrorReporter
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

   /* TODO: Introduce getErrorReporter()
   public ErrorReporter getErrorReporter (){
      return eReporter;
   }
    */
}
