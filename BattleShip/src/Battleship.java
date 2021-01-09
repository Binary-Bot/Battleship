import java.util.Scanner;
import java.io.IOException;

public class Battleship {
    final static Scanner scanner = new Scanner(System.in);
    String[][] gameBoard = new String[11][11];
    String[][] displayBoard = new String[11][11];

    public void makeGame() {
        int alpha = 65;

        for (int i = 0; i < gameBoard.length; i++){
            for (int j = 0; j < gameBoard.length; j++){
                if (i == 0){
                    gameBoard[i][j] = Integer.toString(j);
                    displayBoard[i][j] = Integer.toString(j);
                }
                else if (j == 0){
                    gameBoard[i][j] = Character.toString((char) alpha);
                    displayBoard[i][j] = Character.toString((char) alpha);
                    alpha++;
                }
                else {
                    gameBoard[i][j] = "~";
                    displayBoard[i][j] = "~";
                }
            }
        }
        gameBoard[0][0] = " ";
        gameBoard[0][10] = "10";
        displayBoard[0][0] = " ";
        displayBoard[0][10] = "10";
        seeBoard(gameBoard);
    }

    public String[][] strategize() {
        System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");
        catchError("Aircraft Carrier", 5.0);
        System.out.println("Enter the coordinates of the Battleship (4 cells):");
        catchError("Battleship", 4.0);
        System.out.println("Enter the coordinates of Submarine (3 cells):");
        catchError("Submarine", 3.0);
        System.out.println("Enter the coordinates of the Cruiser (3 cells):");
        catchError("Cruiser", 3.0);
        System.out.println("Enter the coordinates of the Destroyer (2 cells):");
        catchError("Destroyer", 2.0);

        return gameBoard;
    }

    void catchError(String ship, double distance){
        while(true){
            try {
                placeShips(ship, distance);
                seeBoard(gameBoard);
                break;
            } catch(NumberFormatException e) {
                System.out.println("Please enter numbers! Try again:");
            } catch (Exception e) {
                System.out.print("EXCEPTION: " + e.getClass().getName());
            }
        }
    }

    void placeShips(String ship, double cells) {
        while (true) {
            String start = scanner.next();
            String end = scanner.next();

            int x1 = Character.getNumericValue(start.charAt(0)) - 9;
            int y1 = Integer.parseInt(start.substring(1));
            int x2 = Character.getNumericValue(end.charAt(0)) - 9;
            int y2 = Integer.parseInt(end.substring(1));
            double distance = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2)) + 1;
            if (distance == cells) {
                if (x1 > x2){
                    int x3 = x2;
                    x2 = x1;
                    x1 = x3;
                }
                if (y1 > y2){
                    int y3 = y2;
                    y2 = y1;
                    y1 = y3;
                }
                if (isAvailable(x1, y1, x2, y2)) {
                    for (int i = x1; i <= x2; i++){
                        for (int j = y1; j <= y2; j++){
                            gameBoard[i][j] = "O";
                        }
                    }
                    break;
                }
                else{
                    System.out.println("Error! You placed it too close to another one. Try again:");
                }
            } else {
                if ((x1 == x2) || (y1 == y2)){
                    System.out.println("Error! Wrong length of " + ship + "! Try again:");
                }
                else {
                    System.out.println("Error! Wrong ship location! Try again:");
                }
            }
        }
    }

    public void takeShot(String[][] gameField){
        System.out.println("\nTake a shot!");
        do {
            try {
                String cell = scanner.next();
                int x = Character.getNumericValue(cell.charAt(0)) - 9;
                int y = Integer.parseInt(cell.substring(1));
                if (gameField[x][y].equals("O")) {
                    displayBoard[x][y] = "X";
                    gameField[x][y] = "X";
                    System.out.println("You hit a ship!");
                    if (isShipDestroyed(gameField, x, y)){
                        System.out.println("You sank a ship!");
                    }
                }
                else if (gameBoard[x][y].equals("X")) {
                    System.out.println("You hit a ship!");
                }
                else{
                    displayBoard[x][y] = "M";
                    gameBoard[x][y] = "M";
                    System.out.println("You missed!");
                }

                break;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
            } catch (Exception e) {
                System.out.println("Error: " + e.getClass().getName());
            }
        } while (thereAreShips());
    }

    boolean isShipDestroyed(String[][] gameField, int x, int y) {
        boolean destroyed = true;
        int xMin = x == 1? x : x - 1;
        int xMax = x == 10? x : x + 1;
        int yMin = y == 1? y : y - 1;
        int yMax = y == 10? y : y + 1;

        if (gameField[xMin][y].equals("O")){
            destroyed = false;
        }
        else if (gameField[xMax][y].equals("O")){
            destroyed = false;
        }
        else if (gameField[x][yMin].equals("O")){
            destroyed = false;
        }
        else if (gameField[x][yMax].equals("O")){
            destroyed = false;
        }
        return destroyed;
    }

    boolean isAvailable(int x1, int y1, int x2, int y2){
        boolean available = true;
        int x = x2 == 10? x2 : x2+1;
        int y = y2 == 10? y2 : y2+1;
        for (int i = x1-1; i <= x; i++){
            for (int j = y1-1; j <= y; j++){
                if (gameBoard[i][j].equals("O")) {
                    available = false;
                    break;
                }
            }
        }
        return available;
    }

    boolean thereAreShips(){
        boolean value = false;
        for (String[] strings : gameBoard) {
            for (int j = 0; j < gameBoard.length; j++) {
                if (strings[j].equals("O")) {
                    value = true;
                    break;
                }
            }
        }
        return value;
    }

    public void displayGame(String player){
        seeBoard(displayBoard);
        System.out.println("--------------------");
        seeBoard(gameBoard);
        System.out.println(player + ", it's your turn:");
    }

    public void seeBoard(String[][] board){
        for (String[] chars : board) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(chars[j] + " ");
            }
            System.out.print("\n");
        }
    }

    public static int promptEnterKey() {
        System.out.println("Press Enter and pass the move to another player");
        int enterKey;
        try {
            enterKey = System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
            enterKey = 0;
        }
        return enterKey;
    }

    public static void main(String[] args) {
        Battleship player1 = new Battleship();
        Battleship player2 = new Battleship();

        System.out.println("Player 1, place your ships on the game field");
        player1.makeGame();
        String[][] player1field = player1.strategize();
        int enterKey = promptEnterKey();

        if (enterKey == 10) {
            System.out.println("Player 2, place your ships on the game field");
            player2.makeGame();
            String[][] player2field = player2.strategize();
            enterKey = promptEnterKey();
            System.out.println("The game starts!");

            while (true) {
                player1.displayGame("Player 1");
                player1.takeShot(player2field);
                if (!player2.thereAreShips()){
                    break;
                }
                enterKey = promptEnterKey();
                player2.displayGame("Player 2");
                player2.takeShot(player1field);
                if (!player1.thereAreShips()){
                    break;
                }
                enterKey = promptEnterKey();
            }
            System.out.println("You sank the last ship. You won. Congratulations!");
        }
    }
}