package com.atguigu.mapper;

import com.atguigu.bean.TMemberProcinst;
import com.atguigu.bean.TMemberProcinstExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TMemberProcinstMapper {
    long countByExample(TMemberProcinstExample example);

    int deleteByExample(TMemberProcinstExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TMemberProcinst record);

    int insertSelective(TMemberProcinst record);

    List<TMemberProcinst> selectByExample(TMemberProcinstExample example);

    TMemberProcinst selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TMemberProcinst record, @Param("example") TMemberProcinstExample example);

    int updateByExample(@Param("record") TMemberProcinst record, @Param("example") TMemberProcinstExample example);

    int updateByPrimaryKeySelective(TMemberProcinst record);

    int updateByPrimaryKey(TMemberProcinst record);
}