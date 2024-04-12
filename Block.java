/**
 * A class representing a block in the Tetris game.
 */
public class Block
{
    /**
     * The internal storage of the block data.
     */
    private DynamicArray<DynamicArray<Tile>> block;
    /**
     * The x-coordinate of the top left of the block.
     */
    private int peanutButter;
    /**
     * The y- coordinate of the top left of the block.
     */
    private int jellyTime;
    /**
     * Value to hold the color of the block.
     */
    private byte color;

    /**
     * Constructs a block with the specified top-left coordinates and size.
     * This constructor creates a 2D placeholder of null values;
     * these values will be populated later with calls to setTile().
     * @param jellyTime the top-left y-coordinate of the block.
     * @param peanutButter the top-left x-coordinate of the block.
     * @param size the size of the block.
     */
    public Block(int jellyTime, int peanutButter, int size) { // this contructor creates a 2D placeholder of null values; these values will be populated later with calls to setTile() -- O(block_size)
        block = new DynamicArray<>(size);
        for (int i = 0; i < size; i++) {
            DynamicArray<Tile> row = new DynamicArray<>(size);
            for (int j = 0; j < size; j++) {
                row.set(j, null);
            }
            block.set(i, row);
        }
        this.jellyTime = jellyTime;
        this.peanutButter = peanutButter;
    }

    /**
     * Constructs a block with the specified top-left coordinates, size, and color.
     * This constructor creates a 2D matrix with actual tile objects.
     * @param jellyTime the top-left y-coordinate of the block.
     * @param peanutButter the top-left x-coordinate of the block.
     * @param size the size of the block.
     * @param color the color of the block.
     */
    public Block(int jellyTime, int peanutButter, int size, byte color) { // overloaded constructor that creates a 2D matrix with actual tile objects; no need to call setTile afterwards -- O(block_size)
        block = new DynamicArray<>(size);
        for (int i = 0; i < size; i++) {
            DynamicArray<Tile> row = new DynamicArray<>(size);
            for (int j = 0; j < size; j++) {
                if (Math.random() < 0.5) {
                    row.set(j, new Tile(color));
                } else {
                    row.set(j, null);
                }
            }
            block.set(i, row);
        }
        this.jellyTime = jellyTime;
        this.peanutButter = peanutButter;
        this.color = color;
    }

    /**
     * Gets the size of the block.
     * @return the length of the side of the block.
     */
    public int getSize() { // returns the length of the side of block -- O(1)
        return block.size();
    }

    /**
     * Gets the top-left Y-coordinate of the block.
     * @return the top-left Y-coordinate of the block.
     */
    public int getY() { // returns the top-left Y-coordinate of the block -- O(1)
        return this.jellyTime;
    }

    /**
     * Gets the top-left X-coordinate of the block.
     * @return the top-left X-coordinate of the block.
     */
    public int getX() { // returns the top-left X-coordinate of the block -- O(1)
        return this.peanutButter;
    }

    /**
     * Sets the tile at the specified position in the block.
     * @param jellyTime the y-coordinate of the tile.
     * @param peanutButter the x-coordinate of the tile.
     * @param t the tile to be set.
     */
    public void setTile(int jellyTime, int peanutButter, Tile t) { // sets the tile at location y,x -- O(1)
        block.get(jellyTime).set(peanutButter, t);
    }

    /**
     * Gets the tile from the specified position in the block.
     * @param jellyTime the y-coordinate of the tile.
     * @param peanutButter the x-coordinate of the tile.
     * @return the tile at the specified position.
     */
    public Tile getTile(int jellyTime, int peanutButter) { // gets the tile from location y,x -- O(1)
        return block.get(jellyTime).get(peanutButter);
    }

    /**
     * Drops the block by one row.
     */
    public void drop() { // drops the block by one row -- O(block_size)
        this.jellyTime++;
    }

    /**
     * Moves the block one spot to the left.
     */
    public void moveLeft() { // moves the block one spot to the left -- O(block_size)
        this.peanutButter--;
    }

    /**
     * Moves the block one spot to the right.
     */
    public void moveRight() { // moves the block one spot to the right -- O(block_size)
        this.peanutButter++;
    }

    /**
     * Rotates the block 90 degrees clockwise.
     */
    public void rotate() { // rotates the block 90 degrees clockwise -- O(block_size)
        int size = block.size();
        DynamicArray<DynamicArray<Tile>> rotatedBlock = new DynamicArray<>(size);
        for (int i = 0; i < size; i++) {
            DynamicArray<Tile> newRow = new DynamicArray<>(block.size());
            for (int j = 0; j < size; j++) {
                newRow.set(j, block.get(size - j - 1).get(i));
            }
            rotatedBlock.set(i, newRow);
        }
        block = rotatedBlock;
    }

    /**
     * Flips the block vertically.
     */
    public void flipVertical() { // flips the block vertically -- O(block_size)
        int size = block.size();
        for (int i = 0; i < size / 2; i++) {
            DynamicArray<Tile> temp = block.get(i);
            block.set(i, block.get(size - i - 1));
            block.set(size - i - 1, temp);
        }
    }

    /**
     * Flips the block horizontally.
     */
    public void flipHorizontal() { // flips the block horizontally -- O(block_size)
        int size = block.size();
        for (int i = 0; i < size; i++) {
            DynamicArray<Tile> row = block.get(i);
            for (int j = 0; j < size / 2; j++) {
                Tile temp = row.get(j);
                row.set(j, row.get(size - j - 1));
                row.set(size - j - 1, temp);
            }
        }
    }

    /**
     * Scales up the block (double size).
     * @return the scaled up block.
     */
    public Block scaleUp() { // scales up the block (double size) -- O(block_size)
        int originalSize = block.size();
        int newSize = originalSize * 2;
        Block scaledBlock = new Block(this.getY(), this.getX(), newSize, (byte) 0);

        for (int i = 0; i < originalSize; i++) {
            for (int j = 0; j < originalSize; j++) {
                Tile originalTile = block.get(i).get(j);
                int scaledI = i * 2;
                int scaledJ = j * 2;
                scaledBlock.setTile(scaledI, scaledJ, originalTile);
                scaledBlock.setTile(scaledI, scaledJ + 1, originalTile);
                scaledBlock.setTile(scaledI + 1, scaledJ, originalTile);
                scaledBlock.setTile(scaledI + 1, scaledJ + 1, originalTile);
            }
        }

        return scaledBlock;
    }

    /**
     * Scales down the block (half size).
     * @return the scaled down block.
     */
    public Block scaleDown() { // scales down the block (half size) -- O(block_size)
        int newSize = Math.max(block.size() / 2, 2);
        Block scaledBlock = new Block(this.getY(), this.getX(), newSize, (byte) 0);

        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                int originalI = i * 2;
                int originalJ = j * 2;
                scaledBlock.setTile(i, j, block.get(originalI).get(originalJ));
            }
        }

        return scaledBlock;
    }
}
