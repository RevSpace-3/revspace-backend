package com.revature.revspace.services;

import java.util.List;

import com.revature.revspace.models.Like;
import com.revature.revspace.repositories.LikeRepo;

public interface LikeService
{
	public Like addLike(Like obj);
	public Like updateLike(Like obj);
	public boolean deleteLike(int id);
	
	public List<Like> getAllLikes();
	public Like getLikeById(int id);
	
	public List<Like> getPostLikes(int id);
	
}
