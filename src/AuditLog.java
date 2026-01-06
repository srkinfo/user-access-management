import java.time.LocalDateTime;

public class AuditLog {

   private String actoruserName;
   private String action;
   private String targetUser;
   private LocalDateTime time;

    public AuditLog(String actoruserName, String action, String targetUser) {
        this.actoruserName = actoruserName;
        this.action = action;
        this.targetUser = targetUser;
        this.time = LocalDateTime.now();

    }


    public String toString(){
        return "Audit | Actor :" + actoruserName +
                "|action :" + action +
                "|targetUser :"+ targetUser +
                "|time :" + time;
    }
}
