package com.revature.revspace.services.groups;

import java.util.List;

import com.revature.revspace.models.User;
import com.revature.revspace.models.groups.GroupInfo;

public interface GroupInfoService
{
	public List<GroupInfo> getGroupsByOwner(User obj);
	public List<GroupInfo> getGroupsByOther(int id);
	public List<GroupInfo> getGroupsByInterests(String interest);
	public boolean deleteGroupById(int id);
}
