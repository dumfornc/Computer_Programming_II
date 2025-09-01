import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class ProductWriter
{
    public static void main(String[] args)
    {
        ArrayList<String> productIDs = new ArrayList<String>();
        ArrayList<String> productNames = new ArrayList<String>();
        ArrayList<String> productDescriptions = new ArrayList<String>();
        ArrayList<Double> productCosts = new ArrayList<Double>();

        Scanner in = new Scanner(System.in);

        System.out.println("This script will gather data about one or more products and write it to a file.");

        boolean moreProducts = true;
        int productCount = 0;

        while (moreProducts)
        {
            productCount += 1;
            productIDs.add(SafeInput.getRegExString(in, "Enter the ID (#####) of product " + productCount, "\\d{6}"));
            productNames.add(SafeInput.getNonZeroLenString(in, "Enter the name of product " + productCount));
            productDescriptions.add(SafeInput.getNonZeroLenString(in, "Enter the description of product " + productCount));
            productCosts.add(SafeInput.getDouble(in, "Enter the cost of product " + productCount));

            moreProducts = SafeInput.getYNConfirm(in, "Do you want to enter data for another product");
        }

        // Below was copied and only slightly cleaned up from NIOWriteTextFile.java
        ArrayList<String> fileLines = new ArrayList<String>();
        String productFormat = "%s, %s, %s, %s";

        for (int productIndex = 0; productIndex < productCount; productIndex++)
        {
            fileLines.add(productFormat.formatted
                    (
                            productIDs.get(productIndex),
                            productNames.get(productIndex),
                            productDescriptions.get(productIndex),
                            productCosts.get(productIndex)
                    ));
        }

        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "\\src\\ProductTestData.txt");

        try
        {
            // Typical java pattern of inherited classes
            // we wrap a BufferedWriter around a lower level BufferedOutputStream
            OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            // Finally can write the file LOL!

            for(String line : fileLines)
            {
                writer.write(line, 0, line.length());  // stupid syntax for write rec
                // 0 is where to start (1st char) the write
                // rec. length() is how many chars to write (all)
                writer.newLine();  // adds the new line

            }
            writer.close(); // must close the file to seal it and flush buffer
            System.out.println("Data file written!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
