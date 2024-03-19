import { Component } from '@angular/core';
import { Rock } from '../../models/Rock';
import { RockService } from '../../services/rock/rock.service';
import { UserService } from '../../services/user/user.service';
import { CartService } from '../../services/cart/cart.service';

@Component({
  selector: 'app-rocks',
  templateUrl: './rocks.component.html',
  styleUrl: './rocks.component.css'
})
export class RocksComponent {
	rocks: Rock[] = [];

	constructor(
		private rockService: RockService,
		public userService: UserService,
		public cartService: CartService
	) {
		this.userService = userService;
		this.cartService = cartService;
	}

	retrieveRocks(): void {
		this.rockService.getRocks()
			.subscribe(rocks => this.rocks = rocks);
	}

	ngOnInit(): void {
		this.retrieveRocks();
	}

	add(name: string): void {
		name = name.trim();

		if (!name) return;

		const rock = {name};
		this.rockService.addRock(rock as Rock)
			.subscribe(rock => {
				this.rocks.push(rock);
			});
	}

	delete(rock_deleting: Rock): void {
		this.rocks = this.rocks.filter(rock => rock !== rock_deleting);
		this.rockService.deleteRock(rock_deleting.id).subscribe();
	}

	addToCart(rock_adding_to_cart: number, user_id: number): void {
		this.cartService.addToCart(rock_adding_to_cart, user_id).subscribe();
	}
}