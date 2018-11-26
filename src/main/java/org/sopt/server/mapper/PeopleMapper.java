package org.sopt.server.mapper;

import org.apache.ibatis.annotations.*;
import org.sopt.server.dto.People;

/**
 * Created by ds on 2018-11-24.
 */

@Mapper
public interface PeopleMapper {
    //사람 고유 번호로 조회
    @Select("SELECT * FROM people WHERE id = #{id}")
    People findById(@Param("id")final int id);
    //사람 데이터 저장
    @Insert("INSERT INTO people(name, age) VALUES(#{people.name}, #{people.age})")
    @Options(useGeneratedKeys = true, keyProperty = "b_id")
    int save(final People people);
    //사람 데이터 수정
    @Update("UPDATE people SET age = #{age} WHERE name = #{name}")
    void update(@Param("age") final int age, @Param("name") final String name);
    //사람 데이터 삭제
    @Delete("DELETE FROM people WHERE id = #{id}")
    void deleteById(@Param("id") final int id);
}
