import { Component } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { User } from '../../models/User';
import { Observable } from 'rxjs';
import { CartService } from '../../services/cart/cart.service';
import { PasswordService } from '../../services/password/password.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  users: User[] = [];
  password: string = '';

  constructor(
    private userService: UserService,
    private cartService: CartService,
    private passwordService: PasswordService
    ) {}

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

      this.cartService.addCart(user.id).subscribe({
        next: () => console.log(`created cart for user id=${user.id}`),
        error: error => console.error('An error occurred while creating the cart: ', error)
      });
    });
  }

  signIn(username: string, password: string): void {
    username = username.trim();
    password = password.trim();

    if (!username || !password) return;

    this.userService.searchUsers(username).subscribe((users) => (this.users = users));

    this.users.forEach(user => {
			if(username == user.username && this.passwordService.hashPassword(password) == user.password) {
        this.userService.signInUser(user);
      }
    });
  }

  generatePassword() {
    this.userService.generatePassword().subscribe({
      next: (newPassword) => {
        console.log(newPassword);
        this.password = newPassword;
      },
      error: (error) => {
        console.error('Error generating password:', error)
      }
    });
  }
}
