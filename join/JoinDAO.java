package com.movie.web.join;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JoinDAO {

	int join(JoinDTO joinDTO);

	int overlapCheck(String value, String valueType);

	int overlapCheck(Map<String, String> map);

}
