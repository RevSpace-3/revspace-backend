package com.revature.revspace.models.httpmsgmodel;

import java.util.List;

import com.revature.revspace.models.GroupPost;
import com.revature.revspace.models.Like;
import com.revature.revspace.models.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GroupPostMsg 
{
	private int postId;
	private String body;
	private String datePosted;
	
	// Like logic
	private int numOfLikes;
	private boolean isComment;
	
	// References
	private User owner; //Ideally we do the same thing the User model but I'm not going to focus on that.
	private int parentId;
	private int childId;
	
	// Like logic -> To Do
	private int[] likeIds;
	
	public GroupPostMsg(GroupPost post) // 'Copy' Constructor, converting the model for a better json msg to pass
	{
		this(post.getPostId(), post.getBody(), post.getDatePosted(), post.getNumOfLikes(), post.isComment(), // Ref Ids are below
				post.getOwner(), 												
				post.getParent() != null ? post.getParent().getPostId() : -1, // -1 because its null.
				post.getChild() != null ? post.getChild().getPostId(): -1, 
				getIdsFromLikeModel(post.getLikes())); 
	}
	
	// Conversion Helper function. I could do this in a lambda but thats harder to read.
	private static int[] getIdsFromLikeModel(List<Like> likes)
	{
		if(likes == null || likes.isEmpty())
			return null;
	
		int[] resultArray = new int[likes.size()];
		
		for(int i = 0; i < likes.size(); i++)
		{
			resultArray[i] = likes.get(i).getLikeId();
		}
		
		return resultArray;
	}
}
