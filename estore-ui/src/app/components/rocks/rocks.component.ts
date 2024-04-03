import { Component } from '@angular/core';
import { Rock } from '../../models/Rock';
import { RockService } from '../../services/rock/rock.service';
import { UserService } from '../../services/user/user.service';
import { CartService } from '../../services/cart/cart.service';
import { MessageService } from '../../services/message/message.service';

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
		public cartService: CartService,
		private messageService: MessageService
	) {
		this.userService = userService;
		this.cartService = cartService;
		this.messageService = messageService;
	}

	private log(message: string) {
		this.messageService.add(`${message}`);
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
		this.rockService.deleteRock(rock_deleting.id).subscribe(
			() => this.log(`${rock_deleting.name} has been deleted`),
		);
	}

	addToCart(rock_adding_to_cart: Rock, user_id: number): void {
		this.cartService.addToCart(rock_adding_to_cart.id, user_id).subscribe(
			() => this.log(`${rock_adding_to_cart.name} has been added to your cart!`),
			error => this.log(`An error occurred while adding ${rock_adding_to_cart.name} to cart`)
		);
	}
}