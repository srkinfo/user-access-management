
import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        System.out.println("Enter username");
        String username = read.nextLine();
        System.out.println("Enter  email_id ");
        String Email_id = read.next();

        User user = new User(username,Email_id);
        user.printDetails();

        try {
            user.login(false); // 1st failure
            user.login(false); // 2nd failure
            user.login(false); // 3rd failure → LOCKED
            user.login(true);  // should throw exception
        } catch (AccountLockedException e) {
            System.out.println(e.getMessage());
        }

        user.printDetails();
        }
    }
