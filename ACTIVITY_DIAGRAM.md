# Activity Diagram - Sudoku Gameplay Flow

```mermaid
stateDiagram-v2
    [*] --> LoginScreen
    
    LoginScreen --> Authenticate : User enters credentials
    Authenticate --> LoginScreen : [Invalid Credentials] Show Error
    Authenticate --> StartScreen : [Valid Credentials] Success
    
    StartScreen --> GenerateBoard : User clicks "INICIAR JUEGO"
    
    GenerateBoard --> PlayTurn : Board generated with fixed cells
    
    PlayTurn --> AwaitUserInput
    
    AwaitUserInput --> ValidInput : User enters number
    AwaitUserInput --> InvalidInput : User enters invalid range/format
    
    InvalidInput --> ShowErrorMsg
    ShowErrorMsg --> PlayTurn
    
    ValidInput --> CheckRules : Engine validates placement
    
    CheckRules --> ValidPlacement : Number complies with rules
    CheckRules --> InvalidPlacement : Number breaks rules (row/col/block)
    
    InvalidPlacement --> HighlightError
    HighlightError --> PlayTurn
    
    ValidPlacement --> UpdateBoard
    UpdateBoard --> CheckVictory
    
    CheckVictory --> PlayTurn : Board is not completely filled/correct
    CheckVictory --> GameWon : Board is fully and correctly filled
    
    GameWon --> [*] : Game Over (Success)
```
