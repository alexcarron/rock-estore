import { Component } from '@angular/core';
import { UserService } from '../../services/user/user.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrl: './logout.component.css'
})
export class LogoutComponent {
	constructor(
		private userService: UserService
	) {
		this.userService = userService;
	}

	isLoggedIn(): boolean {
		return this.userService.isUserSignedIn();
	}

	logOut() {
		this.userService.logOutUser();
	}
}
