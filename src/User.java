public class User {

    // ===== Fields (Encapsulation) =====
    private String username;
    private String email;
    private String status;
    private int failedAttempts;
    private String role;

    private static final int MAX_ATTEMPTS = 3;

    // ===== Constructor =====
    public User(String username, String email, String role) {

        // Validation
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }

        if (role == null || !(role.equals("ADMIN")|| role.equals("USER"))) {
            throw new IllegalArgumentException("Role must be ADMIN or USER");
        }

        this.username = username;
        this.email = email;
        this.status = "ACTIVE";
        this.role = role;
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

    public String getRole(){
        return  role;
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

    // =======UnlockUser Logic ======
    public void unlockUser(User targetUser) throws UnauthorizedActionException{
        if(!this.role.equals("ADMIN")){
            throw new UnauthorizedActionException ("Only ADMIN can unlock user");
        }
        if (!targetUser.status.equals("LOCKED")) {
            System.out.println("Target user is not locked");
        }
        targetUser.status = "ACTIVE";
        targetUser.failedAttempts = 0;
        System.out.println("User unlocked successfully by ADMIN");
    }

    // ====== ADMIN DEACTIVATES A USER =====

    public void deactivateUser(User targetUser) throws UnauthorizedActionException{
        if(!this.role.equals("ADMIN")){
            throw new UnauthorizedActionException("Only ADMIN can deactivate users");
        }
        if(this == targetUser){
            throw new UnauthorizedActionException("Admin cannot deactivate himself");
        }

            this.status = "DEACTIVATED";
        System.out.println("User deactivated by ADMIN");
    }

    // ===== Print Details =====
    public void printDetails() {
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
        System.out.println("Role: "+ role);
        System.out.println("Status: " + status);
        System.out.println();
    }
}
