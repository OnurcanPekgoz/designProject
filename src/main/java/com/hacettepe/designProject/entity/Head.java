package com.hacettepe.designProject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Head {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public int id;
    public String sha;
}
