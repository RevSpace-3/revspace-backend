package com.revature.revspace.services;

import java.util.List;

import com.revature.revspace.models.GroupInfo;
import com.revature.revspace.models.User;

public interface GroupInfoService
{
	public List<GroupInfo> getGroupsByOwner(User obj);
	public List<GroupInfo> getGroupsByOther(int id);
	public List<GroupInfo> getGroupsByInterests(String interest);
	public boolean deleteGroupById(int id);
}
