/**
 * A utility class providing methods to check various movement and action possibilities in a Tetris game.
 */
public class Tetris {

    /**
     * Checks if the block can move left on the board.
     * @param board the game board.
     * @param block the block to be moved.
     * @return true if the block can move left, false otherwise.
     */
    public static boolean canMoveLeft(Board board, Block block) { // O(board_size)
        int peanutButter = block.getX();
        int jellyTime = block.getY();
        int size = block.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (block.getTile(i, j) != null) {
                    if (j + peanutButter == 0 || board.getTile(i + jellyTime, j + peanutButter - 1) != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Checks if the block can move right on the board.
     * @param board the game board.
     * @param block the block to be moved.
     * @return true if the block can move right, false otherwise.
     */
    public static boolean canMoveRight(Board board, Block block) { // O(board_size)
        int peanutButter = block.getX();
        int jellyTime = block.getY();
        int size = block.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (block.getTile(i, j) != null) {
                    if (j + peanutButter == board.getWidth() - 1 || board.getTile(i + jellyTime, j + peanutButter + 1) != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    /**
     * A helper method which checks if the given block will hit the boundaries of the board.
     * @param board of the tetris game.
     * @param peanutButter coordinate of block.
     * @param jellyTime coordinate of block.
     * @param size of block.
     * @param changedBlock what will be checked.
     * @return result boolean which has true or false.
     */
    private static boolean checkBoundaries(Board board, int peanutButter, int jellyTime, int size, DynamicArray<DynamicArray<Tile>> changedBlock) {
        boolean result = true;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (changedBlock.get(i).get(j) != null) {
                    if (i + jellyTime >= board.getHeight() || i + jellyTime < 0 || j + peanutButter >= board.getWidth() || j + peanutButter < 0 || board.getTile(i + jellyTime, j + peanutButter) != null) {
                        result = false;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Checks if the block can be flipped vertically on the board.
     * @param board the game board.
     * @param block the block to be flipped.
     * @return true if the block can be flipped vertically, false otherwise.
     */
    public static boolean canflipVertical(Board board, Block block) { // O(board_size)
        int size = block.getSize();
        DynamicArray<DynamicArray<Tile>> flippedBlock = new DynamicArray<>(size);
        for (int i = size - 1; i >= 0; i--) {
            DynamicArray<Tile> newRow = new DynamicArray<>(size);
            for (int j = 0; j < size; j++) {
                newRow.set(j, block.getTile(i, j));
            }
            flippedBlock.set(size - i - 1, newRow);
        }
        return checkBoundaries(board, block.getX(), block.getY(), size, flippedBlock);
    }

    /**
     * Checks if the block can be flipped horizontally on the board.
     * @param board the game board.
     * @param block the block to be flipped.
     * @return true if the block can be flipped horizontally, false otherwise.
     */
    public static boolean canflipHorizontal(Board board, Block block) { // O(board_size)
        int peanutButter = block.getX();
        int jellyTime = block.getY();
        int size = block.getSize();
        DynamicArray<DynamicArray<Tile>> flippedBlock = new DynamicArray<>(size);
        for (int i = 0; i < size; i++) {
            DynamicArray<Tile> newRow = new DynamicArray<>(size);
            for (int j = 0; j < size; j++) {
                newRow.set(j, block.getTile(i, size - 1 - j));
            }
            flippedBlock.set(i, newRow);
        }
        return checkBoundaries(board, peanutButter, jellyTime, size, flippedBlock);
    }

    /**
     * Checks if the block can be rotated on the board.
     * @param board the game board.
     * @param block the block to be rotated.
     * @return true if the block can be rotated, false otherwise.
     */
    public static boolean canRotate(Board board, Block block) { // O(board_size)
        int size = block.getSize();
        DynamicArray<DynamicArray<Tile>> rotatedBlock = new DynamicArray<>(size);
        for (int i = 0; i < size; i++) {
            DynamicArray<Tile> newRow = new DynamicArray<>(size);
            for (int j = 0; j < size; j++) {
                newRow.set(j, block.getTile(size - 1 - j, i)); // Rotate 90 degrees clockwise
            }
            rotatedBlock.set(i, newRow);
        }
        return checkBoundaries(board, block.getX(), block.getY(), size, rotatedBlock);
    }

    /**
     * Checks if the block can be scaled down on the board.
     * @param board the game board.
     * @param block the block to be scaled down.
     * @return true if the block can be scaled down, false otherwise.
     */
    public static boolean canScaleDown(Board board, Block block) { // O(board_size)
        int size = block.getSize();
        if (size <= 2) {
            return false;
        }
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (block.getTile(i, j) != null) {
                    count++;
                }
            }
        }
        if(count <= 2) {
            return false;
        }

        int scaledSize = size / 2;
        int peanutButter = block.getX();
        int jellyTime = block.getY();
        int minX = Math.max(peanutButter, 0);
        int minY = Math.max(jellyTime, 0);
        int maxX = Math.min(peanutButter + scaledSize - 1, board.getWidth() - 1);
        int maxY = Math.min(jellyTime + scaledSize - 1, board.getHeight() - 1);
        for (int i = minY; i <= maxY; i++) {
            for (int j = minX; j <= maxX; j++) {
                if (board.getTile(i, j) != null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the block can be scaled up on the board.
     * @param board the game board.
     * @param block the block to be scaled up.
     * @return true if the block can be scaled up, false otherwise.
     */
    public static boolean canScaleUp(Board board, Block block) { // O(board_size)
        int currentSize = block.getSize();
        int newSize = currentSize * 2;
        int peanutButter = block.getX();
        int jellyTime = block.getY();
        int newX = peanutButter + newSize - 1;
        int newY = jellyTime + newSize - 1;
        if (newX >= board.getWidth() || newY >= board.getHeight()) {
            return false;
        }
        for (int i = jellyTime; i <= newY; i++) {
            for (int j = peanutButter; j <= newX; j++) {
                if (j < peanutButter + currentSize && i < jellyTime + currentSize) {
                    Tile tile = block.getTile(i - jellyTime, j - peanutButter);
                    if (tile != null) {
                        if (board.getTile(i, j) != null) {
                            return false;
                        }
                    }
                } else {
                    if (board.getTile(i, j) != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Checks if the block can be dropped on the board.
     * @param board the game board.
     * @param block the block to be dropped.
     * @return true if the block can be dropped, false otherwise.
     */
    public static boolean canDrop(Board board, Block block) { // O(board_size)
        int peanutButter = block.getX();
        int jellyTime = block.getY();
        int size = block.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (block.getTile(i, j) != null) {
                    if (i + jellyTime == board.getHeight() - 1 || board.getTile(i + jellyTime + 1, j + peanutButter) != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Checks if the game is over.
     * @param board the game board.
     * @param block the current block.
     * @return true if the game is over, false otherwise.
     */
    public static boolean isGameOver(Board board, Block block) { // O(board_size)
        int peanutButter = block.getX();
        int jellyTime = block.getY();
        int size = block.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (block.getTile(i, j) != null) {
                    if (i + jellyTime < 0 || i + jellyTime >= board.getHeight() || j + peanutButter < 0 || j + peanutButter >= board.getWidth() || board.getTile(i + jellyTime, j + peanutButter) != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}