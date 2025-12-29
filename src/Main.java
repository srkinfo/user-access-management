import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // ===== Create ADMIN user =====
        System.out.println("Create ADMIN user");
        System.out.print("Enter admin username: ");
        String adminUsername = scanner.nextLine();

        System.out.print("Enter admin email: ");
        String adminEmail = scanner.nextLine();

        User admin = new User(adminUsername, adminEmail, "ADMIN");

        // ===== Create NORMAL user =====
        System.out.println("\nCreate NORMAL user");
        System.out.print("Enter user username: ");
        String userUsername = scanner.nextLine();

        System.out.print("Enter user email: ");
        String userEmail = scanner.nextLine();

        User user = new User(userUsername, userEmail, "USER");

        // ===== Print initial details =====
        System.out.println("\n--- Initial User Details ---");
        admin.printDetails();
        user.printDetails();

        // ===== Simulate failed login attempts =====
        System.out.println("\nSimulating failed login attempts for USER");

        try {
            user.login(false);
            user.login(false);
            user.login(false); // should lock the user
        } catch (AccountLockedException e) {
            System.out.println(e.getMessage());
        }

        // ===== USER tries to unlock himself (should fail) =====
        System.out.println("\nUSER tries to unlock himself");

        try {
            user.unlockUser(user);
        } catch (UnauthorizedActionException e) {
            System.out.println(e.getMessage());
        }

        // ===== ADMIN unlocks USER =====
        System.out.println("\nADMIN unlocks USER");

        try {
            admin.unlockUser(user);
        } catch (UnauthorizedActionException e) {
            System.out.println(e.getMessage());
        }

        // ===== ADMIN deactivates USER =====
        System.out.println("\nADMIN deactivates USER");

        try {
            admin.deactivateUser(user);
        } catch (UnauthorizedActionException e) {
            System.out.println(e.getMessage());
        }

        // ===== ADMIN tries to deactivate himself (should fail) =====
        System.out.println("\nADMIN tries to deactivate himself");

        try {
            admin.deactivateUser(admin);
        } catch (UnauthorizedActionException e) {
            System.out.println(e.getMessage());
        }

        // ===== Final state =====
        System.out.println("\n--- Final User Details ---");
        admin.printDetails();
        user.printDetails();

        scanner.close();
    }
}
