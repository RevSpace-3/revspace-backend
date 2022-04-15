package com.revature.revspace.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.revature.revspace.models.GroupPost;
import com.revature.revspace.repositories.GroupPostRepository;


@Service
public class GroupPostServiceImpl implements GroupPostService 
{
	@Autowired
	private GroupPostRepository repo;
	
	@Override
	public String addGroupPost(GroupPost post) // If this returns null, the insertion failed.
	{	
		String result = null;
		if(!repo.existsById( post.getPostId() ))
			result = "GroupPost of Id: " + repo.save(post).getPostId() + " was created.";
		
		return result;
	}

	@Override
	public boolean deleteGroupPost(int id) 
	{
		boolean result = false;
		
		if(repo.existsById(id))
			repo.deleteById(id);
			
		return result;
	}

	@Override
	public List<GroupPost> getGroupPosts(int id) 
	{
		List<GroupPost> result = new ArrayList<GroupPost>();
		Set<GroupPost> tempList = new HashSet<GroupPost>();
		List<GroupPost> repoList = (List<GroupPost>) repo.findAll();
		
		for(GroupPost p : repoList)
		{
			GroupPost headPointer = null;
			if(p.getParent() != null)
			{
				headPointer = findHead(p);
				
				if(headPointer.getPostId() == id) // if our heads match.
				{
					GroupPost tempRef = findTail(p);
					tempList.add(tempRef);
				}
			}	
		}
		if(!tempList.isEmpty())
			result.addAll(tempList);
		
		return result;
	}
	
	private GroupPost findHead(GroupPost post)
	{
		if(post.getParent() != null)
			return findHead(post.getParent());
		else
			return post;		
	}
	
	private GroupPost findTail(GroupPost post)
	{
		if(post.getChild() != null)
			return findTail(post.getChild());
		else
			return post;
	}


}
