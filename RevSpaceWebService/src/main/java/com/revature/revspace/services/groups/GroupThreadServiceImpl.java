package com.revature.revspace.services.groups;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.revspace.models.User;
import com.revature.revspace.models.groups.GroupInfo;
import com.revature.revspace.models.groups.GroupThread;
import com.revature.revspace.repositories.group.GroupInfoRepository;
import com.revature.revspace.repositories.group.GroupThreadRepository;

@Service
public class GroupThreadServiceImpl implements GroupThreadService 
{
	@Autowired
	GroupThreadRepository repo;
	
	@Autowired
	GroupInfoRepository infoRepo;
	
	
	@Override
	public GroupThread addGroupThread(GroupThread obj) 
	{
		GroupThread res = null;
		if(obj != null)
		{
			infoRepo.save(obj.getGroupInfo());
			res = repo.save(obj);
		}
		return res;
	}

	@Override
	public GroupThread updateGroupThread(GroupThread obj) 
	{
		GroupThread res = null;

		try
		{
			infoRepo.save(obj.getGroupInfo());
			res = repo.save(obj);
		}catch(Exception e)
		{
			
		}

		return res;
	}
	@Override
	public boolean deleteGroupThread(int id) 
	{
		boolean res = false;

		try
		{
			if(repo.existsById(id))
			{
				repo.deleteById(id);
				res = true;
			}
		}catch(Exception e)
		{
			
		}

		return res;
	}

	@Override
	public List<GroupThread> getAllGroupThreads() 
	{

		List<GroupThread> gList = null; // Result list
		
		try
		{
			gList = (List<GroupThread>)repo.findAll();
		}catch(Exception e)
		{
			// Add custom exceptions and actually do something with them.
		}
		
		return gList;
	}

	@Override
	public List<GroupThread> getGroupThreadsByOwner(User obj) // change me when you do the custom search. Placeholder...
	{
		List<GroupThread> gList = null; // Result list
		List<GroupThread> tList = null; // temp list, iterating through it to find owner by email 
		
		try
		{
			tList = (List<GroupThread>)repo.findAll();
			
			if(tList != null || tList.isEmpty())
				gList = new ArrayList<GroupThread>();
			
			for(GroupThread g : tList)
			{
				if(g.getGroupInfo().getOwner().getEmail().equals(obj.getEmail())) // thread.info.owner.email == owner.email
				{
					gList.add(g);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage()); // change to logging.
		}
				
		return gList;
	}

	@Override
	public List<GroupInfo> getGroupThreadsByUser(int id) // Slow needs refactoring.
	{
		List<GroupThread> gList = null;
		List<GroupInfo> rList = null;

		try
		{
			gList = (List<GroupThread>)repo.findAll();
			
			rList = new ArrayList<GroupInfo>();
			for(GroupThread g : gList)
			{
				if(g.getMember().getUserId() == id)
					rList.add(g.getGroupInfo());
			}
		}catch(Exception e)
		{
			
		}
		
		return rList;
	}

	@Override
	public List<GroupThread> getAllUniqueThreads() 
	{
		List<GroupThread> gList = null;
		List<GroupThread> rList = null;
	
		try
		{
			gList = (List<GroupThread>)repo.findAll();
			
			rList = new ArrayList<GroupThread>();
			
			for(GroupThread g : gList)
			{		
				if(g.getMember().equals(g.getGroupInfo().getOwner()))
					rList.add(g);
			}
		}catch(Exception e)
		{
			
		}
		
		return rList;
	}

	@Override
	public List<GroupInfo> getOtherGroups(int id) // really slow lol...
	{
		Set<GroupInfo> rList = null;
	
		try
		{
			List<GroupThread> gList = (List<GroupThread>)repo.findAll();
			List<GroupInfo> temp = (List<GroupInfo>)infoRepo.findAll();
			rList = new HashSet<GroupInfo>();
			
			// Run through once to get baseline
			for(GroupInfo g : temp)
			{
				if(g.getOwner().getUserId() != id)
					rList.add(g);
			}
			// Run through again and remove groups that user belongs to
			for(GroupThread g : gList)
			{
				if(g.getMember().getUserId() == id)
					rList.remove(g.getGroupInfo());
			}
			
		}catch(Exception e)
		{
			
		}
		
		if(rList != null && !rList.isEmpty())
			return new ArrayList<GroupInfo>(rList);
		else
			return null;
	}

	@Override
	public List<GroupThread> getGroupThreadsByInfo(int id)
	{
		List<GroupThread> gList = null;
		List<GroupThread> rList = null;
	
		try
		{
			gList = (List<GroupThread>)repo.findAll();
			
			rList = new ArrayList<GroupThread>();
			
			for(GroupThread g : gList)
			{		
				if(g.getGroupInfo().getInfoId() == id)
					rList.add(g);
			}
		}catch(Exception e)
		{
			
		}
		
		return rList;
	}
	
	@Override
	public boolean deleteAllByInfoId(int id)
	{
		boolean result = false;
		try
		{
			List<GroupThread> repoList = (List<GroupThread>) repo.findAll();
			
			for(GroupThread t : repoList)
			{
				if(t.getGroupInfo().getInfoId() == id)
				{
					repo.delete(t);
				}
			}
			
		}catch(Exception e)
		{
			
		}
		return false;
	}
}
