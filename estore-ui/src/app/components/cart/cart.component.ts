import { Component } from '@angular/core';
import { Rock } from '../../models/Rock';
import { CartService } from '../../services/cart/cart.service';
import { UserService } from '../../services/user/user.service';

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
    public userService: UserService
  ) {
    this.userService = userService;
    this.cartID = this.userService.getSignedInUserId();
  }

  retrieveRocks(): void {
		this.cartService.getRocksFromCart(this.cartID)
			.subscribe(rocks => this.rocks = rocks);
	}

	ngOnInit(): void {
    this.cartID = this.userService.getSignedInUserId();
		this.retrieveRocks();
	}

  removeFromCart(rock_removing_from_cart: number, user_id: number): void {
		this.cartService.removeFromCart(rock_removing_from_cart, user_id).subscribe();
	}
}
