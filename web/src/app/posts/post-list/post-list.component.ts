import { Post } from '../posts.model';
import { Subscription } from 'rxjs';

// import { Component, OnInit, Input } from '@angular/core';
import {Component, OnDestroy, OnInit} from '@angular/core';
import { PostsService } from "../posts.service";

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.less']
})
export class PostListComponent implements OnInit, OnDestroy {

  // posts = [
  //   {title: 'First Post', content: 'This is the first post\'s content'},
  //   {title: 'Seconds Post', content: 'This is the second post\'s content'},
  //   {title: 'Third Post', content: 'This is the third post\'s content'},
  // ];

  // @Input() posts: Post[] = [];
  posts: Post[] = [];
  private postsSub: Subscription;


  constructor(public postsService: PostsService) {}

  // Option 1 for injecting dependencies: (Option 2 is above)
  // postsService: PostsService;
  //
  // constructor(postsService: PostsService) {
  //   this.postsService = postsService;
  // }

  ngOnInit() {
    this.posts = this.postsService.getPosts();
    this.postsSub = this.postsService.getPostUpdateListener()
      .subscribe((posts: Post[]) => {
        this.posts = posts;
      });
  }

  ngOnDestroy() {
    this.postsSub.unsubscribe();
  }

}
