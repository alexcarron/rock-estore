import { Injectable } from '@angular/core';
import shajs from 'sha.js';

@Injectable({
  providedIn: 'root'
})
export class PasswordService {
  

  hashPassword(password: string): String{
    return shajs('sha256').update(password).digest('hex');
    
  }
}
