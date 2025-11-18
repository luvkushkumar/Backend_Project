package com.kushal.BackendApp.Entities;

import com.kushal.BackendApp.Enum.Sentiment;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@ToString
public class JournalEntry {

    @Id
    private String id;
    @NotNull
    private String title;
    private String content;
    private LocalDateTime date;
    private Sentiment sentiment;

    @PrePersist
    public void generateId()
    {
        if(this.id == null)
        {
            this.id = UUID.randomUUID().toString();
        }
    }
}
