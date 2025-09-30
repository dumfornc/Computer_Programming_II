import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FortuneTellerFrame extends JFrame
{
    private ArrayList<String> fortunes = new ArrayList<String>();

    public FortuneTellerFrame() throws HeadlessException
    {
        this.setFortunes();
    }

    private void setFortunes()
    {
        this.fortunes.add("One day when you are older you will get hit by a boulder");
        this.fortunes.add("At some point in the future you will grow older");
        this.fortunes.add("Do or do not, there is no try");
        this.fortunes.add("Your lucky number is the last 2 digits of pi");
        this.fortunes.add("If you never try you will never succeed");
        this.fortunes.add("Honesty is the best policy");
        this.fortunes.add("You should go pet a penguin");
        this.fortunes.add("When the dark comes a rising, 6 shall turn it back");
        this.fortunes.add("Fire on the mountain shall find the harp of gold");
        this.fortunes.add("All shall find the light at last");
        this.fortunes.add("If you fail to hydrate today bad things will happen");
        this.fortunes.add("You will pass a class in the next 2 years");
    }
}
