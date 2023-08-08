package com.hiring.hiring.serviceRest;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;






@Service
public class PostBusinesses implements IServicePost {

    @Autowired
    PostRepository postRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private  SequenceGeneratorService sequenceGeneratorService;

    @Override
    public List<PostDTO> getList() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> mapper.map(post, PostDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public PostDTO getPostById(long idPost) {
        Post post=postRepository.findByIdPost(idPost);
        return mapper.map(post, PostDTO.class);


    }
    @Override
    public PostDTO updatePost(long idPost, PostDTO requestPost) {
        Post post = mapper.map(requestPost, Post.class);
        Post post2=postRepository.findByIdPost(idPost);

        post.setIdPost(idPost);
        post.setIdformation(post2.getIdformation());
        post.setIduser(post2.getIduser());
        post.setDate(post2.getDate());
        Post newPost = postRepository.save(post);
        return mapper.map(newPost, PostDTO.class);


    }
    @Override
    public void deletePost(long idPost) {
        Post post=postRepository.findByIdPost(idPost);
        postRepository.delete(post);


    }
    @Override
    public PostDTO addpost(PostDTO f, long idcreateur, long idPost) {
        Post post = mapper.map(f, Post.class);
        post.setIdPost(Long.parseLong(sequenceGeneratorService.generateSequence(Post.SEQUENCE_NAME)));

        post.setIduser(idcreateur);
        post.setIdformation(idPost);
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        post.setDate(date);

        Post newPost=postRepository.save(post);
        return mapper.map(newPost, PostDTO.class);

    }

    @Override
    public List<PostDTO> getPostByIdFormation(long id) {
        List<Post> posts =postRepository.findByIdformation(id);
        return posts.stream().map(post -> mapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> getAllPostPrivate(Boolean statprivate, long idFormation) {
        List<Post> posts =postRepository.findByStateprivateAndIdformation(statprivate, idFormation);
        return posts.stream().map(post -> mapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO updatePostpublic(long idPost, Boolean statprivate) {

        Post post=postRepository.findByIdPost(idPost);
        post.setStateprivate(statprivate);
        Post newPost = postRepository.save(post);
        return mapper.map(newPost, PostDTO.class);


    }



}

