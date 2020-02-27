package hasaki.community.mapper;

import hasaki.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Create by hanzp on 2020-02-27
 */
@Mapper
public interface UserMapper {
    @Insert("insert into user(name, account_id, token, gmt_create, gmt_modify, thirdparty) values (#{name},#{accountId},#{token},#{gmtCreatetime},#{gmtModifytime},#{thirdParty})")
    void insert(User user);
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);
}
