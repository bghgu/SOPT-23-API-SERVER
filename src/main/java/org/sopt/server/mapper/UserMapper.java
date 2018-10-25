package org.sopt.server.mapper;

import org.apache.ibatis.annotations.*;
import org.sopt.server.dto.User;
import org.sopt.server.model.SignUpReq;

import java.util.Optional;

/**
 * Created by ds on 2018-10-20.
 */

@Mapper
public interface UserMapper {

    /**
     * Optional 테스트
     * @param userIdx
     * @return
     */
    @Select("SELECT * FROM user WHERE u_id = #{u_id}")
    Optional<User> findByUserIdx1(@Param("u_id") final int userIdx);

    /**
     * 로그인
     * @param email
     * @param password
     * @return
     */
    @Select("SELECT * FROM user WHERE u_email = #{u_email} AND u_password = #{u_password}")
    User findByEmailAndPassword(@Param("u_email") final String email, @Param("u_password") final String password);

    /**
     * 마이페이지 조회
     * @param userIdx
     * @return
     */
    @Select("SELECT * FROM user WHERE u_id = #{u_id}")
    User findByUserIdx(@Param("u_id") final int userIdx);

    /**
     * 회원 가입
     * @param signUpReq
     */
    @Insert("INSERT INTO " +
            "user(u_name, u_password, u_part, u_email, u_profile) " +
            "values(#{name}, #{password}, #{part}, #{email}, #{profileUrl})")
    @Options(useGeneratedKeys = true, keyProperty = "u_id")
    void save(final SignUpReq signUpReq);

    /**
     * 회원 정보 수정
     * @param userIdx
     * @param signUpReq
     * @return
     */
    @Update("UPDATE user SET WHERE u_id = #{u_id}")
    void update(@Param("u_id") final int userIdx, final SignUpReq signUpReq);

    /**
     * 회원 탈퇴
     * @param userIdx
     */
    @Delete("DELETE FROM user WHERE u_id = #{u_id}")
    void deleteByUserIdx(@Param("u_id") final int userIdx);
}