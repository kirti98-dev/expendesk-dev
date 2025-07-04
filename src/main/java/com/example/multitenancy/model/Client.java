package com.example.multitenancy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;

    @Column(nullable = false, unique = true)
    private String clientCode;

    @Column(nullable = false)
    private String clientName;

    @Column(nullable = false)
    private String databaseUrl;

    @Column(nullable = false)
    private String databaseUsername;

    @Column(nullable = false)
    private String databasePassword;
    
    
    @Column(nullable = false)
    private String dbUrl;
    
    @Column(nullable = false)
    private String schemaName;
    
    @Column(nullable = false)
    private String dbUsername;
    
    @Column(nullable = false)
    private String dbPassword;
    
    
}
