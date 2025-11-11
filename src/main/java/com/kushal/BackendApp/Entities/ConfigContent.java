package com.kushal.BackendApp.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="config")
@Data
@NoArgsConstructor
public class ConfigContent {

    @Id
    private String key;
    private String value;
}
