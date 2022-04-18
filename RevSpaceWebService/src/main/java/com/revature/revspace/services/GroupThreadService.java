package com.revature.revspace.services;

import java.util.List;

import com.revature.revspace.models.GroupInfo;
import com.revature.revspace.models.GroupThread;
import com.revature.revspace.models.User;

public interface GroupThreadService 
{
	public GroupThread addGroupThread(GroupThread obj);
	public boolean deleteGroupThread(int id);
	public GroupThread updateGroupThread(GroupThread obj);
	
	public List<GroupThread> getAllGroupThreads();
	public List<GroupThread> getAllUniqueThreads();
	public List<GroupThread> getGroupThreadsByOwner(User obj);
	public List<GroupThread> getGroupThreadsByInfo(int id);
	public List<GroupInfo> getGroupThreadsByUser(int id);
	public List<GroupInfo> getOtherGroups(int id);
	public boolean deleteAllByInfoId(int id);
}
