package org.sopt.server.mapper;

import org.apache.ibatis.annotations.*;
import org.sopt.server.dto.Product;

import java.util.List;

/**
 * Created by ds on 2018-11-24.
 */

@Mapper
public interface ProductMapper {

    @Select("SELECT * FROM people")
    List<Product> findAll();
    @Select("SELECT * FROM people WHERE id = #{id}")
    Product findById(@Param("id")final int id);
    //사람 데이터 저장
    @Insert("INSERT INTO people(name, age) VALUES(#{people.name}, #{people.age})")
    @Options(useGeneratedKeys = true, keyProperty = "b_id")
    int save(final Product people);
    //사람 데이터 수정
    @Update("UPDATE people SET age = #{age} WHERE name = #{name}")
    void update(final Product people);
    //사람 데이터 삭제
    @Delete("DELETE FROM people WHERE id = #{id}")
    void deleteById(@Param("id") final int id);
}
