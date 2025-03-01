package com.example.wordquest.domain.model;
import com.example.wordquest.domain.valueObjects.EmailVO;
import jakarta.persistence.*;


@Entity
@Table(name = "players")
public class Player extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String username;

    @Embedded
    private EmailVO email;

    public Player(String username, EmailVO email) {
        this.username = username;
        this.email = email;
    }
    public Player() {
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        return result;
    }



    public String getUsername() {
        return username;
    }

    public EmailVO getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(EmailVO email) {
        this.email = email;
    }



    @Override
    public String toString() {
        return "Player [username=" + username + ", email=" + email.getValue() + "]";
    }
    
    
}