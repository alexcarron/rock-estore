import { Component } from '@angular/core';
import { Rock } from '../../models/Rock';
import { CartService } from '../../services/cart/cart.service';


@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent {

  rocks: Rock[] = [];

  constructor(
    private cartService: CartService
  ) {}

  retrieveRocks(): void {
		this.cartService.getRocksFromCart(3)
			.subscribe(rocks => this.rocks = rocks);
	}

	ngOnInit(): void {
		this.retrieveRocks();
	}
  
}
