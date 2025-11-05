import javax.swing.*;

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
        if(newState == TILE_IS_EMPTY)
        {
            this.tileState = TILE_IS_EMPTY;
            setIcon(emptyIcon);

            setEnabled(true);
        }
        else if(newState == TILE_IS_MISS)
        {
            this.tileState = TILE_IS_MISS;
            setIcon(missIcon);

            setEnabled(false);
        }
        else if(newState == TILE_IS_HIT)
        {
            this.tileState = TILE_IS_HIT;
            setIcon(hitIcon);

            setEnabled(false);
        }
    }
}
