package com.bioTools.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;

import com.bioTools.demo.entities.User;
	

@Mapper
public interface PersonInfoDAO {
	
	@Insert("insert into user(email,name,password,telephone,role) values(#{email},#{name},#{password},#{telephone},#{role})")
	void create(User user);
	
	@Delete("delete from user where email = #{email}")
	void delete();
	
	@Update("update user set name = #{name},password= #{password},telephone= #{telephone} where email = #{email}  ")  
    void updateUser(User user);  
	
	@Select({"select email,name,password,telephone,role from user where email = #{email}"})
	@Results(id="userMap", value={
			@Result(column="email", property="email",jdbcType=JdbcType.VARCHAR, id=true),
		    @Result(column="name", property="name",jdbcType=JdbcType.VARCHAR),
		    @Result(column="password", property="password",jdbcType=JdbcType.VARCHAR),
		    @Result(column="telephone", property="telephone",jdbcType=JdbcType.VARCHAR),
		    @Result(column="role", property="role",jdbcType=JdbcType.VARCHAR),
	})
	User selectUserByEmail(String email);
	
	@Select({"select * from user"})
	@ResultMap("userMap")
	List<User> selectUsers();   

}
