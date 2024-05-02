package com.SpringSecuritySignupFeature.Entity;

import javax.persistence.*;

import lombok.Data;
import java.util.Set;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String username;
    private String password;

    // From here Role concept is added

    // How the User map to this Table? , So we use @ManyToMany mapping here
    // to join these two tables we use @joinTable annotation.

    @ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="user_roles" ,
           joinColumns= @JoinColumn(name="user_id", referencedColumnName="id"),
           inverseJoinColumns= @JoinColumn(name="role_id", referencedColumnName="id"))

    private Set<Role> roles; //here we use Set<>, instead of List<>, because Role name cannot be duplicate.

    //Here which is the foreign key referring to primary key?
    // here "user_id" is a Foreign key & "id" column in user Table is the primary key.
    // Q- How do you join two tables in hibernate?
    // A- we use a third table to join the two tables, using annotation @JoinTable(here we give third table name.)
    // @JoinColumn - to join the column.
    // @JoinTable - to join the table.
}
