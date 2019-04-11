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

  public User(String email, String aboutMe){
    this.email = email;
    this.aboutMe = aboutMe;
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
}
