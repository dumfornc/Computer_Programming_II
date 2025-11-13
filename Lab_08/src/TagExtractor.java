import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class TagExtractor
{
    private ArrayList<String> stopWords;

    private TreeMap<String, Integer> tags = new TreeMap<String, Integer>();

    public TagExtractor(File stopWordsFile)
    {
        setStopWords(stopWordsFile);
    }

    public void setStopWords(File stopWordsFile)
    {
        this.stopWords = new ArrayList<String>();

        Scanner stopWordsScanner = new Scanner(stopWordsFile);
        while(stopWordsScanner.hasNextLine())
        {
            String stopWord = stopWordsScanner.nextLine();
            this.stopWords.add(stopWord);
        }
    }

    public TreeMap<String, Integer> getFileTags(File tagsFile)
    {
        TreeMap<String, Integer> tags = new TreeMap<String, Integer>();

        Scanner tagsScanner = new Scanner(tagsFile);
        while(tagsScanner.hasNext())
        {
            String nextWord = tagsScanner.next();

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

    public ArrayList<String> getStopWords()
    {
        return stopWords;
    }

    public TreeMap<String, Integer> getTags()
    {
        return tags;
    }
}
