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
            INCORRECT,

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
                case INCORRECT:{
                    new Error("Position: "+pos+"   "+probl.getValue()+" is not a recognized opcode.");
                    break;}

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
