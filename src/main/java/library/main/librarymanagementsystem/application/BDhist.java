package library.main.librarymanagementsystem.application;


public class BDhist {
    private Integer Id;
    private Integer Document;
    private Integer Worker;
    private Integer Client;
    private String Name;
    private String Data;

    public BDhist(int Id, int Document, int Worker, int Client, String Name, String Data) {
        this.Id = Id;
        this.Document = Document;
        this.Worker = Worker;
        this.Client = Client;
        this.Name = Name;
        this.Data = Data;}

    public BDhist() {}

    public Integer getPrCode(){ return Id; }

    public void setId(int Id) {
        this.Id = Id;
    }

    public Integer getId() {
        return Id;
    }

    public Integer getDocument() { return Document; }

    public void setDocument(int Document) { this.Document = Document; }

    public Integer getWorker() { return Worker; }

    public void setWorker(int Worker) { this.Worker = Worker; }

    public Integer getClient() {
        return Client;
    }

    public void setClient(int Client) {
        this.Client = Client;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }

}