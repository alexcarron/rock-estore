import { Component } from '@angular/core';
import { UserService } from './services/user/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Party Rockers';
  public userService: UserService;

  constructor(userService: UserService){
    this.userService = userService;
  }
}

