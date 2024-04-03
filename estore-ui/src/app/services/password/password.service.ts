import { Injectable } from '@angular/core';
import shajs from 'sha.js';

@Injectable({
  providedIn: 'root'
})
export class PasswordService {
  
  /**
   * Hashes the password snt by user to be compared to stored password
   * @param password The user provided password
   * @returns The SHA256 hash of the password
   */
  hashPassword(password: string): String{
    return shajs('sha256').update(password).digest('hex');
    
  }
}
