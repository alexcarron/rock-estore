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
  private rocksUrl = 'http://localhost:8080/rocks';  // URL to web api
  
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
}
