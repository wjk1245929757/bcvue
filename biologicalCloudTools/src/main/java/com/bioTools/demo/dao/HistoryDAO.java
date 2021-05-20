package com.bioTools.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

import com.bioTools.demo.entities.History;

/*	
	private String historyId;
	private String dataPath;
	private String dataName;
	private String picPath;
	private String picName;
	private String email;
	private String model;
	private String function;
 */

@Mapper
public interface HistoryDAO {
	
	
	@Insert("insert into history(historyid,datapath,dataname,picpath,picname,email,model,toolfunction) values(#{historyid},#{datapath},#{dataname},#{picpath},#{picname},#{email},#{model},#{toolfunction})")
	void addHistory(History history);
	
	@Delete("delete from history where dataPath = #{dataPath}")
	void deleteByDataPath(String dataPath);
	
	@Delete("delete from history where historyId = #{historyId}")
	void deleteByHistoryId(String historyId);
	
	@Delete("delete from history where email = #{email}")
	void deleteByEmail(String email);
	
	@Delete("delete from history")
	void clearHistroy();
	
	
	@Update("update history set datapath = #{datapath}, dataname = #{dataname},picpath= #{picpath},picname= #{picname},email= #{email},model= #{model},toolfunction= #{toolfunction} where historyid = #{historyid}")  
    void updateHistory(History history);  
	
	@Select({"select historyid,datapath,dataname,picpath,picname,email,model,toolfunction from history where datapath = #{datapath}"})
	@Results(id="historyMap", value={
			@Result(column="historyid", property="historyid",jdbcType=JdbcType.VARCHAR, id=true),
			@Result(column="datapath", property="datapath",jdbcType=JdbcType.VARCHAR, id= true),
		    @Result(column="dataname", property="dataname",jdbcType=JdbcType.VARCHAR),
		    @Result(column="picpath", property="picpath",jdbcType=JdbcType.VARCHAR),
		    @Result(column="picname", property="picname",jdbcType=JdbcType.VARCHAR),
		    @Result(column="email", property="email",jdbcType=JdbcType.VARCHAR),
		    @Result(column="model", property="model",jdbcType=JdbcType.VARCHAR),
		    @Result(column="toolfunction", property="toolfunction",jdbcType=JdbcType.VARCHAR)
	})
	History selectHistoryByDataPath(String dataPath);
	
	@Select({"select * from history where historyid = #{historyid}"})
	@ResultMap("historyMap")
	History selectHistoryByHistoryId(String historyid);
	
	@Select({"select * from history"})
	@ResultMap("historyMap")
	List<History> selectAllHistory(); 
	
	@Select({"select * from history where email = #{email}"})
	@ResultMap("historyMap")
	List<History> selectUserHistory(String email);
	

}
