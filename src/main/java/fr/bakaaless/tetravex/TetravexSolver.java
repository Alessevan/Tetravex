package fr.bakaaless.tetravex;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Code to resolve a Tetravex given in argument.
 *
 * @author : Aless#6161
 * @date : 10/28/2021
 */
public class TetravexSolver {

    private static List<Piece> pieces = new ArrayList<>();
    private static Piece[][] sorted;
    private static int size;
    private static int length;

    public static void main(final String... args) {
        System.out.print('\0');
        try {
            // Read bytes in input
            final ByteArrayOutputStream inputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[32 * 1024];

            // Just read the first line of bytes
            int bytesRead;
            if ((bytesRead = System.in.read(buffer)) > 0)
                inputStream.write(buffer, 0, bytesRead);

            final byte[] input = inputStream.toByteArray();
            byte[] allBytes;

            // remove last byte if it corresponds to an "enter input"
            if (input[input.length - 1] == (byte) 10 || input[input.length - 1] == (byte) 13)
                allBytes = Arrays.copyOf(input, input.length - 1);
            else
                allBytes = input;

//            final File file = new File(args[0]);
//            final InputStream inputStream = new FileInputStream(file);
//            byte[] allBytes = new byte[(int) file.length()];
//
//            inputStream.read(allBytes);

            // Extract game board's size
            size = allBytes[1];
            sorted = new Piece[size][size];

            // Get the amount of data behind the size of the board
            length = allBytes.length - 2;

            // Check if it's a good input : condition will be true if there are more or less pieces available than expected
            if (size * size != length >> 2) {
                System.err.println("The amount of pieces doesn't match with the board's size");
                System.exit(-1);
            }

            // Convert all hexadecimal strings into BigInteger
            final int[] allValues = new int[length];
            for (int i = 2; i < allBytes.length; i ++) {
                final int parallelIndex = i - 2;
                if (parallelIndex >= length)
                    return;
                allValues[parallelIndex] = allBytes[i];
            }

            // Compute four by four all integers
            int value = 0;
            int i = 0;
            for (; i < allValues.length; i++) {
                if (i % 4 == 0 && i != 0) {
                    pieces.add(new Piece(value));
                    value = 0;
                }
                value = (value << 4) | allValues[i];
            }
            if (i % 4 == 0)
                pieces.add(new Piece(value));

            solver(0, 0);

            // Print solution

            byte[] result = new byte[allBytes.length - 2];
            int index = 0;
            for (int y = 0; y < sorted.length; y++) {
                for (int x = 0; x < sorted[y].length; x++) {
                    result[index++] = (byte) sorted[x][y].getLeft();
                    result[index++] = (byte) sorted[x][y].getTop();
                    result[index++] = (byte) sorted[x][y].getRight();
                    result[index++] = (byte) sorted[x][y].getBottom();
                }
            }
            for (final byte b : result)
                System.out.print(b);

//            final File output = new File(args[0] + ".output");
//            output.createNewFile();
//            final OutputStream outputStream = new FileOutputStream(output);
//            outputStream.write('\0');
//            outputStream.write(result);
//            outputStream.flush();
//            outputStream.close();
            System.exit(0);

        } catch (FileNotFoundException ignored) {
            System.err.println("Wrong entry");
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean solver(final int x, final int y) {
        // Halting condition
        if (y == size)
            return true;

        // Iterate all pieces 'cause it's funny
        for (final Piece piece : pieces) {
            // Just check if a piece is already sorted or not
            if (!piece.isPlaced()) {
                // Verification if the piece can be placed where
                if (x == 0 || sorted[x - 1][y].getRight() == piece.getLeft()) {
                    if (y == 0 || sorted[x][y - 1].getBottom() == piece.getTop()) {
                        // Put the piece in the 2d array
                        sorted[x][y] = piece;
                        // Mark it has placed
                        piece.setPlaced(true);
                        // Move on the next piece
                        int nextX = x + 1;
                        int newY = y;
                        if (nextX == size) {
                            nextX = 0;
                            newY++;
                        }
                        // Check if it's a good path
                        if (solver(nextX, newY))
                            return true;
                        // Fock it's not good...
                        piece.setPlaced(false);
                    }
                }
            }
        }
        // The path isn't valid
        return false;
    }

}
