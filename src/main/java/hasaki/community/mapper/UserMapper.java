package hasaki.community.mapper;

import hasaki.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * Create by hanzp on 2020-02-27
 */
@Mapper
public interface UserMapper {
    @Insert("insert into user(name, account_id, token, gmt_create, gmt_modify, thirdparty) values (#{name},#{accountId},#{token},#{gmtCreatetime},#{gmtModifytime},#{thirdParty})")
    void insert(User user);
}
