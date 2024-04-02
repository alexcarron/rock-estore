import { Component, Input } from '@angular/core';
import { Rock } from '../../models/Rock';
import { ActivatedRoute } from '@angular/router';
import { RockService } from '../../services/rock/rock.service';
import { Location } from '@angular/common';
import { UserService } from '../../services/user/user.service';
import { CartService } from '../../services/cart/cart.service';

/**
 * Type to store all information needed for a hat customization option
 */
type Hat = {
	name: string;
	image_url: string;
}
/**
 * Type to store all information needed for a clothing customization option
 */
type Clothing = {
	name: string;
	image_url: string;
}

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

	/**
	 * A temporary field to store all hats with their image urls
	 */
	readonly mockAvailableHats: Hat[] = [
		{ name: "Baseball Cap", image_url: "https://zildjian.com/cdn/shop/products/alchemy-curve-hat-threeforths_grande.png?v=1684246018" },
		{name: "Cowboy Hat", image_url: "https://jillcorbett.com/wp-content/uploads/2020/05/Cowboy-Hats.png"},
	];

	/**
	 * A temporary field to store all clothing options with their image urls
	 */
	readonly mockAvailableClothing: Clothing[] = [
		{ name: "Dwayne", image_url: "https://media.tenor.com/IyweQyb3MhIAAAAj/the-rock-sus.gif" },
		{name: "Blazer", image_url: "https://ae01.alicdn.com/kf/Sba54dde3b8d64be2b9de7f38a8ec9a02b/Men-s-Suit-Jackets-New-Slim-Fit-Business-Casual-Jacket-Fashion-Men-s-Top-Blazer-Masculino.jpg"},
	];

	selectedHatName: string | null = null;
	selectedClothingName: string | null = null;

	constructor(
		private route: ActivatedRoute,
		private rockService: RockService,
		private cartService: CartService,
		private location: Location,
		public userService: UserService
	) {}

	getHatURLFromName(name: string): string | undefined {
		return this.mockAvailableHats.find(hat => hat.name === name)?.image_url;
	}
	getClothingURLFromName(name: string): string | undefined {
		return this.mockAvailableClothing.find(hat => hat.name === name)?.image_url;
	}

	/**
	 * @returns Path to image of rock. Empty string if rock undefined
	 */
	getImagePath(): string {
		if (this.rock !== undefined)
			return this.rockService.getRockImagePath(this.rock);
		else
			return "";
	}

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
