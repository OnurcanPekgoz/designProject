package com.hacettepe.designProject.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "pulls")
public class Pull {
    public String url;
    @Id
    public int id;
    public String node_id;
    public String html_url;
    public String diff_url;
    public String patch_url;
    public String issue_url;
    public int number;
    public String state;
    public boolean locked;
    @Column(columnDefinition = "TEXT")
    public String title;
    @OneToOne
    public User user;
    @Column(columnDefinition = "TEXT")
    public String body;
    public Date created_at;
    public Date updated_at;
    public Date closed_at;
    public Date merged_at;
}
