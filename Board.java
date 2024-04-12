/**
 * A class representing the game board in a Tetris game.
 */
public class Board
{
    /**
     * Dynamic Array used to keep track of the whole board.
     */
    private DynamicArray<DynamicArray<Tile>> board; // the internal storage of the board data

    /**
     * Constructs a Tetris board with the specified height and width.
     * This constructor creates a 2D placeholder of null values;
     * these values will be populated later with calls to setTile().
     * @param height the height of the board.
     * @param width the width of the board.
     */
    public Board(int height, int width) { // this constructor creates a 2D placeholder of null values; these values will be populated later with calls to setTile() -- O(height * width)
        board = new DynamicArray<>(height);
        for (int i = 0; i < height; i++) {
            DynamicArray<Tile> row = new DynamicArray<>(width);
            for (int j = 0; j < width; j++) {
                row.set(j, null);
            }
            board.set(i, row);
        }
    }

    /**
     * Gets the width of the board.
     * @return the width of the board.
     */
    public int getWidth() { // returns the width of the board -- O(1)
        return board.get(0).size();
    }

    /**
     * Gets the height of the board.
     * @return the height of the board.
     */
    public int getHeight() { // returns the height of the board -- O(1)
        return board.size();
    }

    /**
     * Sets the tile at the specified position on the board.
     * @param jellyTime the y-coordinate of the tile.
     * @param peanutButter the x-coordinate of the tile.
     * @param t the tile to be set.
     */
    public void setTile(int jellyTime, int peanutButter, Tile t) { // sets the tile at location y,x -- O(1)
        board.get(jellyTime).set(peanutButter, t);
    }

    /**
     * Gets the tile from the specified position on the board.
     * @param jellyTime the y-coordinate of the tile.
     * @param peanutButter the x-coordinate of the tile.
     * @return the tile at the specified position.
     */
    public Tile getTile(int jellyTime, int peanutButter) { // gets the tile from location y,x -- O(1)
        return board.get(jellyTime).get(peanutButter);
    }

    /**
     * Consolidates the dropped block into the Tetris well.
     * @param block the block to be consolidated.
     */
    public void consolidate(Block block) { // when the dropping block has reached its final location, this method will consolidate it into the tetris well -- O(block_size)
        int size = block.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (block.getTile(i, j) != null) {
                    setTile(i + block.getY(), j + block.getX(), block.getTile(i, j));
                }
            }
        }
    }

    /**
     * Clears any complete rows on the board and shifts the above tiles down.
     */
    public void clearRows() { // clear any/all rows that are complete and shifts the above tiles down -- O(board_size)
        int height = board.size();
        int width = board.get(0).size();
        for (int i = height - 1; i >= 0; i--) {
            boolean rowFull = true;
            for (int j = 0; j < width; j++) {
                if (board.get(i).get(j) == null) {
                    rowFull = false;
                    break;
                }
            }
            if (rowFull) {
                for (int k = i; k > 0; k--) {
                    board.set(k, board.get(k - 1));
                }
                DynamicArray<Tile> newRow = new DynamicArray<>(width);
                for (int j = 0; j < width; j++) {
                    newRow.set(j, null);
                }
                board.set(0, newRow);
                i++;
            }
        }
    }

    /**
     * When this method is called, it finds the row with the most amount of tiles and removes it.
     * In case of a tie it chooses the lowest row.
     * After this it moves all rows above the deleted row down by 1.
     */
    public void reward() { // applies the reward as explained in the project description -- O(board_size)
        int maxTiles = 0;
        int maxRow = -1;

        for (int i = 0; i < board.size(); i++) {
            int count = 0;
            for (int j = 0; j < board.get(0).size(); j++) {
                if (board.get(i).get(j) != null) {
                    count++;
                }
            }
            if (count > maxTiles || (count == maxTiles && maxRow == -1) || (count == maxTiles && i > maxRow)) {
                maxTiles = count;
                maxRow = i;
            }
        }

        if (maxRow != -1) {
            for (int j = 0; j < board.get(0).size(); j++) {
                board.get(maxRow).set(j, null);
            }
            for (int i = maxRow; i > 0; i--) {
                for (int j = 0; j < board.get(0).size(); j++) {
                    board.get(i).set(j, board.get(i - 1).get(j));
                }
            }
            for (int j = 0; j < board.get(0).size(); j++) {
                board.get(0).set(j, null);
            }
        }
    }


    /**
     * Applies the penalty as explained in the project description.
     */
    public void penalize() { // applies the penalty as explained in the project description -- O(board_size)
        int minTiles = Integer.MAX_VALUE;
        int minRow = -1;
        int boardYSize = board.size();
        int boardXSize = board.get(0).size();
        int highestRowIndex = -1;

        for (int i = 0; i < boardYSize; i++) {
            int count = 0;
            for (int j = 0; j < boardXSize; j++) {
                if (board.get(i).get(j) != null) {
                    count++;
                }
            }
            if (count <= minTiles && count > 0) {
                minTiles = count;
                minRow = i;
            }
            if (count > 0 && highestRowIndex == -1) {
                highestRowIndex = i;
            }
        }

        if (minRow > 0) {
            DynamicArray<Tile> duplicatedRow = new DynamicArray<>(boardXSize);
            for (int j = 0; j < boardXSize; j++) {
                duplicatedRow.set(j, board.get(minRow).get(j));
            }
            if(highestRowIndex > 0) {
                for (int j = 0; j < boardXSize; j++) {
                    board.get(highestRowIndex - 1).set(j, duplicatedRow.get(j));
                }
            }
        }
    }
}
