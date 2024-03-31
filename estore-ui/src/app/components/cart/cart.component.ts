import { Component } from '@angular/core';
import { Rock } from '../../models/Rock';
import { CartService } from '../../services/cart/cart.service';
import { UserService } from '../../services/user/user.service';
import { MessageService } from '../../services/message/message.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent {

  rocks: Rock[] = [];
  cartID: number;

  constructor(
    private cartService: CartService,
    public userService: UserService,
    private messageService: MessageService,
    private router: Router
  ) {
    this.userService = userService;
    this.cartID = this.userService.getSignedInUserId();
  }

  private log(message: string) {
    this.messageService.add(`${message}`);
  }

  retrieveRocks(): void {
		this.cartService.getRocksFromCart(this.cartID)
			.subscribe(rocks => this.rocks = rocks);
	}

	ngOnInit(): void {
    this.cartID = this.userService.getSignedInUserId();
		this.retrieveRocks();
	}

  removeFromCart(rock_removing_from_cart: Rock, user_id: number): void {
    this.rocks = this.rocks.filter(rock => rock !== rock_removing_from_cart);
		this.cartService.removeFromCart(rock_removing_from_cart.id, user_id).subscribe();
	}

  calculateCartTotal(): number {
    let total = 0;
    this.rocks.forEach(rock => {
      total += rock.price;
    });
    return total;
  }

  proceedToCheckout(): void {
    if (this.rocks.length == 0) {
      this.log("Cart is empty!");
    }
    else {
      this.router.navigate(['/checkout']);
    }
  }
}
