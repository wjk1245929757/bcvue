package com.bioTools.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.bioTools.demo.entities.InformationSheet;

@Mapper
public interface InformationSheetDAO {
	
	@Insert("insert into informationsheet(orderno,name,workunit,telephone,shunfengno,wechat,state,time,responsibleman,isfree) values(#{orderno},#{name},#{workunit},#{telephone},#{shunfengno},#{wechat},#{state},#{time},#{responsibleman},#{isfree})")
	void addInformationSheet(InformationSheet informationSheet);
	
	@Delete("delete from informationsheet where orderno = #{orderno}")
	void delete();
	
//	@Update("update user set filePath = #{filePath},imgName= #{imgName},imgPath= #{imgPath},mainAddr= #{mainAddr},time= #{time} where fileName = #{fileName}  ")  
//    void updateInformationSheet(InformationSheet data);  
	
	@Select({"select * from informationsheet where orderno = #{orderno}"})
	InformationSheet selectInformationSheetByOrderNO(String orderNO);
	
	@Select({"select * from informationsheet"})
	List<InformationSheet> selectAllInformationsheet(); 


}
