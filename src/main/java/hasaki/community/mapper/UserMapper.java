package hasaki.community.mapper;

import hasaki.community.model.User;
import org.apache.ibatis.annotations.*;

/**
 * Create by hanzp on 2020-02-27
 */
@Mapper
public interface UserMapper {
    @Insert("insert into user(name, account_id, token, gmt_create, gmt_modify, thirdparty, avatarurl) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModify},#{thirdParty},#{avatarUrl})")
    void insert(User user);
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);
    @Select("select * from user where id = #{accountId}")
    User findById(@Param("accountId") Integer accountId);
    @Select("select * from user where thirdparty = #{thirdParty} and account_Id = #{accountId}")
    User findByAccountId(@Param("thirdParty")String thirdParty, @Param("accountId") String accountId);
    @Update("update user set name=#{name},account_id=#{accountId},token=#{token},gmt_create=#{gmtCreate},gmt_modify=#{gmtModify},thirdparty=#{thirdParty},avatarurl=#{avatarUrl} where id=#{id}")
    void update(User user);
}
