import { Component } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { User } from '../../models/User';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  users: User[] = [];

  constructor(private userService: UserService) {}

  retrieveUsers(): void {
    this.userService.getUsers().subscribe((users) => (this.users = users));
  }

  ngOnInit(): void {
		this.retrieveUsers();
	}

  signUp(username: string, password: string): void {
    username = username.trim();

    if (!username || !password) return;

    const user = { username, password };
    this.userService.addUser(user as User).subscribe((user) => {
      this.users.push(user);
    });
  }

  signIn(username: string, password: string): void {
    username = username.trim();
    password = password.trim();

    if (!username || !password) return;

    this.userService.searchUsers(username).subscribe((users) => (this.users = users));

    this.users.forEach(user => { if(username == user.username && password == user.password) {this.userService.signInUser(user.id);}
      
    });
  }
}
