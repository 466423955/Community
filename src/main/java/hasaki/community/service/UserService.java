package hasaki.community.service;

import hasaki.community.mapper.UserMapper;
import hasaki.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Create by hanzp on 2020-02-29
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User createOrUpdate(User user) {
        User dbUser = userMapper.findByAccountId("Github", user.getAccountId());
        if(dbUser == null){
            user.setToken(UUID.randomUUID().toString());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModify(user.getGmtCreate());
            userMapper.insert(user);
            return user;
        }else{
            dbUser.setToken(UUID.randomUUID().toString());
            dbUser.setGmtModify(System.currentTimeMillis());
            userMapper.update(dbUser);
            return dbUser;
        }
    }
}
