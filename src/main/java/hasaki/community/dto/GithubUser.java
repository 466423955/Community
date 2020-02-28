package hasaki.community.dto;

import lombok.Data;

/**
 * Create by hanzp on 2020-02-26
 */
@Data
public class GithubUser {
    private String name;
    private long id;
    private String bio;
    private String avatarUrl;

    /*@Override
    public String toString() {
        return "GithubUser{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", bio='" + bio + '\'' +
                '}';
    }*/
}
