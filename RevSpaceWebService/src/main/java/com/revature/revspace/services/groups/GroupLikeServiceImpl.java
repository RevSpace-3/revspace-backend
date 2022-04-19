package com.revature.revspace.services.groups;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.revspace.models.groups.GroupLike;
import com.revature.revspace.models.groups.GroupPost;
import com.revature.revspace.repositories.group.GroupLikeRepository;

@Service
public class GroupLikeServiceImpl implements GroupLikeService
{
	@Autowired
	private GroupLikeRepository repo;

	@Override
	public GroupLike addGroupLike(GroupLike like)
	{
		GroupLike res = null;
		

		res = repo.save(like);
		
		return res;
	}

	@Override
	public String deleteGroupLike(int id)
	{
		boolean result = false;
		
		if(repo.existsById(id))
			repo.deleteById(id);
			
		return "The deletion of GroupLike id: " + id + (result ? " was successful" : " has failed");
	}

	@Override
	public List<GroupLike> getGroupLikesByPostId(int id)
	{
		List<GroupLike> res = null;
		List<GroupLike> repoList = (List<GroupLike>) repo.findAll();
		
		for(GroupLike g : repoList)
		{
			if(g.getPost().getPostId() == id)
			{
				if(res == null)
					res = new ArrayList<GroupLike>();
				
				res.add(g);
			}
		}
		
		return res;
	}
	
	public List<GroupLike> getGroupLikesByGroupHead(int id)
	{
		List<GroupLike> res = null;
		List<GroupLike> repoList = (List<GroupLike>) repo.findAll();
		
		for(GroupLike g : repoList)
		{
			GroupPost head = findHead(g.getPost());
			
			if(head.getPostId() == id)
			{
				if(res == null)
					res = new ArrayList<GroupLike>();
				
				res.add(g);
			}
		}
		
		return res;
	}
	public GroupPost findHead(GroupPost post)
	{
		if(post.getParent() != null)
			return findHead(post.getParent());
		else
			return post;
	}
	
}
