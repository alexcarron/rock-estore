import { Component, Input } from '@angular/core';
import { Rock } from '../../models/Rock';
import { ActivatedRoute } from '@angular/router';
import { RockService } from '../../services/rock/rock.service';
import { Location } from '@angular/common';
import { UserService } from '../../services/user/user.service';
import { CartService } from '../../services/cart/cart.service';

@Component({
  selector: 'app-rock-detail',
  templateUrl: './rock-detail.component.html',
  styleUrl: './rock-detail.component.css'
})
export class RockDetailComponent {
	@Input() rock?: Rock;
	
	/**
	 * A temporary field to store the image url to the rock image until rock has image property
	 */
	readonly mockRockImageURL: string = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQVanHjWdijGsoNIMwdyQq4tQQPM-CA3Iv9CDjKa9VHIA&s";

	constructor(
		private route: ActivatedRoute,
		private rockService: RockService,
		private cartService: CartService,
		private location: Location,
		public userService: UserService
	) {}

	ngOnInit(): void {
		this.retrieveRock();
	}

	retrieveRock(): void {
		const id = Number(this.route.snapshot.paramMap.get('id'));
		this.rockService.getRock(id)
			.subscribe(rock => this.rock = rock);
	}

	goBack(): void {
		this.location.back();
	}

	save(): void {
		if (this.rock) {
			this.rockService.updateRock(this.rock)
				.subscribe(() => {});
		}
	}

	addToCart(rock_adding_to_cart: number, user_id: number): void {
		this.cartService.addToCart(rock_adding_to_cart, user_id).subscribe();
	}
}
