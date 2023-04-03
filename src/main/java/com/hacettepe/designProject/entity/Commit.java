package com.hacettepe.designProject.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "commits")
public class Commit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public int id;
    @OneToOne
    public User author;
    @OneToOne
    public User committer;
    public String sha;
    @Column(columnDefinition = "TEXT")
    public String message;
    @Column(columnDefinition = "TEXT")
    public String url;
    public int comment_count;
    public Date date;
    public int pullNum;
    public String owner;
    public String repoName;
}
