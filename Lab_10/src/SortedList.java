import java.util.ArrayList;

public class SortedList
{
    private final ArrayList<String> sortedList;

    public SortedList(ArrayList<String> baseList)
    {
        this.sortedList = new ArrayList<String>();

        for(var item : baseList)
        {
            this.add(item);
        }
    }

    public SortedList()
    {
        this.sortedList = new ArrayList<String>();
    }

    public void add(String newItem)
    {
        int highIndex = this.sortedList.size();
        int lowIndex = 0;

        int halfwayIndex;
        String halfwayValue;

        int lexicographicalOrdering;

        boolean itemNotPlaced = true;
        while(itemNotPlaced)
        {
            if(highIndex == lowIndex)
            {
                this.sortedList.add(highIndex, newItem);
                itemNotPlaced = false;
            }
            else
            {
                halfwayIndex = (highIndex + lowIndex) / 2;
                halfwayValue = this.sortedList.get(halfwayIndex);

                lexicographicalOrdering = newItem.compareTo(halfwayValue);

                // If we are down to the two indexes that new item is between
                if(highIndex == halfwayIndex || lowIndex == halfwayIndex)
                {
                    this.sortedList.add(halfwayIndex, newItem);
                    itemNotPlaced = false;
                }
                // New item comes before halfway value
                else if(lexicographicalOrdering < 0)
                {
                    highIndex = halfwayIndex;
                }
                // New item comes after halfway value
                else if(lexicographicalOrdering > 0)
                {
                    lowIndex = halfwayIndex;
                }
                // New item is equal to halfway value
                else//if(lexicographicalOrdering == 0)
                {
                    this.sortedList.add(halfwayIndex, newItem);
                    itemNotPlaced = false;
                }
            }
        }
    }

    public String getIndex(int i)
    {
        return this.sortedList.get(i);
    }

    public ArrayList<String> getSortedList()
    {
        return sortedList;
    }
}
