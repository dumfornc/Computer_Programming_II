import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BattleShipTile extends JButton
{
    // Constants representing what image should be displayed and what the state of the button is
    public static final int TILE_IS_EMPTY = 0;
    public static final int TILE_IS_MISS = 1;
    public static final int TILE_IS_HIT = 2;

    // Constants of the images used by BattleShipTiles so that only one image icon is in memory for each image
    private static final ImageIcon emptyIcon = new ImageIcon("imgs\\emptyIcon.jpg");
    private static final ImageIcon missIcon = new ImageIcon("imgs\\missIcon.jpg");
    private static final ImageIcon hitIcon = new ImageIcon("imgs\\hitIcon.jpg");

    private int tileState;

    private final int row;
    private final int col;

    public BattleShipTile(int row, int col)
    {
        super();
        this.row = row;
        this.col = col;

        setTileState(TILE_IS_EMPTY);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getTileState() {
        return tileState;
    }

    public void setTileState(int newState)
    {
        int buttonWidth = getWidth();
        int buttonHeight = getHeight();

        if(newState == TILE_IS_EMPTY)
        {
            this.tileState = TILE_IS_EMPTY;
            setIcon(emptyIcon);

            setEnabled(true);
        }
        else if(newState == TILE_IS_MISS)
        {
            this.tileState = TILE_IS_MISS;

            ImageIcon disabledIcon = rescaleIcon(missIcon, buttonWidth, buttonHeight);
            setDisabledIcon(disabledIcon);
            setIcon(disabledIcon);

            setEnabled(false);
        }
        else if(newState == TILE_IS_HIT)
        {
            this.tileState = TILE_IS_HIT;

            ImageIcon disabledIcon = rescaleIcon(hitIcon, buttonWidth, buttonHeight);
            setDisabledIcon(disabledIcon);
            setIcon(disabledIcon);

            setEnabled(false);
        }
    }

    private ImageIcon rescaleIcon(ImageIcon icon, int width, int height)
    {
        Image img = icon.getImage();
        BufferedImage scaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = scaled.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(img, 0,0, width, height, null);
        g2d.dispose();

        return new ImageIcon(scaled);
    }
}
