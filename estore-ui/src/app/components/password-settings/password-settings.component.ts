import { Component } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-password-settings',
  templateUrl: './password-settings.component.html',
  styleUrl: './password-settings.component.css'
})
export class PasswordSettingsComponent {
	constructor(
		private userService: UserService
	) {}

	/**
	 * Changes a user's password to a new password
	 * @param newPassword The password the user wants to change their password to
	 */
	changePassword(newPassword: string) {
		if (newPassword.length > 0) {
			const result = this.userService.updatePassword(newPassword);

			if (result !== null) {
				result.forEach(user => {
					this.userService.updateUserCache(user);
				})
			}
		}
	}

	/**
	 * Determines if a user is logged in
	 * @returns True if user logged in, otherwise false
	 */
	isLoggedIn(): boolean {
		return this.userService.isUserSignedIn();
	}
}
