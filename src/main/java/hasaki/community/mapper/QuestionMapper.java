package hasaki.community.mapper;

import hasaki.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Create by hanzp on 2020-02-27
 */
@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title, description, gmt_create, gmt_modify, creator, tag) values (#{title}, #{description}, #{gmtCreate}, #{gmtModify}, #{creator}, #{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset}, #{size}")
    List<Question> list(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select * from question where creator = #{userid} limit #{offset}, #{size}")
    List<Question> listByUserId(@Param("userid") Integer userId, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select count(1) from question where creator = #{userid}")
    Integer countByUserId(@Param("userid") Integer userId);

}
