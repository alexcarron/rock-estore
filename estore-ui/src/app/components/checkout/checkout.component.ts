import { Component } from '@angular/core';
import { CartService } from '../../services/cart/cart.service';
import { UserService } from '../../services/user/user.service';
import { MessageService } from '../../services/message/message.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.css'
})
export class CheckoutComponent {
  constructor(
    private cartService: CartService, 
    private userService: UserService,
    private messageService: MessageService,
    private router: Router
  ) {
    this.cartService = cartService;
    this.userService = userService;
    this.messageService = messageService;
  }

  private log(message: string) {
    this.messageService.add(`${message}`);
  }

  clearCart() {
    let id = this.userService.getSignedInUserId();
    this.cartService.clearCart(id).subscribe(
      () => this.log("Checkout: Success!"),
      error => this.log("Checkout: Failed; Rocks in cart exceed inventory stock!")
    );
    this.router.navigate(['/dashboard']);
  }
}
