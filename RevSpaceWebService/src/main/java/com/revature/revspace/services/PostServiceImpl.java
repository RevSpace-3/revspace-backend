package com.revature.revspace.services;

import com.revature.revspace.models.Like;
import com.revature.revspace.models.Post;
import com.revature.revspace.models.User;
import com.revature.revspace.repositories.LikeRepo;
import com.revature.revspace.repositories.PostRepo;
import com.revature.revspace.repositories.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.revspace.util.PostDateComparator;

import java.util.*;


@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepo postRepo;

    @Autowired
    LikeRepo likeRepo;
    
    @Autowired
    UserService userRepo;


    @Override
    public PostRepo getRepo() {
        return postRepo;
    }

    @Override
    public Integer getIDFor(Post value) {
        return value.getPostId();
    }
    
    public List<List<Post>> pullPostsListFollowing(User currentUser){
    	List<List<Post>> fullPostList = new ArrayList<>();
    	fullPostList.add(this.postRepo.findByCreatorId(currentUser));
    	for(User following : currentUser.getFollowing()) {
    		fullPostList.add(this.postRepo.findByCreatorId(following));
    	}
    	return fullPostList;
    }

    public List<List<Post>> pullPostsList(int lastPostIdOnThePage, User currentUser)
    {
    	List<Post> fullPostList = new ArrayList<>();
    	
    	User loggedUser = userRepo.get(currentUser.getUserId());
    	
    	fullPostList.addAll(this.postRepo.findByCreatorId(currentUser));
    	
    	for(User following : loggedUser.getFollowing()) {
    		fullPostList.addAll(this.postRepo.findByCreatorId(following));
    	}
        List<Post> sortedCurrentPostsList = fullPostList;
        List<Post> sortedCurrentCommentsList = postRepo.findByCommentTrueOrderByDateAsc();
        List<Like> currentLikesList = (List<Like>) likeRepo.findAll();
        List<Post> responsePostsList = new ArrayList<>();
        List<Post> responseCommentsList = new ArrayList<>();
        List<Post> responseLikesList = new ArrayList<>();

        if (lastPostIdOnThePage == 0) {

            if (10 < sortedCurrentPostsList.size()) {

                responsePostsList = sortedCurrentPostsList.subList(0, 10);

            } else {

                responsePostsList = sortedCurrentPostsList.subList(0, sortedCurrentPostsList.size());
            }
        } else {
            for (Post post : sortedCurrentPostsList) {
                if (post.getPostId() == lastPostIdOnThePage) {
                    int index = sortedCurrentPostsList.indexOf(post);

                    if((index + 11) < sortedCurrentPostsList.size()){
                        responsePostsList = sortedCurrentPostsList.subList(index + 1, index + 11);
                    }else {
                        responsePostsList = sortedCurrentPostsList.subList(index + 1, sortedCurrentPostsList.size());
                    }
                }
            }
        }
        for (Post post : responsePostsList) {
            responseCommentsList.addAll(
                    selectedRelatedComments(post, sortedCurrentCommentsList));
        }
        responseCommentsList.sort(new PostDateComparator());
        for (Like like : currentLikesList) {
            for (Post post : responsePostsList) {
                if (post.getPostId() == like.getPostId().getPostId()) {
                    boolean notInList = true;
                    for (Post likePost : responseLikesList) {
                        if (likePost.getPostId() == post.getPostId()) {
                            likePost.setDate(likePost.getDate() + 1);
                            likePost.setCreatorId(like.getUserId());
                            notInList = false;
                            break;
                        }
                    }
                    if (notInList) {
                        Post p = new Post();
                        p.setDate(1);
                        p.setPostId(post.getPostId());
                        p.setCreatorId(like.getUserId());
                        responseLikesList.add(p);
                    }
                }
            }
            for (Post comment : responseCommentsList) {
                if (comment.getPostId() == like.getPostId().getPostId()) {
                    boolean notInList = true;
                    for (Post likePost : responseLikesList) {
                        if (likePost.getPostId() == comment.getPostId()) {
                            likePost.setDate(likePost.getDate() + 1);
                            likePost.setCreatorId(like.getUserId());
                            notInList = false;
                            break;
                        }
                    }
                    if (notInList) {
                        Post p = new Post();
                        p.setDate(1);
                        p.setCreatorId(like.getUserId());
                        p.setPostId(comment.getPostId());
                        responseLikesList.add(p);
                    }
                }
            }
        }
        List<List<Post>> response = new ArrayList<>();

        response.add(responsePostsList);
        response.add(responseCommentsList);
        response.add(responseLikesList);
        return response;
    }
    
    public List<Post> selectedRelatedComments(Post parentsPost, List<Post> allComments) {
        List<Post> childrenComments = new ArrayList<>();
        for (Post comment : allComments) {
            if (parentsPost == comment.getParentPost()) {
                childrenComments.add(comment);
            }
        }
        allComments.removeAll(childrenComments);
        List<Post> childrenOfChildren = new ArrayList<>();
        for (Post parentsComment : childrenComments) {
            childrenOfChildren.addAll(selectedRelatedComments(parentsComment, allComments));
        }
        childrenComments.addAll(childrenOfChildren);
        return childrenComments;
    }
    
    // Since they didnt comment anything I'll help you out...
    // These posts work like a linkedlist/tree. We have a head pointer to the parent and they're daisy chained together
    // So in this function I'm passing the postID of the head of my group. Iterating through and creating a list of all
    
    public List<Post> getPostsByGroupId(int id)
    {
    	List<Post> result = null; // Our result list
    	List<Post> rList = null; // Our list returned by the repository.
    	
    	try
    	{
    		rList = (List<Post>)postRepo.findAll(); // Get all posts, this is slow and gross but I'm not sure how to approach this otherwise.
    		result = new ArrayList<Post>();
    		
    		for(Post p : rList) // Iterate through
    		{
    			Post temp = findHead(p); // Find the head of the list
				
				if(temp.getPostId() == id && !p.isComment()) // If the id matches our group then, create a list and store the path
				{
		    		Set<Post> path = new HashSet<Post>(); // Its a set so we can prevent duplicate adds.
		    		
		    		getPathToHead(p, path);		// Create a list leading to the head
		    		result.addAll(path); 	// Add all our chained nodes to our result
				}
    		}
    		
    		//result = removeDuplicatePaths(result);
    	}catch(Exception e)
    	{
    		
    	}
    	
    	return result;
    }
    // -------------- UPDATE ME!
    public List<Post> getCommentsByGroupId(int id)
    {
    	List<Post> result = null; // Our result list
    	List<Post> rList = null; // Our list returned by the repository.
    	
    	try
    	{
    		rList = (List<Post>)postRepo.findAll(); // Get all posts, this is slow and gross but I'm not sure how to approach this otherwise.
    		result = new ArrayList<Post>();
    		
    		for(Post p : rList) // Iterate through
    		{
    			Post temp = findHead(p); // Find the head of the list
				
				if(temp.getPostId() == id && p.isComment()) // If the id matches our group then, create a list and store the path
				{
		    		Set<Post> path = new HashSet<Post>(); // Its a set so we can prevent duplicate adds.
		    		
		    		//getPathToHead(p, path);		// Create a list leading to the head
		    		result.add(p); 	// Add all our chained nodes to our result
				}
    		}
    		
    		//result = removeDuplicatePaths(result);
    	}catch(Exception e)
    	{
    		
    	}
    	
    	return result;
    }
    private List<List<Post>> removeDuplicatePaths(List<List<Post>> resultList)
    {
    	List<List<Post>> result = new ArrayList<List<Post>>();
    	
    	// iterate through 
    	for(int i = 0; i < resultList.size(); i++)
    	{
    		List<Post> tList = resultList.get(i);
    		
    		for(int j = resultList.size(); j > i; j--)
    		{
    			List<Post> compare = resultList.get(j);
    			
    			if(compare.size() < tList.size())
    			{
    				if(tList.containsAll(compare) && !tList.isEmpty() && !compare.isEmpty())
    					result.add(tList);	
    			}
    			else if(!compare.isEmpty() && !tList.isEmpty())
    			{
    				if(compare.containsAll(tList))
    					result.add(compare);	
    			}
    		}
    	}
    	return result;
    }
    
    // Recursive helper method, I'm moving through the parents looking for the head and keeping track of the object I hit on the way...
    private Post getPathToHead(Post post, Set<Post> result)
    {
    	if(post.getParentPost() != null)
    	{
    		result.add(post);
    		return getPathToHead( post.getParentPost(), result );
    	}
    	else
    		return post;
    }
    // This is what we use to check if we need to create a list, otherwise I'm ditching a bunch of posts to the gc for no reason
    private Post findHead(Post post)
    {
    	if(post.getParentPost() != null)
    		return findHead(post.getParentPost());
    	else
    		return post;
    }

}
