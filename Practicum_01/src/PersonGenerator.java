import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class PersonGenerator
{
    public static void main(String[] args)
    {
        ArrayList<String> peopleIDs = new ArrayList<String>();
        ArrayList<String> peopleFirstNames = new ArrayList<String>();
        ArrayList<String> peopleLastNames = new ArrayList<String>();
        ArrayList<String> peopleTitles = new ArrayList<String>();
        ArrayList<Integer> peopleBirthYears = new ArrayList<Integer>();

        Scanner in = new Scanner(System.in);

        System.out.println("This script will gather data about one or more people and write it to a file.");

        boolean morePersons = true;
        int personCount = 0;

        while (morePersons)
        {
            personCount += 1;
            peopleIDs.add(SafeInput.getRegExString(in, "Enter the ID (#####) of person " + personCount, "\\d{6}"));
            peopleFirstNames.add(SafeInput.getNonZeroLenString(in, "Enter the first name of person " + personCount));
            peopleLastNames.add(SafeInput.getNonZeroLenString(in, "Enter the last name of person " + personCount));
            peopleTitles.add(SafeInput.getNonZeroLenString(in, "Enter the title or honorific of person " + personCount));
            peopleBirthYears.add(SafeInput.getRangedInt(in, "Enter the birth year of person " + personCount, 1000, 1200));

            morePersons = SafeInput.getYNConfirm(in, "Do you want to enter data for another person");
        }

        // Below was copied and only slightly cleaned up from NIOWriteTextFile.java
        ArrayList<String> fileLines = new ArrayList<String>();
        String personFormat = "%s, %s, %s, %s, %s";

        for (int personIndex = 0; personIndex < personCount; personIndex++)
        {
            fileLines.add(personFormat.formatted
            (
                peopleIDs.get(personIndex),
                peopleFirstNames.get(personIndex),
                peopleLastNames.get(personIndex),
                peopleTitles.get(personIndex),
                peopleBirthYears.get(personIndex)
            ));
        }

        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "\\src\\PersonTestData.txt");

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
