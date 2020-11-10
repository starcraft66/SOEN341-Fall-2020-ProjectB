/* Dump.java
//
// Copyright (C) 1996-2020 by Michel de Champlain
//
*/

import java.io.*;

class Dump {
    /*--------------------------------------------------------------
     * hexByte -- print a byte in hex
     *-------------------------------------------------------------
     */
    private static void hexByte(byte b) {
        System.out.print( Integer.toString((b >>> 4) & 0x0F, 16) ); // MSNibble
        System.out.print( Integer.toString( b        & 0x0F, 16) ); // LSNibble
    }
    /*--------------------------------------------------------------
     * hexWord -- print a word in hex
     *-------------------------------------------------------------
     */
    private static void hexWord(short word) {
        hexByte((byte)(word>>>8)); // MSB - Most  Significant Byte.
        hexByte((byte) word);      // LSB - Least Significant Byte.
    }

    private static boolean isPrint(char c) {
        return  (c >= ' ' && c < 0x7f);
    }

    public static void main(String args[]) throws IOException{
        File  fIn;
        int   i, buffer[] = new int[16];
        long  offset = 0;

        if (args.length < 2 || args.length > 3) {
            System.out.println("Error: wrong number of arguments");
            System.out.println("Usage: dump file [starting offset in hex]\n");
            System.exit(1);
        }

        fIn = new File(args[0]);        // Open file for binary read.
        if ( !fIn.canRead() ) {
            System.out.println("Can't open file '" + args[0] + "'");
            System.exit(1);
        }

        FileInputStream f = new FileInputStream(fIn);
        while (true) {
            for (i = 0; i < 16; i++)
                 buffer[i] = f.read();

            if (buffer[0] == -1) break; // Done.

            hexWord((short)offset); System.out.print( ": " );
            for (i = 0; i < 16; i++) {
                if (buffer[i] != -1) {
                    hexByte((byte)buffer[i]);
                    System.out.print( " ");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.print("   ");
            for (i = 0; i < 16; i++) {
                if (buffer[i] != -1) {
                    if ( !isPrint((char)buffer[i]) ) buffer[i] = '.';
                        System.out.print((char)buffer[i]);
                } else {
                    System.out.print(' ');
                }
            }
            System.out.println();
            offset += 16;
        }
    }
}
