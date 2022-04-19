package com.revature.revspace.services.groups;

import java.util.List;

import com.revature.revspace.models.groups.GroupPost;

public interface GroupPostService 
{
	public GroupPost addGroupPost(GroupPost post);
	public boolean deleteGroupPost(int id);
	public List<List<GroupPost>> getGroupPosts(int id);
	
	//public GroupPost unboxMsg(GroupPostMsg msg);
	
}
