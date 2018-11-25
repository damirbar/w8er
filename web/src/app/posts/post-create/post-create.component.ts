// import { Post } from '../posts.model';
// import { Component, EventEmitter, Output } from "@angular/core";
import { Component } from "@angular/core";
import { NgForm } from '@angular/forms';
import {PostsService} from "../posts.service";

@Component({
  selector: 'app-post-create',
  templateUrl: './post-create.component.html',
  styleUrls: ['./post-create.component.css']
})
export class PostCreateComponent {

  enteredTitle   = "";
  enteredContent = "";
  // @Output() postCreated = new EventEmitter<Post>();

  // onAddPost(postInput: HTMLTextAreaElement) {
  //   // console.dir(postInput);
  //   this.newPost = postInput.value;
  // }
  constructor(public postsService: PostsService) {}

  onAddPost(form: NgForm) {
    // const post: Post = {title: this.enteredTitle, content: this.enteredContent};

    if (form.invalid) {
      return;
    }
    // const post: Post = {title: form.value.title, content: form.value.content};
    // this.postCreated.emit(post);
    this.postsService.addPost(form.value.title, form.value.content);
  }

}
