package org.criteria4jpa.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Address {

  @Id @GeneratedValue
  private long id;
  
  @ManyToOne
  @JoinColumn(name="person_id")
  private Person person;
  
  @Basic
  private String street;
  
  @Basic
  private String city;
  
  @ManyToOne
  @JoinColumn(name="country_id")
  private Country country;
  
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  
  
}
