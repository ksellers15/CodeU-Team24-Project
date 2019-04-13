package com.google.codeu.data;

public class User {

  private String email;
  private String aboutMe;
  private String password;
  private AccountType accountType;

  public enum AccountType{
    STUDENT,
    PROFESSOR
  }

  public User(String email, String aboutMe, String password, AccountType type) {
    this.email = email;
    this.aboutMe = aboutMe;
    this.password = password;
    this.accountType = type;
  }

  public User(String email, String aboutMe, String password, String type) {
    this.email = email;
    this.aboutMe = aboutMe;
    this.password = password;
    this.accountType = AccountType.valueOf(type.toUpperCase());
  }

  public String getEmail(){
    return email;
  }

  public String getAboutMe() {
    return aboutMe;
  }

  public String getPassword(){
    return password;
  }

  public AccountType getAccountType(){
    return accountType;
  }

  public void setAboutMe(String text){
    this.aboutMe = text;
  }
}
