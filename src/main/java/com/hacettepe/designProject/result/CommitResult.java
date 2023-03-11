package com.hacettepe.designProject.result;

import java.util.Date;

import com.hacettepe.designProject.entity.Commit;
import com.hacettepe.designProject.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommitResult {
    public String sha;
    public String node_id;
    public Commit commit;
    public String url;
    public String html_url;
    public String comments_url;
    public User author;
    public User committer;
}
