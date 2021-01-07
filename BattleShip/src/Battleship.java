import java.util.Scanner;

public class Battleship {
    final static Scanner scanner = new Scanner(System.in);
    String[][] gameBoard = new String[11][11];

    public void makeGame() {
        int alpha = 65;

        for (int i = 0; i < gameBoard.length; i++){
            for (int j = 0; j < gameBoard.length; j++){
                if (i == 0){
                    gameBoard[i][j] = Integer.toString(j);
                }
                else if (j == 0){
                    gameBoard[i][j] = Character.toString((char) alpha);
                    alpha++;
                }
                else {
                    gameBoard[i][j] = "~";
                }
            }
        }
        gameBoard[0][0] = " ";
        gameBoard[0][10] = "10";
    }

    public void strategize() {
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
    }

    void catchError(String ship, double distance){
        while(true){
            try {
                placeShips(ship, distance);
                seeBoard();
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

    public void seeBoard(){
        for (String[] chars : gameBoard) {
            for (int j = 0; j < gameBoard.length; j++) {
                System.out.print(chars[j] + " ");
            }
            System.out.print("\n");
        }
    }

    public static void main(String[] args) {
        Battleship player1 = new Battleship();

        player1.makeGame();
        player1.seeBoard();
        player1.strategize();

    }
}
