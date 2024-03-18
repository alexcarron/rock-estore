import { Injectable } from '@angular/core';
import { User } from '../../models/User';
import { Observable, of } from 'rxjs';
import { MessageService } from '../message/message.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {
	private usersUrl = 'http://localhost:8080/users';  // URL to web api
	private signedInUserID = -1;
	httpOptions = {
		headers: new HttpHeaders({ 'Content-Type': 'application/json' })
	};

  constructor(
		private http: HttpClient,
		private messageService: MessageService
	) { }

	private log(message: string) {
		this.messageService.add(`UserService: ${message}`);
	}

	signInUser(id: number) : void{
		this.signedInUserID = id;
		this.log(`Signed in user w/ id=${this.signedInUserID}`);
		catchError(this.handleError('signInUser'));
	}

	getSignedInUser(): Observable<User>{
		return this.getUser(this.signedInUserID);
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