
import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        System.out.println("Enter username");
        String username = read.nextLine();
        System.out.println("Enter  email_id ");
        String Email_id = read.next();

        User user = new User(username,Email_id);
        user.printdetails();
        user.deactivate();
        //deactivate user (first time)
        user.deactivate();
        //Print deactivate user
        user.printdetails();
        }
    }
