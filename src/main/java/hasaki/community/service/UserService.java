package hasaki.community.service;

import hasaki.community.mapper.UserMapper;
import hasaki.community.model.User;
import hasaki.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Create by hanzp on 2020-02-29
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andThirdpartyEqualTo("Github")
                .andAccountIdEqualTo(user.getAccountId());
        List<User> dbUsers = userMapper.selectByExample(userExample);
        if (dbUsers.size() == 0) {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModify(System.currentTimeMillis());
            userMapper.insert(user);
        } else {
            User updateUser = new User();
            updateUser.setToken(user.getToken());
            updateUser.setGmtModify(System.currentTimeMillis());
            UserExample dbUserExample = new UserExample();
            dbUserExample.createCriteria()
                    .andThirdpartyEqualTo("Github")
                    .andAccountIdEqualTo(user.getAccountId());
            userMapper.updateByExampleSelective(updateUser, dbUserExample);
        }
        return findByToken(user.getToken());
    }

    public User findByToken(String token){
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andTokenEqualTo(token);
        List<User> dbUsers = userMapper.selectByExample(userExample);
        if (dbUsers.size() == 0) {
            return null;
        }
        return dbUsers.get(0);
    }
}
