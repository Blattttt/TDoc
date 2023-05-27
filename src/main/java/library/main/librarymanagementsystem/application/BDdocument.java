package library.main.librarymanagementsystem.application;


public class BDdocument {
    private Integer Id;
    private String DocName;
    private String DocType;


    public BDdocument(int Id, String DocName, String DocType) {
        this.Id = Id;
        this.DocName = DocName;
        this.DocType = DocType;}

    public BDdocument() {}

    public Integer getPrCode(){ return Id; }

    public void setId(int Id) {
        this.Id = Id;
    }

    public Integer getId() {
        return Id;
    }

    public String getDocName() { return DocName; }

    public void setDocName(String DocName) { this.DocName = DocName; }

    public String getDocType() { return DocType; }

    public void setDocType(String DocType) { this.DocType = DocType; }

}