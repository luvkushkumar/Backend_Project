package com.kushal.BackendApp.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name="user")
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    private String id;

    @NotNull
    @Column(nullable = false,unique = true)
    private String username;
    private String password;
    private String email;
    @Column(name = "sentiment_analysis")
    private boolean sentimentAnalysis;
    List<String> roles;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "user_key",referencedColumnName = "id")
    private List<JournalEntry> journalEntries = new ArrayList<>();

    @PrePersist
    public void generateId()
    {
        if(this.id == null)
        {
            this.id = UUID.randomUUID().toString();
        }
    }

}
