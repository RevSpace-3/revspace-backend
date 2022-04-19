package com.revature.revspace.services.groups;

import java.util.List;

import com.revature.revspace.models.groups.GroupLike;

public interface GroupLikeService 
{
	public GroupLike addGroupLike(GroupLike like);
	public String deleteGroupLike(int id);
	public List<GroupLike> getGroupLikesByPostId(int id);
	public List<GroupLike> getGroupLikesByGroupHead(int id);
}
