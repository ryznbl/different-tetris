/**
 * A class representing a tile in the Tetris game board.
 */
public class Tile
{
    /**
     * Color that will be taken from Game class for the tile.
     */
    private byte color;

    /**
     * Constructs a tile with the specified color.
     * @param color the color of the tile.
     */
    public Tile(byte color)
    {
        this.color = color;
    }

    /**
     * Gets the color of the tile.
     * @return the color of the tile.
     */
    public byte getColor()
    {
        return color;
    }
}
