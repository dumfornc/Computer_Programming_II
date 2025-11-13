import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class TagExtractor
{
    private ArrayList<String> stopWords;

    public TagExtractor(File stopWordsFile) throws java.io.FileNotFoundException
    {
        setStopWords(stopWordsFile);
    }

    public void setStopWords(File stopWordsFile) throws java.io.FileNotFoundException
    {
        this.stopWords = new ArrayList<String>();

        Scanner stopWordsScanner = new Scanner(stopWordsFile);
        while(stopWordsScanner.hasNextLine())
        {
            String stopWord = stopWordsScanner.nextLine().toLowerCase();
            this.stopWords.add(stopWord);
        }
    }

    public TreeMap<String, Integer> getFileTags(File tagsFile) throws java.io.FileNotFoundException
    {
        TreeMap<String, Integer> tags = new TreeMap<String, Integer>();

        Scanner tagsScanner = new Scanner(tagsFile);
        while(tagsScanner.hasNext())
        {
            String nextWord = tagsScanner.next().replaceAll("[^a-zA-Z ]", "").toLowerCase();

            if(!stopWords.contains(nextWord))
            {
                if(tags.containsKey(nextWord))
                {
                    tags.put(nextWord, tags.get(nextWord) + 1);
                }
                else
                {
                    tags.put(nextWord, 1);
                }
            }
        }

        return tags;
    }

    public TreeMap<String, Integer> getHighFrequencyTags(File tagsFile, Integer minimumAppearances) throws java.io.FileNotFoundException
    {
        TreeMap<String, Integer> highFrequencyTags = new TreeMap<String, Integer>();

        for(Map.Entry<String, Integer> entry : this.getFileTags(tagsFile).entrySet())
        {
            if(entry.getValue() >= minimumAppearances)
            {
                highFrequencyTags.put(entry.getKey(), entry.getValue());
            }
        }

        return highFrequencyTags;
    }

    public ArrayList<String> getStopWords()
    {
        return stopWords;
    }
}
