import { Injectable } from '@angular/core';
import { User } from '../../models/User';
import { Observable, of } from 'rxjs';
import { MessageService } from '../message/message.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserService {
	private usersUrl = 'http://localhost:8080/users';  // URL to web api
	private passwordUrl = 'http://localhost:8080/password';
	private signedInUserID = -1;
	private signedInUser: User | null = null;

	httpOptions = {
		headers: new HttpHeaders({ 'Content-Type': 'application/json' })
	};

  constructor(
		private http: HttpClient,
		private messageService: MessageService,
		private router: Router
	) { }

	private log(message: string) {
		this.messageService.add(`UserService: ${message}`);
	}

	isUserSignedIn() {
		return this.signedInUserID !== -1;
	}

	/**
	 * @returns True if a user is signed into the app and that user is a customer, otherwise false
	 */
	isCustomerSignedIn() {
		return (
			this.isUserSignedIn() &&
			!this.userIsAdmin()
		)
	}

	/**
	 * @returns True if a user is signed into the app and that user is an admin, otherwise false
	 */
	isAdminSignedIn() {
		return (
			this.isUserSignedIn() &&
			this.userIsAdmin()
		)
	}

	/**
	 * If a user is signed in, logs out user that is currently signed in.
	 * @returns The id of the user that is signed in if a user is signed in, otherwise returns undefined
	 */
	logOutUser(): number | undefined {
		if (this.isUserSignedIn()) {
			let userLoggingOutID = this.signedInUserID;
			this.signedInUserID = -1;
			this.signedInUser = null;

			this.log(`Logged out user w/ id=${userLoggingOutID}`);
			return userLoggingOutID;
		}
		else {
			return undefined;
		}
	}

	signInUser(user: User) : void{
		this.signedInUserID = user.id;
		this.signedInUser = user;
		this.log(`Signed in user w/ id=${this.signedInUserID}`);
		catchError(this.handleError('signInUser'));

		this.router.navigate(['/dashboard']);
	}

	/**
	 * Updates cached user stored in user service so we don't have to make an HTTP request to get user info
	 */
	updateUserCache(user: User) {
		this.signedInUser = user;
	}

	/**
	 * Updates the password of the currently signed in user
	 * @param password The new password the signed in user will have
	 * @returns new User object if succesfully updated password, otherwise null
	 */
	updatePassword(password: string): Observable<User> | null {
		console.log({password});
		console.log({user: this.signedInUser});
		if (this.signedInUser !== null) {
			const new_user_object: User = {
				id: this.signedInUser.id,
				username: this.signedInUser.username,
				password: password,
			}

			console.log({new_user_object});

			return this.updateUser(new_user_object);
		}

		return null;
	}

	getSignedInUser(): User | null {
		return this.signedInUser;
	}

	getSignedInUserId(): number{
		return this.signedInUserID;
	}

	userIsAdmin(): boolean {
		return this.signedInUserID === 0;
	}

	getUsers(): Observable<User[]> {
		return this.http.get<User[]>(this.usersUrl)
			.pipe(
				tap(() => this.log('fetched users')),
				catchError(
					this.handleError<User[]>('getUsers', [])
				)
			)
	}

	getUser(id: number): Observable<User> {
		const url = `${this.usersUrl}/${id}`;

		return this.http.get<User>(url)
			.pipe(
				tap(() => this.log(`fetched user id=${id}`)),
				catchError(this.handleError<User>(`getUser id=${id}`))
			);
	}

	updateUser(user: User): Observable<any> {
		return this.http.put(
			this.usersUrl,
			user,
			this.httpOptions
		)
		.pipe(
			tap(() => this.log(`updated user id=${user.id}`)),
			catchError(this.handleError<any>('updateUser')),
		)
	}

	addUser(user: User): Observable<User> {
		return this.http.post<User>(
			this.usersUrl,
			user,
			this.httpOptions
		)
		.pipe(
			tap((newUser: User) => this.log(`added user w/ id=${newUser.id}`)),
			catchError(this.handleError<User>('addUser')),
		)
	}

	deleteUser(id: number): Observable<User> {
		const url = `${this.usersUrl}/${id}`

		return this.http.delete<User>(url, this.httpOptions)
			.pipe(
				tap(() => this.log(`deleted user id=${id}`)),
				catchError(this.handleError<User>('deleteUser')),
			)
	}

	/* GET users whose name contains search term */
	searchUsers(search_term: string): Observable<User[]> {
		if (!search_term.trim()) {
			// if not search term, return empty user array.
			return of([]);
		}

		return this.http.get<User[]>(
			`${this.usersUrl}/?name=${search_term}`
		)
		.pipe(
			tap(users => {
				if (users.length)
					this.log(`found users matching "${search_term}"`);
				else
					this.log(`no users matching "${search_term}"`);
			}),
			catchError(this.handleError<User[]>('searchUsers', []))
		);
	}

	generatePassword(): Observable<string> {
		return this.http.get(this.passwordUrl, {responseType: 'text'})
			.pipe(
				tap(() => this.log(`generated password successfully`)),
				catchError(
					this.handleError<string>('generate password failure')
				)
			)
	}

	/**
	 * Handle Http operation that failed.
	 * Let the app continue.
	 *
	 * @param operation - name of the operation that failed
	 * @param result - optional value to return as the observable result
	 */
	private handleError<Type>(operation = 'operation', result?: Type) {
		return (error: any): Observable<Type> => {

			// TODO: send the error to remote logging infrastructure
			console.error(error); // log to console instead

			// TODO: better job of transforming error for user consumption
			this.log(`${operation} failed: ${error.message}`);

			// Let the app keep running by returning an empty result.
			return of(result as Type);
		};
	}
}
