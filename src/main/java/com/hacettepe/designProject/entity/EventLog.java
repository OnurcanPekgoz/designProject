package com.hacettepe.designProject.entity;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "eventlog")
public class EventLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public int eventId;
    public String caseId;
    public String activity;
    public Date timestamp;
    public String user;
    public String title;
    public int commentCount;
    
    public EventLog(String caseId, String activity, Date timestamp, String user, String title, int commentCount) {
        this.caseId = caseId;
        this.activity = activity;
        this.timestamp = timestamp;
        this.user = user;
        this.title = title;
        this.commentCount = commentCount;
    }
}
