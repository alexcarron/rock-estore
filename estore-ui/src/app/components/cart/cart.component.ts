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
    private userService: UserService
  ) {
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
  
}
