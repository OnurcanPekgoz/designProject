package com.hacettepe.designProject.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    public String login;
    @Id
    public int id;
    public String node_id;
    public String url;
    public String repos_url;
    public String type;
    public String company;
    public String location;
    public int public_repos;
    public int public_gists;
    public int followers;
    public int following;
    public Date date=null;
}
