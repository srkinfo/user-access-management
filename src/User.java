public class User {
    String username;
    String email_id;
    String status;
    public User (String username, String email_id){
        if( username == null || username.isEmpty()){
            throw new IllegalArgumentException("Invaild user name..");
        }
        if( email_id == null || !email_id.contains("@")){
            throw new IllegalArgumentException("Invalid email id...");
        }

        this.username = username;
        this.email_id = email_id;
        this.status = "Active";
    }

    public void deactivate(){
        if(status.equals("Active")){
            status = "Deactivate";
            System.out.println("user deactivated Sucessfully..");
        }else{
            System.out.println("User already deacctivated..");
        }
    }

    public void printdetails() {
        System.out.println("Username: "+ username);
        System.out.println("email_id: "+ email_id);
        System.out.println("Status: "+ status);

    }
}
