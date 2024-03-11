import { Component } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { User } from '../../models/User';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  users: User[] = [];

  constructor(
    private userService: UserService,){}

  

  signUp(username: string, password: string): void{
    username = username.trim();

    if(!username || !password) return;

    const user = {username, password};
		this.userService.addUser(user as User).subscribe(user => {
      this.users.push(user);
    });
  }
}