public class User {

    // ===== Fields (Encapsulation) =====
    private String username;
    private String email;
    private String status;
    private int failedAttempts;

    private static final int MAX_ATTEMPTS = 3;

    // ===== Constructor =====
    public User(String username, String email) {

        // Validation
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }

        this.username = username;
        this.email = email;
        this.status = "ACTIVE";
        this.failedAttempts = 0;
    }

    // ===== Getters =====
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    // ===== Login Logic =====
    public void login(boolean success) throws AccountLockedException {

        if (status.equals("LOCKED")) {
            throw new AccountLockedException("Account is locked. Contact admin.");
        }

        if (success) {
            System.out.println("Login successful");
            failedAttempts = 0; // reset on success
        } else {
            failedAttempts++;
            System.out.println("Login failed. Attempt: " + failedAttempts);

            if (failedAttempts >= MAX_ATTEMPTS) {
                status = "LOCKED";
                System.out.println("Account locked due to multiple failed attempts");
            }
        }
    }

    // ===== Deactivate User =====
    public void deactivate() {
        if (status.equals("ACTIVE")) {
            status = "DEACTIVATED";
            System.out.println("User deactivated successfully");
        } else {
            System.out.println("User cannot be deactivated from current state: " + status);
        }
    }

    // ===== Print Details =====
    public void printDetails() {
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
        System.out.println("Status: " + status);
        System.out.println();
    }
}
