package co.tdude.soen341.projectb.ErrorReporter;

import co.tdude.soen341.projectb.Lexer.Tokens.Token;


public class Error {

    /**
     * Constructor of Error
     * @param message: error message to be displayed
     *
     */
    //enum is used to choose the error type for which a message will be printed
       private String message;
        public enum err_type{
            MNEMONIC,
            NONIDENT,
            ILLEGALTOKEN,
            MISSINGNEWLINE,
            OPERAND,
            TERMINATOR,
            UNRESOLVABLE,
            NEGATIVE,
            IOERROR,
            NUMBERFORMAT,
            DIRECTIVE


        }
    /**
     * Constructor of Error
     * @param msg: error message which will be displayed

     */
        public Error(String msg)
        {
            this.message=msg;

        }
        public Error()
        {
            this("");

        }
    /**
     * Creating an error message
     * @param type: enum representing the error type
     * @param pos: position (line, column) of the error
     * @param probl :token which contains the error
     */

        public void generatemsg (err_type type, String pos, Token probl){
            switch(type){
                case MNEMONIC:{
                    new Error("Position:"+pos+" Expected Mnemonic, got " +probl.getValue());
                    break;}
                case OPERAND:{
                    new Error("Position:"+pos+" Expected Operand, got " + probl.getValue());
                    break;}
                case TERMINATOR:{
                    new Error("Position:"+pos+" Expected a terminating token, got " + probl.getValue());
                    break;}
                case NONIDENT:{
                    new Error("Position: "+ pos+" The Identifier had a non-identifiable character in it");
                    break;
                }
                case ILLEGALTOKEN:{
                    new Error("Position:"+pos+" Illegal Token Detected");
                    break;
                }
                case MISSINGNEWLINE:{
                    new Error("Position: "+pos+" A \\r character must be followed by a \\n on dos architectures");
                    break;
                }
                case UNRESOLVABLE:{
                    new Error("Unable to resolve offset");
                    break;}

                case NEGATIVE:{
                    new Error("Negative number in a disallowed context");
                    break;}

                case IOERROR:{
                    new Error("File cannot be read or written to");
                    break;}
                case NUMBERFORMAT:{
                    new Error("Number Format Error");
                    break;
                }
                case DIRECTIVE:{
                    new Error("Position: "+pos+" The Directive had a non-directive character in it.");
                    break;
                }
                default:{
                    new Error("Unknown error");
                    break;}
            }


        }

    /**
     * Returns the message associated with that error
     * @return: The message
     */
    public String getmsg(){

            return this.message;

}



    }
