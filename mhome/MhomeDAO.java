package com.movie.web.mhome;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MhomeDAO {

	void insertMovie(MovieDTO movieDTO);

	void updateMovie(MovieDTO movieDTO);

	String findMovieByMovieCd(String movieCd);

	String findMovieByMovieCd1(String movieCd);

	void insertAudience(MovieDTO movieDTO);

	void updateAudience(MovieDTO movieDTO);

	void infoupdate(MovieDTO movieDTO);

	void infoupdate1(MovieDTO movieDTO);

	

	Collection<? extends String> MovieCd();

	List<String> moviename();

	void kmdbupdate(MovieDTO movieDTO);

	void kmdbupdate1(MovieDTO movieDTO);

	List<Movie> list();

	
	List<String> mvruntime();

	String poster(String movieNm);

	List<Movie> list1();

	List<Movie> list2();

	List<Movie> list3();

	List<Movie> list4();

	List<Movie> list5();

	String dbplot(String movieNm);

	String dbvod(String movieNm);







	

}
