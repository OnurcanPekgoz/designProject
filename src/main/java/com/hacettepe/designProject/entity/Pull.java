package com.hacettepe.designProject.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pulls")
public class Pull {
    public String url;
    @Id
    public int id;
    public String node_id;
    public int number;
    public String state;
    public boolean locked;
    @Column(columnDefinition = "TEXT")
    public String title;
    @Column(columnDefinition = "TEXT")
    public String body;
    @OneToOne
    public User user;//owner
    public Date created_at;
    public Date updated_at;
    public Date closed_at;
    public Date merged_at;
    @ManyToMany
    public List<User> assignees;
    @ManyToMany
    public List<User> requested_reviewers;
    @OneToOne(cascade = CascadeType.ALL)
    public Head head=null;
    public String sha;
    public String repoName;
}
