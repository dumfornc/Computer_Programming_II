import java.util.*;

public class BattleShipGame
{
    // Constants describing the state of each cell in the board
    public static final int EMPTY_NOT_SHOT = 11;
    public static final int SHIP_NOT_SHOT = 21;
    public static final int EMPTY_YES_SHOT = 12;
    public static final int SHIP_YES_SHOT = 22;

    // Constants describing game state after an action
    public static final int NORMAL_MISS = 0;
    public static final int STRIKE_MISS = 1;
    public static final int LOOSE_MISS = 2;
    public static final int NORMAL_HIT = 3;
    public static final int WIN_HIT = 4;
    public static final int TILE_PREVIOUSLY_SHOT = 5;

    // Constants describing if a ship is to be place horizontal or vertical
    private static final int HORIZONTAL_SHIP = 0;
    private static final int VERTICAL_SHIP = 1;

    // Constants for default board dimensions
    public static final int DEFAULT_ROW = 10;
    public static final int DEFAULT_COL = 10;

    // Constant determining default ship sizes
    public static final Integer[] DEFAULT_SHIPS = {5, 4, 3, 3, 2};

    private static final Random shipRandomizer = new Random();

    private final Integer[][] board;

    private int boardRows;
    private int boardColumns;

    private final Integer[] shipSizes;

    private final int totalPossibleHits;

    private int currentMissStreak;
    private int totalMisses;
    private int totalHits;
    private int strikes;

    public BattleShipGame()
    {
        this(DEFAULT_ROW, DEFAULT_COL, DEFAULT_SHIPS);
    }

    public BattleShipGame(int boardRows, int boardColumns, Integer[] shipSizes)
    {
        this.boardRows = boardRows;
        this.boardColumns = boardColumns;

        Arrays.sort(shipSizes, Collections.reverseOrder());
        this.shipSizes = shipSizes;

        this.totalPossibleHits = Arrays.stream(this.shipSizes).mapToInt(Integer::intValue).sum();

        this.board = new Integer[boardRows][boardColumns];

        this.startNewGame();
    }

    public void startNewGame()
    {
        this.currentMissStreak = 0;
        this.totalMisses = 0;
        this.totalHits = 0;
        this.strikes = 0;

        for(int row = 0; row < this.boardRows; row++)
        {
            for(int col = 0; col < boardColumns; col++)
            {
                this.board[row][col] = EMPTY_NOT_SHOT;
            }
        }

        this.placeShips();
    }

    private void placeShips()
    {
        for(Integer shipLength : this.shipSizes)
        {
            placeShip(shipLength);
        }
    }

    private void placeShip(int shipLength)
    {
        ArrayList<Integer> validRows = new ArrayList<Integer>();
        for(int i = 0; i < this.boardRows; i++){validRows.add(i);}

        ArrayList<Integer> validColumns = new ArrayList<Integer>();
        for(int i = 0; i < this.boardColumns; i++){validColumns.add(i);}

        ArrayList<Integer> openStartingIndexes;

        do
        {
            int shipOrientation = shipRandomizer.nextBoolean() ? HORIZONTAL_SHIP : VERTICAL_SHIP;

            if (shipOrientation == HORIZONTAL_SHIP)
            {
                int shipRow = shipRandomizer.nextInt(this.boardRows);
                openStartingIndexes = getAvailableRowStartIndexes(shipLength, shipRow);

                if(openStartingIndexes.isEmpty())
                {
                    validRows.remove(Integer.valueOf(shipRow));
                }
                else
                {
                    int randomIndexIndex = shipRandomizer.nextInt(openStartingIndexes.size());
                    int startingIndex = openStartingIndexes.get(randomIndexIndex);

                    placeShipOnBoard(shipRow, startingIndex, HORIZONTAL_SHIP, shipLength);
                }
            }
            else //if (shipOrientation == VERTICAL_SHIP)
            {
                int shipColumn = shipRandomizer.nextInt(this.boardColumns);
                openStartingIndexes = getAvailableColumnStartIndexes(shipLength, shipColumn);

                if(openStartingIndexes.isEmpty())
                {
                    validColumns.remove(Integer.valueOf(shipColumn));
                }
                else
                {
                    int randomIndexIndex = shipRandomizer.nextInt(openStartingIndexes.size());
                    int startingIndex = openStartingIndexes.get(randomIndexIndex);

                    placeShipOnBoard(startingIndex, shipColumn, VERTICAL_SHIP, shipLength);
                }
            }

            // If there is no room to place a BattleShip then increase the board size
            if(validRows.isEmpty() && validColumns.isEmpty())
            {
                this.boardRows++;
                this.boardColumns++;

                for(int i = 0; i < this.boardRows; i++){validRows.add(i);}
                for(int i = 0; i < this.boardColumns; i++){validColumns.add(i);}
            }
        } while(openStartingIndexes.isEmpty());
    }

