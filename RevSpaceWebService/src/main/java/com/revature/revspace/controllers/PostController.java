package com.revature.revspace.controllers;

import com.revature.revspace.models.Credentials;
import com.revature.revspace.models.Post;
import com.revature.revspace.models.User;
import com.revature.revspace.services.CredentialsService;
import com.revature.revspace.services.PostService;
import com.revature.revspace.services.UserService;
import com.revature.revspace.services.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class PostController
{
    @Autowired
    PostService pos;
    @Autowired
    UserService us;
//    @Autowired
//    PostService ps;

    @PostMapping(value ="/posts", consumes = "application/json" , produces = "application/json" )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Post> addPost(@RequestBody Post p)
    {
        Post tempPost = pos.add(p);
        return new ResponseEntity<>(tempPost, HttpStatus.OK);
    }
    
//    @GetMapping("/follow/posts/{uId}")
//    public ResponseEntity<List<List<Post>>> getNextTenFolow (@PathVariable(name="uId") String uId){
//        int userId = 0;
//        try {
//            userId = Integer.parseInt(uId);
//        } catch (NumberFormatException e){
//            e.printStackTrace();
//        }
//        User loggedUser = us.get(userId);
//        List<List<Post>> response = new ArrayList<>();
//        if(loggedUser != null){
//            response = pos.pullPostsListFollowing(loggedUser);
//          
//        }else {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//        if(!response.isEmpty()){
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }else {
//            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
//        }
//    }


    //Get Post By ID
    @GetMapping("/posts/{id}")
    public Post getPostById(@PathVariable(name = "id") String id)
    {
        int safeId;
        try
        {
            safeId = Integer.parseInt(id);
        }catch (NumberFormatException e)
        {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Post foundPost = pos.get(safeId);
        if (null != foundPost)
        {
            return foundPost;
        }else
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    //Get Next Ten Posts

    @GetMapping("/full/posts/{id}")
    public ResponseEntity<List<List<Post>>> getNextTen (@RequestHeader("lastPostIdOnThePage") String lastPostIdOnThePage, @PathVariable(name="id") String userId){
        int postId;
        int uId = 0;
        try {
            postId = Integer.parseInt(lastPostIdOnThePage);
            uId = Integer.parseInt(userId);
        } catch (NumberFormatException e){
            postId = -1;
            e.printStackTrace();
        }
        User currentUser = us.get(uId);
        List<List<Post>> response = new ArrayList<>();
        if(postId != -1){
            response = pos.pullPostsList(postId, currentUser);
          
        }else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        if(!response.isEmpty()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }
    
    @GetMapping("/posts/GroupPosts/{groupHeadId}")
    public ResponseEntity<List<Post>> getPostsByGroupId(@PathVariable("groupHeadId") int id)
    {
    	ResponseEntity<List<Post>> res = null;
    	List<Post> tSet = pos.getPostsByGroupId(id);
    	List<Post> pList = new ArrayList<Post>();
    	
    	if(id <= 0 || tSet == null)
    		res = new ResponseEntity<List<Post>>(HttpStatus.NO_CONTENT);
    	else
    	{
    		//pList.add(tSet);
    		res = new ResponseEntity<List<Post>>(tSet, HttpStatus.OK);
    	}
    	
    	return res;
    }
    @GetMapping("/posts/GroupComments/{groupId}")
    public ResponseEntity<List<Post>> getGroupComments(@PathVariable("groupId") int id)
    {
    	ResponseEntity<List<Post>> res = null;
    	List<Post> comments = pos.getCommentsByGroupId(id);
    	
    	if(comments.isEmpty() || comments == null)
    		res = new ResponseEntity<List<Post>>(HttpStatus.NO_CONTENT);
    	else
    		res = new ResponseEntity<List<Post>>(comments, HttpStatus.OK);
    	
    	return res;
    }
    

    //Update Post By ID
    @PutMapping(value = "/posts", consumes = "application/json", produces = "application/json")
    public Post updatePost(@RequestBody Post newPost)
    {
        

            return pos.update(newPost);


    }

    //Delete Post By ID
    @DeleteMapping(value = "/posts/{id}")
    public boolean deletePost(@PathVariable("id") String id)
    {

        int safeId;
        try
        {
            safeId = Integer.parseInt(id);
        }catch (NumberFormatException e)
        {
            safeId = 0;
        }
        return pos.delete(safeId);
    }
}
