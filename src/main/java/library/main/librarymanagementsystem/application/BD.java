package library.main.librarymanagementsystem.application;


public class BD {
    private Integer Id;
    private String Fio;
    private String Phone;
    private String Passport;
    private String Address;
    private String Gender;
    private String Mail;
    private String Birth;

    public BD(int Id, String Fio, String Phone, String Passport, String Address, String Gender, String Mail, String Birth) {
        this.Id = Id;
        this.Fio = Fio;
        this.Phone = Phone;
        this.Passport = Passport;
        this.Address = Address;
        this.Gender = Gender;
        this.Mail = Mail;
        this.Birth = Birth;}

    public BD() {}

    public Integer getPrCode(){ return Id; }

    public void setId(int Id) {
        this.Id = Id;
    }

    public Integer getId() {
        return Id;
    }

    public String getFio() { return Fio; }

    public void setFio(String Fio) { this.Fio = Fio; }

    public String getPhone() { return Phone; }

    public void setPhone(String Phone) { this.Phone = Phone; }

    public String getPassport() {
        return Passport;
    }

    public void setPassport(String Passport) {
        this.Passport = Passport;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String Mail) {
        this.Mail = Mail;
    }

    public String getBirth() {
        return Birth;
    }

    public void setBirth(String Birth) {
        this.Birth = Birth;
    }

}