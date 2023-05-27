package library.main.librarymanagementsystem.application;


public class BDworkers {
    private Integer Id;
    private String WorkFio;
    private String WorkLogin;
    private String WorkPassword;
    private String WorkType;

    public BDworkers(int Id, String WorkFio, String WorkLogin, String WorkPassword, String WorkType) {
        this.Id = Id;
        this.WorkFio = WorkFio;
        this.WorkLogin = WorkLogin;
        this.WorkPassword = WorkPassword;
        this.WorkType = WorkType;}

    public BDworkers() {}

    public Integer getPrCode(){ return Id; }

    public void setId(int Id) {
        this.Id = Id;
    }

    public Integer getId() {
        return Id;
    }

    public String getWorkFio() { return WorkFio; }

    public void setWorkFio(String WorkFio) { this.WorkFio = WorkFio; }

    public String getWorkLogin() { return WorkLogin; }

    public void setWorkLogin(String WorkLogin) { this.WorkLogin = WorkLogin; }

    public String getWorkPassword() {
        return WorkPassword;
    }

    public void setWorkPassword(String WorkPassword) {
        this.WorkPassword = WorkPassword;
    }

    public String getWorkType() {
        return WorkType;
    }

    public void setWorkType(String WorkType) {
        this.WorkType = WorkType;
    }
}