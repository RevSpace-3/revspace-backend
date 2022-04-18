package com.revature.revspace.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.revature.revspace.models.GroupPost;
import com.revature.revspace.models.httpmsgmodel.GroupPostMsg;
import com.revature.revspace.repositories.GroupPostRepository;


@Service
public class GroupPostServiceImpl implements GroupPostService 
{
	@Autowired
	private GroupPostRepository repo;
	
	@Override
	public GroupPost addGroupPost(GroupPost post) // If this returns null, the insertion failed.
	{	
		GroupPost result = null;
		if(!repo.existsById( post.getPostId() ))
			result = repo.save(post);
		
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
	public List<List<GroupPost>> getGroupPosts(int id) 
	{
		List<List<GroupPost>> result = new ArrayList<List<GroupPost>>(); 
		Set<GroupPost> postList = new HashSet<GroupPost>();
		Set<GroupPost> comList = new HashSet<GroupPost>();
		List<GroupPost> repoList = (List<GroupPost>) repo.findAll();
		
		for(GroupPost p : repoList)
		{
			GroupPost headPointer = null;
			if(p.getParent() != null)
			{
				headPointer = findHead(p);
				
				if(headPointer.getPostId() == id ) // if our heads match.
				{
					if(p.isComment())
						comList.add(p);
					else
						postList.add(p);
				}
			}	
		}
		if(!postList.isEmpty())
		{
			result.add(new ArrayList<>(postList));
			result.add(new ArrayList<>(comList));
		}
		
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
	
	public GroupPost unboxMsg(GroupPostMsg msg)
	{
		Optional<GroupPost> repoRes = repo.findById(msg.getPostId());
		GroupPost res = null;
		
		if(repoRes.isPresent())
			res = repoRes.get();
		else
		{
			Optional<GroupPost> parent = repo.findById(msg.getParentId());
			Optional<GroupPost> child = repo.findById(msg.getChildId());
			
			res = new GroupPost(msg.getBody(), msg.getDatePosted(), msg.getNumOfLikes(), msg.isComment(),
								msg.getOwner(),
								parent.isPresent() ? parent.get() : null,
								child.isPresent() ? child.get() : null
							);
		}
		
		return res;
	}


}
