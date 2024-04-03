import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class MessageService {
  messages: string[] = [];

  add(message: string) {
    this.messages.push(message);

    setTimeout(() => {
      this.messages = this.messages.filter(m => m !== message);
    }, 5000);
  }

  clear() {
    this.messages = [];
  }
}