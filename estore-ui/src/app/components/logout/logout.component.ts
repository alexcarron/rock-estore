import { Component } from '@angular/core';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrl: './logout.component.css'
})
export class LogoutComponent {
	// Temporary mock variable to maintain state of user logged in or not
	isUserLoggedIn: boolean = true;

	constructor(
		// Should pass service which manages log in state
	) {
		// Initialize service here
	}

	isLoggedIn(): boolean {
		// TODO: Implement way to check if user is current logged in
		return this.isUserLoggedIn;
	}

	logOut() {
		// TODO: Implement way to logout
		this.isUserLoggedIn = false;
		console.log("Logged Out User");
	}
}
