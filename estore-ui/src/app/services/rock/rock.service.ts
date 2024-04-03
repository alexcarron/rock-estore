import { Injectable } from '@angular/core';
import { Rock } from '../../models/Rock';
import { Observable, of } from 'rxjs';
import { MessageService } from '../message/message.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RockService {
	private rocksUrl = 'http://localhost:8080/rocks';  // URL to web api
	httpOptions = {
		headers: new HttpHeaders({ 'Content-Type': 'application/json' })
	};
	private static readonly ROCK_IMAGES_DIRECTORY = "/assets/images/rocks";

  constructor(
		private http: HttpClient,
		private messageService: MessageService
	) { }

	private log(message: string) {
		this.messageService.add(`${message}`);
	}

	/**
	 * Gets the path to the image of a rock
	 * @param rock Rock you want the image url path of
	 * @returns Image url path of rock
	 */
	getRockImagePath(rock: Rock): string {
		return `${RockService.ROCK_IMAGES_DIRECTORY}/${rock.image_url}`;
	}

	getRocks(): Observable<Rock[]> {
		return this.http.get<Rock[]>(this.rocksUrl)
			.pipe(
				tap(() => this.log('Fetched rocks')),
				catchError(
					this.handleError<Rock[]>('getRocks', [])
				)
			)
	}

	getRock(id: number): Observable<Rock> {
		const url = `${this.rocksUrl}/${id}`;

		return this.http.get<Rock>(url)
			.pipe(
				tap(() => this.log(`Fetched rock id=${id}`)),
				catchError(this.handleError<Rock>(`getRock id=${id}`))
			);
	}

	updateRock(rock: Rock): Observable<any> {
		return this.http.put(
			this.rocksUrl,
			rock,
			this.httpOptions
		)
		.pipe(
			tap(() => this.log(`Updated rock id=${rock.id}`)),
			catchError(this.handleError<any>('updateRock')),
		)
	}

	addRock(rock: Rock): Observable<Rock> {
		return this.http.post<Rock>(
			this.rocksUrl,
			rock,
			this.httpOptions
		)
		.pipe(
			catchError(this.handleError<Rock>('addRock')),
		)
	}

	deleteRock(id: number): Observable<Rock> {
		const url = `${this.rocksUrl}/${id}`

		return this.http.delete<Rock>(url, this.httpOptions)
			.pipe(
				tap(() => this.log(`Deleted rock id=${id}`)),
				catchError(this.handleError<Rock>('deleteRock')),
			)
	}

	/* GET rocks whose name contains search term */
	searchRocks(search_term: string): Observable<Rock[]> {
		if (!search_term.trim()) {
			// if not search term, return empty rock array.
			return of([]);
		}

		return this.http.get<Rock[]>(
			`${this.rocksUrl}/?name=${search_term}`
		)
		.pipe(
			tap(rocks => {
				if (rocks.length)
					this.log(`Found rocks matching "${search_term}"`);
				else
					this.log(`No rocks matching "${search_term}"`);
			}),
			catchError(this.handleError<Rock[]>('searchRocks', []))
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
