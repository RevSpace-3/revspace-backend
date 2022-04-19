package com.revature.revspace.repositories.group;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.revature.revspace.models.groups.GroupLike;


public interface GroupLikeRepository extends CrudRepository<GroupLike, Integer>
{
	@Query("SELECT l FROM GroupLike l WHERE l.post.postId = ?1")
	public List<GroupLike> findByPost(int id);
}