    private ArrayList<Integer> getAvailableRowStartIndexes(int length, int shipRow)
    {
        ArrayList<Integer> openStartingIndexes = new ArrayList<Integer>();

        int contiguousOpenTiles = 0;
        for(int i = 0; i < this.boardColumns; i++)
        {
            if(board[shipRow][i] == EMPTY_NOT_SHOT)
            {
                contiguousOpenTiles++;
                if((contiguousOpenTiles - length) >= 0)
                {
                    openStartingIndexes.add(i - length + 1);
                }
            }
            else
            {
                contiguousOpenTiles = 0;
            }
        }

        return openStartingIndexes;
    }

    private ArrayList<Integer> getAvailableColumnStartIndexes(int length, int shipColumn)
    {
        ArrayList<Integer> openStartingIndexes = new ArrayList<Integer>();

        int contiguousOpenTiles = 0;
        for(int i = 0; i < this.boardRows; i++)
        {
            if(board[i][shipColumn] == EMPTY_NOT_SHOT)
            {
                contiguousOpenTiles++;
                if((contiguousOpenTiles - length) >= 0)
                {
                    openStartingIndexes.add(i - length + 1);
                }
            }
            else
            {
                contiguousOpenTiles = 0;
            }
        }

        return openStartingIndexes;
    }

    private void placeShipOnBoard(int row, int col, int orientation, int length)
    {
        if(orientation == HORIZONTAL_SHIP)
        {
            for(int i = 0; i < length; i++)
            {
                board[row][col + i] = SHIP_NOT_SHOT;
            }
        }
        else if(orientation == VERTICAL_SHIP)
        {
            for(int i = 0; i < length; i++)
            {
                board[row + i][col] = SHIP_NOT_SHOT;
            }
        }
    }

    public int makeMove(int row, int col)
    {
        int moveResult;

        int tileState = board[row][col];

        if(tileState == EMPTY_NOT_SHOT)
        {
            board[row][col] = EMPTY_YES_SHOT;
            moveResult = handleMiss();
        }
        else if(tileState == SHIP_NOT_SHOT)
        {
            board[row][col] = SHIP_YES_SHOT;
            moveResult = handleHit();
        }
        else
        {
            moveResult = TILE_PREVIOUSLY_SHOT;
        }

        return moveResult;
    }

    private int handleMiss()
    {
        int missType;

        totalMisses++;

        if(currentMissStreak < 4)
        {
            currentMissStreak++;
            missType = NORMAL_MISS;
        }
        else//if(currentMissStreak >= 5)
        {
            currentMissStreak = 0;

            strikes++;
            missType = strikes >= 3 ? LOOSE_MISS : STRIKE_MISS;
        }

        return missType;
    }

    public int handleHit()
    {
        int hitType;

        currentMissStreak = 0;
        totalHits++;

        if(totalHits < totalPossibleHits)
        {
            hitType = NORMAL_HIT;
        }
        else//if(totalHits >= totalPossibleHits)
        {
            hitType = WIN_HIT;
        }

        return hitType;
    }

    public int getCurrentMissStreak()
    {
        return currentMissStreak;
    }

    public int getTotalMisses()
    {
        return totalMisses;
    }

    public int getTotalHits()
    {
        return totalHits;
    }

    public int getStrikes()
    {
        return strikes;
    }
}
