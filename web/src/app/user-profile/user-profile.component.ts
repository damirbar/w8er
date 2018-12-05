import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.less']
})
export class UserProfileComponent implements OnInit {

  public numOfSidenavBtns = 13;
  public isClicked: boolean[] = new Array<boolean>(this.numOfSidenavBtns);
  public oldActive = 0;

  changeActiveBtn(newActive: number) {
      this.isClicked[this.oldActive] = false;
      this.isClicked[newActive]      = true;

      this.oldActive = newActive;
  }


  constructor() { }

  ngOnInit() {
    this.isClicked[0] = true;
  }

}
