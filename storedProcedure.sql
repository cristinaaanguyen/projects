 DROP PROCEDURE IF EXISTS add_movie;
 
 delimiter //

CREATE PROCEDURE add_movie (IN MovieTitle varchar(100), IN MovieYear INT, IN MovieDirector varchar(100), IN StarName varchar(100), IN GenreName varchar(32), OUT Message varchar(200))
	BEGIN
		Declare NewGenreID varchar(10) default '';
        declare NewStarID varchar(10) default '';
        declare NewMovieID varchar(10) default '';
        declare SuccessMessage varchar(200) default '';
        
		IF not exists(select * from genres where name = GenreName)
			then insert into genres (name) values (GenreName);
			select id from genres where name = GenreName into NewGenreID;
            set SuccessMessage = concat(SuccessMessage, 'New Genre Added;');
		else
			select id from genres where name = GenreName into @GenreID;
            set NewGenreID = @GenreID;
            set SuccessMessage = concat(SuccessMessage, 'Genre Not Added (Duplicate);');
        end if;
        
        If not exists(select * from stars where name = StarName)
			then SELECT id into @maxId from max_star_id;
			select substring(@maxId, 3) into @NumId;
			select substring(@maxId, 1, 2)into @charID;
			select convert(@NumId, signed) + 1 into @newId;
			update max_star_id set id = concat(@charID, @newId) where MaxId = 1;
			insert into stars (id, name) values (concat(@charID, @newId), StarName);
			set NewStarID = concat(@charID, @newId);
            set SuccessMessage = concat(SuccessMessage, 'New Star Added;');
        else 
			select id from stars where name = StarName into @StarID;
			set NewStarID = @StarID;
            set SuccessMessage = concat(SuccessMessage, 'Star Not Added (Duplicate);');
        end if;
        
        IF not exists(select * from movies where title = MovieTitle and year = MovieYear)
			then SELECT MaxMovieId into @maxMovieId from max_star_id;
			select substring(@maxMovieId, 3) into @NumMovieId;
			select substring(@maxMovieId, 1, 2)into @charMovieID;
			select convert(@NumMovieId, signed) + 1 into @newMovieId;
			update max_star_id set MaxMovieId = concat(@charMovieID, @newMovieId) where MaxId = 1;
			insert into movies (id, title, year, director) values (concat(@charMovieID, @newMovieId), MovieTitle, MovieYear, MovieDirector);
			set NewMovieID = concat(@charMovieID, @newMovieId);
            set SuccessMessage = concat(SuccessMessage, 'New Movie Added;');
		else
			select id from movies where title = MovieTitle and year = MovieYear into @MovieID;
            set NewMovieID = @MovieID;
            set SuccessMessage = concat(SuccessMessage, 'Movie Not Added (Duplicate);');
        end if;
        
        if not exists(select * from genres_in_movies where genreId = NewGenreID and movieId = NewMovieID)
			then insert into genres_in_movies values (NewGenreID, NewMovieID);
			set SuccessMessage = concat(SuccessMessage, 'New Genre in Movie Added;');
		else
			set SuccessMessage = concat(SuccessMessage, 'Genre in Movie already added;');
        end if;
        
        if not exists(select * from stars_in_movies where starId = NewStarID and movieId = NewMovieID)
			then insert into stars_in_movies values (NewStarID, NewMovieID);
			set SuccessMessage = concat(SuccessMessage, 'New Star in Movie Added;');
		else
			set SuccessMessage = concat(SuccessMessage, 'Star in Movie already added;');
        end if;
        
        set Message = SuccessMessage;
	END//

delimiter ;