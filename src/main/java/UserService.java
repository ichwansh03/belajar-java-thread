public class UserService {

    final ThreadLocal<String> threadLocal = new ThreadLocal<>();
    //tanpa thread local
    //private String user;

    public void setUser(String user){
        threadLocal.set(user);
        //this.user = user
    }

    public void doAction(){
        var user = threadLocal.get();
        System.out.println(user + " do action");
    }
}
