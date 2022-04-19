package com.revature.revspace.services;

import com.revature.revspace.models.Like;
import com.revature.revspace.repositories.LikeRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService{

    @Autowired
    private LikeRepo likeRepo; // You dont need a getter/setter if you're following the design principle correctly. This should be the only place the repository is used. Don't be pepega that makes me big sad.

	@Override
	public Like addLike(Like obj)
	{
		Like res = null;
		try
		{
			res = likeRepo.save(obj);
		}catch(Exception e)
		{
			
		}
		return res;
	}

	@Override
	public Like updateLike(Like obj) 
	{
		Like res = null;
		try
		{
			if(likeRepo.existsById(obj.getLikeId())) // only update if we have it already
				res = likeRepo.save(obj);
		}catch(Exception e)
		{
			
		}
		return res;
	}

	@Override
	public boolean deleteLike(int id) 
	{
		boolean res = false;
		try
		{
			if(likeRepo.existsById(id))
			{
				likeRepo.deleteById(id);
				res = true;
			}
		}catch(Exception e)
		{
			
		}
		return res;
	}

	@Override
	public List<Like> getAllLikes() 
	{
		List<Like> lList = null;
		
		try
		{
			lList = (List<Like>)likeRepo.findAll();
		}catch(Exception e)
		{
			
		}
		
		return lList;
	}

	@Override
	public Like getLikeById(int id)
	{
		Like res = null;
		
		try
		{
			Optional<Like> temp = likeRepo.findById(id);
			
			if(temp.isPresent())
				res = temp.get();
		}catch(Exception e)
		{
			
		}
		
		return res;
	}
	
	@Override 
	public List<Like> getPostLikes(int id)
	{
		List<Like> repoList = null;
		List<Like> result = null;
		
		
		try
		{
			repoList = (List<Like>) likeRepo.findAll();
			
			if(!repoList.isEmpty())
				result = new ArrayList<Like>();
			for(Like l : repoList)
			{
				if(l.getPostId().getPostId() == id)
				{
					result.add(l);
				}
			}
		}catch(Exception e)
		{
			
		}
		return result;
	}


}
