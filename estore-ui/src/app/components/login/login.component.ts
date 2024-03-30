import { Component } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { User } from '../../models/User';
import { Observable } from 'rxjs';
import { CartService } from '../../services/cart/cart.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  users: User[] = [];

  constructor(
    private userService: UserService,
    private cartService: CartService,
    private toastr: ToastrService
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

    this.users.forEach(user => { if(username == user.username && password == user.password) {
      this.userService.signInUser(user.id);
      //this.showSuccess();
    }
    });
  }

  showSuccess() {
    this.toastr.success('You have successfully signed in!', 'Success!');
  }
}
