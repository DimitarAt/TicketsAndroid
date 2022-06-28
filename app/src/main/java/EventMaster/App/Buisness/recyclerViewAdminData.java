package EventMaster.App.Buisness;

public class recyclerViewAdminData {




    private String username;
    private String name;
    private String roles;

    public recyclerViewAdminData(String username, String name, String roles) {
        this.username = username;
        this.name = name;
        this.roles = roles;
    }
    public void setRoles(String roles) {
        this.roles = roles;
    }
    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getRoles() {
        return roles;
    }

}
