import java.util.*;
import java.time.LocalDateTime;

public class User {

    // ===== Fields (Encapsulation) =====
    private String username;
    private String email;
    private UserStatus status;
    private int failedAttempts;
    private Role role;

    private static final int MAX_ATTEMPTS = 3;
    private Set<Permission> permissions;


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

        this.permissions = new HashSet<>();
        if(role == Role.USER){
           this.permissions.add(Permission.LOGIN);
        }
        if(role == Role.ADMIN){
           this.permissions.add(Permission.LOGIN);
           this.permissions.add(Permission.DEACTIVATE_USER);
           this.permissions.add(Permission.UNLOCK_USER);
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


    public boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
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
    public void deactivateSelf() {
        if (isActive()) {
            status = UserStatus.DEACTIVATED;
            System.out.println("User deactivated successfully");
        } else {
            System.out.println("User cannot be deactivated from current state: " + status);
        }
    }

    // =======UnlockUser Logic ======
    public void unlockUser(User targetUser) throws UnauthorizedActionException{
        if(!hasPermission(Permission.UNLOCK_USER)){
            throw new UnauthorizedActionException ("Only ADMIN can unlock user");
        }
        if (!targetUser.isLocked()) {
            System.out.println("Target user is not locked");
            return;
        }
        targetUser.status = UserStatus.ACTIVE;
        targetUser.failedAttempts = 0;
        System.out.println("User unlocked successfully by ADMIN");

        //=====AuditLog====//
        AuditLog log = new AuditLog(this.username,"UNLOCK_USER",targetUser.username);
        System.out.println(log);

    }

    // ====== ADMIN DEACTIVATES A USER =====

    public void deactivateUser(User targetUser) throws UnauthorizedActionException{
        if(!hasPermission(Permission.DEACTIVATE_USER)){
            throw new UnauthorizedActionException("Only ADMIN can deactivate users");
        }
        if(this == targetUser){
            throw new UnauthorizedActionException("Admin cannot deactivate himself");
        }

            targetUser.status = UserStatus.DEACTIVATED;
        System.out.println("User deactivated by ADMIN");

        AuditLog log = new AuditLog(this.username,"DEACTIVATE_USER", targetUser.username);
        System.out.println(log);
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
