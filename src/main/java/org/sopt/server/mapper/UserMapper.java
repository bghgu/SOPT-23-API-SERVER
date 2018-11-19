package org.sopt.server.mapper;

import org.apache.ibatis.annotations.*;
import org.sopt.server.dto.User;
import org.sopt.server.model.SignUpReq;
import org.springframework.stereotype.Service;

/**
 * Created by ds on 2018-10-20.
 */

@Mapper
public interface UserMapper {

    /**
     * 로그인
     *
     * @param email    아이디 겸 이메일
     * @param password 비밀번호
     * @return User
     */
    @Select("SELECT * FROM user WHERE u_email = #{u_email} AND u_password = #{u_password}")
    User findByEmailAndPassword(@Param("u_email") final String email, @Param("u_password") final String password);

    /**
     * 이메일로 회원 조회
     *
     * @param email 이메일
     * @return User
     */
    @Select("SELECT * FROM user WHERE u_email = #{u_email}")
    User findByEmail(@Param("u_email") final String email);

    /**
     * 마이페이지 조회
     *
     * @param userIdx 사용자 고유 번호
     * @return User
     */
    @Select("SELECT * FROM user WHERE u_id = #{u_id}")
    User findByUserIdx(@Param("u_id") final int userIdx);

    /**
     * 회원 가입
     *
     * @param signUpReq 회원 가입 정보
     */
    @Insert("INSERT INTO " +
            "user(u_name, u_password, u_part, u_email, u_profile) " +
            "values(#{name}, #{password}, #{part}, #{email}, #{profileUrl})")
    @Options(useGeneratedKeys = true, keyProperty = "u_id")
    void save(final SignUpReq signUpReq);


    /**
     * 회원 정보 수정
     *
     * @param user 회원 정보
     */
    @Update("UPDATE user SET u_name = #{user.u_name}, u_part = #{user.u_part}, " +
            "u_profile = #{user.u_profile}, u_password = #{user.u_password} WHERE u_id = #{user.u_id}")
    void update(@Param("user") final User user);

    /**
     * 회원 탈퇴
     *
     * @param userIdx
     */
    @Delete("DELETE FROM user WHERE u_id = #{u_id}")
    void deleteByUserIdx(@Param("u_id") final int userIdx);
}