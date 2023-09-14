package com.springboot.blog.config;


import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Component
@Log4j2
public class SeedData implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {

        if(roleRepository.findByName("ADMIN").isEmpty()) {
            Set<Role> userRoles = new HashSet<>();

            // Seed ROLE
            Role role = new Role();
            role.setName("ADMIN");
            roleRepository.save(role);
            userRoles.add(role);
            log.info("SAVED ROLE: " + role.getName());

            // Seed USER
            User user = new User();
            user.setName("Cong Dat");
            user.setEmail("congdat@gmail.com");
            user.setRoles(userRoles);
            user.setUsername("congdat");
            user.setPassword("$2a$10$EJ0d/FY8wywaEoKsKlJ/TuSuwCq0FtrMrSsoZQ4aT9Y8WywI/RwTq");
            userRepository.save(user);
            log.info("Saved User " + user.getName());

            // Seed Category
            Category category = new Category();
            category.setDescription("Category Desc");
            category.setName("Category 1" + Math.random() * ( 10000 ));
            categoryRepository.save(category);
            log.info("Saved Category" + category.getName());

            // Seed Post
            Post post = new Post();
            post.setDescription("Post DESC");
            post.setCategory(category);
            post.setContent("Content 1" + Math.random() * ( 10000 ));
            post.setTitle("Title 1" + Math.random() * ( 10000 ));
            postRepository.save(post);
            log.info("Saved Post " + post.getTitle());
        }
    }
}
