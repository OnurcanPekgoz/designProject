package com.hacettepe.designProject.entity;

import java.util.Date;
import jakarta.persistence.*;

import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "repos")
public class UserRepo {
    @Id
    public int id;
    public String name;
    public String node_id;
    @OneToOne
    public User owner;
    @Column(columnDefinition = "TEXT")
    public String description;
    public String url;
    public Date created_at;
    public Date updated_at;
    public Date pushed_at;
    public int size;
    public int stargazers_count;
    public int watchers_count;
    public String language;
    public int forks_count;
    public boolean archived;
    public boolean disabled;
    public int open_issues_count;
    public boolean allow_forking;
    public String visibility;
    public int open_issues;
}
