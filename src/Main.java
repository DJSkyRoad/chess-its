import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Board brd = new Board();
        System.out.println(brd);
        
        String inp = scanner.nextLine();
        System.out.println(inp);
    }
}
