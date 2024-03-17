import { Injectable } from '@angular/core';
import { Rock } from '../../models/Rock';
import { Observable, of } from 'rxjs';
import { MessageService } from '../message/message.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartUrl = 'http://localhost:8080/cart';  // URL to web api

  httpOptions = {
		headers: new HttpHeaders({ 'Content-Type': 'application/json' })
	};

  constructor(
    private http: HttpClient,
    private messageService: MessageService
  ) { }

  private log(message: string) {
    this.messageService.add(`CartService: ${message}`);
  }

    getRocksFromCart(id: number): Observable<Rock[]> {
		const url = `${this.cartUrl}/${id}`;

		return this.http.get<Rock[]>(url)
			.pipe(
				tap(() => this.log(`fetched rocks from cart ${id}`)),
				catchError(this.handleError<Rock[]>(`getRocksFromCart id=${id}`))
			);
	}

	addToCart(rock_updating: number, id: number): Observable<any> {
		const url = `${this.cartUrl}`;
		let adding = true;
		const payload = { rock_updating, id, adding };

		return this.http.put(url, payload, this.httpOptions)
			.pipe(
				tap(() => this.log(`adding rock id=${rock_updating} to user id=${id}`)),
				catchError(this.handleError<any>(`addToCart rock id=${rock_updating} user id=${id}`))
			);
	}

	removeFromCart(rock_updating: number, id: number): Observable<any> {
		const url = `${this.cartUrl}`;
		let adding = false;
		const payload = { rock_updating, id, adding };

		return this.http.put(url, payload, this.httpOptions)
			.pipe(
				tap(() => this.log(`removing rock id=${rock_updating} from user id=${id}`)),
				catchError(this.handleError<any>(`removeFromCart rock id=${rock_updating} user id=${id}`))
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
