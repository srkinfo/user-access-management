public class User {

    // ===== Fields (Encapsulation) =====
    private String username;
    private String email;
    private UserStatus status;
    private int failedAttempts;
    private Role role;

    private static final int MAX_ATTEMPTS = 3;

    // ===== Constructor =====
    public User(String username, String email, Role role) {

        // Validation
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }

        if ( role == null || !( role == Role.USER|| role == Role.ADMIN ) ){
            throw new IllegalArgumentException("Role must be ADMIN or USER");
        }

        this.username = username;
        this.email = email;
        this.status = UserStatus.ACTIVE;
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

    public UserStatus getStatus() {
        return status;
    }

    public Role getRole(){

        return  role;
    }
// ===== helper methods ======
    private boolean isAdmin(){
        return role == Role.ADMIN;
    }
    private boolean isUser(){
        return role == Role.USER ;
    }
    private boolean isLocked() {
        return status == UserStatus.LOCKED ;
    }

    private boolean isActive(){
        return status == UserStatus.ACTIVE;
    }
    private boolean isDeactivated(){
        return status == UserStatus.DEACTIVATED;
    }
    // ===== Login Logic =====
    public void login(boolean success) throws AccountLockedException {

        if (isLocked()) {
            throw new AccountLockedException("Account is locked. Contact admin.");
        }

        if (success) {
            System.out.println("Login successful");
            failedAttempts = 0; // reset on success
        } else {
            failedAttempts++;
            System.out.println("Login failed. Attempt: " + failedAttempts);

            if (failedAttempts >= MAX_ATTEMPTS) {
                status = UserStatus.LOCKED;
                System.out.println("Account locked due to multiple failed attempts");
            }
        }
    }




    // ===== Deactivate User =====
    public void deactivate() {
        if (isActive()) {
            status = UserStatus.DEACTIVATED;
            System.out.println("User deactivated successfully");
        } else {
            System.out.println("User cannot be deactivated from current state: " + status);
        }
    }

    // =======UnlockUser Logic ======
    public void unlockUser(User targetUser) throws UnauthorizedActionException{
        if(!isAdmin()){
            throw new UnauthorizedActionException ("Only ADMIN can unlock user");
        }
        if (!isLocked()) {
            System.out.println("Target user is not locked");
        }
        targetUser.status = UserStatus.ACTIVE;
        targetUser.failedAttempts = 0;
        System.out.println("User unlocked successfully by ADMIN");
    }

    // ====== ADMIN DEACTIVATES A USER =====

    public void deactivateUser(User targetUser) throws UnauthorizedActionException{
        if(!isAdmin()){
            throw new UnauthorizedActionException("Only ADMIN can deactivate users");
        }
        if(this == targetUser){
            throw new UnauthorizedActionException("Admin cannot deactivate himself");
        }

            status = UserStatus.DEACTIVATED;
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
