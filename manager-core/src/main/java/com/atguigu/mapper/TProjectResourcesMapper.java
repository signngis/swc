package com.atguigu.mapper;

import com.atguigu.bean.TProjectResources;
import com.atguigu.bean.TProjectResourcesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TProjectResourcesMapper {
    long countByExample(TProjectResourcesExample example);

    int deleteByExample(TProjectResourcesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TProjectResources record);

    int insertSelective(TProjectResources record);

    List<TProjectResources> selectByExampleWithBLOBs(TProjectResourcesExample example);

    List<TProjectResources> selectByExample(TProjectResourcesExample example);

    TProjectResources selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TProjectResources record, @Param("example") TProjectResourcesExample example);

    int updateByExampleWithBLOBs(@Param("record") TProjectResources record, @Param("example") TProjectResourcesExample example);

    int updateByExample(@Param("record") TProjectResources record, @Param("example") TProjectResourcesExample example);

    int updateByPrimaryKeySelective(TProjectResources record);

    int updateByPrimaryKeyWithBLOBs(TProjectResources record);

    int updateByPrimaryKey(TProjectResources record);
}