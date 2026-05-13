# Class Diagram - Sudoku in Java

```mermaid
classDiagram
    class Sudoku {
        -int[][] board
        -boolean[][] fixedCells
        +generateBoard(String difficulty) void
        +isMovementValid(int row, int col, int value) boolean
        +placeNumber(int row, int col, int value) boolean
        +isSolved() boolean
        +getBoard() int[][]
        +getFixedCells() boolean[][]
    }

    class SudokuGenerator {
        -int[][] board
        -Random random
        +generateInitialBoard(int emptyCells) int[][]
        -fillDiagonalBlock(int startRow, int startCol) void
        -isSafeInBlock(int startRow, int startCol, int num) boolean
        -isValid(int[][] tab, int row, int col, int num) boolean
        -solveSudoku(int[][] tab) boolean
        -removeCells(int count) void
    }

    class DatabaseConnection {
        -String CONFIG_FILE
        -Properties properties
        +static getConnection() Connection
    }

    class UserDAO {
        +authenticate(String email, String pass) boolean
    }

    class SudokuGUI {
        -Sudoku sudoku
        -UserDAO userDAO
        -JTextField[][] guiCells
        +startGUI() void
        -createLoginScreen() JPanel
        -createStartScreen() JPanel
        -createGameScreen() JPanel
        -generateNewGame(String difficulty) void
        -updateBoardUI() void
        -handleUserInput(int row, int col, String text) void
    }

    class Main {
        +main(String[] args) void
    }

    class SudokuTest {
        -Sudoku sudoku
        +setUp() void
        +testGenerateBoard() void
        +testIsMovementValid() void
        +testPlaceNumber() void
        +testFixedCellModification() void
        +testIsSolved() void
    }

    Sudoku --> SudokuGenerator : uses
    SudokuGUI --> Sudoku : manages
    SudokuGUI --> UserDAO : uses for auth
    UserDAO --> DatabaseConnection : gets connection
    Main --> SudokuGUI : launches
    SudokuTest --> Sudoku : tests
```
