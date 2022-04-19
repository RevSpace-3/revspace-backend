package com.revature.revspace.repositories.group;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.revature.revspace.models.groups.GroupThread;

public interface GroupThreadRepository extends CrudRepository<GroupThread, Integer>
{
//	@Query("select DISTINCT from groupthread g join g.user_id u where u.email = :email")
//	List<GroupThread> findByEmail(@Param("email")String email);
//	@Query("from groupthread t join groupinfo i on t.info_id = i.info_id where t.user_id = i.user_id ")
//	public List<GroupThread> findAll();
}
