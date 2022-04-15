package com.revature.revspace.services;

import java.util.List;

import com.revature.revspace.models.GroupPost;

public interface GroupPostService 
{
	public String addGroupPost(GroupPost post);
	public boolean deleteGroupPost(int id);
	public List<GroupPost> getGroupPosts(int id);
	
}
